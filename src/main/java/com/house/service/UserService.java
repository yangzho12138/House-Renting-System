package com.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.google.common.collect.*;
import com.house.common.Page;
import com.house.common.Result;
import com.house.dao.*;
import com.house.dao.UserDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.HouseStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.*;
import com.house.pojo.User;
import com.house.utils.ConvertUtil;
import com.house.utils.PageUtil;
import com.house.vo.PasswordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class UserService {

	private final UserDao userDao;

	//TODO 后面这些操作全部改为服务，否则不符合层次规范
	private final OwnerDao ownerDao;

	private final RenterDao renterDao;

	private final NoticeDao noticeDao;

	private final HouseDao houseDao;

	private final HouseRentRelationDao houseRentRelationDao;

	private final LoginService loginService;

	public UserService(UserDao userDao, OwnerDao ownerDao, RenterDao renterDao, NoticeDao noticeDao, HouseDao houseDao, HouseRentRelationDao houseRentRelationDao, LoginService loginService) {
		this.userDao = userDao;
		this.ownerDao = ownerDao;
		this.renterDao = renterDao;
		this.noticeDao = noticeDao;
		this.houseDao = houseDao;
		this.houseRentRelationDao = houseRentRelationDao;
		this.loginService = loginService;
	}

	public User getUserByPhone(String phone){
		List<User> users = userDao.select(ImmutableMap.of("phone", phone));
		if (users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	@Transactional
	public Page findUserByPage(Map<String, Object> params) {
		Integer totalCount = userDao.count(params);
		PageUtil.addPageParams(params);
		return new Page(userDao.select(params), totalCount,
				(Integer) params.get("pageSize"),
				(Integer) params.get("currPage"));
	}

	public void updatePassword(PasswordVO passwordVO){
		if(passwordVO == null){
			throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", passwordVO.getUserId());
		params.put("password", passwordVO.getOldPassword());
		List<User> users = userDao.select(params);
		if(users.size() < 1){
			throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
		}
		User user = users.get(0);
		user.setPassword(passwordVO.getNewPassword());
		try{
			int effectedNum = userDao.update(user);
			if(effectedNum < 1){
				throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
			}
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}

	public void addUser(User user) {
		if(user == null){
			throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
		}
		try{
			int effectedNum = userDao.insert(user);
			if(effectedNum < 1){
				throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
			}
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}


	@Transactional
	public void updateUser(User user) {
        if(user == null || user.getId() == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        boolean flag = false;
        String oldPhone = "";
		if (!StringUtils.isEmpty(user.getPhone())){
			flag = true;
			Map<String, Object> params = new HashMap<>();
			params.put("id", user.getId());
			User oldUser = userDao.select(params).get(0);
			if (oldUser == null)
				return;
			oldPhone = oldUser.getPhone();
		}
        try{
            int effectedNum = userDao.update(user);
            if (flag){
            	// 更新用户信息操作需要注意用户电话号码是否更改
				// 电话信息如果发生更改需要同步更改租户，房主及通知信息电话号码
				Owner owner = new Owner();
				owner.setOwnerId(user.getId());
				owner.setPhone(user.getPhone());
				ownerDao.update(owner);

				Renter renter = new Renter();
				renter.setRenterId(user.getId());
				renter.setPhone(user.getPhone());
				renterDao.update(renter);

				Notice notice = new Notice();
				notice.setPhone(user.getPhone());
				notice.setUserId(user.getId());
				noticeDao.updateByUserId(notice);
			}
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
	}

    @Transactional
    public void deleteUser(Integer userId) {
        if(userId == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        //删除用户操作需要注意目前用户身份
		//1. 用户是房主，该房主名下房产有还在租期内的房产，无法删除用户
		//2. 用户是租户，该租户租的房产还在租期内，需要用户强制确认退租并调用 deleteUserForce 方法删除用户
		List<House> houses = houseDao.select(ImmutableMap.of("ownerId", userId));
		long count = houses.stream().filter(house -> {
			return HouseStatusEnum.Rented.getCode().equals(house.getStatus());
		}).count();
		if (count > 0){
			throw new OperationException(ExceptionEnum.OWNER_HOUSE_RENTED);
		}
		List<HouseRentRelation> houseRentRelations = houseRentRelationDao.select(ImmutableMap.of("renterId", userId));
		count = houseRentRelations.size();
		if (count > 0){
			throw new OperationException(ExceptionEnum.RENTED_HOUSE_NOT_OUT_DATE);
		}
        deleteUserForce(userId);
    }

    @Transactional
    public void deleteUserForce(Integer userId){
		//强制删除用户信息，仅用户无租房或无被租房允许进行删除
		//1. 删除 user 表中用户信息
		//TODO 2. 删除 redis 缓存中的用户登录信息
		//3. 删除 owner，renter 表中身份信息
		//4. 删除名下房产 house 信息
		//5. 删除通知该用户信息
		ImmutableList<Integer> ids = ImmutableList.of(userId);
		try{
			userDao.delete(ids);
			ownerDao.delete(ids);
			renterDao.delete(ids);
			houseDao.delete(houseDao.select(ImmutableMap.of("ownerId", userId))
					.stream().map(House::getId).collect(Collectors.toList()));
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}

	@Transactional
    public void register(User user) {
		try{
			Integer insert = userDao.insert(user);
			if (insert < 1){
				throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION, "用户注册失败");
			}
		} catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, "用户注册失败");
		}
		loginService.doLogin(ConvertUtil.convert(user));
    }
}

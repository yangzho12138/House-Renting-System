package com.house.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.google.common.collect.*;
import com.house.common.Constant;
import com.house.common.Page;
import com.house.component.RedisCache;
import com.house.dao.UserDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.HouseStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.*;
import com.house.pojo.User;
import com.house.service.*;
import com.house.utils.ConvertUtil;
import com.house.utils.PageUtil;
import com.house.vo.PasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	private final HouseOwnerService houseOwnerService;

	private final RenterService renterService;

	private final NoticeService noticeService;

	private final HouseService houseService;

	private final RentService rentService;

	private final LoginService loginService;

	private final RedisCache redisCache;

	public UserServiceImpl(UserDao userDao, HouseOwnerService houseOwnerService, RenterService renterService, NoticeService noticeService, HouseService houseService, RentService rentService, LoginService loginService, RedisCache redisCache) {
		this.userDao = userDao;
		this.houseOwnerService = houseOwnerService;
		this.renterService = renterService;
		this.noticeService = noticeService;
		this.houseService = houseService;
		this.rentService = rentService;
		this.loginService = loginService;
		this.redisCache = redisCache;
	}


	@Override
	public User getUserByPhone(String phone){
		List<User> users = userDao.select(ImmutableMap.of("phone", phone));
		if (users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	@Override
	public Page<User> findUserByPage(Map<String, Object> params) {
		Integer totalCount = userDao.count(params);
		PageUtil.addPageParams(params);
		return new Page<User>(userDao.select(params), totalCount,
				(Integer) params.get("pageSize"),
				(Integer) params.get("currPage"));
	}

	@Override
	public void updatePassword(PasswordVO passwordVO){
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

	@Override
	public void addUser(User user) {
		try{
			int effectedNum = userDao.insert(user);
			if(effectedNum < 1){
				throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
			}
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}


	@Override
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
				houseOwnerService.updateOwner(owner);

				Renter renter = new Renter();
				renter.setRenterId(user.getId());
				renter.setPhone(user.getPhone());
				renterService.updateRenter(renter);

				Notice notice = new Notice();
				notice.setPhone(user.getPhone());
				notice.setUserId(user.getId());
				noticeService.updateByUserId(notice);
			}
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
	}

    @Override
	@Transactional
    public void deleteUser(Integer userId) {
        if(userId == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        //删除用户操作需要注意目前用户身份
		//1. 用户是房主，该房主名下房产有还在租期内的房产，无法删除用户
		//2. 用户是租户，该租户租的房产还在租期内，需要用户强制确认退租并调用 deleteUserForce 方法删除用户
		List<House> houses = houseService.findHouseListByCondition(ImmutableMap.of("ownerId", userId));
		long count = houses.stream().filter(house -> {
			return HouseStatusEnum.Rented.getCode().equals(house.getStatus());
		}).count();
		if (count > 0){
			throw new OperationException(ExceptionEnum.OWNER_HOUSE_RENTED);
		}
		List<HouseRentRelation> houseRentRelations = rentService.getRentRelationByRenterId(ImmutableMap.of("renterId", userId));
		count = houseRentRelations.size();
		if (count > 0){
			throw new OperationException(ExceptionEnum.RENTED_HOUSE_NOT_OUT_DATE);
		}
        deleteUserForce(userId);
    }

    @Override
	@Transactional
    public void deleteUserForce(Integer userId){
		//强制删除用户信息，仅用户无租房或无被租房允许进行删除
		//1. 删除 user 表中用户信息
		User user = userDao.select(ImmutableMap.of("id", userId)).get(0);
		redisCache.deleteObject(Constant.REDIS_TOKEN_PREFIX + user.getPhone());
		redisCache.deleteObject(Constant.REDIS_USER_INFO_PREFIX + user.getPhone());
		//3. 删除 owner，renter 表中身份信息
		//4. 删除名下房产 house 信息
		//5. 删除通知该用户信息
		ImmutableList<Integer> ids = ImmutableList.of(userId);
		try{
			userDao.delete(ids);
			houseOwnerService.deleteOwner(userId);
			renterService.deleteRenter(userId);
			houseService.deleteHouses(houseService.findHouseListByCondition(ImmutableMap.of("ownerId", userId))
					.stream().map(House::getId).collect(Collectors.toList()));
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}

	@Override
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

package com.house.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.google.common.collect.*;
import com.house.common.Page;
import com.house.component.BCryptPasswordEncoderUtil;
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
import com.house.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	private final HouseOwnerService houseOwnerService;

	private final BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

	private final RenterService renterService;

	private final NoticeService noticeService;

	private final HouseService houseService;

	private final RentService rentService;

	private final RedisCache redisCache;

	private final PaymentRecordService paymentRecordService;

	public UserServiceImpl(UserDao userDao, HouseOwnerService houseOwnerService, BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil, RenterService renterService, NoticeService noticeService, HouseService houseService, RentService rentService, RedisCache redisCache, PaymentRecordService paymentRecordService) {
		this.userDao = userDao;
		this.houseOwnerService = houseOwnerService;
		this.bCryptPasswordEncoderUtil = bCryptPasswordEncoderUtil;
		this.renterService = renterService;
		this.noticeService = noticeService;
		this.houseService = houseService;
		this.rentService = rentService;
		this.redisCache = redisCache;
		this.paymentRecordService = paymentRecordService;
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
	public Page<UserVO> findUserByPage(Map<String, Object> params) {
		Integer totalCount = userDao.count(params);
		PageUtil.addPageParams(params);
		List<User> users = userDao.select(params);
		List<UserVO> userVOS = users.stream().map(ConvertUtil::convertToVo).collect(Collectors.toList());
		return new Page<UserVO>(userVOS, totalCount,
				(Integer) params.get("pageSize"),
				(Integer) params.get("currPage"));
	}

	@Override
	public void updatePassword(PasswordVO passwordVO){
		Map<String, Object> params = new HashMap<>();
		params.put("phone", passwordVO.getPhone());
		List<User> users = userDao.select(params);
		if(users.size() < 1){
			throw new OperationException(ExceptionEnum.USER_NOT_FOUND_OR_PASSWORD_WRONG);
		}
		User user = users.get(0);
		if (!bCryptPasswordEncoderUtil.matches(passwordVO.getOldPassword(), user.getPassword())){
			throw new OperationException(ExceptionEnum.PASSWORD_WRONG_EXCEPTION, "????????????????????????????????????");
		}
		user.setPassword(bCryptPasswordEncoderUtil.encode(passwordVO.getNewPassword()));
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
            	// ??????????????????????????????????????????????????????????????????
				// ??????????????????????????????????????????????????????????????????????????????????????????
				Owner owner = new Owner();
				owner.setOwnerId(user.getId());
				owner.setPhone(user.getPhone());
				houseOwnerService.updateOwnerByAdmin(owner);

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
        //????????????????????????????????????????????????
		//1. ???????????????????????????????????????????????????????????????????????????????????????
		//2. ???????????????????????????????????????????????????????????????????????????????????????????????? deleteUserForce ??????????????????
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
    }

    @Override
	@Transactional
    public void deleteUserForce(Integer userId){
		//??????????????????????????????????????????????????????????????????????????????
		//1. ?????? user ??????????????????
		User user = userDao.select(ImmutableMap.of("id", userId)).get(0);
		//3. ?????? owner???renter ??????????????????
		//4. ?????????????????? house ??????
		//5. ???????????????????????????
		ImmutableList<Integer> ids = ImmutableList.of(userId);
		try{
			userDao.delete(ids);
			houseOwnerService.deleteOwner(userId);
			renterService.deleteRenter(userId);
			rentService.deleteHouseRentRelation(userId);
			houseService.deleteHouses(houseService.findHouseListByCondition(ImmutableMap.of("ownerId", userId))
					.stream().map(House::getId).collect(Collectors.toList()));
			paymentRecordService.findPaymentRecords(userId).forEach(paymentRecord -> {
				paymentRecordService.deletePaymentRecord(paymentRecord.getId());
			});
		}catch (Exception e){
			throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
		}
	}
}

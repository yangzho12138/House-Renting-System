package com.house.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.dao.OwnerDao;
import com.house.enums.ExceptionEnum;
import com.house.enums.HouseStatusEnum;
import com.house.enums.OwnerStatusEnum;
import com.house.exception.OperationException;
import com.house.pojo.House;
import com.house.pojo.Owner;
import com.house.service.HouseOwnerService;
import com.house.service.HouseService;
import com.house.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 房主操作服务
 * @since 2022/5/20
 **/
@Service
public class HouseOwnerServiceImpl implements HouseOwnerService {

    private final OwnerDao ownerDao;

    private final HouseService houseService;

    public HouseOwnerServiceImpl(OwnerDao ownerDao, HouseService houseService) {
        this.ownerDao = ownerDao;
        this.houseService = houseService;
    }

    @Override
    public void addOwner(Owner owner){
        owner.setOwnerId(UserUtil.getUserInfo().getId());
        owner.setPhone(UserUtil.getUserInfo().getPhone());
        owner.setName(UserUtil.getUserInfo().getUsername());
        Integer insert = ownerDao.insert(owner);
        if (insert < 1){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
        }
    }

    @Override
    public void updateOwner(Owner owner){
        try{
            owner.setOwnerId(UserUtil.getUserInfo().getId());
            Integer update = ownerDao.update(owner);
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public void updateOwnerByAdmin(Owner owner) {
        try{
            Integer update = ownerDao.updateByAdmin(owner);
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public void deleteOwner(Integer ownerId){
        try{
            Integer delete = ownerDao.delete(ImmutableList.of(ownerId));
            if (delete < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public Owner getByOwnerId(Integer ownerId) {
        List<Owner> ownerList =
                ownerDao.select(ImmutableMap.of("ownerId", ownerId));
        return ownerList.get(0);
    }

    @Transactional
    @Override
    public void sealHouseOwner(Integer ownerId) {
        try{
            //1. 首先，更新对应的 HouseOwner 的状态，被封控
            Owner owner = new Owner();
            owner.setOwnerId(ownerId);
            owner.setStatus(OwnerStatusEnum.Seal.getCode());
            ownerDao.updateByAdmin(owner);
            //2. 其次，更新名下未出租房产的的状态为封禁
            houseService.findHouseListByCondition(ImmutableMap.of("ownerId", ownerId))
                    .forEach(house -> {
                        if (!HouseStatusEnum.Rented.getCode().equals(house.getStatus())){
                            House newHouse = new House();
                            newHouse.setId(house.getId());
                            newHouse.setStatus(HouseStatusEnum.Sealed.getCode());
                            houseService.updateHouse(newHouse);
                        }
                    });
        } catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
        }
    }


}

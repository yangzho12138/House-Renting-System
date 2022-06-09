package com.house.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.house.dao.OwnerDao;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.Owner;
import com.house.service.HouseOwnerService;
import com.house.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 房主操作服务
 * @since 2022/5/20
 **/
@Service
public class HouseOwnerServiceImpl implements HouseOwnerService {

    private final UserUtil userUtil;

    private final OwnerDao ownerDao;

    public HouseOwnerServiceImpl(OwnerDao ownerDao, UserUtil userUtil) {
        this.ownerDao = ownerDao;
        this.userUtil = userUtil;
    }

    @Override
    public void addOwner(Owner owner){
        owner.setOwnerId(userUtil.getUserInfo().getId());
        Integer insert = ownerDao.insert(owner);
        if (insert < 1){
            throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
        }
    }

    @Override
    public void updateOwner(Owner owner){
        try{
            Integer update = ownerDao.update(owner);
            if (update < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
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
}
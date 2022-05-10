package com.house.service;

import com.house.common.Page;
import com.house.dao.HouseDao;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.House;

import com.house.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseService {

    @Autowired
    private HouseDao houseDao;

    public Page findHouseListByPage(Map<String, Object> params) {
        Integer totalCount = houseDao.count(params);
        List<House> houses =  houseDao.select(PageUtil.addPageParams(params));
        return new Page(houses, totalCount, (Integer) params.get("pageSize"), (Integer) params.get("currPage"));
    }

    public List<House> findHouseListByCondition(Map<String, Object> params){
        return houseDao.select(params);
    }

    public void addHouse(House house) {
        if(house == null){
            throw new OperationException(ExceptionEnum.Parameter_Null_Exception);
        }
        try{
            int effectedNum = houseDao.insert(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.Database_Operation_Exception);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.Database_Connection_Exception);
        }
    }

    public void deleteHouse(Integer houseId) {
        if(houseId == null){
            throw new OperationException(ExceptionEnum.Parameter_Null_Exception);
        }
        try{
            List<Integer> houseIds = new ArrayList<>();
            houseIds.add(houseId);
            int effectedNum = houseDao.delete(houseIds);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.Database_Operation_Exception);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.Database_Connection_Exception);
        }
    }

    public void updateHouse(House house) {
        if(house == null || house.getId() == null){
            throw new OperationException(ExceptionEnum.Parameter_Null_Exception);
        }
        try{
            int effectedNum = houseDao.update(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.Database_Operation_Exception);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.Database_Connection_Exception);
        }
    }


}

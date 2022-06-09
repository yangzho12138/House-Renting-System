package com.house.service;

import com.google.common.collect.ImmutableMap;
import com.house.common.Page;
import com.house.dao.HouseDao;
import com.house.dao.HouseViewDao;
import com.house.enums.ExceptionEnum;
import com.house.exception.OperationException;
import com.house.pojo.House;

import com.house.pojo.HouseView;
import com.house.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HouseService {

    private final HouseDao houseDao;
    private final HouseViewDao houseViewDao;

    public HouseService(HouseDao houseDao, HouseViewDao houseViewDao) {
        this.houseDao = houseDao;
        this.houseViewDao = houseViewDao;
    }

    public Page findHouseListByPage(Map<String, Object> params) {
        Integer totalCount = houseDao.count(params);
        List<House> houses =  houseDao.select(PageUtil.addPageParams(params));
        return new Page(houses, totalCount, (Integer) params.get("pageSize"), (Integer) params.get("currPage"));
    }

    public Page findHouseViewListPage(Map<String, Object> params){
        Integer totalCount = houseViewDao.count(params);
        List<HouseView> houseViews = houseViewDao.select(PageUtil.addPageParams(params));
        return new Page(houseViews,totalCount,(Integer) params.get("pageSize"), (Integer) params.get("currPage"));
    }

    public List<House> findHouseListByCondition(Map<String, Object> params){
        return houseDao.select(params);
    }

    public void addHouse(House house) {
        if(house == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        try{
            int effectedNum = houseDao.insert(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    public void addHouseView(HouseView houseView){
        if(houseView == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }try{
            int effectedNum = houseViewDao.insert(houseView);
            if(effectedNum < 1){
                throw  new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    public void deleteHouse(Integer houseId) {
        if(houseId == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        try{
            List<Integer> houseIds = new ArrayList<>();
            houseIds.add(houseId);
            int effectedNum = houseDao.delete(houseIds);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }

    public void updateHouse(House house) {
        if(house == null || house.getId() == null){
            throw new OperationException(ExceptionEnum.PARAMETER_NULL_EXCEPTION);
        }
        try{
            int effectedNum = houseDao.update(house);
            if(effectedNum < 1){
                throw new OperationException(ExceptionEnum.DATABASE_OPERATION_EXCEPTION);
            }
        }catch (Exception e){
            throw new OperationException(ExceptionEnum.DATABASE_CONNECTION_EXCEPTION);
        }
    }


    public House getHouseById(Integer houseId) {
        List<House> houses = houseDao.select(ImmutableMap.of("id", houseId));
        return houses.get(0);
    }
}

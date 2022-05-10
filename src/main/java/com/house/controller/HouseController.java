package com.house.controller;

import com.house.common.Page;
import com.house.common.Result;
import com.house.pojo.House;
import com.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value="/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "/houseList/page",method = RequestMethod.GET)
    public Result getHouseList(@RequestParam("params")Map<String, Object> params){
        Page page = houseService.findHouseListByPage(params);
        return Result.success("查找房屋信息列表成功", page);
    }

    @RequestMapping(value = "/houseList",method = RequestMethod.GET)
    public Result getHouseListByCondition(@RequestParam("params") Map<String, Object> params){
        return Result.success("按条件查找房屋信息列表成功", houseService.findHouseListByCondition(params));
    }

    @RequestMapping(value="/addHouse",method = RequestMethod.POST)
    public Result addHouse(@RequestBody House house){
        houseService.addHouse(house);
        return Result.success("添加房屋信息成功");
    }

    @RequestMapping(value="/updateHouse",method = RequestMethod.POST)
    public Result updateHouse(@RequestBody House house){
        houseService.updateHouse(house);
        return Result.success("更新房屋信息成功");
    }

    @RequestMapping(value="/deleteHouse",method = RequestMethod.DELETE)
    public Result deleteHouse(@RequestParam("houseId")Integer houseId){
        houseService.deleteHouse(houseId);
        return Result.error("删除房屋信息成功");
    }


}

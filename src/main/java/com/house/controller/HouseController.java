package com.house.controller;

import com.house.common.Page;
import com.house.common.Result;
import com.house.pojo.House;
import com.house.service.HouseService;
import com.house.validate.HouseInsertValidate;
import com.house.validate.HouseUpdateValidate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value="/house")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public Result getHouseList(@RequestParam("params") Map<String, Object> params){
        Page page = houseService.findHouseListByPage(params);
        return Result.success("查找房屋信息列表成功", page);
    }


    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Result addHouse(@Validated({HouseInsertValidate.class}) @RequestBody House house){
        houseService.addHouse(house);
        return Result.success("添加房屋信息成功");
    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public Result updateHouse(@Validated({HouseUpdateValidate.class}) @RequestBody House house){
        houseService.updateHouse(house);
        return Result.success("更新房屋信息成功");
    }

    @RequestMapping(value="/deleteHouse/{houseId}",method = RequestMethod.DELETE)
    public Result deleteHouse(@PathVariable("houseId") Integer houseId){
        houseService.deleteHouse(houseId);
        return Result.error("删除房屋信息成功");
    }


}

package com.house.controller;

import com.house.common.Page;
import com.house.common.Result;
import com.house.pojo.House;
import com.house.pojo.HouseView;
import com.house.service.HouseService;
import com.house.service.RentService;
import com.house.validate.HouseInsertValidate;
import com.house.validate.HouseUpdateValidate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value="/house")
public class HouseController {

    private final HouseService houseService;

    private final RentService rentService;

    public HouseController(HouseService houseService, RentService rentService) {
        this.houseService = houseService;
        this.rentService = rentService;
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public Result getHouseList(@RequestParam("params") Map<String, Object> params){
        Page page = houseService.findHouseListByPage(params);
        return Result.success("查找房屋信息列表成功", page);
    }

    @RequestMapping(value = "/rent/{houseId}")
    public Result rentHouse(@PathVariable(value = "houseId") Integer houseId){
        rentService.rent(houseId);
        return Result.success("房子已被出租，请在三日内进行缴费缴费");
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


    // 根据houseid,renterid，把所有记录搜出来
    @RequestMapping(value="/viewer/select",method = RequestMethod.GET)
    public Result getHouseViewList(@RequestParam("params") Map<String, Object> params){
        Page page = houseService.findHouseViewListPage(params);
        return Result.success("查找租房记录成功", page);
    }

    // 插入 houseid,renterid,detail,star,date
    @RequestMapping(value="/viewer/insert",method = RequestMethod.POST)
    public Result addHouseView(@RequestBody @Valid HouseView houseView){
        houseService.addHouseView(houseView);
        return Result.success("添加租房信息信息成功");
    }
}

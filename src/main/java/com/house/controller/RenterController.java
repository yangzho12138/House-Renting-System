package com.house.controller;

import com.house.common.Result;
import com.house.dao.RenterDao;
import com.house.pojo.Renter;
import com.house.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 租赁者信息管理
 * @since 2022/5/28
 **/
@RestController
@CrossOrigin
@RequestMapping(value = "/renter")
public class RenterController {

    private final RenterService renterService;

    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping(value = "/register")
    public Result register(@Validated @RequestBody Renter renter){
        renterService.register(renter);
        return Result.success("登记补充租赁者信息成功！");
    }
}

package com.house.controller;

import com.house.common.Result;
import com.house.pojo.Renter;
import org.springframework.web.bind.annotation.*;

/**
 * @version 租赁者信息管理
 * @since 2022/5/28
 **/
@RestController
@CrossOrigin
@RequestMapping(value = "/renter")
public class RenterController {

    @PostMapping(value = "/register")
    public Result register(@RequestBody Renter renter){

        return Result.success("登记补充租赁者信息成功！");
    }
}

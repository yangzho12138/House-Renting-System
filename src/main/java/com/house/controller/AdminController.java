package com.house.controller;

import com.house.common.Result;
import com.house.component.BCryptPasswordEncoderUtil;
import com.house.pojo.User;
import com.house.service.HouseOwnerService;
import com.house.service.UserService;
import com.house.validate.UserInsertValidate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 仅有管理员用户有权进行访问的相关接口
 **/
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final HouseOwnerService houseOwnerService;

    private final UserService userService;

    private final BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    public AdminController(HouseOwnerService houseOwnerService, UserService userService, BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil) {
        this.houseOwnerService = houseOwnerService;
        this.userService = userService;
        this.bCryptPasswordEncoderUtil = bCryptPasswordEncoderUtil;
    }

    @PutMapping(value = "/seal/houseOwner/{ownerId}")
    public Result sealHouseOwner(@PathVariable Integer ownerId){
        houseOwnerService.sealHouseOwner(ownerId);
        return Result.success("封控该房主成功");
    }

    @PostMapping(value = "/user/register")
    public Result addUser(@Validated({UserInsertValidate.class}) @RequestBody User user){
        user.setPassword(bCryptPasswordEncoderUtil.encode(user.getPassword()));
        userService.addUser(user);
        return Result.success("添加用户成功");
    }
}

package com.house.controller;

import com.house.common.Result;
import com.house.pojo.Owner;
import com.house.service.HouseOwnerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 房主信息接口
 * @since 2022/5/17
 **/
@CrossOrigin
@RestController
@RequestMapping(value = "/houseOwner")
public class HouseOwnerController {

    private final HouseOwnerService houseOwnerService;

    public HouseOwnerController(HouseOwnerService houseOwnerService) {
        this.houseOwnerService = houseOwnerService;
    }

    /**
     * 登记为房主
     **/
    @PostMapping(value = "/register")
    public Result addHouseOwner(@Validated @RequestBody Owner owner){
        houseOwnerService.addOwner(owner);
        return Result.success("插入房主信息成功");
    }

    /**
     * 普通用户更新房主信息
     **/
    @PutMapping(value = "/update")
    public Result updateHouseOwner(@RequestBody Owner owner){
        owner.setStatus(null);
        houseOwnerService.updateOwner(owner);
        return Result.success("更改房主信息成功");
    }

    @DeleteMapping(value = "/delete/{ownerId}")
    public Result deleteHouseOwner(@PathVariable Integer ownerId){
        if (ownerId == null){
            return Result.error("删除的房主 ID 为空");
        }
        houseOwnerService.deleteOwner(ownerId);
        return Result.success("删除房主 " + ownerId + " 成功");
    }

}

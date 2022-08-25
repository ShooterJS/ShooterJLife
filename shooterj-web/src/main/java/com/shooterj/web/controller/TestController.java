package com.shooterj.web.controller;


import com.shooterj.core.model.UserDto;
import com.shooterj.core.constants.ErrorCodeEnum;
import com.shooterj.core.model.ResponseResult;
import com.shooterj.core.model.User;
import com.shooterj.core.util.MyModelUtil;
import com.shooterj.core.validator.beanvalid.WWValidationUtil;
import com.shooterj.core.validator.group.UpdateGroup;
import com.shooterj.core.version.APIVersion;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

@RestController
@RequestMapping(value = "/")
public class TestController {




    @GetMapping(value = "api/user")
    @APIVersion("v4")
    public int right4() {
        return 4;
    }



    @PostMapping(value = "updateUser")
    public ResponseResult<Void> validateUpdate(@RequestBody UserDto userDto) {
        WWValidationUtil.validateEntity(userDto, UpdateGroup.class);
        MyModelUtil.copyTo(userDto, User.class);
        return ResponseResult.success();
    }



}

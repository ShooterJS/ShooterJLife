package com.shooterj.web.controller;


import com.google.common.collect.Lists;
import com.shooterj.core.apiresponse.APIException;
import com.shooterj.core.constants.ErrorCodeEnum;
import com.shooterj.core.model.ResponseResult;
import com.shooterj.core.model.User;
import com.shooterj.core.model.UserDto;
import com.shooterj.core.util.MyCommonUtil;
import com.shooterj.core.util.MyModelUtil;
import com.shooterj.core.validator.group.UpdateGroup;
import com.shooterj.core.version.APIVersion;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TestController {

    @GetMapping(value = "testResponse")
    public List<String> testResponse(Integer type) {
        if (type == 1) {
            return Lists.newArrayList("1", "2");
        } else {
            throw new APIException(300, "非法字符");
        }
    }


    @GetMapping(value = "api/user")
    @APIVersion("v4")
    public int right4() {
        return 4;
    }



    @PostMapping(value = "updateUser")
    public ResponseResult<Void> validateUpdate(@RequestBody UserDto userDto) {
        String errorMessage = MyCommonUtil.getModelValidationError(userDto, Default.class, UpdateGroup.class);
        if(errorMessage!=null){
            return ResponseResult.error(ErrorCodeEnum.DATA_VALIDATED_FAILED, errorMessage);
        }
        MyModelUtil.copyTo(userDto, User.class);
        return ResponseResult.success();
    }



}

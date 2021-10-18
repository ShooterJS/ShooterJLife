package com.shooterj.controller;

import com.alibaba.fastjson.JSON;
import com.shooterj.annotation.EncryptField;
import com.shooterj.annotation.EncryptMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/8/12
 */
@Slf4j
@RestController
@RequestMapping("/encryptor/")
public class Encryptor {

    @EncryptMethod
    @PostMapping(value = "test")
    @ResponseBody
    public Object testEncrypt(@RequestBody UserVo user, @EncryptField String name) {
        return insertUser(user, name);
    }

    private UserVo insertUser(UserVo user, String name) {
        System.out.println("加密后的数据：user" + JSON.toJSONString(user));
        return user;
    }


}

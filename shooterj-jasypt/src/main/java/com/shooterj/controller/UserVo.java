package com.shooterj.controller;

import com.shooterj.annotation.EncryptField;
import lombok.Data;

import java.io.Serializable;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/8/12
 */
@Data
public class UserVo implements Serializable {

    private Long userId;

    @EncryptField
    private String mobile;

    @EncryptField
    private String address;

    private String age;


}

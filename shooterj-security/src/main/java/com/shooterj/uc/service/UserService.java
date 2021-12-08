package com.shooterj.uc.service;

import com.shooterj.uc.model.UserFacade;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/05 14:01
 **/
public interface UserService {

    UserFacade loadUserByUsername(String username);
}

package com.shooterj.uc.service.impl;

import com.shooterj.uc.dao.UserDao;
import com.shooterj.uc.model.UserFacade;
import com.shooterj.uc.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/05 14:01
 **/
@Primary
@Service
public class UserServiceImpl implements UserService {

    @Lazy
    @Resource
    UserDao userDao;

    @Override
    public UserFacade loadUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}

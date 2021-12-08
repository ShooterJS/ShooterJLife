package com.shooterj.uc.dao;

import com.shooterj.uc.model.UserFacade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/07 10:36
 **/
public interface UserDao {

    UserFacade findByUsername(@Param("username") String username);
}

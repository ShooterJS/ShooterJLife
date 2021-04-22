package com.shooterj.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shooterj.core.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

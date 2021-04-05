package com.winway.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winway.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

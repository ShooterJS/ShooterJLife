package com.shooterj.web.controller;


import com.google.common.collect.Lists;
import com.shooterj.core.model.User;
import com.shooterj.core.apiresponse.APIException;
import com.shooterj.core.version.APIVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TestController {
   /* @Value("${member.nickname}")
    private  String nickname;

    @Value("${member.age}")
    private  Integer age;*/

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

    @GetMapping(value = "testFilter")
    public List<User> testFilter(@RequestBody User user) {
        ArrayList<User> list = Lists.newArrayList();
        list.add(user);
        return list;
    }

}

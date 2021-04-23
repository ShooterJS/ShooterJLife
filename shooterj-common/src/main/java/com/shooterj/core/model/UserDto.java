package com.shooterj.core.model;

import com.alibaba.fastjson.JSON;
import com.shooterj.core.constants.UserType;
import com.shooterj.core.validator.ConstDictRef;
import com.shooterj.core.validator.customer.TextLength;
import com.shooterj.core.validator.group.UpdateGroup;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class UserDto {
    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    private Integer id;
    private Integer age;
    @TextLength(min = 2,max = 5)
    private String name;
    @NotNull(message = "数据验证失败，用户类型不能为空！")
    @ConstDictRef(constDictClass = UserType.class, message = "数据验证失败，用户类型为无效值！")
    private Integer deviceType;

    public static void main(String[] args) {
        System.out.println(JSON.toJSON(UserDto.builder().age(29).name("蒋帅").deviceType(3).build()));
    }
}

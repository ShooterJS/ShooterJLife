package com.shooterj.design.strategy;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmitRequest {
    /**
     * 提交类型
     *
     * @see FormSubmitHandler#getSubmitType()
     */
    private String submitType;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 表单提交的值
     */
    private Map<String, Object> formInput;

    // 其他属性

}

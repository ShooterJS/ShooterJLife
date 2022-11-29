package com.shooterj.design.strategy;

import com.baomidou.mybatisplus.extension.api.R;
import com.shooterj.core.model.ResponseResult;

import java.io.Serializable;

public interface FormSubmitHandler {

    /**
     * 获得提交类型（返回值也可以使用已经存在的枚举类）
     *
     * @return 提交类型
     */
    String getSubmitType();
    /**
     * 处理表单提交请求
     * @param request 请求
     */
    ResponseResult handleSubmit(FormSubmitRequest request);
}

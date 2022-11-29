package com.shooterj.design.strategy;


import com.shooterj.core.constants.ErrorCodeEnum;
import com.shooterj.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class FormService {

    @Autowired
    FormSubmitHandlerFactory submitHandlerFactory;


    public ResponseResult submitForm(FormSubmitRequest request) {
        if (request.getSubmitType() == null) {
            return ResponseResult.error(ErrorCodeEnum.DATA_VALIDATED_FAILED, "提交类型为空");
        }
        FormSubmitHandler handler = submitHandlerFactory.getHandler(request.getSubmitType());
        if (handler == null) {
            return ResponseResult.error(ErrorCodeEnum.DATA_VALIDATED_FAILED, "提交类型非法");
        }
        return handler.handleSubmit(request);

    }

}

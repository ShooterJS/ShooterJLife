package com.shooterj.design.strategy;

import com.shooterj.core.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FormPreviewSubmitHandler implements FormSubmitHandler{
    @Override
    public String getSubmitType() {
        return "preview";
    }

    @Override
    public ResponseResult handleSubmit(FormSubmitRequest request) {
        log.info("预览模式提交：userId={}, formInput={}", request.getUserId(), request.getFormInput());

        return ResponseResult.success(null);
    }
}

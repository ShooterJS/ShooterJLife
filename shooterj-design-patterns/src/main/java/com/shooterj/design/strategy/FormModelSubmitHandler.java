package com.shooterj.design.strategy;

import com.shooterj.core.model.ResponseResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FormModelSubmitHandler implements FormSubmitHandler{


    @Override
    public String getSubmitType() {
        return "model";
    }

    @Override
    public ResponseResult handleSubmit(FormSubmitRequest request) {
        log.info("模型提交：userId={}, formInput={}", request.getUserId(), request.getFormInput());

        // 模型创建成功后获得模型的 id
        Long modelId = createModel(request);
        return ResponseResult.success();
    }

    private Long createModel(FormSubmitRequest request) {
        return 1111L;
    }
}

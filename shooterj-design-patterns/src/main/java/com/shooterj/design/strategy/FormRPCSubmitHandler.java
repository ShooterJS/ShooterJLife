package com.shooterj.design.strategy;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.shooterj.core.model.ResponseResult;
import com.shooterj.core.util.MapUtil;
import com.shooterj.design.pipeline.InstanceBuildContext;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class FormRPCSubmitHandler implements FormSubmitHandler{

    @Override
    public String getSubmitType() {
        return "rpc";
    }

    @Override
    public ResponseResult handleSubmit(FormSubmitRequest request) {
        log.info("rpc 模式提交：userId={}, formInput={}", request.getUserId(), request.getFormInput());
        // 进行 HSF 泛化调用，获得业务方返回的提示信息和业务数据
        ResponseResult< Serializable> response = rpcLitSubmitData(request);
        return null;
    }

    private ResponseResult rpcLitSubmitData(FormSubmitRequest request) {
        //do something
        return ResponseResult.success();
    }

    public static void main(String[] args) {
        InstanceBuildContext context = new InstanceBuildContext();
        context.setUserId(1111);

        System.out.println(JSON.toJSONString(ResponseResult.success(context)));
    }
}

package com.shooterj.design.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 输入校验处理器
 */
@Slf4j
@Component
public class InputDataPreChecker implements ContextHandler<InstanceBuildContext>{

    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("------输入数据校验----");
        Map<String, Object> formInput = context.getFormInput();
        if (CollectionUtils.isEmpty(formInput)) {
            log.error("表单输入数据不能为空");
            return false;
        }

        String instanceName = (String) formInput.get("instanceName");

        if (StringUtils.isBlank(instanceName)) {
            log.error("表单输入数据必须包含实例名称");
            return false;
        }

        return true;
    }

}

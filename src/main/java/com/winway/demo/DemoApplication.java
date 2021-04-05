package com.winway.demo;

import com.winway.demo.version.APIVersionHandlerMapping;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@EnableAsync
@MapperScan("com.winway.demo.mapper")
public class DemoApplication implements WebMvcRegistrations {

    public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 版本号统一处理
     * @return
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new APIVersionHandlerMapping();
    }
}

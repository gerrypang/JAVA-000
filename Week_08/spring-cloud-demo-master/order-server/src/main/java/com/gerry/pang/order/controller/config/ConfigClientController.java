package com.gerry.pang.order.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建ConfigClientController，从Nacos配置中心中获取配置信息
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/config/get")
    public boolean get() {
        return useLocalCache;
    }

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}

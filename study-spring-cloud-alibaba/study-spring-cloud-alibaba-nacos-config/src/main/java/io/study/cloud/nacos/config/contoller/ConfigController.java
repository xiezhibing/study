package io.study.cloud.nacos.config.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    // 注入配置文件上下文
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    // 从上下文中读取配置
    @GetMapping(value = "/config/hi")
    public String sayHi() {
        return "Hello " + applicationContext.getEnvironment().getProperty("user.name");
    }
}
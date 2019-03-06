package io.study.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * <pre>
 * 动态刷新配置：
 * 1. pom中引用spring-boot-starter-actuator
 * 2. bootstrap.yml中配置：management.endpoints.web.exposure.include=refresh
 * 3. 在需要改变动态变量的类使用 @RefreshScope
 * 4. 使用Post方式请求http://${host}:${port}/actuator/refresh
 * </pre>
 */
@SpringBootApplication
@EnableConfigServer
@RefreshScope
public class AppConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(AppConfigServer.class, args);
    }
}

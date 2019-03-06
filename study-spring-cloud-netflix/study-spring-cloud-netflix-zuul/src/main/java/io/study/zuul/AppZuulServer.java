package io.study.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2019/2/21 0021.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableZuulProxy
public class AppZuulServer {

    public static void main(String[] args) {
        SpringApplication.run(AppZuulServer.class, args);
    }
}

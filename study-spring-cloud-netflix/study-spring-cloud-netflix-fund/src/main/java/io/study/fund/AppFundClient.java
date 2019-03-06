package io.study.fund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2019/2/19 0019.
 */
@SpringBootApplication
@ComponentScan("io.study")
@EnableEurekaClient
public class AppFundClient {

    public static void main(String[] args) {
        SpringApplication.run(AppFundClient.class, args);
    }
}

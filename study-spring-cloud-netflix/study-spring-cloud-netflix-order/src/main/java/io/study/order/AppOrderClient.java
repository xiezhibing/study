package io.study.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Administrator on 2019/2/19 0019.
 */
@SpringBootApplication
@EnableEurekaClient
public class AppOrderClient {

    public static void main(String[] args) {
        SpringApplication.run(AppOrderClient.class, args);
    }
}

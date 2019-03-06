package io.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(AppEurekaServer.class);
    }
}

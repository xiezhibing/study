package io.study.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Created by Administrator on 2019/2/22 0022.
 */
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableTurbine
@EnableEurekaClient
public class AppTurbineServer {

    public static void main(String[] args) {
        SpringApplication.run(AppTurbineServer.class, args);
    }
}

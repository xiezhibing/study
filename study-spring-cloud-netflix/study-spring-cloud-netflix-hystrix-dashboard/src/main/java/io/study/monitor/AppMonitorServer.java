package io.study.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by Administrator on 2019/2/22 0022.
 */
@SpringBootApplication
@EnableHystrixDashboard
public class AppMonitorServer {

    public static void main(String[] args) {
        SpringApplication.run(AppMonitorServer.class, args);
    }
}

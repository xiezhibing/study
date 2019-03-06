package io.study.user;

import io.study.user.config.FundRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@RibbonClients({
        @RibbonClient(name = "SERVER-FUND", configuration = FundRuleConfig.class),
        //@RibbonClient(name = "SERVER-ORDER", configuration = OrderRuleConfig.class)
})
public class AppUserClient {

    public static void main(String[] args) {
        SpringApplication.run(AppUserClient.class, args);
    }
}

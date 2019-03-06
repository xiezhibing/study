package io.study.user.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@Configuration
public class OrderRuleConfig {

    public static final String ORDER_URL="http://SERVER-ORDER/api/v1/order/";

    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}

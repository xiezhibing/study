package io.study.user.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@Configuration
public class FundRuleConfig {
    public static final String FUND_URL="http://SERVER-FUND/api/v1/fund/";

    @Bean
    public IRule iRule(){
        return new RoundRobinRule();
    }
}

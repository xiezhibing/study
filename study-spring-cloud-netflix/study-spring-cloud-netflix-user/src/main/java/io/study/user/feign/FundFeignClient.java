package io.study.user.feign;

import io.study.data.Result;
import io.study.user.feign.fallback.FundFeignFallback;
import io.study.user.feign.fallback.FundFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@FeignClient(value = "SERVER-FUND", fallbackFactory = FundFeignFallbackFactory.class)
public interface FundFeignClient {

    /**
     * 调用此方法超时，会调用fallback或fallbackFactory中的降级方法
     * @param userId
     * @return
     */
    @GetMapping("/api/v1/fund/user/{userId}")
    public Result getUserFund(@PathVariable("userId") String userId);
}

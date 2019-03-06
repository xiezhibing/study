package io.study.user.feign.fallback;

import io.study.data.Result;
import io.study.user.feign.FundFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@Component
public class FundFeignFallback implements FundFeignClient {

    private Logger logger = LoggerFactory.getLogger(FundFeignFallback.class);

    @Override
    public Result getUserFund(String userId) {
        logger.info("请求降级");
        return new Result(Result.FAIL, "请稍后重试!");
    }
}

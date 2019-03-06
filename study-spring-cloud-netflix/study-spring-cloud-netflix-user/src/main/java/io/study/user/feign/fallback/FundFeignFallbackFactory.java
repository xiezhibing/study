package io.study.user.feign.fallback;

import feign.hystrix.FallbackFactory;
import io.study.data.Result;
import io.study.data.ResultCode;
import io.study.user.feign.FundFeignClient;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@Component
public class FundFeignFallbackFactory implements FallbackFactory<FundFeignClient> {

    @Override
    public FundFeignClient create(Throwable throwable) {

        return new FundFeignClient() {
            @Override
            public Result getUserFund(String userId) {
                String code = throwable.getMessage();
                String message = ResultCode.getMessage(code);
                if(message != null){
                    return new Result(code, message);
                } else {
                    return new Result(Result.FAIL, code);
                }
            }
        };
    }
}

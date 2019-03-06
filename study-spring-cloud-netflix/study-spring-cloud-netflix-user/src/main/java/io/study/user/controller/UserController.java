package io.study.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.study.data.PageData;
import io.study.user.config.FundRuleConfig;
import io.study.user.feign.FundFeignClient;
import io.study.user.service.IUserService;
import io.study.data.Result;
import io.study.data.ResultCode;
import io.study.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IUserService userService;
    @Autowired
    private FundFeignClient fundFeignClient;

    @PostMapping("/add")
    public Result add(UserInfo userParam) {
        userService.add(userParam);
        return ResultCode.getSucc(ResultCode.ADD_SUCC);
    }

    @GetMapping("/list")
    public Result getList(UserInfo userParam){
        PageData<UserInfo> data = userService.queryPageList(userParam);
        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }

    @RequestMapping("/view/{userId}")
    public Result queryUser(@PathVariable("userId") String userId){
        UserInfo data = userService.queryById(userId);

        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }

    @RequestMapping("/fund/{userId}")
    public Result queryFund(@PathVariable("userId") String userId){
        return restTemplate.getForObject(FundRuleConfig.FUND_URL + "/1/{userId}", Result.class, userId);
    }

    @RequestMapping("/fund/feign/{userId}")
    @HystrixCommand(fallbackMethod = "queryFeignfundFeignClient")
    public Result queryUserFundFeign(@PathVariable("userId") String userId){
        return fundFeignClient.getUserFund(userId);
    }

    public Result queryFeignfundFeignClient(String userId){
        logger.info("feign1 call query user:{} fund fail !", userId);
        return new Result(Result.FAIL, "降级信息");
    }

    /**
     * 注意:
     * 1. queryUserFundFeign2与queryFeignfundFeignClient2参数必须一致
     * 2. 如果queryUserFundFeign2超时，会调用fallbackMethod的方法
     * 3. 如果fundFeignClient.getUserFund超时，会调用fundFeignClient相应的fallback方法
     * @param userId
     * @return
     */
    @RequestMapping("/fund/feign/user/{userId}")
    @HystrixCommand(fallbackMethod = "queryFeignfundFeignClient2")
    public Result queryUserFundFeign2(@PathVariable("userId") String userId){
        return fundFeignClient.getUserFund(userId);
    }

    /**
     * 参数必须一致
     * @param userId
     * @return
     */
    public Result queryFeignfundFeignClient2(String userId){
        logger.info("feign2 call query user:{} fund fail !", userId);
        return new Result(Result.FAIL, "降级信息");
    }
//
//    @RequestMapping("/fund/feign/1/{num}/{userId}")
//    @HystrixCommand(fallbackMethod = "queryFeignfundFeignClient")
//    public Result queryUserFundFeign3(@PathVariable("num") Integer num, @PathVariable("userId") String userId){
//        return fundFeignClient.getUserFund(num, userId);
//    }

}

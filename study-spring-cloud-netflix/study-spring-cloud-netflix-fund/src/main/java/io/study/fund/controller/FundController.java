package io.study.fund.controller;

import io.study.data.PageData;
import io.study.data.Result;
import io.study.data.ResultCode;
import io.study.fund.FundInfo;
import io.study.fund.service.IFundService;
import io.study.fund.service.impl.FundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@RestController
@RequestMapping("/api/v1/fund")
public class FundController {

    @Autowired
    private IFundService fundService;

    @PostMapping("/add")
    public Result add(FundInfo fund) {
        fundService.add(fund);
        return ResultCode.getSucc(ResultCode.ADD_SUCC);
    }

    @GetMapping("/list")
    public Result getList(FundInfo fund){
        PageData<FundInfo> data = fundService.queryPageList(fund);
        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }

    @GetMapping("/view/{fundId}")
    public Result getFund(@PathVariable("fundId") String fundId){

        FundInfo fund = fundService.queryById(fundId);

        return ResultCode.getSucc(ResultCode.QUERY_SUCC, fund);
    }

    @GetMapping("/user/{userId}")
    public Result getUserFund(@PathVariable("userId") String userId) throws Exception {
        FundInfo param = new FundInfo();
        param.setUserId(userId);
        FundInfo fund = fundService.queryOne(param);

        return ResultCode.getSucc(ResultCode.QUERY_SUCC, fund);
    }

}

package io.study.order.controller;

import io.study.data.PageData;
import io.study.data.Result;
import io.study.data.ResultCode;
import io.study.order.OrderInfo;
import io.study.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/2/19 0019.
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/list")
    public Result queryList(OrderInfo orderInfo){
        PageData<OrderInfo> data = orderService.queryPageList(orderInfo);
        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }

    @RequestMapping("/{userId}/list")
    public Result queryUserOrder(@PathVariable("userId") String userId){
        OrderInfo param = new OrderInfo();
        param.setUserId(userId);
        List<OrderInfo> data = orderService.queryList(param);
        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }

    @RequestMapping("/{orderId}")
    public Result queryOrder(@PathVariable("orderId") String orderId){
        OrderInfo data = orderService.queryById(orderId);
        return ResultCode.getSucc(ResultCode.QUERY_SUCC, data);
    }
}

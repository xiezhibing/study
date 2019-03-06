package io.study.user.config;

import io.study.data.Result;
import io.study.data.ResultCode;
import io.study.util.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(HttpServletRequest request, Exception e) {
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            return ResultCode.getFail(exception.getCode());
        } else if (e instanceof RuntimeException) {
            logger.error("【系统异常】{}", e);
            return new Result(Result.FAIL, "接口异常, 请求失败");
        } else if(e instanceof NoHandlerFoundException) {
            logger.error("【系统异常】{}", e);
            return new Result(Result.FAIL, "接口不存在");
        } else if(e instanceof HttpRequestMethodNotSupportedException){
            logger.error("【系统异常】{}", e);
            return new Result(Result.FAIL, "请求方式错误");
        } else {
            logger.error("【系统异常】{}", e);
            return new Result(Result.FAIL, "接口异常");
        }
    }
}
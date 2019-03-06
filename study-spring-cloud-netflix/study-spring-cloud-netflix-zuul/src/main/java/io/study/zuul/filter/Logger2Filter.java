package io.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2019/2/21 0021.
 */
@Component
public class Logger2Filter extends ZuulFilter {


    private Logger logger = LoggerFactory.getLogger(Logger2Filter.class);
    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * <pre>
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     * </pre>
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filterOrder：过滤的顺序, order越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    /**
     * shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("=======logger2Filter.order:{}=======", filterOrder());
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String remoteAddr = request.getRemoteAddr();
        logger.info("访问者IP："+remoteAddr+"访问地址:"+request.getRequestURI());
        return null;
    }
}

package io.study.order.service.impl;

import io.study.base.BaseMapper;
import io.study.base.BaseServiceImpl;
import io.study.order.mapper.OrderMapper;
import io.study.order.OrderInfo;
import io.study.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * 
 * @author jabin-fastgen
 * @since 2019-02-19
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<OrderInfo> implements IOrderService {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public BaseMapper<OrderInfo> getBaseMapper() {
		return orderMapper;
	}
	
}

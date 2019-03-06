package io.study.order.mapper;

import io.study.base.BaseMapper;
import io.study.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper类
 * 
 * @author jabin-fastgen
 * @since 2019-02-19
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {

}
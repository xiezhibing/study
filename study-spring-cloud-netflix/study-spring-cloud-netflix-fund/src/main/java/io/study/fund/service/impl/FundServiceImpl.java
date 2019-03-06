package io.study.fund.service.impl;

import io.study.base.BaseMapper;
import io.study.base.BaseServiceImpl;
import io.study.fund.FundInfo;
import io.study.fund.mapper.FundMapper;
import io.study.fund.service.IFundService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * 资金
 * @author jabin-fastgen
 * @since 2019-02-19
 */
@Service("fundService")
public class FundServiceImpl extends BaseServiceImpl<FundInfo> implements IFundService {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(FundServiceImpl.class);
	
	@Autowired
	private FundMapper fundMapper;
	
	@Override
	public BaseMapper<FundInfo> getBaseMapper() {
		return fundMapper;
	}
	
}

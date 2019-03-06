package io.study.user.service.impl;

import io.study.base.BaseMapper;
import io.study.base.BaseServiceImpl;
import io.study.user.UserInfo;
import io.study.user.mapper.UserMapper;
import io.study.user.service.IUserService;

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
@Service("userInfoService")
public class UserServiceImpl extends BaseServiceImpl<UserInfo> implements IUserService {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public BaseMapper<UserInfo> getBaseMapper() {
		return userMapper;
	}
	
}

package io.study.user.mapper;

import io.study.base.BaseMapper;
import io.study.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper类
 * 
 * @author jabin-fastgen
 * @since 2019-02-19
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

}
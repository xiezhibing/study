package io.study.fund.mapper;

import io.study.base.BaseMapper;
import io.study.fund.FundInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@Mapper
public interface FundMapper extends BaseMapper<FundInfo> {
}


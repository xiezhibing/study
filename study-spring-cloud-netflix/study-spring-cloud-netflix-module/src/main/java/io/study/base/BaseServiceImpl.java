package io.study.base;

import io.study.data.PageData;
import io.study.util.PKUtil;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 业务实现基础类
 * @author Zhibing.Xie
 * @since 2017-11-01
 */
public abstract class BaseServiceImpl<T extends BaseBean> implements IBaseService<T> {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Override
	public long add(T bean) {
		
		String uuid = PKUtil.nextSid();
		bean.setId(uuid);
		logger.debug("id: {}", uuid);
		
		return getBaseMapper().add(bean);		
	}

	@Override
	public long update(T bean) {
		return getBaseMapper().update(bean);
	}
	
	@Override
	public long remove(String id) {
		return getBaseMapper().remove(id);
	}
	
	@Override
	public T queryById(String id) {
		return getBaseMapper().queryById(id);
	}

	@Override
	public T queryOne(T bean) {
		return getBaseMapper().queryOne(bean);
	}

	@Override
	public List<T> queryList(T bean) {
		return getBaseMapper().queryList(bean);
	}

	@Override
	public PageData<T> queryPageList(T bean) {
		Integer pageNum = bean.getPageNum();
		Integer pageSize = bean.getPageSize();
		PageData<T> pageData = new PageData<T>(pageNum, pageSize);
		
		pageNum = pageNum - 1;
		if(pageNum < 0){
			pageNum = 0;
		}
		if(pageSize <= 0){
			pageSize = 10;
		}
		
		RowBounds rowBounds = new RowBounds(pageNum * pageSize, pageSize);		
		Long total = getBaseMapper().queryCount(bean);
		if(total == null || total == 0){
			return pageData;
		}
		
		List<T> rows = getBaseMapper().queryList(bean, rowBounds);
		pageData.setRows(rows);
		pageData.setTotal(total);
		
		return pageData;
	}
}

package io.study.base;

import io.study.data.PageData;

import java.io.Serializable;
import java.util.List;

/**
 * Service基础业务接口
 * @author Zhibing.Xie
 * @since 2017-11-01
 */
public interface IBaseService<T> extends Serializable {
	
	/**
	 * 重新此方法，注入Mapper业务接口
	 * @return
	 */
	public BaseMapper<T> getBaseMapper();
	
	/**
	 * 新增
	 * @param bean
	 */
	public long add(T bean);
	
	/**
	 * 更新
	 * @param bean
	 */
	public long update(T bean);
	
	/**
	 * 删除(逻辑)
	 * @param id
	 */
	public long remove(String id);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T queryById(String id);
	
	/**
	 * 条件查询单个对象
	 * @param bean
	 * @return
	 */
	public T queryOne(T bean);
	
	/**
	 * 条件查询
	 * @param bean
	 * @return
	 */
	public List<T> queryList(T bean);

	/**
	 * 分页条件查询
	 * @param bean
	 * @return
	 */
	public PageData<T> queryPageList(T bean);
}

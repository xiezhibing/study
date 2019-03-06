package io.study.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 * @author Zhibing.Xie
 * @since 2017-11-01
 */
public class PageData<T> implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 分页数据 */
	private List<T> rows = new ArrayList<T>();
	/** 分页数据总数 */
	private Long total = 0L;
	/** 页码 */
	private Integer pageNum = 0;
	/** 每页条数 */
	private Integer pageSize = 0;

	/** 构造方法 */
	public PageData() {
		super();
	}
	/**
	 * 构造方法
	 * @param pageNum	页码
	 * @param pageSize	每页条数
	 */
	public PageData(Integer pageNum, Integer pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	/**
	 * 构造方法
	 * @param rows		分页数据
	 * @param total		分页数据总数
	 * @param pageNum	页码
	 * @param pageSize	每页条数
	 */
	public PageData(List<T> rows, Long total, Integer pageNum, Integer pageSize) {
		super();
		this.rows = rows;
		this.total = total;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	/** 分页数据 */
	public List<T> getRows() {
		return rows;
	}

	/** 分页数据 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/** 分页数据总数 */
	public Long getTotal() {
		return total;
	}

	/** 分页数据总数 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/** 页码 */
	public Integer getPageNum() {
		return pageNum;
	}

	/** 页码 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/** 每页条数 */
	public Integer getPageSize() {
		return pageSize;
	}

	/** 每页条数 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

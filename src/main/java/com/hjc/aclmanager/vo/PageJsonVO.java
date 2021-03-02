package com.hjc.aclmanager.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 *easyui分页查询类
 */
@Builder
@AllArgsConstructor
public class PageJsonVO implements Serializable {

	private long total;
	
	private List<?> footer;
	
	private List<?> rows;
	
	private long pageNum;//获取页数

	public long getPageNum() {
		return pageNum;
	}

	public void setPageNum(long pageSize) {
		this.pageNum =  (this.total%pageSize==0)?(this.total/pageSize):(this.total/pageSize+1);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	/**
	 * @param total
	 * @param footer
	 * @param rows
	 */
	public PageJsonVO(long total, List<?> footer, List<?> rows) {
		super();
		this.total = total;
		this.footer = footer;
		this.rows = rows;
	}

	public PageJsonVO() {
	}

}

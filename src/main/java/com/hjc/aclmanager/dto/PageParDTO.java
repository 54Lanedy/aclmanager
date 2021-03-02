/**
 * 
 */
package com.hjc.aclmanager.dto;



/**
 * @项目名称：平台V1.0
 * @作 者： 甘智昌
 * @创建时间：2014-2-7 下午2:36:37
 * @描 述：easyui分页查询通用参数
 */
public class PageParDTO {
	
	private int page=0;
	
	private int rows=0;
	
	private int pageDao=0;
	
	
	private String sort="";
	
	private String order="";
	
	/**
	 * 返回分页FirstResult
	 * @param page
	 * @param rows
	 * @return
	 */
	private  Integer getFirstResult(int page, int rows){
		return (page-1)*rows;
	}
	
	public int getPage() {
		
		return page;
	}

	
	
	public int getPageDao() {
		if(page>0){
			pageDao = this.getFirstResult(page, rows);
		}
		return pageDao;
	}

	public void setPageDao(int pageDao) {
		this.pageDao = pageDao;
	}

	public void setPage(int page) {
		this.page = page;
		
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public PageParDTO() {
		
	}

	/**
	 * @param page
	 * @param rows
	 */
	public PageParDTO(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}

}

package cn.itcast.core.action;


import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.page.PageResult;

public abstract class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] selectedRow;
	
	protected PageResult pageResult;

	private int pageNo;

	private int pageSize;
	
	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	
	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public int getPageNo() {
		if(pageNo==0) pageNo=1;
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if(pageSize==0) pageSize=3;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}

package com.demo.bean;

import java.util.List;

public class Page {
	private long total;
	private int page;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows2) {
		this.rows = rows2;
	}
	public Page(long total, int page, List rows) {
		super();
		this.total = total;
		this.page = page;
		this.rows = rows;
	}
	public Page() {
		super();
	}
	
	
	
}

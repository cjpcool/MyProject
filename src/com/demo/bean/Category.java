package com.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author cjp
 *
 */
public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//-1所有专业, 0 未知专业
	private long categoryId;
	private String categoryName;
	/**
	 * �����
	 */
	private long hotLevel;
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getHotLevel() {
		return hotLevel;
	}
	public void setHotLevel(long hotLevel) {
		this.hotLevel = hotLevel;
	}
	public Category(long categoryId, String categoryName, long hotLevel) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.hotLevel = hotLevel;
	}
	public Category(String categoryName, long hotLevel) {
		super();
		this.categoryName = categoryName;
		this.hotLevel = hotLevel;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", hotLevel=" + hotLevel + "]";
	}
	
}

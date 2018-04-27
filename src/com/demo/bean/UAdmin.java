package com.demo.bean;

import java.io.Serializable;

/**
 * 该bean当前主要映射1对1的关系表, 
 * 因为是后期需求才加入进来的,所以特意建了个表,方便扩展
 * @author 陈建朋
 *
 */
public class UAdmin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long userId;
	private long adminId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	@Override
	public String toString() {
		return "UAdmin [userId=" + userId + ", adminId=" + adminId + "]";
	}
	public UAdmin() {
		super();
	}
	public UAdmin(long userId, long adminId) {
		super();
		this.userId = userId;
		this.adminId = adminId;
	}
}

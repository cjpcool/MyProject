package com.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author cjp
 *
 */
public class Admin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long adminId;
	private String adminName;
	private String pwd;
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Admin(long adminId, String adminName, String pwd) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.pwd = pwd;
	}
	public Admin(String adminName, String pwd) {
		super();
		this.adminName = adminName;
		this.pwd = pwd;
	}
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + ", pwd=" + pwd + "]";
	}
	
}

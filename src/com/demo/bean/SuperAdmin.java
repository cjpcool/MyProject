package com.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author cjp
 *
 */
public class SuperAdmin implements Serializable{
	private static final long serialVersionUID = 1L;
	private long superadId;
	private String superadName;
	private String pwd;
	
	
	
	public SuperAdmin() {
		super();
	}
	public long getSuperadId() {
		return superadId;
	}
	public void setSuperadId(long superadId) {
		this.superadId = superadId;
	}
	public String getSuperadName() {
		return superadName;
	}
	public void setSuperadName(String superadName) {
		this.superadName = superadName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "SuperAdmin [superadId=" + superadId + ", superadName=" + superadName + ", pwd=" + pwd + "]";
	}
	public SuperAdmin(long superadId, String superadName, String pwd) {
		super();
		this.superadId = superadId;
		this.superadName = superadName;
		this.pwd = pwd;
	}
	public SuperAdmin(String superadName, String pwd) {
		super();
		this.superadName = superadName;
		this.pwd = pwd;
	}
	
}

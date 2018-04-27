package com.demo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author cjp
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long userId;
	private String userName;
	private short job;
	private String img;
	private String pwd;
	private String email;
	private long categoryId;
	private Timestamp outDate;
	private String validataCode;

	
	public Timestamp getOutDate() {
		return outDate;
	}
	public void setOutDate(Timestamp outDate) {
		this.outDate = outDate;
	}
	public String getValidataCode() {
		return validataCode;
	}
	public void setValidataCode(String secretKey) {
		this.validataCode = secretKey;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public short getJob() {
		return job;
	}
	public void setJob(short job) {
		this.job = job;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", job=" + job + ", img=" + img + ", pwd="
				+ pwd + ", tel=" + email + ", categoryId=" + categoryId + "]";
	}
	public User(String userName, short job, String img, String pwd, String email, long categoryId) {
		super();
		this.userName = userName;
		this.job = job;
		this.img = img;
		this.pwd = pwd;
		this.email = email;
		this.categoryId = categoryId;
	}
	
	public User(long userId, String userName, short job, String img, String pwd, String email, long categoryId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.job = job;
		this.img = img;
		this.pwd = pwd;
		this.email = email;
		this.categoryId = categoryId;
	}
	
	public User(long userId, String userName, String pwd, String email, long categoryId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.pwd = pwd;
		this.email = email;
		this.categoryId = categoryId;
	}
	public User() {
		super();
	}
	
}

package com.demo.bean;

import java.io.Serializable;
import java.sql.Date;


public class Vocabulary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long vocabId;
	private String vocab;
	private String transe;
	private String img;
	private Date lastTime;
	private Date addTime;
	private long categoryId;
	private long userId;
	//状态 1=审核成功  0=待审核 
	private short status;

	
	public String getVocab() {
		return vocab;
	}

	public void setVocab(String vocab) {
		this.vocab = vocab;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public long getVocabId() {
		return vocabId;
	}

	public void setVocabId(long vocabId) {
		this.vocabId = vocabId;
	}

	public String getvocab() {
		return vocab;
	}

	public void setvocab(String vocab) {
		this.vocab = vocab;
	}

	public String getTranse() {
		return transe;
	}

	public void setTranse(String transe) {
		this.transe = transe;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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

	public Vocabulary() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "Vocabulary [vocabId=" + vocabId + ", vocab=" + vocab + ", transe=" + transe + ", img=" + img
				+ ", lastTime=" + lastTime + ", addTime=" + addTime + ", categoryId=" + categoryId + ", userId="
				+ userId + ", status=" + status + "]";
	}


	public Vocabulary(String vocab, String transe, String img, Date lastTime, Date addTime, long categoryId,
			short status) {
		super();
		this.vocab = vocab;
		this.transe = transe;
		this.img = img;
		this.lastTime = lastTime;
		this.addTime = addTime;
		this.categoryId = categoryId;
		this.status = status;
	}

	public Vocabulary(long vocabId, String vocab, String transe, String img, Date lastTime, Date addTime,
			long categoryId, long userId, short status) {
		super();
		this.vocabId = vocabId;
		this.vocab = vocab;
		this.transe = transe;
		this.img = img;
		this.lastTime = lastTime;
		this.addTime = addTime;
		this.categoryId = categoryId;
		this.userId = userId;
		this.status = status;
	}


	public Vocabulary(String vocab, String transe, String img, Date lastTime, Date addTime, long categoryId,
			long userId, short status) {
		super();
		this.vocab = vocab;
		this.transe = transe;
		this.img = img;
		this.lastTime = lastTime;
		this.addTime = addTime;
		this.categoryId = categoryId;
		this.userId = userId;
		this.status = status;
	}

	public Vocabulary(String vocab, String transe) {
		super();
		this.vocab = vocab;
		this.transe = transe;
	}

	

}

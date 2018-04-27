package com.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author cjp
 *
 */
public class Similar implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long similarId;
	private long vocabId;
	private String similar;
	public long getSimilarId() {
		return similarId;
	}
	public void setSimilarId(long similarId) {
		this.similarId = similarId;
	}
	
	public long getVocabId() {
		return vocabId;
	}
	public void setVocabId(long vocabId) {
		this.vocabId = vocabId;
	}
	public String getSimilar() {
		return similar;
	}
	public void setSimilar(String similar) {
		this.similar = similar;
	}
	public Similar() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Similar(long vocabId, String similar) {
		super();
		this.vocabId = vocabId;
		this.similar = similar;
	}
	public Similar(long similarId, long vocabId, String similar) {
		super();
		this.similarId = similarId;
		this.vocabId = vocabId;
		this.similar = similar;
	}
	@Override
	public String toString() {
		return "Similar [similarId=" + similarId + ", vocabId=" + vocabId + ", similar=" + similar + "]";
	}
	
	
	
}

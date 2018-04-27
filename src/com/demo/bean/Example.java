package com.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author cjp
 *
 */
public class Example implements Serializable{
	private static final long serialVersionUID = 1L;
	private long exampleId;
	private long vocabId;
	private String example;

	public long getExampleId() {
		return exampleId;
	}

	public Example(long vocabId, String example) {
		super();
		this.vocabId = vocabId;
		this.example = example;
	}

	public Example(long exampleId, long vocabId, String example) {
		super();
		this.exampleId = exampleId;
		this.vocabId = vocabId;
		this.example = example;
	}

	@Override
	public String toString() {
		return "Example [exampleId=" + exampleId + ", vocabId=" + vocabId + ", example=" + example + "]";
	}

	public void setExampleId(long exampleId) {
		this.exampleId = exampleId;
	}

	public long getVocabId() {
		return vocabId;
	}

	public void setVocabId(long vocabId) {
		this.vocabId = vocabId;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Example() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

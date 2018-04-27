package com.demo.bean;

import java.util.List;

/**
 * 
 * @author cjp
 *
 */
public class VocSimExam {
	private Vocabulary voca;
	private List<Similar> similars;
	private List<Example> exams;
	public Vocabulary getVoca() {
		return voca;
	}
	public void setVoca(Vocabulary voca) {
		this.voca = voca;
	}
	public List<Similar> getSimilars() {
		return similars;
	}
	public void setSimilars(List<Similar> similars) {
		this.similars = similars;
	}
	public List<Example> getExams() {
		return exams;
	}
	public void setExams(List<Example> exams) {
		this.exams = exams;
	}
	public VocSimExam(Vocabulary voca, List<Similar> similars, List<Example> exams) {
		super();
		this.voca = voca;
		this.similars = similars;
		this.exams = exams;
	}
	public VocSimExam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VocSimExam(Vocabulary voca) {
		super();
		this.voca = voca;
	}
	@Override
	public String toString() {
		return "VocSimExam [voca=" + voca + ", similars=" + similars + ", exams=" + exams + "]";
	}
	
	
	
	
}

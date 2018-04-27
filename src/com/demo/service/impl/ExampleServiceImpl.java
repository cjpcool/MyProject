package com.demo.service.impl;

import com.demo.bean.Example;
import com.demo.bean.Vocabulary;
import com.demo.dao.ExampleDao;
import com.demo.dao.impl.ExampleDaoImpl;
import com.demo.service.ExampleService;

import java.sql.SQLException;
import java.util.List;

public class ExampleServiceImpl implements ExampleService{
	ExampleDao ed = new ExampleDaoImpl();
	@Override
	public List<Example> getExamplesByVocab(Vocabulary vocab) throws SQLException {
		List<Example> res = ed.selectExamplesByVocab(vocab.getVocabId());
		return res;
	}

	@Override
	public int deleteExamplesByVocab(Vocabulary vocab) throws SQLException {
		
		return 0;
	}

	@Override
	public int updateExampleById(long examId, String newExam) throws SQLException {
		int res = ed.updateExampleById(examId, newExam);
		return res;
	}

	@Override
	public int addExample(Example exam) throws SQLException {
		return 0;
	}

	@Override
	public int addAllExamples(List<Example> exams) throws SQLException {
		return 0;
	}

}

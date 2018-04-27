package com.demo.dao;

import com.demo.bean.Example;

import java.sql.SQLException;
import java.util.List;

public interface ExampleDao {
	int insertExample(Example exam) throws SQLException;

	List<Example> selectExamplesByVocab(long vocabId) throws SQLException;

	int updateExampleById(long examId, String newExam) throws SQLException;
}

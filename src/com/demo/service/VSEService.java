package com.demo.service;

import com.demo.bean.Vocabulary;

import java.sql.SQLException;

public interface VSEService {

	int updateVSE(Vocabulary vocabulary, String similarsStr, String examsStr) throws SQLException;
	
}

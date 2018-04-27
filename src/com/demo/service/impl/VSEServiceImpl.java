package com.demo.service.impl;

import com.demo.bean.Vocabulary;
import com.demo.dao.ExampleDao;
import com.demo.dao.SimilarDao;
import com.demo.dao.VocabularyDao;
import com.demo.dao.impl.ExampleDaoImpl;
import com.demo.dao.impl.SimilarDaoImpl;
import com.demo.dao.impl.VocabularyDaoImpl;
import com.demo.service.VSEService;
import com.demo.utils.JDBCTool;

import java.sql.SQLException;

public class VSEServiceImpl implements VSEService {

	SimilarDao sd = new SimilarDaoImpl();
	ExampleDao ed = new ExampleDaoImpl();
	VocabularyDao vd = new VocabularyDaoImpl();

	@Override
	public int updateVSE(Vocabulary vocabulary, String similarsStr, String examsStr) throws SQLException {
		int res = 1;
		try{
			JDBCTool.beginTransaction();
		
			vd.updateVocabulary(vocabulary);
			
			if (similarsStr != null && similarsStr.length() > 0) {
				String[] similarWithId = similarsStr.split(";");
				for (int i = 0; i < similarWithId.length; i++) {
					String[] similar = similarWithId[i].split("~");
					res = sd.updateSimilarById(Long.parseLong(similar[0]), similar[1]);
					if(res == 0){
						throw new SQLException();
					}
				}
			}
			if (examsStr != null && examsStr.length() > 0) {
				String[] examWithId = examsStr.split(";");
				for (int i = 0; i < examWithId.length; i++) {
					String[] exam = examWithId[i].split("~");
					res = ed.updateExampleById(Long.parseLong(exam[0]), exam[1]);
					if(res == 0){
						throw new SQLException();
					}
				}
			}
			JDBCTool.commitTransaction();
		}catch(SQLException e){
			try{
				JDBCTool.rollbackTransaction();
			}catch (Exception e1) {
				e.printStackTrace();
			}
			throw e;
		}
		return res;
	}
}

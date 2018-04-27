package com.demo.dao.impl;

import com.demo.bean.Example;
import com.demo.dao.ExampleDao;
import com.demo.utils.JDBCTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ExampleDaoImpl implements ExampleDao{
	DataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	
	@Override
	public int insertExample(Example exam) throws SQLException {
		String sql = "insert into tb_example values(?,?,?)";
		int res = qr.update(sql, exam.getExample(), (exam.getExampleId()==0)?null:exam.getExampleId(), exam.getVocabId());
	
		return res;
	}

	@Override
	public List<Example> selectExamplesByVocab(long vocabId) throws SQLException {
		String sql="select* from tb_example where vocabId=?";
		List<Example> res = qr.query(sql, new BeanListHandler<Example>(Example.class), vocabId);
		return res;
	}

	@Override
	public int updateExampleById(long examId, String newExam) throws SQLException {
		QueryRunner qrunner = new QueryRunner();
		Connection conn = JDBCTool.getConnection();
		String sql = "update tb_example set example=? where exampleId=?";
		int res = qrunner.update(conn, sql, newExam, examId);
		
		return res;
	}

}

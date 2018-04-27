package com.demo.dao.impl;

import com.demo.bean.Similar;
import com.demo.dao.SimilarDao;
import com.demo.utils.JDBCTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SimilarDaoImpl implements SimilarDao{
	DataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);

	@Override
	public int insertSimilar(Similar similar) throws SQLException {
		String sql="insert into tb_similar values(?,?,?)";
		int res = qr.update(sql, (similar.getSimilarId()==0)?null:similar.getSimilarId(), similar.getVocabId(),similar.getSimilar());
		return res;
	}

	@Override
	public List<Similar> selectSimilarsByVocab(long vocabId) throws SQLException {
		String sql="select* from tb_similar where vocabId=?";
		List<Similar> res = qr.query(sql, new BeanListHandler<Similar>(Similar.class), vocabId);
		
		return res;
	}

	@Override
	public int updateSimilarById(long similarId, String newSimilar) throws SQLException {
		QueryRunner qrunner = new QueryRunner();
		Connection conn = JDBCTool.getConnection();
		String sql = "update tb_similar set similar=? where similarId=?";
		int res = qrunner.update(conn, sql,  newSimilar, similarId);
		return res;
	}

}

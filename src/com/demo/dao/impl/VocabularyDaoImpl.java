package com.demo.dao.impl;

import com.demo.bean.Vocabulary;
import com.demo.dao.VocabularyDao;
import com.demo.utils.JDBCTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VocabularyDaoImpl implements VocabularyDao {
	DataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	@Override
	public List<Vocabulary> selectVocabByName(String vocabName) throws SQLException {
		String sql = "select* from tb_vocabulary where vocab regexp ?";
		List<Vocabulary> res = qr.query(sql,new BeanListHandler<Vocabulary>(Vocabulary.class),vocabName);
		return res;
	}


	@Override
	public Vocabulary selectVocabById(long vocabId) throws SQLException {
		String sql = "select * from tb_vocabulary where vocabId=?";
		Vocabulary res = qr.query(sql, new BeanHandler<Vocabulary>(Vocabulary.class), vocabId);
		return res;
	}

	@Override
	public List<Vocabulary> selectVocabsByUser(long userId) throws SQLException {
		String sql = "select * from tb_vocabulary where userId=?";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class), userId);
		return res;
	}

	@Override
	public List<Vocabulary> selectVocabsByTrans(String trans) throws SQLException {
		String sql="select * from tb_vocabulary where transe like ?";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class), trans+"%");
		return res;
	}

	@Override
	public int insertVocabulary(Vocabulary vocab) throws SQLException {
		String sql="insert into tb_vocabulary values(?,?,?,?,?,?,?,?,?)";
		int res = qr.update(sql,(vocab.getVocabId()==0)?null:vocab.getVocabId(), vocab.getvocab(), vocab.getTranse(), vocab.getImg(), vocab.getLastTime(), vocab.getAddTime(), vocab.getCategoryId(), (vocab.getUserId()==0)?null:vocab.getUserId() ,vocab.getStatus());
		return res;
	}


	@Override
	public List<Vocabulary> selectVocabsByStatus(short status) throws SQLException {
		String sql = "select* from tb_vocabulary where status=?";
		List<Vocabulary> res = qr.query(sql,  new BeanListHandler<Vocabulary>(Vocabulary.class), status);
		return res;
	}


	@Override
	public List<Vocabulary> selectVocabByNameAndCategory(String vocab, long categoryId) throws SQLException {
		String sql = "select* from tb_vocabulary where vocab REGEXP ? and categoryId=?";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class), vocab, categoryId);
		return res;
	}


	@Override
	public List<Vocabulary> selectVocabsByTransAndCategory(String trans, long categoryId) throws SQLException {
		String sql = "select* from tb_vocabulary where transe like ? and categoryId=?";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class), trans+"%", categoryId);
		return res;
	}


	@Override
	public int updateVocabulary(Vocabulary vocabulary) throws SQLException {
		QueryRunner qrunner = new QueryRunner();
		Connection conn = JDBCTool.getConnection();
		String sql = "update tb_vocabulary set vocab=?, transe=?, img=? ,addTime=?, categoryId=?, status=? where vocabId=?";
		int res = qrunner.update(conn, sql, vocabulary.getVocab(), vocabulary.getTranse(),vocabulary.getImg(), vocabulary.getAddTime(), vocabulary.getCategoryId(), vocabulary.getStatus(), vocabulary.getVocabId());
		
		return res;
	}


	@Override
	public int deleteVocabById(long vocabId) throws SQLException {
		String sql = "delete from tb_vocabulary where vocabId="+vocabId;
		int res = qr.update(sql);
		return res;
	}


	@Override
	public List<Vocabulary> selectAllVocabsOrderByLastTime() throws SQLException {
		String sql = "select * from tb_vocabulary order by lastTime";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class));
		System.out.println(res);
		return res;
	}


	@Override
	public long countVocabByUserIdAndCategoryId(long userId, short status, long categoryId) throws SQLException {
		String sql = "select count(0) from tb_vocabulary where userId=? and status=? and categoryId=?";
		Object res[] =  qr.query(sql, new ArrayHandler(), userId, status, categoryId);
		return Long.parseLong(res[0].toString());
	}


	@Override
	public long countVocabByUserId(long userId, short statuts) throws SQLException {
		String sql = "select count(0) from tb_vocabulary where userId=? and status=?";
		Object res[] =  qr.query(sql, new ArrayHandler(), userId, statuts);
		return Long.parseLong(res[0].toString());
	}

	@Override
	public List<Vocabulary> selectAllVocabsOrderByID() throws SQLException {
		String sql = "select * from tb_vocabulary order by vocabId";
		List<Vocabulary> res = qr.query(sql, new BeanListHandler<Vocabulary>(Vocabulary.class));
		System.out.println(res);
		return res;
	}

}

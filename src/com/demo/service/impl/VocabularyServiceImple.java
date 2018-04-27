package com.demo.service.impl;

import com.demo.bean.Example;
import com.demo.bean.Similar;
import com.demo.bean.Vocabulary;
import com.demo.dao.ExampleDao;
import com.demo.dao.VocabularyDao;
import com.demo.dao.impl.ExampleDaoImpl;
import com.demo.dao.impl.SimilarDaoImpl;
import com.demo.dao.impl.VocabularyDaoImpl;
import com.demo.service.VocabularyService;

import java.sql.SQLException;
import java.util.List;

public class VocabularyServiceImple implements VocabularyService{
	VocabularyDao vd = new VocabularyDaoImpl();

	@Override
	public int addVocabulares(List<Vocabulary> vocabs) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public List<Vocabulary> queryVocabularyByName(String vocab, long categoryId) throws SQLException {
		List<Vocabulary> res = null;
		if(categoryId == -1){
			res = vd.selectVocabByName(vocab);
		}else{
			res = vd.selectVocabByNameAndCategory(vocab, categoryId);
		}
		
		return res;
	}
	@Override
	public List<Vocabulary> queryVocabularyByTransAndCategory(String trans, long categoryId) throws SQLException {
		List<Vocabulary> res = null;
		if(categoryId == -1){
			 res = vd.selectVocabsByTrans(trans);
		}else{
			res = vd.selectVocabsByTransAndCategory(trans, categoryId);
		}
		return res;
	}

	@Override
	public int addVocabulary(Vocabulary vocab, String exam, String similar) throws SQLException {
		ExampleDao ed = new ExampleDaoImpl();
		SimilarDaoImpl sd = new SimilarDaoImpl();
		int vdRes = vd.insertVocabulary(vocab);
		vocab = vd.selectVocabByName(vocab.getvocab()).get(0);
		Example example = new Example(vocab.getVocabId(), exam);
		Similar similarobj = new Similar(vocab.getVocabId(), similar);
		int edRes = ed.insertExample(example);
		int sdRes = sd.insertSimilar(similarobj);
		if(edRes == 1 && sdRes == 1 && vdRes == 1){
			return 1;
		}
		return 0;
	}



	@Override
	public List<Vocabulary> getVocabsByUserId(long userId) throws SQLException {
		List<Vocabulary> vocabs = vd.selectVocabsByUser(userId);
		return vocabs;
	}



	@Override
	public List<Vocabulary> getVocabsByStatus(short status) throws SQLException {
		List<Vocabulary> res = null;
		res = vd.selectVocabsByStatus(status);
		return res;
	}



	@Override
	public Vocabulary getVocabularyByVocabId(long vocabId) throws SQLException {
		Vocabulary res = vd.selectVocabById(vocabId);
		return res;
	}



	@Override
	public int updateVocabulary(Vocabulary vocabulary) throws SQLException {
		int res = vd.updateVocabulary(vocabulary);
		return res;
	}



	@Override
	public int deleteVocabById(long vocabId) throws SQLException {
		int res = vd.deleteVocabById(vocabId);
		return res;
	}



	@Override
	public List<Vocabulary> getAllVocabsOrderByLastTime( ) throws SQLException {
		List<Vocabulary> res = vd.selectAllVocabsOrderByLastTime();
		return res;
	}



	@Override
	public long countVocabByUserIdAndCategoryId(long userId,short status, long categoryId) throws SQLException {
		long res = vd.countVocabByUserIdAndCategoryId(userId, status, categoryId);
		return res;
	}



	@Override
	public long countVocabByUserId(long userId, short status) throws SQLException {
		long res = vd.countVocabByUserId(userId, status);
		return res;
	}

	@Override
	public List<Vocabulary> getAllVocabsOrderByID() throws SQLException {
		List<Vocabulary> res = vd.selectAllVocabsOrderByID();
		return res;
	}




}

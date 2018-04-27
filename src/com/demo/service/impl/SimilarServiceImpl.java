package com.demo.service.impl;

import com.demo.bean.Similar;
import com.demo.bean.Vocabulary;
import com.demo.dao.SimilarDao;
import com.demo.dao.impl.SimilarDaoImpl;
import com.demo.service.SimilarService;

import java.sql.SQLException;
import java.util.List;

public class SimilarServiceImpl implements SimilarService{
	SimilarDao sd  =new SimilarDaoImpl();
	
	@Override
	public List<Similar> getSimilarsByVocab(Vocabulary vocab) throws SQLException {
		List<Similar> res = sd.selectSimilarsByVocab(vocab.getVocabId());
		return res;
	}

	@Override
	public int deletSimilarsByVocab(Vocabulary vocab) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSimilarById(long similarId, String newSimilar) throws SQLException {
		int res = sd.updateSimilarById(similarId, newSimilar);
		return res;
	}

	@Override
	public int addSimilar(Similar similar) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addAllSimilars(List<Similar> similar) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}

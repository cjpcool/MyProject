package com.demo.dao;

import com.demo.bean.Similar;

import java.sql.SQLException;
import java.util.List;

public interface SimilarDao {
	int insertSimilar(Similar similar) throws SQLException;

	List<Similar> selectSimilarsByVocab(long vocabId) throws SQLException;

	int updateSimilarById(long similarId, String newSimilar) throws SQLException;
}

package com.demo.dao;

import com.demo.bean.Vocabulary;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author cjp
 *
 */
public interface VocabularyDao {
	List<Vocabulary> selectVocabByName(String vocabName) throws SQLException;

	Vocabulary selectVocabById(long vocabId) throws SQLException;

	List<Vocabulary> selectVocabsByUser(long userId) throws SQLException;

	List<Vocabulary> selectVocabsByTrans(String trans) throws SQLException;

	int insertVocabulary(Vocabulary vocab) throws SQLException;

	List<Vocabulary> selectVocabsByStatus(short status) throws SQLException;

	List<Vocabulary> selectVocabByNameAndCategory(String vocab, long categoryId) throws SQLException;

	List<Vocabulary> selectVocabsByTransAndCategory(String trans, long categoryId) throws SQLException;

	int updateVocabulary(Vocabulary vocabulary) throws SQLException;

	int deleteVocabById(long vocabId) throws SQLException;

	List<Vocabulary> selectAllVocabsOrderByLastTime( ) throws SQLException;

	long countVocabByUserIdAndCategoryId(long userId, short statuts, long categoryId) throws SQLException;

	long countVocabByUserId(long userId, short statuts) throws SQLException;

	List<Vocabulary> selectAllVocabsOrderByID()throws SQLException;
}

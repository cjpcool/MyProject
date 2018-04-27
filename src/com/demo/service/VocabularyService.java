package com.demo.service;

import com.demo.bean.Vocabulary;

import java.sql.SQLException;
import java.util.List;

public interface VocabularyService {
	List<Vocabulary> queryVocabularyByName(String vocab, long categoryId) throws SQLException;

	List<Vocabulary> queryVocabularyByTransAndCategory(String trans, long categoryId) throws SQLException;

	/**
	 * ������Ӵʻ�
	 * 
	 * @param vocabs
	 * @return
	 * @throws SQLException
	 */
	int addVocabulares(List<Vocabulary> vocabs) throws SQLException;

	/**
	 * ��ӵ����ʻ�
	 * 
	 * @param vocab
	 * @return
	 * @throws SQLException
	 */
	int addVocabulary(Vocabulary vocab, String exam, String similar) throws SQLException;

	/**
	 * 找到某用户上传的单词
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	List<Vocabulary> getVocabsByUserId(long userId) throws SQLException;

	List<Vocabulary> getVocabsByStatus(short status) throws SQLException;

	Vocabulary getVocabularyByVocabId(long vocabId) throws SQLException;

	/**
	 * 管理员修改单词时调用, 会修改单词的单词, 释义,图片,专业,审核状态,和添加时间
	 * 
	 * @param vocabulary
	 * @return
	 * @throws SQLException
	 */
	int updateVocabulary(Vocabulary vocabulary) throws SQLException;

	int deleteVocabById(long vocabId) throws SQLException;

	List<Vocabulary> getAllVocabsOrderByLastTime( ) throws SQLException;

	/**
	 * 获取某个用户上传的单词数,
	 *可选择是否通过审核 
	 * @param userId 
	 * @param status 
	 * @param categoryId 
	 * @return
	 * @throws SQLException
	 */
	long countVocabByUserIdAndCategoryId(long userId, short status, long categoryId) throws SQLException;
	long countVocabByUserId(long userId, short status) throws SQLException;

	List<Vocabulary> getAllVocabsOrderByID() throws SQLException;
}

package com.demo.service;
/**
 * �����Service�ӿ�
 * @author �½���
 *
 */

import com.demo.bean.Similar;
import com.demo.bean.Vocabulary;

import java.sql.SQLException;
import java.util.List;

public interface SimilarService {
	/**
	 * ���ݴʻ��ҵ��������Ӧ�Ľ����
	 * 
	 * @param vocab
	 * @return
	 * @throws SQLException
	 */
	List<Similar> getSimilarsByVocab(Vocabulary vocab) throws SQLException;

	/**
	 * ɾ���ʻ��Ӧ�����н����
	 * 
	 * @param vocab
	 * @return �ɹ�����1 ʧ�ܷ���0
	 * @throws SQLException
	 */
	int deletSimilarsByVocab(Vocabulary vocab) throws SQLException;

	int updateSimilarById(long similarId, String newSimilar) throws SQLException;

	/**
	 * ��ӵ��������
	 * @param similar
	 * @return	
	 * @throws SQLException
	 */
	int addSimilar(Similar similar) throws SQLException;
	
	/**
	 * ������Ӵʻ�
	 * @param similar
	 * @return
	 * @throws SQLException
	 */
	int addAllSimilars(List<Similar> similar) throws SQLException;
}

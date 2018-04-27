package com.demo.service;
/**
 * Example
 * @author �½���
 *
 */

import com.demo.bean.Example;
import com.demo.bean.Vocabulary;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author �½���
 *
 */
public interface ExampleService {
	List<Example> getExamplesByVocab(Vocabulary vocab) throws SQLException;

	/**
	 * �ҵ������Ӧ�ĵ��������䲢ɾ��
	 * 
	 * @param vocab
	 * @return ɾ���ɹ�����1��ʧ�ܷ���0
	 * @throws SQLException
	 */
	int deleteExamplesByVocab(Vocabulary vocab) throws SQLException;

	/**
	 * �����ӽ��и���,���ı�����Ӷ�Ӧ�Ĵʻ�
	 * 
	 * @param examId
	 * @return �ɹ�����1��ʧ�ܷ���0
	 * @throws SQLException
	 */
	int updateExampleById(long examId, String newExam) throws SQLException;

	/**
	 * Ϊĳ�ʻ����һ������
	 * 
	 * @param exam
	 * @return �ɹ�����1��ʧ�ܷ���0
	 * @throws SQLException
	 */
	int addExample(Example exam) throws SQLException;

	/**
	 * �����������
	 * 
	 * @param exams
	 * @return
	 * @throws SQLException
	 */
	int addAllExamples(List<Example> exams) throws SQLException;
}

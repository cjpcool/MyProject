package com.demo.service;

import java.sql.SQLException;
import java.util.List;

public interface UAdminService {
	int insertUA(long userId, long adminId) throws SQLException;
	/**
	 * 通过adminId查找出其对应的userId, 当前只有1对1关系, 因为是后期扩展,所以 特意添加了一个表, 改代码相对方便,
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	List<Long> selectUserIdByAdminId(long adminId) throws SQLException;
	/**
	 * 通过userId查找出其对应的adminId, 当前只有1对1关系, 因为是后期扩展,所以 特意添加了一个表, 改代码相对方便,
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	List<Long> selectAdminIdByUserId(long userId) throws SQLException;
}

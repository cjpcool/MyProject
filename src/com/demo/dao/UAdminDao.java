package com.demo.dao;

import java.sql.SQLException;
import java.util.List;

public interface UAdminDao {
	/**
	 * 插入一个admin到tb_admin, 将对应的user与admin插入到tb_uadmin关系中
	 * @param userId
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	int insertUA(long userId, long adminId)throws SQLException;
	
	List<Long> selectUserIdByAdminId(long adminId) throws SQLException;
	
	List<Long> selectAdminIdByUserId(long userId) throws SQLException;
}

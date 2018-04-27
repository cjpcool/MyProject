package com.demo.dao;

import com.demo.bean.Admin;

import java.sql.SQLException;

public interface AdminDao {
	Admin selectAdminById(long adminId) throws SQLException;

	int insertAdmin(Admin admin) throws SQLException;

	int deleteAdminById(long adminId) throws SQLException;
}

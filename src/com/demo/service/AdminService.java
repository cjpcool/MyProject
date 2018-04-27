package com.demo.service;

import com.demo.bean.Admin;

import java.sql.SQLException;

public interface AdminService {
	Admin login(long adminId) throws SQLException;

	int regist(Admin admin, long userId)throws SQLException;
	
	int deleteAdminById(long adminId) throws SQLException;
}

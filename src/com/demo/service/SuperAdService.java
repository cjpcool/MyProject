package com.demo.service;

import com.demo.bean.SuperAdmin;

import java.sql.SQLException;
import java.util.List;

public interface SuperAdService {
	SuperAdmin login(long superAdId) throws SQLException;

	int regist(SuperAdmin newSuperAdmin) throws SQLException;

	List<SuperAdmin> getAllSuperAdmins() throws SQLException;

	int deleteSuperAdminById(long superadId) throws SQLException;
}

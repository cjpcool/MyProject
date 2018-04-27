package com.demo.dao;

import com.demo.bean.SuperAdmin;

import java.sql.SQLException;
import java.util.List;

public interface SuperAdDao {
	SuperAdmin selectSuperAdById(long sperAd) throws SQLException;

	int insertSuperAdmin(SuperAdmin newSuperAdmin) throws SQLException;

	List<SuperAdmin> selectAllSuperAdmins() throws SQLException;

	int deleteSuperAdminById(long superadId) throws SQLException;
}

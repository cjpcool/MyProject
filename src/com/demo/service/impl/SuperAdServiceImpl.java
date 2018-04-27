package com.demo.service.impl;

import com.demo.bean.SuperAdmin;
import com.demo.dao.SuperAdDao;
import com.demo.dao.impl.SuperAdDaoImpl;
import com.demo.service.SuperAdService;

import java.sql.SQLException;
import java.util.List;

public class SuperAdServiceImpl implements SuperAdService {
	SuperAdDao sad = new SuperAdDaoImpl();
	@Override
	public SuperAdmin login(long superAdId) throws SQLException {
		SuperAdmin res = sad.selectSuperAdById(superAdId);
		return res;
	}
	@Override
	public int regist(SuperAdmin newSuperAdmin) throws SQLException {
		int res = sad.insertSuperAdmin(newSuperAdmin);
		return res;
	}
	@Override
	public List<SuperAdmin> getAllSuperAdmins() throws SQLException {
		List<SuperAdmin> res = sad.selectAllSuperAdmins();
		return res;
	}
	@Override
	public int deleteSuperAdminById(long superadId) throws SQLException {
		int res = sad.deleteSuperAdminById(superadId);
		return res;
	}

}

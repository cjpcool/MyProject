package com.demo.service.impl;

import com.demo.dao.UAdminDao;
import com.demo.dao.impl.UAdminDaoImpl;
import com.demo.service.UAdminService;

import java.sql.SQLException;
import java.util.List;

public class UAdminServiceImpl implements UAdminService {
	UAdminDao uad = new UAdminDaoImpl();
	@Override
	public int insertUA(long userId, long adminId) throws SQLException {
		int res = uad.insertUA(userId, adminId);
		return res;
	}

	@Override
	public List<Long> selectUserIdByAdminId(long adminId) throws SQLException {
		List<Long> res = uad.selectUserIdByAdminId(adminId);
		return res;
	}

	@Override
	public List<Long> selectAdminIdByUserId(long userId) throws SQLException {
		List<Long> res = uad.selectAdminIdByUserId(userId);
		return res;
	}

}

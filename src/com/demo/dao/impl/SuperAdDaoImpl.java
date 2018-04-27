package com.demo.dao.impl;

import com.demo.bean.SuperAdmin;
import com.demo.dao.SuperAdDao;
import com.demo.utils.JDBCTool;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class SuperAdDaoImpl implements SuperAdDao {
	ComboPooledDataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	@Override
	public SuperAdmin selectSuperAdById(long sperAd) throws SQLException {
		String sql = "select * from tb_superadmin where superadId=?";
		SuperAdmin res = qr.query(sql, new BeanHandler<SuperAdmin>(SuperAdmin.class), sperAd);
		return res;
	}
	@Override
	public int insertSuperAdmin(SuperAdmin newSuperAdmin) throws SQLException {
		String sql = "insert into tb_superadmin values(?,?,?)";
		int res = qr.update(sql, newSuperAdmin.getSuperadId(), newSuperAdmin.getSuperadName(), newSuperAdmin.getPwd());
		return res;
	}
	@Override
	public List<SuperAdmin> selectAllSuperAdmins() throws SQLException {
		String sql = "select* from tb_superadmin ";
		List<SuperAdmin> res = qr.query(sql, new BeanListHandler<SuperAdmin>(SuperAdmin.class));
		return res;
	}
	@Override
	public int deleteSuperAdminById(long superadId) throws SQLException {
		String sql = "delete from tb_superadmin where superadId=?";
		int res = qr.update(sql, superadId);
		return res;
	}
}
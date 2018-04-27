package com.demo.dao.impl;

import com.demo.bean.Admin;
import com.demo.dao.AdminDao;
import com.demo.utils.JDBCTool;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
	ComboPooledDataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);

	@Override
	public Admin selectAdminById(long adminId) throws SQLException {
		String sql = "select* from tb_admin where adminId=?";
		Admin res = qr.query(sql, new BeanHandler<Admin>(Admin.class), adminId);

		return res;
	}

	@Override
	public int insertAdmin(Admin admin) throws SQLException {
		// 为了支持事务控制
		qr = new QueryRunner();
		Connection conn = JDBCTool.getConnection();
		String sql = "insert into tb_admin  values(?,?,?)";
		int res = qr.update(conn, sql, admin.getAdminId(), admin.getAdminName(), admin.getPwd());
		return res;
	}

	@Override
	public int deleteAdminById(long adminId) throws SQLException {
		String sql = "delete from tb_admin where adminId=?";
		int res = qr.update(sql, adminId);

		return res;
	}

}

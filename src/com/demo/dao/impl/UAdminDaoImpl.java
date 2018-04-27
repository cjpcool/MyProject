package com.demo.dao.impl;

import com.demo.dao.UAdminDao;
import com.demo.utils.JDBCTool;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UAdminDaoImpl implements UAdminDao {
	ComboPooledDataSource ds =  JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	
	@Override
	public int insertUA(long userId, long adminId) throws SQLException {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCTool.getConnection();
		String sql = "insert into tb_uadmin(adminId, userId) values(?, ?)";
		int res = qr.update(conn, sql, adminId, userId);
		return res;
	}

	@Override
	public List<Long> selectUserIdByAdminId(long adminId) throws SQLException {
		String sql = "select userId from tb_uadmin where adminId=?";
		List<BigInteger> res1 = (List<BigInteger>) qr.query(sql, new ColumnListHandler<BigInteger>(), adminId);
		List<Long> res = new ArrayList<>();
		for(BigInteger item: res1){
			res.add(item.longValue());
		}
		return res;
	}

	@Override
	public List<Long> selectAdminIdByUserId(long userId) throws SQLException {
		String sql = "select adminId from tb_uadmin where userId=?";
		List<BigInteger> res1 = (List<BigInteger>) qr.query(sql, new ColumnListHandler<BigInteger>(), userId);
		List<Long> res = new ArrayList<>();
		for(BigInteger item: res1){
			res.add(item.longValue());
		}
		return res;
	}
}

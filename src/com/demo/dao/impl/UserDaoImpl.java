package com.demo.dao.impl;

import com.demo.bean.User;
import com.demo.dao.UserDao;
import com.demo.utils.JDBCTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{
	DataSource ds = JDBCTool.getDataSource();
	QueryRunner	qr = new QueryRunner(ds);
	
	@Override
	public List<User> selectAllUser() throws SQLException {
		String sql = "select* from tb_user";
		List<User> res = qr.query(sql, new BeanListHandler<User>(User.class));
		return res;
	}

	@Override
	public List<User> selectUsersByCategory(String category) throws SQLException {
		return null;
	}

	@Override
	public User selectUserByName(String userName) throws SQLException {
		String sql = "select* from tb_user where userName=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), userName);
		return user;
	}

	@Override
	public User selectUserById(long userId) throws SQLException {
		String sql = "select* from tb_user where userId=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), userId);
		return user;
	}

	@Override
	public int insertUser(User user) throws SQLException {
		String sql = "insert into tb_user(userId, userName, job, img, pwd, email, categoryId, outDate, validataCode) values(?,?,?,?,?,?,?,?,?)";
		int res = qr.update(sql, user.getUserId(), user.getUserName(), user.getJob(), user.getImg(), user.getPwd(),user.getEmail(), user.getCategoryId(), user.getOutDate(), user.getValidataCode());
		return res;
	}

	@Override
	public int updateBasicInfo(User user) throws SQLException {
		String sql = "update tb_user set pwd=?, img=?, userName=?, job=?,email=?, categoryId=?,outDate=?, validataCode=?  where userId=?";
		int res = qr.update(sql,user.getPwd(), user.getImg(), user.getUserName(), user.getJob(), user.getEmail(), user.getCategoryId(),user.getOutDate(), user.getValidataCode(),user.getUserId());
		return res;
	}

	@Override
	public int deleteUserById(long userId) throws SQLException {
		String sql = "delete from tb_user where userId=?";
		int res = qr.update(sql, userId);
		return res;
	}

}

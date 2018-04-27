package com.demo.service.impl;

import com.demo.bean.User;
import com.demo.dao.UserDao;
import com.demo.dao.impl.UserDaoImpl;
import com.demo.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
	UserDao ud = new UserDaoImpl();
	@Override
	public int regist(User user) throws SQLException {
		int res = ud.insertUser(user);
		return res;
	}
	@Override
	public User getUserByName(String userName) throws SQLException {
			User user = ud.selectUserByName(userName);
			return user;
	}
	@Override
	public int setBasicInfo(User user) throws SQLException {
		int res = ud.updateBasicInfo(user);
		return res;
	}
	@Override
	public User getUserById(long userId) throws SQLException {
		User res = ud.selectUserById(userId);
		return res;
	}
	@Override
	public int deleteUserById(long userId) throws SQLException {
		int res = ud.deleteUserById(userId);
		return res;
	}
	@Override
	public List<User> getAllUsers() throws SQLException {
		List<User> res = ud.selectAllUser();
		return res;
	}

}

package com.demo.service;

import com.demo.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
	/**
	 * 
	 * @param user
	 * @return 注册成功返回1，失败0
	 */
	int regist(User user) throws SQLException;

	User getUserByName(String userName) throws SQLException;

	/**
	 * 重置user的tel,userName, gender, categoryId,img
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	int setBasicInfo(User user) throws SQLException;

	User getUserById(long userId) throws SQLException;

	int deleteUserById(long userId) throws SQLException;

	List<User> getAllUsers() throws SQLException;
}

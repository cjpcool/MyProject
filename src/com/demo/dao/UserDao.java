package com.demo.dao;

import com.demo.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
	List<User> selectAllUser() throws SQLException;

	List<User> selectUsersByCategory(String category) throws SQLException;

	User selectUserByName(String userName) throws SQLException;

	User selectUserById(long userId) throws SQLException;

	int insertUser(User user)throws SQLException ;

	int updateBasicInfo(User user) throws SQLException;

	int deleteUserById(long userId) throws SQLException;
}

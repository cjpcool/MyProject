package com.demo.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCTool {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//存放当前线程的连接
	private static ThreadLocal<Connection> t1 = new ThreadLocal<Connection>();

	public static ComboPooledDataSource getDataSource(){
		return dataSource;
	}
	public static Connection getConnection() throws SQLException{
		/*
		 * 1. 取出当前线程的连接
		 * 2. 当con != null时，说明已经调用过本类的beginTransaction()方法了，
		 *        正是该方法给con赋的值，  表示开启了事务，所以把这个事务专用con返回，保证是同一个连接
		 * 3. 如果con = null ,说明没有调用过本类的beginTransaction()方法，
		 *        说明并没有开启事务，接下来的操作也不需要使用同一个连接对象，所以返回一个新的非事务专用的连接对象
		  */
		Connection con = t1.get();
		if(con != null){
			return con;
		}
		return dataSource.getConnection();
	}
	/**
	 * 事务处理一: 开启事务
	 * 开启的这个事务会一直贯穿到事务结束提交
	 */
	public static void beginTransaction() throws SQLException{
		/**
		 * 1.取出当前事务连接
		 * 2.防止重复开启
		 * 3.给con赋值,并且设con位手动提交
		 *        这个连接时事务传用连接
		 * 4.把当前线程连接保存下来, 供给其他的提交回滚 等多个类使用
		 */
		Connection con = t1.get();
		if(con != null){
			throw new SQLException("已开启事务, 不要重复开启!");
		}
		con = getConnection();
		con.setAutoCommit(false);
		t1.set(con);
	}
	
	/*
	 * 事务处理二:提交事务
	 * 　　获取开启的事务，然后提交
	 */
	public static void commitTransaction() throws SQLException{
		/**
		 * 1.获取当前线程的连接
		 * 2.防止事务未开启就提交,若未开启,则第一步获取的
		 * 3.提交事务
		 * 4.提交事务后整个事务结束,所以需要关闭连接
		 * 5.关闭了当前连接之后,线程内还是存在这个con,所以需要移除
		 */
		Connection con = t1.get();
		if(con == null){
			throw new SQLException("事务未开启,不能提交");
		}
		
		con.commit();
		
		con.close();
		t1.remove();
	}
	
	/**
	 * 事务处理三: 回滚事务
	 *     获取开启的事务, 调用rollback
	 * @throws SQLException 
	 */
	public static void rollbackTransaction() throws SQLException{
		/**
		 * 1.获取当前线程的连接
		 * 2.防止未开启事务就回滚
		 * 3.回滚
		 * 4.关闭连接, 移除事务
		 */
		Connection con = t1.get();
		if(con == null){
			throw new SQLException("事务未开启,不能回滚!");
		}
		
		con.rollback();
		con.close();
		t1.remove();
	}
	
	/**
	 * 释放连接
	 *   所有connection对象连接的关闭都要调用该方法
	 *   如果是事务专用连接: 则不能关闭
	 *   如果不是事务传用, 则需要关闭
	 *   
	 *在此项目中, 所以不需要关闭连接.
	 * @param connection
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		Connection con = t1.get();
		if(con == null){
			connection.close();
		}
		if(con != connection){
			connection.close();
		}
	}
	
}

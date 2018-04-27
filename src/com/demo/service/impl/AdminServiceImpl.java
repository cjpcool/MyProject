package com.demo.service.impl;

import com.demo.bean.Admin;
import com.demo.dao.AdminDao;
import com.demo.dao.UAdminDao;
import com.demo.dao.impl.AdminDaoImpl;
import com.demo.dao.impl.UAdminDaoImpl;
import com.demo.service.AdminService;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {
	AdminDao ad = new AdminDaoImpl();
	@Override
	public Admin login(long adminId) throws SQLException {
		Admin res = ad.selectAdminById(adminId);
		return res;
	}
	@Override
	public int regist(Admin admin, long userId) throws SQLException {
		int res = 0;
		UAdminDao ua = new UAdminDaoImpl();
		/*try{
			JDBCTool.beginTransaction();
			res = ad.insertAdmin(admin);
			if(res == 0){
				throw new SQLException();
			}
			res = ua.insertUA(userId, admin.getAdminId());
			
			JDBCTool.commitTransaction();
		}catch (SQLException e) {
			try{
				JDBCTool.rollbackTransaction();
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			throw e;
		}*/
		int res1 = ad.insertAdmin(admin);
		int res2 = 0;
		if(res1 == 1){//模拟事务
			res2 = ua.insertUA(userId, admin.getAdminId());
			if(res2 == 1){
				res = 1;
			}else{
				ad.deleteAdminById(admin.getAdminId());
			}
		}
		return res;
		
	}
	@Override
	public int deleteAdminById(long adminId) throws SQLException {
		int res = ad.deleteAdminById(adminId);
		return res;
	}

}

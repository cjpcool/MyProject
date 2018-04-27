package com.demo.dao.impl;

import com.demo.bean.Category;
import com.demo.dao.CategoryDao;
import com.demo.utils.JDBCTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao{
	DataSource ds = JDBCTool.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	@Override
	public List<Category> getAllCategories() throws SQLException {
		String sql = "select * from tb_category;";
		List<Category> res = qr.query(sql, new BeanListHandler<Category>(Category.class));
		
		return res;
	}
	@Override
	public Category selectCategoryById(long categoryId) throws SQLException {
		String sql = "select * from tb_category where categoryId =?";
		Category res = qr.query(sql, new BeanHandler<Category>(Category.class), categoryId);
		return res;
	}
	@Override
	public int deleteCategoryById(long categoryId) throws SQLException {
		String sql = "delete from tb_category where categoryId=?";
		int res = qr.update(sql, categoryId);
		return res;
	}
	@Override
	public Category insertCategory(Category category) throws SQLException {
		String sql = "insert into tb_category(categoryName=? hotlevel=?)";
		Category res = qr.insert(sql, new BeanHandler<Category>(Category.class), category.getCategoryName(), category.getHotLevel());
		return res;
	}
	@Override
	public int updateCategoryLevel(long hotLevel, long categoryId) throws SQLException {
		String sql = "update tb_category set hotlevel=? where categoryId=?";
		int res = qr.update(sql, hotLevel, categoryId);
		return res;
	}
	@Override
	public long getCategoryLevelById(long categoryId) throws SQLException {
		String sql = "select hotlevel from tb_category where categoryId=?";
		BigInteger res = qr.query(sql,new ScalarHandler<BigInteger>(), categoryId);
		return res.longValue();
	}

}

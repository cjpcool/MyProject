package com.demo.service.impl;

import com.demo.bean.Category;
import com.demo.dao.CategoryDao;
import com.demo.dao.impl.CategoryDaoImpl;
import com.demo.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService{
	CategoryDao cd = new CategoryDaoImpl();
	@Override
	public List<Category> getAllCategories() throws SQLException {
		List<Category> res = cd.getAllCategories();
		return res;
	}
	@Override
	public Category getCategoryById(long categoryId) throws SQLException {
		Category res = cd.selectCategoryById(categoryId);
		return res;
	}
	@Override
	public int deleteCategoryById(long categoryId) throws SQLException {
		int res = cd.deleteCategoryById(categoryId);
		return res;
	}
	@Override
	public Category insertCategory(Category category) throws SQLException {
		Category res = cd.insertCategory(category);
		return res;
	}
	@Override
	public int updateCategoryLevel(long categoryId) throws SQLException {
		long hotLevel = cd.getCategoryLevelById(categoryId);
		int res = cd.updateCategoryLevel(hotLevel+1, categoryId);
		return res;
	}

}

package com.demo.dao;

import com.demo.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
	List<Category> getAllCategories() throws SQLException;

	Category selectCategoryById(long categoryId) throws SQLException;

	int deleteCategoryById(long categoryId) throws SQLException;

	/**
	 * 返回插入的对象
	 * 
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	Category insertCategory(Category category) throws SQLException;

	int updateCategoryLevel(long hotLevel, long categoryId) throws SQLException;

	long getCategoryLevelById(long categoryId) throws SQLException;
}

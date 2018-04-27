package com.demo.service;

import com.demo.bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
	List<Category> getAllCategories() throws SQLException;

	Category getCategoryById(long categoryId) throws SQLException;

	int deleteCategoryById(long categoryId)throws SQLException;
	
	Category insertCategory(Category category)throws SQLException;

	int updateCategoryLevel(long categoryId)throws SQLException;
}

package com.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.*;
import com.demo.service.AdminService;
import com.demo.service.CategoryService;
import com.demo.service.UAdminService;
import com.demo.service.UserService;
import com.demo.service.impl.AdminServiceImpl;
import com.demo.service.impl.CategoryServiceImpl;
import com.demo.service.impl.UAdminServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.utils.GenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
@WebServlet(description = "categoryController", urlPatterns = { "/categoryController" })
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		CategoryService cs = new CategoryServiceImpl();
		PrintWriter out = response.getWriter();
		
		switch (op) {
		case "getCategoryOption":
			getCategoryOption(cs, out);
			
			break;
		case "getUserCategory":
			getUserCategory(request, new UserServiceImpl(), out);
			break;
		case "getAllCategories":
			getAllCategories(request, cs, out);
			break;
		case "deleteCategory":
			deleteCategory(request, cs, out);
			break;
		case"adminAddCategory":
			adminAddCategory(request, cs, out); 
			break;
		case "getHotCategories":
			try {
				List<Category> allCategories = cs.getAllCategories(); 
				Collections.sort(allCategories, new Comparator<Category>() {

					@Override
					public int compare(Category o1, Category o2) {
						if( o1.getHotLevel()-o2.getHotLevel() <= 0)
							return 1;
						else
							return -1;
					}
				});
				
				String jsonString = JSONArray.toJSONString(allCategories);
				out.print(jsonString);
				out.flush();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			break;
		case"getCategoryById":
			String categoryIdStr = request.getParameter("categoryId");
			if(categoryIdStr != null && categoryIdStr.length() > 0){
				long categoryId = Long.parseLong(categoryIdStr);
				try {
					Category category = cs.getCategoryById(categoryId);
					out.print(category.getCategoryName());
					out.flush();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			break;
		default:
			break;
		}
	}

	private void adminAddCategory(HttpServletRequest request, CategoryService cs, PrintWriter out) {
		/**
		 * 
		 * 1 获取传递过来的用户名 专业名, 管理员名称 
		 * 验证
		 * 2 生成完整用户, 管理员, 专业
		 * 3 添加专业, 添加用户, 添加管理员
		 * 4 将结果传递到新的页面
		 */
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String adminName = request.getParameter("adminName");
		String adminPwd = request.getParameter("adminPwd");
		String categoryName = request.getParameter("categoryName");
		String checkCode = request.getParameter("checkCode");
		HttpSession session = request.getSession();
		Object code = session.getAttribute("code");
		String email = request.getParameter("email");
		
		
		//验证码
		if(!checkCode.equals(code)){
			//验证码错误
			out.print(2);
			out.flush();
			return ;
		}
		if(!GenUtil.emaileCheck(email)){
			//邮箱格式正确
			out.print(3);
			out.flush();
			return ;
		}
		
		try {
			AdminService as = new AdminServiceImpl(); 
			long adminId = GenUtil.getMgrId();;
			while (as.login(adminId) != null) {
				adminId += new Random().nextInt(10);
			}
			long userId = Long.parseLong(GenUtil.genId());
			Category category = new Category(categoryName, 0);
			//添加专业
			category = cs.insertCategory(category);
			
			//添加用户
			int uRes = 0;
			UserService us = new UserServiceImpl();
			if(category != null){
				User user = new User(userId, userName, userPwd, email, category.getCategoryId());
				uRes = us.regist(user);
			}else{
				out.print(0);
				out.flush();
				return ;
			}
			//添加管理员
			if(uRes == 0){
				out.print(0);
				out.flush();
				return ;
			}
			Admin admin = new Admin(adminId, adminName, adminPwd);
			int adRes = as.regist(admin, userId);
			if(adRes == 0){
				us.deleteUserById(userId);
			}else{
				UAdminService uads = new UAdminServiceImpl();
				int uaRes = uads.insertUA(userId, adminId);
			}
		} catch (Exception e) {
			out.print(0);
			out.flush();
			e.printStackTrace();
		}
	}

	private void deleteCategory(HttpServletRequest request, CategoryService cs, PrintWriter out) {
		Object superAdminObj = request.getSession().getAttribute("superAdmin");
		//必须是超管的操作
		if ( superAdminObj == null || superAdminObj instanceof SuperAdmin) {
			out.print(2);
			out.flush();
			return ;
		}
		String categoryIdStr = request.getParameter("categoryId");
		long categoryId = -1;
		if(categoryIdStr != null && categoryIdStr.length() > 0){
			categoryId = Long.parseLong(categoryIdStr);
		}
		try {
			int res = cs.deleteCategoryById(categoryId);
			out.print(res);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAllCategories(HttpServletRequest request, CategoryService cs, PrintWriter out) {
		try {
			List<Category> categories = cs.getAllCategories();
			String categoryName = request.getParameter("categoryName");
			if(categoryName != null && categoryName.length() > 0){
				//过滤
				Iterator<Category> iterator = categories.iterator();
				while(iterator.hasNext()){
					if(!iterator.next().getCategoryName().matches(".*"+categoryName+".*")){
						iterator.remove();
					}
				}
			}
			String res = pageSplit(request, categories);
			out.print(res);
			out.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String pageSplit(HttpServletRequest request, List<Category> categories) {
		String limitStr = request.getParameter("limit");
		int limit = 0;
		if (limitStr != null && limitStr.length() > 0) {
			limit = Integer.parseInt(limitStr);
		}
		String pageIndexStr = request.getParameter("pageIndex");
		int pageIndex = 0;
		if (pageIndexStr != null && pageIndexStr.matches("\\d+")) {
			pageIndex = Integer.parseInt(pageIndexStr);
		}

		int lastIndex = pageIndex + limit;
		if (lastIndex > categories.size()) {
			lastIndex = categories.size();
		}

		Page page = new Page();
		page.setPage(pageIndex);
		page.setTotal(categories.size());

		List<Category> rows = categories.subList(pageIndex, lastIndex);
		page.setRows(rows);
		String res = JSONObject.toJSONString(page);
		return res;
	}
	private void getUserCategory(HttpServletRequest request, UserService us, PrintWriter out) {
		String userIdStr = request.getParameter("userId");
		long userId = 0;
		if(userIdStr != null && userIdStr.length() > 0){
			userId = Long.parseLong(userIdStr);
		}
		try {
			User user = us.getUserById(userId);
			if(user != null){
				CategoryService cs = new CategoryServiceImpl();
				long categoryId = user.getCategoryId();
				Category category = cs.getCategoryById(categoryId);
				out.print(category.getCategoryName());
				out.flush();
			}else{
				out.print('1');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getCategoryOption(CategoryService cs, PrintWriter out) {
		try {
			List<Category> allCategories = cs.getAllCategories();
			JSONArray jsonArr= new JSONArray();
			for(Category category: allCategories){
				jsonArr.add(category);
			}
			out.print(jsonArr);
			out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

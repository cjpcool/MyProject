package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.demo.bean.User;
import com.demo.service.UAdminService;
import com.demo.service.impl.UAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class UAdminController
 */
@WebServlet(description = "uAdminController", urlPatterns = { "/uAdminController" })
public class UAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		UAdminService uas = new UAdminServiceImpl();
		PrintWriter out = response.getWriter();
		switch (op) {
		case "getAdminId":
			String userIdStr = request.getParameter("userId");
			long userId = 0;
			if(userIdStr != null && userIdStr.length() > 0){
				userId = Long.parseLong(userIdStr);
			}else{
				User user = (User) request.getSession().getAttribute("user");
				userId = user.getUserId();
			}
			try {
				List<Long> adminIds = uas.selectAdminIdByUserId(userId);
				String jString = JSON.toJSONString(adminIds);
				out.print(jString);
				out.flush();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;

		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

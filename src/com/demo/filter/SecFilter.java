package com.demo.filter;

import com.demo.bean.Admin;
import com.demo.bean.SuperAdmin;
import com.demo.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter("/sec/*")
public class SecFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		User user = (User)req.getSession().getAttribute("user");
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		SuperAdmin superAdmin = (SuperAdmin) req.getSession().getAttribute("superAdmin");
		
		if(user == null && admin == null && superAdmin == null){
			res.sendRedirect(req.getContextPath()+"/reLogin.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

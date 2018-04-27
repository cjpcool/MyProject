package com.demo.filter;

import com.demo.bean.SuperAdmin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class SuperAdminFilter
 */
//@WebFilter(urlPatterns = {"/mgr/superAdminRegist.jsp", "/mgr/userAdmin.jsp", "/mgr/wordsAdmin.jsp"})
public class SuperAdminFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SuperAdminFilter() {
        // TODO Auto-generated constructor stub
    }

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
		SuperAdmin superAdmin = (SuperAdmin) req.getSession().getAttribute("superAdmin");
		if(superAdmin == null ){
			req.setAttribute("note", "抱歉,您没有权限访问改页面!");
			req.setAttribute("url", "mgr/manage.jsp");
			req.getRequestDispatcher("../notice.jsp").forward(req, res);
			return ;
		}
		
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

package com.demo.filter;

import com.demo.bean.Admin;
import com.demo.bean.SuperAdmin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/mgr/")
public class AdminFilter implements Filter {

    public AdminFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		SuperAdmin superAdmin = (SuperAdmin) req.getSession().getAttribute("superAdmin");
		if(admin == null && superAdmin == null ){
			req.setAttribute("note", "抱歉,尊敬的管理员,您还没有登陆!");
			req.setAttribute("url", "CommanderLogin");
			req.getRequestDispatcher("../notice.jsp").forward(req, res);
			return ;
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

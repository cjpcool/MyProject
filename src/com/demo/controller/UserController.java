package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.Page;
import com.demo.bean.User;
import com.demo.service.UAdminService;
import com.demo.service.UserService;
import com.demo.service.VocabularyService;
import com.demo.service.impl.UAdminServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.service.impl.VocabularyServiceImple;
import com.demo.utils.GenUtil;
import com.demo.utils.JiaJieMiUtil;
import com.demo.utils.MD5;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet implementation class UserController
 */
@MultipartConfig
@WebServlet(description = "userController", urlPatterns = { "/userController" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		UserService us = new UserServiceImpl();
		PrintWriter out = response.getWriter();
		switch (op) {
		case "login":
			login(response, request, us, out);
			break;
		case "regist":
			regist(response, request, us);
			break;
		case "logout":
			request.getSession().setAttribute("user", null);
			response.sendRedirect("index.jsp"); // 请求转发
			break;
		case "nameExist":
			nameExist(request, us, out);
			break;
		case "changeBasicInfo":
			changeBasicInfo(response, request, us, out);
			break;
		case "emailCheck":
			userEmailCheck(request, out);
			break;
		case "changePwd":
			changePwd(request, response, us, out);
			break;
		case "getUserById":
			getUserById(request, us, out);
			break;
		case "countUserVocab":
			countUserVocab(request, out);
			break;
		case "deleteUser":
			deleteUser(request, us, out);
			break;
		case "getEmail":
			getEmail(request, out);
			break;
			
		case "getJob":
			getJob(request, out);
			break;

		case "getAllUsers":
			getAllUsers(request, us, out);
			break;
		default:
			break;
		}
	}

	private void getEmail(HttpServletRequest request, PrintWriter out) {
		try{
			User user = (User) request.getSession().getAttribute("user");
			out.print(user.getEmail());
			out.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getJob(HttpServletRequest request, PrintWriter out) {
		try{
			User user = (User) request.getSession().getAttribute("user");
			out.print(user.getJob());
			out.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAllUsers(HttpServletRequest request, UserService us, PrintWriter out) {
		List<User> users = null;
		try {
			users = us.getAllUsers();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<User> userFilter = resFilter(request, us, users);
		String res = pageSplit(request, userFilter);
		out.print(res);
		out.flush();
	}

	private List<User> resFilter(HttpServletRequest request, UserService us, List<User> users) {
		String adminIdStr = request.getParameter("adminId");
		long adminId = -1;
		if(adminIdStr != null && adminIdStr.matches("\\d+")){
			adminId = Long.parseLong(adminIdStr);
		}
		String userName = request.getParameter("userName");
		List<User> res = new ArrayList<>();
		// 过滤user
		if (userName != null && userName.length() > 0) {
			users.forEach(user -> {
				if (user.getUserName().equals(userName)) {
					res.add(user);
				}
			});
		}
		//过滤adminId
		if(adminId >= 0){
			UAdminService uas = new UAdminServiceImpl();
			try {
				List<Long> userIds = uas.selectUserIdByAdminId(adminId);
				for(int i = 0; i < userIds.size(); i++){
					User user = us.getUserById(userIds.get(i));
					res.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//过滤之后没有结果
		if(res.isEmpty()){
			res.addAll(users);
		}
		return res;
	}

	private void deleteUser(HttpServletRequest request, UserService us, PrintWriter out) {
		String userIdStr = request.getParameter("userId");
		long userId = 0;
		if (userIdStr != null && userIdStr.length() > 0) {
			userId = Long.parseLong(userIdStr);
		}
		try {
			int res = us.deleteUserById(userId);
			out.print(res);
			out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String pageSplit(HttpServletRequest request, List<User> users) {
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
		if (lastIndex > users.size()) {
			lastIndex = users.size();
		}

		Page page = new Page();
		page.setPage(pageIndex);
		page.setTotal(users.size());

		List<User> rows = users.subList(pageIndex, lastIndex);
		page.setRows(rows);
		String res = JSONObject.toJSONString(page);
		return res;
	}

	private void countUserVocab(HttpServletRequest request, PrintWriter out) {
		String userIdStr = request.getParameter("userId");
		long userId = 1;
		if(userIdStr != null && userIdStr.length() > 0){
			userId = Long.parseLong(userIdStr);
		}
		String statusStr = request.getParameter("status");
		short status = 1;
		if(statusStr != null && statusStr.length() > 0){
			status = Short.parseShort(statusStr);
		}
		
		VocabularyService vs = new VocabularyServiceImple();
		try {
			long count = vs.countVocabByUserId(userId, status);
			out.print(count);
			out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getUserById(HttpServletRequest request, UserService us, PrintWriter out) {
		String userIdStr = request.getParameter("userId");
		User user = null;
		try {
			long userId = 0;
			if (userIdStr != null && userIdStr.length() > 0) {
				userId = Long.parseLong(userIdStr);
			}
			user = us.getUserById(userId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.print(JSONObject.toJSONString(user));
		out.flush();
	}

	private void changePwd(HttpServletRequest request, HttpServletResponse response, UserService us, PrintWriter out)
			throws IOException {
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String confirmPwd = request.getParameter("confirmPwd");

		if (!newPwd.equals(confirmPwd)) {
			out.print("<script>alert('两次输入密码不一致,请重试!');history.go(-1)</script>;");
			// 保证后面代码不执行
			return;
		}
		// 获取当前sesion
		Object userObj = request.getSession().getAttribute("user");
		if (userObj == null) {
			response.sendRedirect("reLogin.jsp");
			return;
		}
		User user = (User) userObj;

		// 获取其原密码
		String pwd = user.getPwd();
		// 解密
		pwd = JiaJieMiUtil.JM(pwd);
		// 旧密码进行md5加密
		oldPwd = MD5.md5(oldPwd);
		if (!oldPwd.equals(pwd)) {
			out.print("<script>alert('密码错误,请重试!');history.go(-1)</script>;");
			return;
		}

		// 对新密码进行加密
		newPwd = JiaJieMiUtil.KL(MD5.md5(newPwd));
		// 修改密码
		user.setPwd(newPwd);
		try {
			int res = us.setBasicInfo(user);
			if (res == 1) {
				request.getSession().removeAttribute("user");
				out.print("<script>alert('密码修改成功!');history.go(-1)</script>;");
			} else {
				out.print("<script>alert('密码修改出现错误,请重试!');history.go(-1)</script>;");
			}
		} catch (SQLException e) {
			out.print("<script>alert('密码修改出现错误,请重试!');history.go(-1)</script>;");
			e.printStackTrace();
		}
	}

	private boolean isCode(Object code, String checkCode) {
		if (code.equals(checkCode)) {
			return true;
		} else
			return false;
	}

	private void regist(HttpServletResponse response, HttpServletRequest request, UserService us)
			throws IOException, ServletException {
		// 验证码验证
		Object code = request.getSession().getAttribute("code");
		String checkCode = request.getParameter("checkCode");
		if (!isCode(code, checkCode)) {
			response.getWriter().print("<script>alert(\"验证码错误,重新输入!\");history.go(-1);</script>");
			return;
		}

		// 创建新的对象
		User user = new User();
		// 获取专业编号
		String categoryIdStr = request.getParameter("categories");
		long categoryId = -1;
		if (categoryIdStr != null && categoryIdStr.length() >= 0) {
			categoryId = Long.parseLong(categoryIdStr);
		}
		user.setCategoryId(categoryId);
		// 获取用户名
		String userName = request.getParameter("userName");
		try {
			if (userName == null || userName.length() == 0 || us.getUserByName(userName) != null) {
				response.getWriter().print("<script>alert(\"用户名已存在或您输入错误,请重新填写\");history.go(-1)</script>");
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		user.setUserName(userName);

		// 对密码进行处理
		String pwd = request.getParameter("pwd");
		String checkPwd = request.getParameter("checkPwd");
		if (!pwd.equals(checkPwd)) {
			response.getWriter().print("<script>alert(\"密码不一致,请检查\");history.go(-1)</script>");
			return;
		}
		// MD5
		String md5Pwd = MD5.md5(pwd);
		// 加密
		String encryptPwd = JiaJieMiUtil.KL(md5Pwd);
		user.setPwd(encryptPwd);
		// 获取性别
		String jobStr = request.getParameter("job");
		short job = 3;
		try{
			job = Short.parseShort(jobStr);
		}catch (Exception e) {
			e.printStackTrace();
		}
		user.setJob(job);
		// 获取电话
		String email = request.getParameter("email");
		if (!GenUtil.emaileCheck(email)) {
			response.getWriter().print("<script>alert(\"邮箱格式有误,请检查\");history.go(-1)</script>");
			return;
		}
		user.setEmail(email);
		// 文件上传
		Part part = request.getPart("img");
		String imgName = "Chrysanthemum";
		String suffix = ".jpg";
		if (part != null && part.getSize() >= 1) {
			// 获取文件后缀
			String header = part.getHeader("content-disposition");
			suffix = header.substring(header.lastIndexOf("."), header.length() - 1);
			// 随机产生文件名
			imgName = GenUtil.genImgName();

			// 保存文件
			String realPath = request.getServletContext().getRealPath("/") + "img/user/";
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			part.write(realPath + imgName + suffix);
			String img = imgName + suffix;
			// 路径保存至对象内
			user.setImg(img);
		}
		user.setUserId(Long.parseLong(GenUtil.genId()));
		try {
			int res = us.regist(user);
			if (res == 1) {
				request.setAttribute("url", "index.jsp");
				request.setAttribute("note", "注册成功,正在跳转至主页!");
				request.getRequestDispatcher("notice.jsp").forward(request, response);
			} else {
				request.setAttribute("url", "regist.jsp");
				request.setAttribute("note", "注册出现未知错误,请重新注册,或向管理员反馈");
				request.getRequestDispatcher("notice.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("url", "regist.jsp");
			request.setAttribute("note", "注册出现未知错误,请重新注册,或向管理员反馈");
			request.getRequestDispatcher("notice.jsp").forward(request, response);
			e.printStackTrace();
		}
	}

	private void userEmailCheck(HttpServletRequest request, PrintWriter out) {
		String email = request.getParameter("email");
		if (GenUtil.emaileCheck(email)) {
			out.print("{\"res\": \"true\"}");
		} else {
			out.print("{\"res\": \"false\"}");
		}
		out.flush();
	}

	/**
	 * 修改秀爱用户基本信息
	 */
	protected void changeBasicInfo(HttpServletResponse response, HttpServletRequest request, UserService us,
			PrintWriter out) {
		Object userObj = request.getSession().getAttribute("user");
		User user = null;
		if (userObj instanceof User && userObj != null) {
			user = (User) userObj;
		}
		if (user == null) {
			try {
				response.sendRedirect("reLogin.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String pwd = request.getParameter("pwd");

		// 检验密码不为空 对pwd进行MD5加密
		if (pwd != null && MD5.md5(pwd).equals(JiaJieMiUtil.JM(user.getPwd()))) {
			try {
				// 图片上传
				Part part = request.getPart("img");
				// 默认图片
				String imgName = "";
				String suffix = "";
				if (part != null && part.getSize() >= 1) {
					// 获取上传文件后缀
					String header = part.getHeader("content-disposition");
					suffix = header.substring(header.lastIndexOf("."), header.length() - 1);
					// 随机生产文件名;
					imgName = GenUtil.genImgName();

					// 保存文
					String realPath = request.getServletContext().getRealPath("/") + "img/user/";
					File file = new File(realPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					part.write(realPath + imgName + suffix);
					String img = imgName + suffix;
					user.setImg(img);
				}

				String categoryIdStr = request.getParameter("categories");
				long categoryId = -1;
				// Id
				if (categoryIdStr!= null && categoryIdStr.length() > 0) {
					categoryId = Long.parseLong(categoryIdStr);
					user.setCategoryId(categoryId);
				}
				// 性别
				String jobStr = request.getParameter("job");
				short job = 0;
				if (jobStr != null && jobStr.length() > 0) {
					job = Short.parseShort(jobStr);
					user.setJob(job);
				}
				// email
				String email = request.getParameter("email");
				if (email != null && email.length() > 0 && !GenUtil.emaileCheck(email)) {
					out.print("<script>alert(\"邮箱格式错误,请检查\");history.go(-1)</script>");
					out.flush();
				} else if (email != null) {
					user.setEmail(email);
				}
				// 用户名
				String newName = request.getParameter("newName");
				if (newName != null && newName.length() > 0 && us.getUserByName(newName) == null) {
					user.setUserName(newName);
				} else if (us.getUserByName(newName) != null) {
					out.print("<script>alert(\"新的用户名存在,请检查!\");history.go(-1)</script>");
					out.flush();
				}
				user.setCategoryId(categoryId);
				user.setJob(job);
				// 持久化数据
				int res = us.setBasicInfo(user);
				if (res == 1) {
					out.print("<script>alert(\"修改成功\");history.go(-1)</script>");
					out.flush();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			out.print("<script>alert('密码错误,请重新填写');history.go(-1)</script>");
		}
	}

	protected void nameExist(HttpServletRequest request, UserService us, PrintWriter out) {
		String userName = request.getParameter("userName");
		User user = null;
		try {
			user = us.getUserByName(userName);
		} catch (SQLException e) {
			out.print("{\"res\":\"notExist\"}");
			e.printStackTrace();
		}
		if (user != null) {
			out.print("{\"res\":\"exist\"}");
			out.flush();
		}else{
			out.print("{\"res\":\"notExist\"}");
		}
		out.flush();
	}

	protected void login(HttpServletResponse response, HttpServletRequest request, UserService us, PrintWriter out) {
		String userName = request.getParameter("userName");
		// 原密码
		String oldPwd = request.getParameter("pwd");
		// 密码进行MD5处理后再判断
		String md5Pwd = MD5.md5(oldPwd);
		HttpSession session = request.getSession();
		Object code = session.getAttribute("code");
		String checkCode = request.getParameter("checkCode");

		// 返回json status:1代表验证码错误, 2代表帐户错误, 3代表密码错误, 4代表位置错误 ,0代表成功并返回json独享
		// 验证码进行正则匹配, 忽略大小写
		Pattern p = Pattern.compile("^" + code + "$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(checkCode);
		if (!matcher.matches()) {
			out.print("{\"status\":\"1\"}");
			out.flush();
			return;
		}
		User user = null;
		try {
			user = us.getUserByName(userName);
		} catch (SQLException e) {
			out.print("{\"status\":\"2\"}");
			out.flush();
			e.printStackTrace();
			return;
		}
		if (user == null) {
			out.print("{\"status\":\"2\"}");
			out.flush();
			return;
		} else if (!JiaJieMiUtil.JM((user.getPwd())).equals(md5Pwd)) {
			out.print("{\"status\":\"3\"}");
			out.flush();
			return;
		}
		session.setAttribute("user", user);
		out.print("{\"status\":\"0\"}");
	}

}

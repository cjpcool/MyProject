package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.Admin;
import com.demo.bean.Page;
import com.demo.bean.SuperAdmin;
import com.demo.bean.User;
import com.demo.service.AdminService;
import com.demo.service.SuperAdService;
import com.demo.service.UAdminService;
import com.demo.service.VocabularyService;
import com.demo.service.impl.AdminServiceImpl;
import com.demo.service.impl.SuperAdServiceImpl;
import com.demo.service.impl.UAdminServiceImpl;
import com.demo.service.impl.VocabularyServiceImple;
import com.demo.utils.Const;
import com.demo.utils.GenUtil;
import com.demo.utils.JiaJieMiUtil;
import com.demo.utils.MD5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet implementation class AdminController
 */
@WebServlet(name = "adminController", urlPatterns = { "/adminController" })
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		PrintWriter out = response.getWriter();

		// superadminer service
		SuperAdService sads = new SuperAdServiceImpl();
		// admin service
		AdminService ads = new AdminServiceImpl();
		// vocab service
		VocabularyService vs = new VocabularyServiceImple();

		UAdminService uads = new UAdminServiceImpl();
		HttpSession session = request.getSession();

		switch (op) {
		case "login":
			login(request, response, out, sads, ads, session);

			break;
		case "logout":
			session.setAttribute("admin", null);
			session.setAttribute("superAdmin", null);
			out.print("<script>alert('注销成功!');window.location.href='CommanderLogin.html'; </script>");
			break;
		case "adminRegist":
			adminRegist(request, response, out, ads, vs, session);
			break;
		case "superAdminRegist":
			superAdminRegist(request, out, sads, session);
			break;
		case "getAllsuperAdmins":
			try {
				List<SuperAdmin> superAdmins = sads.getAllSuperAdmins();

				String res = pageSplit(request, superAdmins);
				out.print(res);
				out.flush();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "deleteSuperAdmin":
			deleteSuperAdmin(request, out, sads, session);
			break;
		case "checkPwd":
			checkPwd(request, out);
			break;
		case "deleteAdmin":
			deleteAdmin(request, out, ads, uads);

			break;
		default:
			break;
		}

	}

	/**
	 * 必须是超管进行的操作, 该超管不能对自己登陆的帐户进行删除//保证了数据库必定保留至少一个超管
	 * 
	 * @param request
	 * @param out
	 * @param sads
	 * @param session
	 */
	private void deleteSuperAdmin(HttpServletRequest request, PrintWriter out, SuperAdService sads,
			HttpSession session) {
		try {
			String superadIdStr = request.getParameter("superadId");
			long superadId = 0;
			SuperAdmin superAdmin = (SuperAdmin) session.getAttribute("superAdmin");
			if (superAdmin != null) {
				if (superadId == superAdmin.getSuperadId()) {
					out.print(0);
					out.flush();
					return;
				}
			} else {
				out.print(0);
				out.flush();
				return;
			}
			if (superadIdStr != null && superadIdStr.length() > 0) {
				superadId = Long.parseLong(superadIdStr);
			}
			int res = sads.deleteSuperAdminById(superadId);
			out.print(res);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String pageSplit(HttpServletRequest request, List<SuperAdmin> superAds) {
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
		if (lastIndex > superAds.size()) {
			lastIndex = superAds.size();
		}

		Page page = new Page();
		page.setPage(pageIndex);
		page.setTotal(superAds.size());

		List<SuperAdmin> rows = superAds.subList(pageIndex, lastIndex);
		page.setRows(rows);
		String res = JSONObject.toJSONString(page);
		return res;
	}

	/**
	 * 必须是超管进行添加超管
	 * 
	 * @param request
	 * @param out
	 * @param sads
	 * @param session
	 */
	private void superAdminRegist(HttpServletRequest request, PrintWriter out, SuperAdService sads,
			HttpSession session) {
		try {
			Object superAdminObj = (Object) session.getAttribute("superAdmin");
			if (superAdminObj == null || !(superAdminObj instanceof SuperAdmin)) {
				out.print("<script>alert('请先登陆您的超管帐户!');window.location.href='../CommanderLogin.html'</script>");
				return;
			}
			String checkCode = request.getParameter("checkCode");
			Object code = session.getAttribute("code");
			// 验证码进行正则匹配, 忽略大小写
			Pattern p = Pattern.compile("^" + code + "$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(checkCode);
			if (!matcher.matches()) {
				out.print("<script>alert('验证码错误,请重新填写');window.location.href='mgr/superAdminRegist.jsp'; </script>");
				out.flush();
				return;
			}

			String superAdminName = request.getParameter("superAdminName");
			String pwd = request.getParameter("pwd");
			if (GenUtil.pwdSecury(pwd)) {
				pwd = JiaJieMiUtil.KL(MD5.md5(pwd));
			}
			long superadId = GenUtil.getMgrId();
			while (sads.login(superadId) != null) {
				superadId = GenUtil.getMgrId();
			}
			SuperAdmin newSuperAdmin = new SuperAdmin(superadId, superAdminName, pwd);
			int res = sads.regist(newSuperAdmin);
			if (res == 0) {
				out.print("<script>alert('添加失败,请重试!');window.location.href='mgr/superAdminRegist.jsp'</script>");
			} else {
				out.print("<script>alert('添加成功,转到管理员页面!');window.location.href='mgr/manage.jsp'</script>");
			}
		} catch (SQLException e) {
			out.print("<script>alert('添加失败,请重试!');window.location.href='mgr/superAdminRegist.jsp'</script>");
			e.printStackTrace();
		}
	}

	private void deleteAdmin(HttpServletRequest request, PrintWriter out, AdminService ads, UAdminService uads) {
		try {
			String userIdStr = request.getParameter("userId");
			long userId = Long.parseLong(userIdStr);

			List<Long> adminIds = uads.selectAdminIdByUserId(userId);

			int res = ads.deleteAdminById(adminIds.get(0));
			out.print(res);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkPwd(HttpServletRequest request, PrintWriter out) {
		String pwd = request.getParameter("pwd");
		if (GenUtil.pwdSecury(pwd)) {
			out.print("1");
		} else {
			out.print("0");
		}
		out.flush();
	}

	/**
	 * 1 驗證是否是超级管理员再进行注册 若是, 可直接注册管理员账号, 该账号对应user为Null 若不是,则是用户注册, 需对该用户进行验证 //
	 * 验证该user是否已经上传了150个单词 // 如果该用户上传单词达标,则可注册帐户, 否则拒绝注册并返回用户个人信息页面 //
	 * 如果是以超级管理员身份注册,则不需要进行验证
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @param ad
	 * @param vs
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void adminRegist(HttpServletRequest request, HttpServletResponse response, PrintWriter out, AdminService ad,
			VocabularyService vs, HttpSession session) throws ServletException, IOException {
		// 验证该user是否已经上传了150个单词
		// 如果该用户上传单词达标,则可注册帐户, 否则拒绝注册并返回用户个人信息页面
		// 如果是以超级管理员身份注册,则不需要进行验证
		Object superAdminObj = session.getAttribute("superAdmin");
		Object userObj = session.getAttribute("user");
		User user = null;
		// 若超管未登陆
		if (superAdminObj == null || !(superAdminObj instanceof SuperAdmin)) {
			if (userObj != null) {
				user = (User) userObj;
			} else {
				out.print("<script>alert('用户登陆失效,请先登陆您的帐户);window.location.href='sec/userInfo.jsp'</script>");
				return;
			}
			try {
				short status = 1;
				// 验证在本专业上传的单词上传数量是否达标
				long sum = vs.countVocabByUserIdAndCategoryId(user.getUserId(), status, user.getCategoryId());
				if (sum < Const.VOCAB_NUM_BE_ADMIN) {
					out.print("<script>alert('您再您的专业已上传单词" + sum + ",成功上传单词数不到" + Const.VOCAB_NUM_BE_ADMIN
							+ "个,您还不能成为管理员哦,请继续努力!');window.location.href='sec/userInfo.jsp'</script>");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			String adminName = request.getParameter("adminName");
			String pwd = request.getParameter("pwd");
			Object code = session.getAttribute("code");
			String checkCode = request.getParameter("checkCode");

			// 验证码进行正则匹配, 忽略大小写
			Pattern p = Pattern.compile("^" + code + "$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(checkCode);
			if (!matcher.matches()) {
				out.print("<script>alert('验证码错误,请重新填写');window.location.href='sec/manageRegist.jsp'; </script>");
				out.flush();
				return;
			}
			long adminId = GenUtil.getMgrId();
			;
			while (ad.login(adminId) != null) {
				adminId += new Random().nextInt(10);
			}
			if (GenUtil.pwdSecury(pwd)) {
				pwd = JiaJieMiUtil.KL(MD5.md5(pwd));
			} else {
				out.print("<script>alert('密码不符合要求,请重新填写');window.location.href='sec/manageRegist.jsp'; </script>");
				out.flush();
				return;
			}

			Admin admin = new Admin(adminId, adminName, pwd);

			int res = ad.regist(admin, user.getUserId());
			if (res == 1) {
				// 注册成功跳转到管理员登陆页面
				out.print("<h1 style='float:center;text-align:center'>请记住您的账号:" + adminId + "</h1>"
						+ "<a href='CommanderLogin.html' style='float:center;text-align:center'>点击此处登陆您的管理员账号</a>"
						+ "<br><a href='sec/userInfo.jsp' style='float:center;text-align:center'>点击此处返回您的个人页面</a>");
				return;
			} else {
				out.print("<script>alert('注册失败, 请重试');window.location.href='sec/manageRegist.jsp'; </script>");
				out.flush();
				return;
			}
		} catch (SQLException e) {
			out.print("<script>alert('注册失败,你是否已经登陆?');window.location.href='sec/manageRegist.jsp'; </script>");
			out.flush();
			e.printStackTrace();
		}
	}

	/**
	 * 登陆, 判断是超级管理员登陆还是普通管理员登陆
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @param identify
	 * @param mgrPwd
	 * @param mgrId
	 * @param sad
	 * @param ad
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response, PrintWriter out, SuperAdService sad,
			AdminService ad, HttpSession session)

			throws ServletException, IOException {

		String identify = request.getParameter("identify");
		if (identify == null) {
			out.print("<script>alert('选择您的登陆身份');history.go(-1)</script>");
			out.flush();
			return;
		}
		String mgrPwd = request.getParameter("mgrPwd");
		String mgrIdStr = request.getParameter("mgrId");
		long mgrId = 0;
		try {
			if (mgrIdStr != null && mgrIdStr.length() > 0) {
				mgrId = Long.parseLong(mgrIdStr);
			}
		} catch (Exception e) {
			out.print("<script>alert('请输入ID帐号!');history.go(-1)</script>");
			return ;
		}

		// 超级管理员
		if (identify.equals("1")) {
			try {
				SuperAdmin superAd = sad.login(mgrId);
				if (superAd != null) {
					if (MD5.md5(mgrPwd).equals(JiaJieMiUtil.JM(superAd.getPwd()))) {
						session.setAttribute("superAdmin", superAd);
						response.sendRedirect("mgr/manage.jsp");
					} else {
						out.print("<script>alert('密码错误,请重新填写');history.go(-1)</script>");
						out.flush();
					}
				} else {
					out.print("<script>alert('帐户名错误,请重新填写');history.go(-1)</script>");
					out.flush();
				}
			} catch (SQLException e) {
				out.print("<script>alert('帐户名错误,请重新填写');history.go(-1)</script>");
				out.flush();
				e.printStackTrace();
			}
		} else {// 普通管理员登陆
			try {
				Admin admin = ad.login(mgrId);
				if (admin != null) {
					if (MD5.md5(mgrPwd).equals(JiaJieMiUtil.JM(admin.getPwd()))) {
						session.setAttribute("admin", admin);
						response.sendRedirect("mgr/manage.jsp");
					} else {
						out.print("<script>alert('密码错误,请重新填写');history.go(-1)</script>");
						out.flush();
					}
				} else {
					out.print("<script>alert('帐户名错误,请重新填写');history.go(-1)</script>");
					out.flush();
				}
			} catch (SQLException e) {
				out.print("<script>alert('出错,请重试');history.go(-1)</script>");
				out.flush();
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

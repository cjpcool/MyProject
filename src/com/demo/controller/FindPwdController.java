package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.demo.bean.User;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import com.demo.utils.Const;
import com.demo.utils.JiaJieMiUtil;
import com.demo.utils.MD5;

/**
 * Servlet implementation class FindPwdController
 */
@WebServlet("/findPwdController")
public class FindPwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		PrintWriter out = response.getWriter();
		UserService us = new UserServiceImpl();
		switch (op) {
		case "check":
			check(request, response, out, us);

			break;
		case "findPwd3":
			try {
				toFindPassword3(us, request, out);
			} catch (SQLException e) {
				out.print("<script>alert('未找到用户,请重试!');</script>");
				e.printStackTrace();
			}
		case "changePwd":

			changePwd(request, out, us);

		default:
			break;
		}
	}

	private void changePwd(HttpServletRequest request, PrintWriter out, UserService us) {
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
			out.print("<script>alert('无法获取用户名,请重新进行验证!');window.location.href='findPwd.jsp';</script>;");
			return;
		}
		User user = (User) userObj;
		newPwd = JiaJieMiUtil.KL(MD5.md5(newPwd));
		// 修改密码
		user.setPwd(newPwd);
		try {
			int res = us.setBasicInfo(user);
			if (res == 1) {
				request.getSession().removeAttribute("user");
				out.print("<script>alert('密码修改成功!');window.location.href='index.jsp';</script>;");
			} else {
				out.print("<script>alert('密码修改出现错误,请重试!');history.go(-1)</script>;");
			}
		} catch (SQLException e) {
			out.print("<script>alert('密码修改出现错误,请重试!');history.go(-1)</script>;");
			e.printStackTrace();
		}
	}

	private void check(HttpServletRequest request, HttpServletResponse response, PrintWriter out, UserService us)
			throws IOException {
		// 验证码验证
		Object code = request.getSession().getAttribute("code");
		String checkCode = request.getParameter("checkCode");
		if (!code.equals(checkCode)) {
			response.getWriter().print("<script>alert(\"验证码错误,重新输入!\");history.go(-1)</script>");
			return;
		}
		/*
		 * 验证用户名及邮箱
		 */
		try {
			String userName = request.getParameter("userName");
			User user = us.getUserByName(userName);
			if (user == null) {
				out.print("<script>alert('未找到用户名!');history.go(-1)</script>");
				out.flush();
				return;
			}
			String email = user.getEmail();

			/*
			 * 发送邮件
			 */
			// 获取发送邮件信息
			String content = createLink(user, request, out);
			toFindPassword2(out, content, request, us);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @Description: 生成邮箱链接地址
	 * @throws SQLException
	 * @date 2016-11-3 下午01:50:14
	 */
	private String createLink(User user, HttpServletRequest request, PrintWriter out) throws SQLException {

		UserService us = new UserServiceImpl();
		// 生成密钥
		String secretKey = UUID.randomUUID().toString();
		// 设置过期时间
		Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
		System.out.println(System.currentTimeMillis());
		Timestamp date = new Timestamp(outDate.getTime() / 1000 * 1000);// 忽略毫秒数
																		// mySql
																		// 取出时间是忽略毫秒数的

		// 此处应该更新Studentinfo表中的过期时间、密钥信息
		user.setOutDate(date);
		user.setValidataCode(secretKey);
		String emailContent;
		int res = us.setBasicInfo(user);
		if (res == 0) {
			throw new SQLException();
		}
		// 将用户名、过期时间、密钥生成链接密钥
		String key = user.getUserId() + "$" + date + "$" + secretKey;

		String digitalSignature = MD5.md5(key);// 数字签名

		String path = request.getContextPath();

		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

		String resetPassHref = basePath + "/findPwdController?op=findPwd3&sid=" + digitalSignature + "&id="
				+ user.getUserId();

		emailContent = "<p>请勿回复本邮件.点击下面的链接,重设密码,本邮件超过30分钟,链接将会失效，需要重新申请找回密码.</p><a href='"+resetPassHref+"'>" + resetPassHref + "</a><h4>若您点击无法载入,请手动复制地址到地址栏打开!</h4>";
		return emailContent;
	}

	/**
	 * @Description: 该方法用于处理从邮箱链接过来的修改密码请求
	 * @throws SQLException
	 * @date 2016-11-3 下午02:24:17
	 */
	public void toFindPassword3(UserService us, HttpServletRequest request, PrintWriter out) throws SQLException {
		String message = "";
		// 获取链接中的加密字符串
		String sid = request.getParameter("sid");
		// 获取链接中的用户名
		String idStr = request.getParameter("id");
		if (StringUtils.isBlank(sid) || StringUtils.isBlank(idStr)) {
			message = "请求的链接不正确,请重新操作.";
		}

		long id = Long.parseLong(idStr);
		User user = us.getUserById(id);
		if (user != null) {
			// 获取当前用户申请找回密码的过期时间
			// 找回密码链接已经过期
			if (user.getOutDate().compareTo(new java.util.Date()) == -1) {
				System.out.println("链接已经过期");
				message = "链接已经过期";
			}
			// 获取当前登陆人的加密码
			String key = user.getUserId() + "$" + user.getOutDate() + "$" + user.getValidataCode();// 数字签名
			String digitalSignature = MD5.md5(key);// 数字签名
			System.out.println(digitalSignature);
			if (!digitalSignature.equals(sid)) {
				System.out.println("链接加密密码不正确");
				message = "链接加密密码不正确";
			} else {
				// 验证成功,跳入到修改密码界面
				request.getSession().setAttribute("user", user);
				out.print("<script>alert('验证成功,正在跳转至密码修改页面');window.location.href='sec/findPwd3.jsp';</script>");
				return;
			}

		} else {
			System.out.println("用户信息不存在");
			message = "用户信息不存在";
		}
		out.println("<script>alert('" + message + "');");
	}

	private void toFindPassword2(PrintWriter out, String content, HttpServletRequest request, UserService us)
			throws SQLException {
		String userName = request.getParameter("userName");
		User user = us.getUserByName(userName);
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", "smtp.qq.com");
			prop.setProperty("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", "587"); // qq端口
			prop.setProperty("mail.debug", "true");
			// 验证写信者邮箱,此处使用第三方授权码登陆,使用密码不知道为什么登录不上
			// 创建会话
			Session session = Session.getInstance(prop);
			// 填写信封写信
			MimeMessage msg = createMimeMessage(session, Const.EMAIL_ACCOUNT, user.getEmail(), content);
			// 验证用户名密码发送邮件
			Transport transport = session.getTransport();
			transport.connect(Const.EMAIL_ACCOUNT, Const.EMAIL_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			out.print("<script>alert('验证邮件已发送至您的邮箱,请您查收后继续操作');</script>");
		} catch (Exception e) {
			out.print("<script>alert('发送失败重试!');history.go(-1)</script>");
			e.printStackTrace();
		}
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String content)
			throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
		message.setFrom(new InternetAddress(sendMail, "nwnu在线翻译", "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail));

		// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject("找回密码!", "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new java.util.Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}

}

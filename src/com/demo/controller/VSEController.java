package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.bean.*;
import com.demo.service.*;
import com.demo.service.impl.*;
import com.demo.utils.GenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Servlet implementation class VSEController
 */
@WebServlet("/vseController")
@MultipartConfig
public class VSEController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		// userService
		UserService us = new UserServiceImpl();
		// vocabularyService
		VocabularyService vs = new VocabularyServiceImple();
		// similarService
		SimilarService ss = new SimilarServiceImpl();
		ExampleService es = new ExampleServiceImpl();

		String op;
		op = request.getParameter("op");
		switch (op) {
		case "showUserVocabs":
			showUserVocabs(request, response, session, out, vs, ss, es);
			break;
		case "getWaitingVocabs":
			getVocabs(Short.parseShort("0"), request, out, us, vs, ss, es);
			break;
		case "saveModify":
			saveModify(request, out, vs);

			break;
		case "getAllVocabs":
			getVocabs(Short.parseShort("-1"), request, out, us, vs, ss, es);

		default:
			break;
		}

	}

	/**
	 * 1.是不是超管进行的操作, 如果是则直接进行 若不是: 1.获取该管理员的Id 2.得到对应的userId
	 * 3.若为空,则可执行操作;否则获取该userId的user 4.若该user的categoryId与该单词的categryId相等,则继续操作
	 * 若不想等,则退出
	 * 
	 * @param request
	 * @param out
	 * @param vs
	 */
	private void saveModify(HttpServletRequest request, PrintWriter out, VocabularyService vs) {
		/*
		 * 1获取前端信息
		 */
		String vocab = request.getParameter("vocab");
		String vocabIdStr = request.getParameter("vocabId");
		long vocabId = -1;
		if (vocabIdStr != null && vocabIdStr.length() > 0) {
			vocabId = Long.parseLong(vocabIdStr);
		}
		String transe = request.getParameter("transe");
		String categoryIdStr = request.getParameter("categoryId");
		long newCategoryId = -1;
		if (categoryIdStr != null && categoryIdStr.length() > 0) {
			newCategoryId = Long.parseLong(categoryIdStr);
		}
		String statusStr = request.getParameter("status");
		short status = 0;
		if (statusStr != null && !statusStr.equals("undefined") && statusStr.length() > 0) {
			status = Short.parseShort(statusStr);
		}
		String similarsStr = request.getParameter("similars");
		String examsStr = request.getParameter("exams");
		if (examsStr != null && examsStr.length() > 0) {
			String[] examWithId = examsStr.split(";");
			for (int i = 0; i < examWithId.length; i++) {
				String[] exam = examWithId[i].split("~");
				if (!GenUtil.hasThisVocab(exam[1], vocab)) {
					out.print("3");
					out.flush();
					return;
				}
			}
		}

		/*
		 * 2.判断登陆身份
		 */
		Object superAdminObj = request.getSession().getAttribute("superAdmin");
		// 是不是超管
		try {
			if (superAdminObj == null || superAdminObj instanceof SuperAdmin) { // 不是超管的操作
				Object adminObj = request.getSession().getAttribute("admin");
				if (adminObj != null && adminObj instanceof Admin) { // 是管理员造作
					Admin admin = (Admin) adminObj;
					// 获取对应user
					UAdminService uads = new UAdminServiceImpl();
					List<Long> userIds = uads.selectUserIdByAdminId(admin.getAdminId());
					UserService us = new UserServiceImpl();
					boolean flag = false;
					for (Long userId : userIds) {
						User user = us.getUserById(userId);
						if (user.getCategoryId() == vs.getVocabularyByVocabId(vocabId).getCategoryId()) { // 若属于该专业
							flag = true;
							break;
						}
					}
					if (!flag) { // 若不属于该专业
						out.print("2");
						out.flush();
						return;
					}
				} else {
					out.print("0");
					out.flush();
					return;
				}
			}
			
			/*
			 * 通過層層關卡 !!!終於可以修改單詞啦!!!
			 */
			// 修改单词
			Vocabulary vocabulary = vs.getVocabularyByVocabId(vocabId);
			if (vocabulary == null) {
				out.print("0");
			}
			// 文件上传
			Part part = request.getPart("img");
			if (part != null && part.getSize() > 4) {
				// 获取文件后缀
				String header = part.getHeader("content-disposition");
				String suffix = header.substring(header.lastIndexOf("."), header.length() - 1);
				// 随机产生文件名
				String imgName = GenUtil.genImgName();
				// 保存文件
				String realPath = request.getServletContext().getRealPath("/") + "img/vocab/";
				File file = new File(realPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				part.write(realPath + imgName + suffix);
				String img = imgName + suffix;
				vocabulary.setImg(img);
			}
			vocabulary.setCategoryId(newCategoryId);
			vocabulary.setStatus(status);
			vocabulary.setTranse(transe);
			vocabulary.setVocab(vocab);
			java.sql.Date addTime = new java.sql.Date(new Date().getTime());
			vocabulary.setAddTime(addTime);

			VSEService vses = new VSEServiceImpl();

			int res = vses.updateVSE(vocabulary, similarsStr, examsStr);
			if (res == 1) {
				out.print("1");
			} else {
				out.println("0");
			}
			out.flush();

		} catch (Exception e) {
			out.print("0");
			out.flush();
			e.printStackTrace();
		}
	}

	/**
	 * 管理员获取词汇
	 * 
	 * @param status
	 *            为-1时获取所有词汇
	 * @param request
	 * @param out
	 * @param us
	 * @param vs
	 * @param ss
	 * @param es
	 */
	private void getVocabs(short status, HttpServletRequest request, PrintWriter out, UserService us,
			VocabularyService vs, SimilarService ss, ExampleService es) {
		List<VocSimExam> vses = new ArrayList<VocSimExam>();

		try {
			List<Vocabulary> vocabs = null;
			// 获取待词汇
			if (status == -1) {
				String condition = request.getParameter("sortOrder");
				if(condition.equals("lastTime")) {
					vocabs = vs.getAllVocabsOrderByLastTime();
				}else if(condition.equals("vocabId")){
					vocabs = vs.getAllVocabsOrderByID();
				}
			} else
				vocabs = vs.getVocabsByStatus(status);
			// 结果集过滤
			resFilter(request, us, vocabs);
			setVSEList(ss, es, vses, vocabs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String res = pageSplit(request, vses);
		out.print(res);
		out.flush();
	}

	/**
	 * 对userName, categoryId, vocab, transe进行查询时过滤出结果
	 * 
	 * @param request
	 * @param us
	 * @param vocabs
	 *            被改变的单词结果集
	 * @throws SQLException
	 */
	private void resFilter(HttpServletRequest request, UserService us, List<Vocabulary> vocabs) throws SQLException {
		String categoryIdStr = request.getParameter("categoryId");
		long categoryId = -1;
		if (categoryIdStr != null && categoryIdStr.matches("\\d+")) {
			categoryId = Long.parseLong(categoryIdStr);
		}
		String transe = request.getParameter("transe");
		String vocab = request.getParameter("vocab");
		String userName = request.getParameter("user");

		// 过滤user查询
		if (userName != null && userName.length() > 0) {
			User user = us.getUserByName(userName);
			if (user != null) {
				// 此处只能用迭代器遍历vocabs,否则remove时会抛出异常
				Iterator<Vocabulary> iterator = vocabs.iterator();
				long userId = user.getUserId();
				while (iterator.hasNext()) {
					if (iterator.next().getUserId() == userId) {
						continue;
					}
					iterator.remove();
				}
			}
		}

		// 过滤vocabulary查询
		if (vocab != null && vocab.length() > 0) {
			String regex = "[a-zA-Z]*" + GenUtil.regexText(vocab) + "[a-zA-Z]*";
			// 此处只能用迭代器遍历vocabs,否则remove时会抛出异常
			Iterator<Vocabulary> iterator = vocabs.iterator();
			while (iterator.hasNext()) {
				// 正则匹配
				if (Pattern.matches(regex, iterator.next().getVocab())) {
					continue;
				}
				// 移除不符合的
				iterator.remove();
			}
		}

		// 过滤翻译查询
		if (transe != null && transe.length() > 0) {
			// [\u4e00-\u9fa5]中文匹配
			String regex = "[\u4e00-\u9fa5a-zA-Z]*" + transe + "[\u4e00-\u9fa5a-zA-Z]*";
			// 此处只能用迭代器遍历vocabs,否则remove时会抛出异常
			Iterator<Vocabulary> iterator = vocabs.iterator();
			while (iterator.hasNext()) {
				// 正则匹配
				if (Pattern.matches(regex, iterator.next().getTranse())) {
					continue;
				}
				// 移除不符合的
				iterator.remove();
			}
		}

		// 过滤专业
		if (categoryId != -1) {
			// 此处只能用迭代器遍历vocabs,否则remove时会抛出异常
			Iterator<Vocabulary> iterator = vocabs.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getCategoryId() != categoryId) {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * 分页用
	 * 
	 * @param request
	 * @param vses
	 *            词语,近义词 ,例句集合
	 * @return
	 */
	private String pageSplit(HttpServletRequest request, List<VocSimExam> vses) {
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
		if (lastIndex > vses.size()) {
			lastIndex = vses.size();
		}

		Page page = new Page();
		page.setPage(pageIndex);
		page.setTotal(vses.size());
		List<VocSimExam> rows = vses.subList(pageIndex, lastIndex);
		page.setRows(rows);
		String res = JSONObject.toJSONString(page);
		return res;
	}

	private void showUserVocabs(HttpServletRequest request,HttpServletResponse response, HttpSession session, PrintWriter out,
			VocabularyService vs, SimilarService ss, ExampleService es) throws IOException {
		Object userObj = session.getAttribute("user");
		if (userObj == null) {
			response.sendRedirect("reLogin.jsp");
			return;
		}
		User user = (User) userObj;
		long userId = user.getUserId();

		List<VocSimExam> vses = new ArrayList<VocSimExam>();
		try {
			// 查询每个用户上传的单词
			List<Vocabulary> vocabs = vs.getVocabsByUserId(userId);

			setVSEList(ss, es, vses, vocabs);

			String res = pageSplit(request, vses);
			// 将vse返回到页面
			out.print(res);
			out.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以list形式捕获每个单词对应的similar list, exam list, 每个单词近义词是一个list, exam 是一个list
	 * 
	 * @param ss
	 * @param es
	 * @param vses
	 *            被改变的vse对象
	 * @param vocabs
	 *            传入需上传的单词的list
	 * @throws SQLException
	 */
	private void setVSEList(SimilarService ss, ExampleService es, List<VocSimExam> vses, List<Vocabulary> vocabs)
			throws SQLException {
		// 以list形式捕获每个单词对应的similar list, exam list
		// 每个单词近义词是一个list, exam 是一个list
		for (Vocabulary vocab : vocabs) {
			// 创建新的vse对象
			VocSimExam vse = new VocSimExam(vocab);
			// 设置其similars
			vse.setSimilars(ss.getSimilarsByVocab(vocab));
			// 设置exam
			vse.setExams(es.getExamplesByVocab(vocab));
			// 添加进vse集合
			vses.add(vse);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

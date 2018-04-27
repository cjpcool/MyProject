package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.User;
import com.demo.bean.VocSimExam;
import com.demo.bean.Vocabulary;
import com.demo.service.CategoryService;
import com.demo.service.ExampleService;
import com.demo.service.SimilarService;
import com.demo.service.VocabularyService;
import com.demo.service.impl.CategoryServiceImpl;
import com.demo.service.impl.ExampleServiceImpl;
import com.demo.service.impl.SimilarServiceImpl;
import com.demo.service.impl.VocabularyServiceImple;
import com.demo.utils.Const;
import com.demo.utils.GenUtil;
import com.demo.utils.TransApi;
import com.lsj.trans.LANG;
import com.lsj.trans.Translator;
import com.lsj.trans.exception.DupIdException;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(name = "translateController", urlPatterns = {"/translateController"})
public class TranslateController extends HttpServlet {
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
        VocabularyService vs = new VocabularyServiceImple();
        PrintWriter out = response.getWriter();
        switch (op) {
            case "translate":
                translator(request, response);
                break;

            case "addVocab":
                addVocab(request, vs, out);
                break;
            case "deleteVocab":
                deleteVocab(request, vs, out);

                break;
            case "hasThisVocab":
                useHasThisVocab(request, out);
                break;
            case "isVocab":
                useIsVocab(request, out);
                break;
            case "vocabExist":
                vocabExist(request, vs, out);
                break;
            default:
                break;
        }
    }

    private void deleteVocab(HttpServletRequest request, VocabularyService vs, PrintWriter out) {
        String vocabIdStr = request.getParameter("vocabId");
        long vocabId = -1;
        if (vocabIdStr != null && vocabIdStr.length() > 0) {
            vocabId = Long.parseLong(vocabIdStr);
        }

        try {
            int res = vs.deleteVocabById(vocabId);
            if (res == 0) {
                out.print("0");
            } else {
                out.print("1");
            }
            out.flush();
        } catch (SQLException e) {
            out.println("0");
            out.flush();
            e.printStackTrace();
        }
    }

    protected void vocabExist(HttpServletRequest request, VocabularyService vs, PrintWriter out) {
        String vocab = request.getParameter("vocab");
        Vocabulary res = null;
        try {
            res = vs.queryVocabularyByName(vocab, -1).get(0);
        } catch (SQLException e) {

        } catch (IndexOutOfBoundsException e) {
            out.print("{\"res\":\"true\"}");
            return;
        }
        if (res == null) {
            out.print("{\"res\":\"true\"}");
        } else {
            out.print("{\"res\":\"false\"}");
        }
        out.flush();
    }

    protected void useHasThisVocab(HttpServletRequest request, PrintWriter out) {
        String example = request.getParameter("example");
        String vocab = request.getParameter("vocab");
        if (GenUtil.hasThisVocab(example, vocab)) {
            out.print("{\"res\":\"true\"}");
        } else {
            out.print("{\"res\":\"false\"}");
        }
    }

    protected void useIsVocab(HttpServletRequest request, PrintWriter out) {
        String vocab = request.getParameter("vocab");
        if (GenUtil.isVocab(vocab)) {
            out.print("{\"res\":\"true\"}");
        } else {
            out.print("{\"res\":\"false\"}");
        }
        out.flush();
    }

    protected void addVocab(HttpServletRequest request, VocabularyService vs, PrintWriter out)
            throws IOException, ServletException {
        String vocab = request.getParameter("vocab");

        String trans = request.getParameter("trans");
        String categoryIdStr = request.getParameter("categoryId");
        long categoryId = 0;
        if (categoryIdStr != null && categoryIdStr.length() > 0) {
            categoryId = Long.parseLong(categoryIdStr);
        }

        String similar = request.getParameter("similar");
        String example = request.getParameter("example");
        // 得到sesion
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        User user = (obj != null) ? (User) obj : null;

        java.sql.Date addTime = new java.sql.Date(new Date().getTime());

        // 文件上传
        Part part = request.getPart("img");
        // 默认图片
        String imgName = "";
        String suffix = "";
        if (part.getSize() >= 1) {
            // 获取上传文件后缀
            String header = part.getHeader("content-disposition");
            suffix = header.substring(header.lastIndexOf("."), header.length() - 1);
            // 随机生产文件名;
            imgName = GenUtil.genImgName();

            // 保存文
            String realPath = request.getServletContext().getRealPath("/") + "img/vocab/";
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            part.write(realPath + imgName + suffix);
        }
        String img = imgName + suffix;
        // 判断不能传空值过来进行添加
        if (vocab.length() <= 0 || vocab == null || trans.length() <= 0 || trans == null) {
            out.print("{\"res\":\"adderror\"}");
            return;
        }
        Vocabulary vocabulary = new Vocabulary(vocab, trans, img, addTime, addTime, categoryId,
                (user == null) ? null : user.getUserId(), Short.parseShort("0"));

        int addRes = 0;
        try {
            addRes = vs.addVocabulary(vocabulary, example, similar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (addRes == 1) {
            out.print("{\"res\":\"addsuc\"}");
        } else {
            out.print("{\"res\":\"adderror\"}");
        }
        out.flush();
    }

    private void translator(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取需翻译的文本
        String text = request.getParameter("text");
        String langStr = request.getParameter("lang");
        String[] lang = new String[2];
        if (langStr != null && langStr.length() > 0) {
            lang = langStr.split("2");
        }
        String from = lang[0];
        String to = lang[1];
        // 百度翻译
        List<String>[] baiduRes = baiduTranslate(text, from, to);
        // google翻译
        List<String> googleTrans = googleTranslate(text, from, to);
        // 金山翻译
        JSONObject icibaTrans = icibaTranslate(text, from, to);

        // 数据库翻译
        VocabularyService vs = new VocabularyServiceImple();
        ExampleService es = new ExampleServiceImpl();
        SimilarService ss = new SimilarServiceImpl();
        CategoryService cs = new CategoryServiceImpl();

        List<String> vocabArr = (text == null) ? null : editText(text);

        String category = request.getParameter("categoryId");
        long categoryId = -1;
        if (category != null && category.length() > 0) {
            categoryId = Long.parseLong(category);
            if(categoryId > 0) {
                // 给传过来的categoryhotlevel+1
                try {
                    int res = cs.updateCategoryLevel(categoryId);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        /**
         * 1 輸入為英文， 根據voca查找 2、輸入為中文 ， 根據trans查找 3、其他不翻譯
         */
        List<VocSimExam> vseList = new ArrayList<>();
        if (from.equals("en")) {
            /*
			 * 查詢文本所得到單詞，將結果放到vocabs中
			 */
            for (int i = 0; i < vocabArr.size(); i++) {
                try {
                    // 如果categoryId==-1,就是所有单词
                    String vocabStr = vocabArr.get(i);
                    vocabStr = GenUtil.regexText(vocabStr);
                    List<Vocabulary> query = vs.queryVocabularyByName(vocabStr, categoryId);
                    // 将每一行查询到的结果集加到vseList中
                    for (Vocabulary item : query) {
                        if (item.getStatus() == 1)
                            vseList.add(new VocSimExam(item));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }
        } else if (from.equals("zh")) {
            for (String vocab : vocabArr) {
                try {
                    List<Vocabulary> query = vs.queryVocabularyByTransAndCategory(vocab, categoryId);
                    for (Vocabulary item : query) {
                        if (item.getStatus() == 1)
                            vseList.add(new VocSimExam(item));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		/*
		 * 根據查詢的詞語的結果繫得到相應的例句與近義詞
		 */
        if (vseList != null && !vseList.isEmpty()) {
            for (VocSimExam vse : vseList) {
                try {
                    Vocabulary vocab = vse.getVoca();
                    vse.setExams(es.getExamplesByVocab(vocab));
                    vse.setSimilars(ss.getSimilarsByVocab(vocab));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("vseList", vseList);
        }
        List<String> baiduRes1 = null;
        List<String> baiduRes0 = null;
        if (baiduRes != null) {
            if (baiduRes.length == 2) {
                baiduRes1 = baiduRes[1];
                baiduRes0 = baiduRes[0];
            } else if (baiduRes.length == 1) {
                baiduRes0 = baiduRes[0];
            }
        }
        request.setAttribute("baiduTrans", baiduRes1);
        request.setAttribute("src", baiduRes0);
        request.setAttribute("googleTrans", googleTrans);
        request.setAttribute("icibaTrans", icibaTrans);

        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    /*
     * private List<String> baiduTranslate(String text, String from, String to)
     * { String res = null; try { TFactory factory = new TranslatorFactory();
     * Translator translator = factory.get("baidu"); switch (from) { case "zh":
     * res = translator.trans(LANG.ZH, LANG.EN, text); break; case "en": res =
     * translator.trans(LANG.EN, LANG.ZH, text); break; case "ru": res =
     * translator.trans(LANG.RU, LANG.ZH, text); break;
     *
     * default: break; } } catch (ClassNotFoundException e) {
     * e.printStackTrace(); } catch (IllegalAccessException e) {
     * e.printStackTrace(); } catch (DupIdException e) { e.printStackTrace(); }
     * catch (URISyntaxException e) { e.printStackTrace(); } catch (Exception e)
     * { e.printStackTrace(); } List<String> baiduTrans = new ArrayList<>();
     * if(res != null) for(String r :res.split("\n")){ baiduTrans.add(r); }
     * return baiduTrans;
     *
     * }
     */
    private JSONObject icibaTranslate(String text, String from, String to) {
        String res = null;
        try {
            TFactory factory = new TranslatorFactory();
            Translator translator = factory.get("jinshan");
            switch (from) {
                case "zh":
                    res = translator.trans(LANG.ZH, LANG.EN, text);
                    break;
                case "en":
                    res = translator.trans(LANG.EN, LANG.ZH, text);
                    break;
                case "ru":
                    res = translator.trans(LANG.RU, LANG.ZH, text);
                    break;

                default:
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DupIdException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private List<String> googleTranslate(String text, String from, String to) {

        String res = null;
        try {
            TFactory factory = new TranslatorFactory();
            Translator translator = factory.get("google");
            switch (from) {
                case "zh":
                    res = translator.trans(LANG.ZH, LANG.EN, text);
                    break;
                case "en":
                    res = translator.trans(LANG.EN, LANG.ZH, text);
                    break;
                case "ru":
                    res = translator.trans(LANG.RU, LANG.ZH, text);
                    break;

                default:
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DupIdException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> googleTrans = new ArrayList<>();
        if (res != null)
            for (String r : res.split("\n")) {
                googleTrans.add(r);
            }
        return googleTrans;
    }

    /**
     * 将输入的text以换行分割单词 类似于百度翻译的输入处理
     *
     * @param text 输入的文本
     * @return 分割出来的单词数组
     */
    public List<String> editText(String text) {
        // 用来存放被分割为多份的元素单词
        List<String> res = new ArrayList<String>();
        // * 將text分爲以換行符分割的單詞，每一行一個單詞，（與百度翻譯類似）
        String[] vocaArr = text.split("\n");
        Pattern compile = Pattern.compile("\\s*|\t*|\r*|\n*", Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < vocaArr.length; i++) {
            Matcher matcher = compile.matcher(vocaArr[i]);
            if (!matcher.matches()) {
                res.add(matcher.replaceAll(""));
            }
        }

        return res;
    }

    /**
     * 百度翻译 通过接口!
     *
     * @param text 翻译文本
     * @param from 文本语言呢
     * @param to   翻译语言
     * @return res[0]储存src，res【1】储存dst。
     */
    private ArrayList<String>[] baiduTranslate(String text, String from, String to) {

        TransApi api = new TransApi(Const.BAIDU_APP_ID, Const.BAIDU_SECURITY_KEY);
        ArrayList<String>[] result = new ArrayList[2];
        result[1] = new ArrayList<String>();
        result[0] = new ArrayList<String>();
        String allRes = null;
        try {
            allRes = api.getTransResult(text, from, to);
        } catch (Exception e) {
            System.out.println("Error in Internet");
            e.printStackTrace();
        }
        if (allRes == null)
            return null;
        try {
            JSONObject jo = JSON.parseObject(allRes);
            String transRes = (jo.get("trans_result") == null) ? null : jo.get("trans_result").toString();
            JSONArray transArray = (jo != null) ? JSONArray.parseArray(transRes) : null;
            // 被翻译的目标语言与源语言， trans_result在json中为一个Json数组，数组每一项为src中回车分隔的每一个文本。
            if (transArray != null) {
                for (Object trans : transArray) {
                    result[0].add(JSON.parseObject(trans.toString()).get("src").toString());
                    result[1].add(JSON.parseObject(trans.toString()).get("dst").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

<%@page import="com.demo.bean.VocSimExam" %>
<%@page import="com.demo.bean.Similar" %>
<%@page import="java.util.Map.Entry" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="com.demo.bean.Example" %>
<%@page import="com.alibaba.fastjson.JSONArray" %>
<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>nwnu在线翻译首页</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<style>
    .nav-tabs li a {
        font-size: 16px;
        color: white;
        padding-top: 10px;
    }

    #myTrans {
        height: 300px;
        overflow: auto;
    }

    .num {
        font-size: 12px;
        height: 40px;
        line-height: 40px;
        padding-right: 10px;
        font-weight: bold;
    }

    .show-title {
        padding-left: 10px;
    }

    .hotLevel {
        margin-top: 10px;
        color: red;
        float: right;

    }

    p {
        font-size: 16px;
        color: blue;
        padding-top: 10px;
        padding-left: 10px;

    }

    p1 {
        font-size: 15px;
        color: #86C3E7;
        padding-top: 10px;
        padding-left: 40px;
    }
</style>
<body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
    $(function () {

        $
            .post(
                "categoryController?op=getCategoryOption",
                function (data) {
                    var jsonArr = eval(data);
                    $
                        .each(
                            jsonArr,
                            function (index, item) {

                                $("#categories")
                                    .html(
                                        $(
                                            "#categories")
                                            .html()
                                        + "<option value=\"" + jsonArr[index].categoryId + "\">"
                                        + jsonArr[index].categoryName
                                        + "</option>")
                            })
                })

    })
</script>
<script>
    function am_start() {
        var audio = document.getElementById('am_mp3');
        audio.play();
    }
    function en_start() {
        var audio = document.getElementById('en_mp3');
        audio.play();
    }
</script>
<script src="/OnlineTransducer/js/jquery.min.js"></script>
<script src="/OnlineTransducer/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function refreshImg(img) {
        img.src = img.src + "?" + new Date().getTime();
    }
</script>

<!-- 提交登陆表单 -->
<script type="text/javascript">
    function login() {

        var form = new FormData($("#loginForm")[0]);

        $.ajax({
            url: "userController?op=login",
            data: form,
            type: "post",
            processData: false,
            contentType: false,
            cache: false,
            dataType: "json",
            success: function (res) {
                if (res.status == '0') {
                    location.reload();
                } else if (res.status == '1') {
                    alert('验证码错误,请重新输入!');
                } else if (res.status == '2') {
                    alert("帐户名不存在,请先注册!");
                } else if (res.status == '3') {
                    alert("密码错误,请重新输入!");
                } else if (res.status == '4') {
                    alert("未知错误~~重试一下")
                }
                refreshImg(document.getElementById('code'));
            },
            error: function () {
                alert("与服务器通讯失败");
            },
        })
    }
</script>

<script type="text/javascript">
    function getCategory(categoryName) {
        $.ajax({
            url: "categoryController?op=getCategoryOption",
            type: "get",
            async: false,
            success: function (data) {
                $("#categories").html('');
                var jsonArr = eval(data);
                $.each(jsonArr, function (index, item) {
                    if (jsonArr[index].categoryName == categoryName) {
                        $("#categories").html("<option value=\"" + jsonArr[index].categoryId + "\">"
                            + jsonArr[index].categoryName
                            + "</option>"
                            + $("#categories").html())
                    } else {
                        $("#categories").html($("#categories").html()
                            + "<option value=\"" + jsonArr[index].categoryId + "\">"
                            + jsonArr[index].categoryName
                            + "</option>")
                    }
                })

            },

        })
    }
    ;
    $.ajax({
        url: "categoryController?op=getHotCategories",
        type: 'get',
        dataType: 'json',
        success: function (data) {
            $.each(data, function (index, item) {
                var a = item.categoryName;
                $("#hotCategories").html($("#hotCategories").html() +
                    "<div class='show-tittle'><span class='num'>" + (index + 1) + "</span><a onclick='getCategory(\"" + a + "\")' href='javascript:void(0);'>" + item.categoryName + "</a></span>"
                    + "<span class='hotLevel'>" + item.hotLevel + "</span");
            })
        }

    })
</script>


<div style="width: 100%">
    <div style="background-color: #316FAF; height: 120px;">
        <img src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"
             style="  position: relative;left: 30px;bottom: 130px;"></img>
    </div>
    <div class="container-fluid"
         style="background-color: #b2bec3; height: 45px;">
        <ul class="nav nav-tabs navbar-right" style="border-bottom:none;">
            <li><a onFocus="if(this.blur)this.blur()"
                   href="sec/addVocab.jsp">添加词汇</a></li>
            <li><a onFocus="if(this.blur)this.blur()" data-toggle="modal"
                   data-target="#userresponse">用户反馈</a></li>
            <div class="modal fade" id="userresponse" tabindex="-1"
                 role="dialog" aria-labelledby="myModalLabe0" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">&times;</button>
                            <div class="modal-title" id="myModalLabe0"
                                 style="height:100px;background-color:#316FAF;">
                                <img src="${pageContext.request.contextPath }/img/static/indexLogo.png"
                                     alt="logo" width="300px" height="180px"
                                     style="position: relative;left: 30px;bottom: 50px;"></img>
                            </div>
                        </div>
                        <div class="modal-body" style="height: 400px;">
                            <div
                                    style="width: 30%; height: 400px; float: right; margin-top: 10px; background-image: url(img/static/cbc3668505e73afb.jpg); background-size: 100% 300px; background-repeat: no-repeat;">
                            </div>
                            <div
                                    style="width: 70%; height: 400px; float: left; padding-top: 20px; text-align: center;">
                                <p style="font-size: 20px;color:black;">请将反馈发至邮箱：78495796078</p>
                                <br>
                                <p style="font-size: 20px;color:black;">谢谢您的反馈！</p>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <li><a onFocus="if(this.blur)this.blur()" href="regist.jsp" target="_Blank">注册</a></li>

            <c:if test="${user!=null }">
                <li><a onFocus="if(this.blur)this.blur()"
                       href="sec/userInfo.jsp">个人中心</a></li>
                <li><a><span>${user.userName }</span></a></li>
                <li><a onFocus="if(this.blur)this.blur()" href="userController?op=logout">注销</a></li>
                <li><a style="padding: 0px"><img src="${pageContext.request.contextPath }/img/user/${user.img }"
                                                 height="45px" width="60px"></a></li>
            </c:if>

            <c:if test="${user == null }">
                <li><a style="text-decoration: none;"
                       onFocus="if(this.blur)this.blur()" data-toggle="modal"
                       data-target="#myModal">登陆</a></li>
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header" style="height: 120px;">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <div class="modal-title" id="myModalLabel"
                                     style="height:100px;background-color:#316FAF;">
                                    <img src="${pageContext.request.contextPath }/img/static/indexLogo.png"
                                         alt="logo" width="300px" height="180px"
                                         style="position: relative;left: 30px;bottom: 50px;"></img>
                                </div>
                            </div>
                            <div class="modal-body"
                                 style="height: 400px; padding-top: 40px; padding-left: 70px;">
                                <form id="loginForm" class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <label for="userName" class="col-md-3 control-label">用户名:</label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" name="userName"
                                                   id="userName">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="pwd" class="col-md-3 control-label">密码:</label>
                                        <div class="col-md-4">
                                            <input type="password" class="form-control" name="pwd"
                                                   id="pwd">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="checkCode" class="col-md-3 control-label">验证码:</label>
                                        <div class="col-md-4">
                                            <input type="text" class="form-control" name="checkCode" id="checkCode">
                                            <img onclick="refreshImg(this)" alt="验证码"
                                                 src="${pageContext.request.contextPath }/checkCode" width="50px"
                                                 height="30px"
                                                 style="vertical-align:middle;cursor: pointer;padding-top:4px;">

                                            <span>(不区分大小写,点击刷新)</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-8"
                                             style="padding-left: 150px; padding-top: 20px;">
                                            <input type="button" onclick="login()"
                                                   class="btn btn-primary" value="登陆"> <input
                                                type="reset" name="reset" class="btn btn-primary"
                                                value="重置"/> <br> <input type="button"
                                                                         onclick="javascript:window.location.href='findPwd.jsp'"
                                                                         class="btn btn-primary"
                                                                         style="margin-left: 220px; margin-top: 10px;"
                                                                         value="找回密码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-8"
                                             style="padding-left: 150px; padding-top: 20px;">
                                            <p style="color:black;font-size:16px;">
                                                没有账号，去<a href="regist.jsp" target="_Blank"
                                                         style="text-decoration: none;"
                                                         onFocus="if(this.blur)this.blur()">注册</a>
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default"
                                        data-dismiss="modal">关闭
                                </button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal -->
                </div>
            </c:if>
        </ul>
    </div>
</div>
<div class="col-md-10 col-md-offset-1"
     style="height: 200px; margin-top: 10px;">
    <div class="col-md-2"
         style="width: 20%; height: 180px; float: left; margin-top: 6px;">
        <form role="form" action="translateController?op=translate"
              method="post"
        ">
        <div class="btn-group"
             style="padding-top: 30px; padding-left: 10px;">
            <select class="btn btn-default dropdown-toggle"
                    data-toggle="dropdown" name="lang">
                <option value="en2zh">英译汉</option>
                <option value="zh2en">汉译英</option>
            </select>
        </div>
        <div class="btn-group" style="padding-top: 30px; padding-left: 3px;">
            <select id="categories" style="width: 140px;"
                    class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                    name="categoryId">
            </select>
        </div>
    </div>

    <div class="col-md-7"
         style="width: 60%; float: right; margin-right: 40px; height: 200px; padding-top: 10px;">
        <div class="form-group">
				<textarea style="height: 130px;" class="form-control" name="text">
							<c:forEach items="${src }" var="item">
                                ${item }
                            </c:forEach>
						</textarea>
            <button type="submit" class="btn btn-primary"
                    style="float: right; margin-top: 10px;">翻译
            </button>

        </div>
    </div>
    </form>
</div>
<div class="col-md-10 col-md-offset-1"
     style="height: 350px; padding-top: 15px;">
    <div class="col-md-3"
         style="width: 30%; height: 330px; border: 1px solid black; float: left;">
        <p style="font-size: 16px;">热门专业</p>
        <div id="hotCategories">

        </div>
    </div>
    <div class="col-md-7"
         style="width: 60%; height: 330px; float: right; margin-right: 40px;">
        <div id="myTrans" class="panel panel-default">
            <div class="panel-heading" style="background-color: white;">
                <h4 class="panel-title">专业翻译</h4>
            </div>
            <c:if test="${vseList==null }">
                <a href="sec/addVocab.jsp" onFocus="if(this.blur)this.blur()">
                    <p1>暂无此词汇的专业翻译或该单词正在审核, 您可以进行添加</p1>
                </a>
            </c:if>

            <c:if test="${vseList!=null }">
                <div class="panel-body">
                    <%
                        List<VocSimExam> vsels = (List<VocSimExam>) request.getAttribute("vseList");
                    %>
                    <%for (VocSimExam vse : vsels) {%>
                    <div style="overflow: auto">
                        <div style=" width: 30%; float: left;">
                            <p style="size: 24px; font-weight: bolder;">
                                <span>相关图片: </span>
                                <br>
                                <img alt="暂无相关图片" width="60px" height="60px"
                                     src="img/vocab/<%=vse.getVoca().getImg() %>">
                            </p>
                        </div>
                        <div style=" width: 70%; float: right; padding-top: 20px;">

                            <p style="size: 24px; font-weight: bolder; color: #86C3E7;">
                                <span>单词: </span> <span style="color: black"><%=vse.getVoca().getVocab() %></span>
                            </p>
                            <p style="size: 24px; font-weight: bolder; color: #86C3E7;">
                                <span>翻译: </span> <span style="color: black"><%=vse.getVoca().getTranse() %></span>
                            </p>

                            <p style="size: 24px; font-weight: bolder; color: #86C3E7;">
                                <span>近义词: </span>
                                <%for (Similar similar : vse.getSimilars()) { %>
                                <span style="color: black"><%=similar.getSimilar() %>;</span><br>
                                <%} %>
                            </p>

                            <p style="size: 24px; font-weight: bolder; color: #86C3E7;">
                                <span>例句: </span>
                                <%for (Example exam : vse.getExams()) { %>
                                <span style="color: black"><%=exam.getExample() %>;</span><br>
                                <%} %>
                            </p>
                        </div>
                    </div>
                    <hr>
                    <%} %>
                </div>
            </c:if>
        </div>
    </div>
</div>
<div class="col-md-10 col-md-offset-1" id="accordion">
    <div
            style="height: 25px; background-color: #F5F3EC; text-align: center;">
        <a onFocus="if(this.blur)this.blur()" data-toggle="collapse"
           data-parent="#accordion" href="#collapseOne"> 点击我进行展开，查看其它翻译。 </a>
    </div>
    <div id="collapseOne" class="collapse collapse"
         style="height: 500px; padding-top: 30px;">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: white;">
                    <h3 class="panel-title">百度翻译</h3>
                </div>
                <div class="panel-body" style="height: 150px;">
                    <c:forEach items="${baiduTrans }" var="item">
                        ${item }<br>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: white;">
                    <h3 class="panel-title">金山翻译</h3>
                </div>
                <div class="panel-body" style="height: 150px;">
                    <c:if test="${icibaTrans.status ==1}">
                        <span>${icibaTrans.content.out }</span>
                    </c:if>
                    <c:if test="${icibaTrans.status ==0}">
                        <p style="color: #EECBAD; text-size: small;">
                            <span>美：</span><span>[${icibaTrans.content.ph_am }]</span><a
                                href="#"><span class="glyphicon glyphicon-music"
                                               onclick="am_start();"></span></a>  
                            <audio src="${icibaTrans.content.ph_am_mp3 }"
                                   controls="controls" preload id="am_mp3" hidden></audio>
                            <span>英：</span><span>[${icibaTrans.content.ph_en }]</span><a
                                href="#"><span class="glyphicon glyphicon-music"
                                               onclick="en_start()"></span></a>  
                            <audio src="${icibaTrans.content.ph_en_mp3 }"
                                   controls="controls" preload id="en_mp3" hidden></audio>
                        </p>
                        <%
                            String jsonStr = request.getAttribute("icibaTrans").toString();
                            JSONArray wordMean = JSONObject.parseObject(jsonStr).getJSONObject(("content"))
                                    .getJSONArray("word_mean");
                            for (int i = 0; i < wordMean.size(); i++) {
                        %>
                        <p><%=wordMean.get(i)%>
                        </p>
                        <%
                            }
                        %>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: white;">
                    <h3 class="panel-title">谷歌翻译</h3>
                </div>
                <div class="panel-body" style="height: 150px;">
                    <c:forEach items="${googleTrans }" var="item">
                        ${item }<br>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="col-md-12"
        style="background-color: #868f96; height: 200px; border-top: 1px solid black;">
    <div class="col-md-12"
         style="height: 120px; padding-top: 50px; text-align: center">
        <table>
            <tr style="font-size: 20px;">
                <th style="width: 400px; heigt: 80px; padding-left: 120px;"><a
                        onFocus="if(this.blur)this.blur()" href="http://iciba.com/"
                        style="color: #CCC; text-decoration: none;">金山词霸</a></th>
                <th style="width: 400px; heigt: 80px; padding-left: 120px;"><a
                        onFocus="if(this.blur)this.blur()"
                        href="https://translate.google.cn/"
                        style="color: #CCC; text-decoration: none;">谷歌翻译</a></th>
                <th style="width: 400px; heigt: 80px; padding-left: 120px;"><a
                        onFocus="if(this.blur)this.blur()"
                        href="http://www.fanyi.baidu.com/"
                        style="color: #CCC; text-decoration: none;">百度翻译</a></th>
            </tr>
        </table>
    </div>
    <div class="col-md-12" style="height: 80px; padding-top: 10px;">
        <p style="font-size: 14px; color: #CCC;">联系我们：0930-7665362367 邮箱：
            0239408277557ghrefnwreufri</p>
    </div>
</footer>
</body>
</html>
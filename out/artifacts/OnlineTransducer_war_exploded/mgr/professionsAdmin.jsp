<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理员页面-待审核词汇</title>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link href="${pageContext.request.contextPath }/css/bootstrap-table.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath }/js/bootstrap-table.js" ></script>
<script src="${pageContext.request.contextPath }/js/bootbox.min.js" ></script>
<script src="../js/manageCategory-table.js"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap-table-zh-CN.js" ></script>
<link rel="stylesheet" href="../css/manager.css">

<!-- 高亮设置 -->
<script>
$(function(){
	$(".navbar-nav, .nav-list").find("a").each(function () {
	    if (this.href == document.location.href || document.location.href.search(this.href) >= 0) {
	        $(this).parent().addClass('active'); // this.className = 'active';
	    }
	});
});
</script>

<script type="text/javascript">
function refreshImg(img){
	 img.src = img.src + "?" + new Date().getTime();  
}
</script>

<!-- 管理员密码安全验证 -->
<script type="text/javascript">
$(function(){
	$("#checkEmail").hide();
	$("#email").blur(function(){
		var email = $("#email").val();
		$.get("../userController?op=emailCheck",
			{"email": email},
			function(data){
				var str = JSON.stringify(data);
				if(str.indexOf("false") >= 0){
					$("#mailCheck").show();
				}else{
					$("#mailCheck").hide();
				}
			})
	});
	
	
	$("#checkAdminPwd").hide();
	$("#adminPwd").blur(function(){
		var pwd = $("#adminPwd").val();
		$.get("../adminController?op=checkPwd",
				{"pwd": pwd},
				function(res){
					if(res == '0'){
						$("#checkAdminPwd").show();
					}else{
						
						$("#checkAdminPwd").hide();
					}
				})
	});
	$("#confirmAdminPwd").blur(function(){
		var confirmAdminPwd = $("#confirmAdminPwd").val();
		var adminPwd = $("#adminPwd").val();
		if(adminPwd != confirmAdminPwd){
			alert("管理员密码不一致,请重新输入");
		}
		
	})
	$("#confirmUserPwd").blur(function(){
		var confirmUserPwd = $("#confirmUserPwd").val();
		var userPwd = $("#userPwd").val();
		if(userPwd != confirmUserPwd){
			alert("用户密码不一致,请重新输入");
		}
		
	})
})
</script>

<script type="text/javascript">
$(function(){
	$("#exist").hide();
	$("#userName").blur(function(){
		var userName=$("#userName").val();
		$.post("${pageContext.request.contextPath}/userController?op=nameExist",
				{"userName":userName},		
				function(data){
					var res = JSON.stringify(data);
					if(res.indexOf("exist") >=0){
						$("#exist").show();
					}else{
						$("#exist").hide();
					}
				},"json")
	})
})
</script>

</head>
<body>
<!-- 头部 -->
<div class="container logo">
	<img alt="nwnu专业词汇在线翻译" src="">
		<a style="float:right; margin-left: 10px;" href="${pageContext.request.contextPath }/adminController?op=logout">注销</a>
	
	<a style="float:right" href="#">欢迎您: 
	<c:if test="${admin.adminName != null }">${admin.adminName }</c:if>
	<c:if test="${admin.adminName==null }">${superAdmin.superadName }</c:if>
	
	</a>
</div>
<!-- 导航栏 -->
<header class="container-fluid navigation">
	<nav class="navbar navbar-inverse" role="navigation">
		<div class="container container-fluid">
		<div class="row">
		<div class="nav-header col-md-2">
			<button type="button" class="navbar-toggle" data-toggle="collapse"data-target="#navbar-collapse">
				<span class="sr-only">切换导航</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>		
			<a class="navbar-brand mgr"  style="color:blue;font-size: 26px">Management</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse"style="text-align:center">
			<ul class="nav navbar-nav col-md-10 row">
				<li class="col-md-3"><a href="manage.jsp">待审核词汇</a></li>
				<li class="col-md-3"><a href="wordsAdmin.jsp">所有词汇</a></li>
				<li class="col-md-3"><a href="professionsAdmin.jsp">专业管理</a></li>
				<li class="col-md-3"><a href="usersAdmin.jsp">用户管理</a></li>
			</ul>
		</div>
		</div>
		</div>
	</nav>
</header>
<!-- 内容 -->

<div class="col-md-1"></div>
<section class="container col-md-10">
<div class="row" style="margin-top: 10px;">
	<div class="right  col-md-12 row">
		<div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading">查询条件</div>
            <!-- 查询条件  -->
            <div class="panel-body">
                         <div class="form-inline" style="margin-bottom: 15px; margin-top: 15px">
					            <div class="form-group">
					                <label for="queryCategoryNameText">输入专业名称：</label>
					                <input id="queryCategoryNameText" class="form-control input-md">
					            </div>
					            <div style="margin-top:15px; float:right">
						            <button class="btn btn-primary btn-md" id="queryBtn">查询</button>
						            <button class="btn btn-primary btn-md" id="addBtn">新增</button>
						            <button class="btn btn-primary btn-md" id="resetBtn">重置</button>
						        </div>
					        </div>
            </div>
            
         <!-- 添加模态窗 -->
        <div class="modal fade" id="modifyModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">添加专业</h4>
                    </div>
                    <div class="modal-body">
                    	
                            <div class="form-group">
                                <label for="modifyCategoryNameText">专业名称：</label>
                                <input type="text" id="modifyCategoryNameText" class="form-control input-sm">
                            </div>
                            <div class="form-group">
                                <label for="userName">您的初始用户名称：</label>
                                <input type="text" id="userName" class="form-control input-sm">
                                <span style="color: red; " id="exist">用户名已存在</span>
                            </div>
                            <div class="form-group">
                                <label for="userPwd">您的初始用户密码：</label>
                                <input  type="password" id="userPwd" class="form-control input-sm">
                            </div>
                            <div class="form-group">
                                <label for="confirmUserPwd">确认密码：</label>
                                <input id="confirmUserPwd" class="form-control input-sm">
                            </div>
                            <div class="form-group">
                                <label for="email">输入邮箱：</label>
                                <input id="email" class="form-control input-sm">
                                <span id="checkEmail" style="color:red;">邮箱格式错误!</span>
                            </div>
                            <div class="form-group">
                                <label for="adminName">您的初始管理员名称：</label>
                                <input type="text" id="adminName" class="form-control input-sm">
                            </div>
                            <div class="form-group">
                                <label for="adminPwd">您的初始管理员密码：</label>
                                <input type="password"  id="adminPwd" class="form-control input-sm" placeholder="不能全是数字,至少8位至16位,不能太简单">
		    					<span id="checkAdminPwd" style="color: red">密码不符合要求</span>
                            </div>
                            <div class="form-group">
                                <label for="confirmAdminPwd">确认密码：</label>
                                <input type="password" class="form-control input-sm" id="confirmAdminPwd" >
                            </div>
                            <div class="form-group">
								<label for="checkCode" class="control-label">验证码:</label>
									<div class="col-md-4">
										<input  class="form-control" type="text" name="checkCode" id="checkCode" />
							           <img onclick="refreshImg(this)" alt="验证码" src="${pageContext.request.contextPath }/checkCode" width="50px" height="30px" style="vertical-align:middle;cursor: pointer;padding-top:4px;">
							          <span style="padding-top:4px;width:60px;">(区分大小写,点击刷新)</span>   
									</div>
							</div>
                    <div class="modal-footer">
                        <button class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button class="btn btn-primary" id="saveModify">添加</button>
                    </div>
                </div>
            </div>
        </div>
    </div>  
            
            
        </div>       

        <table id="tb_categories" style="table-layout: fixed;" ></table>
    </div>
	</div>
	
	
	
</section>
<hr>
<footer class="col-md-12"
		style=" height: 100px;"></footer>
</body>
</html>
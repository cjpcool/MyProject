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
<script src="../js/manageUser-table.js"></script>
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
					                <label for="queryAdminText">管理员ID：</label>
					                <input id="queryAdminText" class="form-control input-md">
					            </div>
					            <div class="form-group">
					                <label for="queryUserText">用户名：</label>
					                <input id="queryUserText" class="form-control input-md">
					            </div>
					            <div style="margin-top:15px; float:right">
						            <button class="btn btn-primary btn-md" id="queryBtn">查询</button>
						            <button class="btn btn-primary btn-md" id="addBtn">添加超管</button>
						            <button class="btn btn-primary btn-md" id="resetBtn">重置</button>
						        </div>
					        </div>
            </div>
    </div>  
            
            
        </div>       
        <table id="tb_users" style="table-layout: fixed;" ></table>
        <table id="tb_superAdmin" style="table-layout: fixed;" ></table>
        
    </div>
	</div>
	
	
</section>
<hr>
<footer class="col-md-12"
		style=" height: 100px;"></footer>
</body>
</html>
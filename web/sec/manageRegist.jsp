<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员注册</title>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../css/bootstrap.min.css">

<script type="text/javascript">
      function refreshImg(img){
	 		img.src = img.src + "?" + new Date().getTime();
          }
</script>

<script type="text/javascript">
$(function(){
	$("#checkPwd").hide();
	$("#pwd").blur(function(){
		var pwd = $("#pwd").val();
		$.post("../adminController?op=checkPwd",
				{"pwd": pwd},
				function(res){
					if(res == '0'){
						$("#checkPwd").show();
					}else{
						
						$("#checkPwd").hide();
					}
				})
	})
})

</script>

</head>
<body>
<header>
	<nav class="navbar navbar-inverse" role="navigation">
		<div class="container container-fluid">
		<div class="row">
		<div class="nav-header col-md-2">
			<a class="navbar-brand mgr"  style="color:blue;font-size: 26px;font-style:italic;">Management</a>
		</div>
		</div>
		</div>
	</nav>

</header>
<section  style="width:80%;float:center;height:400px;margin:auto;">
	<div style="width:100%;height:50px ;float:center;margin:50px;">
			<h1 style="font-style:inherit ; text-align: center">管理员注册</h1>
	</div>
	<form action="../adminController?op=adminRegist" method="post" style="width:50%;float:center;margin:auto;" role="form">
	  <div class="form-group">
	    <label for="adminName" class="col-md-2 control-label">昵称:</label>
	     <div class="col-md-9">
	    	<input name="adminName" type="text" class="form-control" id="adminName" placeholder="请输入您的昵称">
	  	 </div>
	  </div>
	  <div class="form-group">
	    <label for="pwd" class="col-md-2 control-label">密码:</label>
	     <div class="col-md-9">
		    <input name="pwd" type="password" class="form-control" id="pwd" placeholder="不能全是数次,至少8位至16位,不能太简单">
		    <span id="checkPwd" style="color: red">密码不符合要求</span>
	 	 </div>
	  </div>
	 <div class="form-group">
		     <label for="checkCode" class="col-md-2 control-label">验证码:</label>
		     <div class="col-md-9">
			    <input type="text" class="form-control" name="checkCode" id="checkCode" >
	            <img id="code" onclick="refreshImg(this)" alt="验证码" src="${pageContext.request.contextPath }/checkCode" width="50px" height="20px" style="vertical-align:middle;cursor: pointer;">
                <span>(不区分大小写,点击刷新)</span>
		    </div>
	  </div>
	  <div>
	  
	  <button style="float:right" type="submit" class="btn btn-default">提交</button>
	  </div>
	</form>
</section>
	<footer class="col-md-12"
		style="background-color: #999; height: 200px;">
		 <div class="col-md-12" style="height:120px;">
		 <table style="margin-top:50px;">
		 <tr style="font-size:20px;">
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a href="http://m.iciba.com/" style="color:#CCC;text-decoration:none;">金山词霸</a></th>
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a href="#" style="color:#CCC;text-decoration:none;">谷歌翻译</a></th>
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a href="#" style="color:#CCC;text-decoration:none;">百度翻译</a></th>
		 </tr>
		 </table>
		 </div>
		 <div class="col-md-12" style="height:80px;">
		 <p style="font-size:18px;margin-top:40px;color:#CCC;">联系我们：0930-7665362367    邮箱： 0239408277557ghrefnwreufri</p>
		 </div>
  </footer>
</body>
</html>
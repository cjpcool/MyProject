<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
</head>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">
      function refreshImg(img){
	 		img.src = img.src + "?" + new Date().getTime();
          }
 </script>
 
 <style>
 #nav1 {
	float: left;
}
p
{
font-family:"宋体";
font-size:20px;
padding-top:8px;
padding-left:20px;
}
 </style>
 <link rel="stylesheet" href="css/bootstrap.min.css">
<body>
<div class="container-fluid" style="background-color: #316FAF;">
<div class="row">
  <div class="col-md-12 nav" style="width: 100%;height:120px;">
				<nav id="nav1">
					<div class="navbar-header">
			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"   style="  position: relative;left: 30px;bottom: 130px;"></img>
					</div>
				</nav>
			</div>
			<div class="col-md-12" style=" flaot:left;height:50px;background-color:#b2bec3;">
		      <p>找回密码</p>
		    </div>
</div>
</div>	
 <div class="container-fluid">
  <div class="row">
   <div  id="check" class="col-md-8 col-md-offset-2" style="height:400px;margin-top:40px;">
	<form action="./findPwdController?op=check" method="post" class="col-md-8" style="padding-top:40px;padding-left:160px;">
	        <div class="form-group">
				<label for="username" class="col-md-3 control-label" >用户名:</label>
					<div class="col-md-5">
					   <input type="text" name="userName" class="form-control"
									id="username" style="height:30px;"/>
					</div>
			</div>
			<br>
			<div class="form-group">
			  <label for="yanzhengma" class="col-md-3 control-label" style="margin-top:10px;">验证码:</label>
			<div class="col-md-5">
					   <input type="text" name="checkCode" class="form-control"
									id="yanzhengma"  style="height:30px;" />
				<img id="code" onclick="refreshImg(this)" alt="验证码" src="checkCode" width="50px" height="20px" style="vertical-align:middle;cursor: pointer; margin-top:5px;">
                <span >(区分大小写,点击刷新)</span>
			</div>
		</div>	
			<div class="form-group">
	        <div class="col-md-7" style="padding-left:150px;height:20px;padding-top:30px;">
			  <input type="submit" class="btn btn-primary"  value="提交"/>
	       </div>
	 </div>     		
	 </form>
</div>
</div>
</div>
</body>
</html>
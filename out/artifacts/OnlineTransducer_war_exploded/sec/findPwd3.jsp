<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码</title>
</head>
<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
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
<link rel="stylesheet" href="../css/bootstrap.min.css">
<body>
<div class="container-fluid" >
		<div class="row ">
			<div class="col-md-12 nav" style="width: 100%;height:120px;background-color: #316FAF;">
				<nav id="nav1">
					<div class="navbar-header">
			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"   style="  position: relative;left: 30px;bottom: 130px;"></img>
					</div>
				</nav>
			</div>
			<div class="col-md-12" style="height:50px;background-color:#b2bec3;">
		      <p>重置密码</p>
		    </div>		
                <div class="col-md-8 col-md-offset-2" style="height: 400px; padding-top: 100px;style="padding-top:40px;padding-left:100px;"">
					<form
						action="../findPwdController?op=changePwd"
						class="form-horizontal" role="form" method="post" class="col-md-8">
						<div class="form-group">
							<label for="newPwd" class="col-md-3 control-label">新密码:</label>
							<div class="col-md-4">
								<input type="password" name="newPwd" class="form-control"
									id="newPwd" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-md-3 control-label">确认新密码:</label>
							<div class="col-md-4">
								<input type="password" name="confirmPwd" class="form-control"
									id="confirmPwd" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8" style="padding-left: 300px;">
								<input type="submit" class="btn btn-primary" value="确认修改" />
							</div>
						</div>
					</form>
					</div>
				</div>
 </div>
</body>
</html>
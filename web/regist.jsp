<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>
<!-- 专业选框 -->
		$(function() {
			$.post("${pageContext.request.contextPath}/categoryController?op=getCategoryOption",
					function(data){
						var jsonArr = eval(data);
						$.each(jsonArr, function(index, item){
							
							$("#categories").html($("#categories").html()
									+"<option value=\""+jsonArr[index].categoryId+"\">"
									+jsonArr[index].categoryName
									+"</option>")
						})
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
					if(data.res == 'exist'){
						$("#exist").show();
					}else{
						$("#exist").hide();
					}
				},"json")
		
	})
})

</script>
<script>
$(function(){
	$("#isSame").hide();
	$("#checkPwd").blur(function(){
		var pwd = $("#pwd").val();
		var checked = $("#checkPwd").val();
		if(pwd != checked){
			$("#isSame").show();
		}else{
			$("#isSame").hide();
		}
	})
})



</script>
<script>
$(function(){
	$("#emailCheck").hide();
	
	$("#email").blur(function(){
		var email = $("#email").val();
		$.get("${pageContext.request.contextPath }/userController?op=emailCheck",
				{"email": email},
				function(data){
					var str = JSON.stringify(data);
					if(str.indexOf("false") >= 0){
						$("#emailCheck").show();
					}else{
						$("#emailCheck").hide();
					}
			}, "json")
	})
})
</script>

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
    <script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
<div class="container-fluid" style="background-color: #316FAF;">
		<div class="row ">
			<div class="col-md-12 nav" style="width: 100%;height:120px;">
				<nav id="nav1">
					<div class="navbar-header">
			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"   style="  position: relative;left: 30px;bottom: 130px;"></img>
					</div>
				</nav>
			</div>
			<div class="col-md-12" style="height:50px;background-color:#b2bec3;">
		      <p>注册</p>
		    </div>		
		</div>
 </div>
 
 <div class="container-fluid">
   <div class="row">
     <div class="col-md-8 col-md-offset-2" style=" height:725px;margin-top:10px;height:725px;padding-top:50px;padding-left:140px;">
     <form action="${pageContext.request.contextPath }/userController?op=regist" method = "post" 
       enctype="multipart/form-data" class="form-horizontal" role="form" >
    <div class="form-group">
		<label for="userName" class="col-md-3 control-label">用户名:</label>
		<div class="col-md-4">
			<input type="text" class="form-control" id="userName" name="userName" placeholder="必填">
			<span style="color: red; " id="exist">用户名已存在</span>
		</div>
	</div>
	
	<div class="form-group">
		<label for="job" class="col-md-3 control-label">职业:</label>
		<div class="col-md-4">
			       <select class="form-control" id="job" name="job" >
							<option value="1">老师</option>
							<option value="0">学生</option>
							<option value="2">其他</option>
						</select>
		</div>
	</div>
	
	
    <div class="form-group">
		<label for="img" class="col-md-3 control-label">上传头像:</label>
		<div class="col-md-4">
			<input type="file" class="form-control" id="img" name="img">
		</div>
	</div>
	<div class="form-group">
		<label for="categories" class="col-md-3 control-label">选择专业:</label>
		<div class="col-md-4">
			<select class="form-control" id="categories" name="categories">
			</select>
		</div>
	</div>
	
	<div class="form-group">
		<label for="pwd" class="col-md-3 control-label">密码:</label>
		<div class="col-md-4">
			<input type="password" class="form-control" id="pwd" name="pwd" placeholder="必填">
		</div>
	</div>
	<div class="form-group">
		<label for="checkPwd" class="col-md-3 control-label" >确认密码:</label>
		<div class="col-md-4">
			<input type="password" class="form-control" id="checkPwd" name="checkPwd"placeholder="必填">
			<span id="isSame" style="color:red">密码不一致!</span>
		</div>
	</div>
	
	<div class="form-group">
		<label for="email" class="col-md-3 control-label">邮箱:</label>
		<div class="col-md-4">
			<input  class="form-control" id="email" type="text" name="email" placeholder="必填" /><span id="emailCheck" style="color:red;">邮箱格式错误</span>
		</div>
	</div>
	<div class="form-group">
		<label for="checkCode" class="col-md-3 control-label">验证码:</label>
		<div class="col-md-4">
			<input  class="form-control" type="text" name="checkCode" id="checkCode" />
           <img onclick="refreshImg(this)" alt="验证码" src="${pageContext.request.contextPath }/checkCode" width="50px" height="30px" style="vertical-align:middle;cursor: pointer;padding-top:4px;">
          <span style="padding-top:4px;width:60px;">(区分大小写,点击刷新)</span>   
		</div>
	</div>
	<div class="form-group">
	        <div class="col-md-7" style="padding-left:220px;height:20px;">
			  <input type="submit" class="btn btn-primary" name="submit" onclick="submitForm()" value="注册"/>
			  <input type="reset"class="btn btn-primary"  name="reset" value="重置"/>
	       </div>
	 </div>
    </form>
  </div>
</div>
</div>


 <div class="col-md-12" style="margin-top:4px;background-color: #999; height: 200px; border-top:1px solid black;">
		 <div class="col-md-12" style="height:120px;padding-top:50px;">
		 <table>
		 <tr style="font-size:20px;">
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a onFocus="if(this.blur)this.blur()" href="http://m.iciba.com/" style="color:#CCC;text-decoration:none;">金山词霸</a></th>
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a onFocus="if(this.blur)this.blur()" href="https://translate.google.cn/" style="color:#CCC;text-decoration:none;">谷歌翻译</a></th>
		  <th style="width:400px;heigt:80px;padding-left:120px;"><a onFocus="if(this.blur)this.blur()" href="fanyi.baidu.com/" style="color:#CCC;text-decoration:none;">百度翻译</a></th>
		 </tr>
		 </table>
		 </div>
		 <div class="col-md-12" style="height:80px;padding-top:10px;">
		 <p style="font-size:14px;color:#CCC;">联系我们：0930-7665362367    邮箱： 0239408277557ghrefnwreufri</p>
		 </div>
  </div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息修改</title>
<style>
    ul.nav-tabs li.active a, ul.nav-tabs li.active a:hover{
        color: #fff;
        background: #0088cc;
        border: 1px solid #0088cc;
    }
    

p {
	font-family: "宋体";
	font-size: 20px;
	padding-top: 8px;
	padding-left: 20px;
}


h2 {
	font-size: 20px;
	padding-top: 10px;
}

caption {
	font-size: 20px;
	padding-top: 40px;
	padding-left: 50px;
	color: black;
}
th {
	width: 40px;
	height: 30px;
	font-size: 13px;
	text-align: center;
	border: 1px solid black;
}
td {
	width: 40px;
	height: 30px;
	font-size: 13px;
	text-align: center;
	border: 1px solid black;
}

tr {
	font-size: 13px;
	border: 1px solid black;
}
li1{
	padding-left:120px;
	width:900px;
	height:80px;
	font-size:20px;
}

</style>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.css"></script>
<link href="${pageContext.request.contextPath }/css/bootstrap-table.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath }/js/bootstrap-table.js" ></script>
<script src="../js/userinfor-table.js"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap-table-zh-CN.js" ></script>
<link rel="stylesheet" href="../css/manager.css">
<link rel="stylesheet" href="/css">

<script>
$(function(){
	var job = '';
	var str1 ='<option value="1">老师</option>'; 
	var str0 ='<option value="0">学生</option>'; 
	var str2 ='<option value="2">其他</option>'; 
	
	$.get("../userController?op=getJob",
			function(data){
				job = data;		
			}
	)
	
	if(job == '1'){
		$("#job").html(str1 + str0 + str2);
	}else if(job =='2'){
		$("#job").html(str2 + str0 + str1);
	}else{
		$("#job").html(str0 + str1 + str2);
	}
})
</script>

<script>
	$(document).ready(function() {
		$("#myNav").affix({
			offset : {
				top : 125
			}
		});
	});
</script>
<script>
$(function (){
    $("[data-toggle='popover']").popover();
});
</script>
<script type="text/javascript">
$(function() {
	var categoryName='';
	var userId = ${user.userId};
	$.ajax({url:"${pageContext.request.contextPath}/categoryController?op=getUserCategory",
			data:{
				"userId" : userId
			},
			async:false,
			type: 'get',
			success: function(data) {
				categoryName = data;
			}, 
			dataType:'text'
	})
		$.post("${pageContext.request.contextPath}/categoryController?op=getCategoryOption",
						function(data) {
							var jsonArr = eval(data);
							$.each(jsonArr, function(index, item){
                                if(jsonArr[index].categoryName == categoryName){
									$("#categories").html("<option value=\""+jsonArr[index].categoryId+"\">"
											+jsonArr[index].categoryName
											+"</option>"
											+$("#categories").html())
									}else{
									$("#categories").html($("#categories").html()
											+"<option value=\""+jsonArr[index].categoryId+"\">"
											+jsonArr[index].categoryName
											+"</option>")
									}
								})
						})
	})
</script>
<!-- 获取专业, 用户名验证 -->
<script>
$(function(){
		//用户名是否存在检验
		$("#exist").hide();
		$("#newName").blur(function() {
							var userName = $("#newName").val();
							$.get("${pageContext.request.contextPath}/userController?op=nameExist",
											{
												"userName" : userName
											}, function(data) {
												if (data.res == "exist") {
													$("#exist").show();
												} else {
													$("#exist").hide();
												}
											}, "json")
						})
	})
</script>

<!-- 获取邮箱 -->
<script type="text/javascript">
$(function(){
	$.get('${pageContext.request.contextPath}/userController?op=getEmail',
		function(data){
			$("#email").val(data);
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
$(function(){
	var adminId = '';
	$.ajax({
		url:'../uAdminController?op=getAdminId',
		async:false,
		type:'get',
		dataType:'json',
		success:function(res){
			adminId = res[0];
		}
	})
	if(adminId ==null || adminId == "" || adminId =="undefine"){
		
		$("#uAdmin").html("<a href='manageRegist.jsp'>注册管理员帐户(您还没有注册管理员帐户,必须成功上传150个单词才能注册)</a>"+$("#uAdmin").html());
	}else
		$("#uAdmin").html("<p >您的管理员账号:"+adminId+"</p>"+$("#uAdmin").html());
})
</script>

<link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>
 <div style="width:100%" >
 <div  style="background-color:#316FAF;height:120px;">
			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"   style="  position: relative;left: 30px;bottom: 130px;"></img>
 </div>
 <div class="container-fluid" style="background-color:#b2bec3;height:45px;">
 <ul class="nav nav-tabs navbar-right" style="border-bottom:none;">
  <li><a onFocus="if(this.blur)this.blur()" href="../index.jsp" style="font-size: 16px;color: white;padding-top:10px;">查询词汇</a></li> 
 <li><a onFocus="if(this.blur)this.blur()" href="addVocab.jsp" style="font-size: 16px;color: white;padding-top:10px;">添加词汇</a></li>
 <li><a onFocus="if(this.blur)this.blur()" data-toggle="modal" data-target="#userresponse" style="font-size: 16px;color: white;padding-top:10px;">用户反馈</a></li>
                     <div class="modal fade" id="userresponse" tabindex="-1" role="dialog" aria-labelledby="myModalLabe2" aria-hidden="true">
                        <div class="modal-dialog">
                                <div class="modal-content">
                                 <div class="modal-header">
                                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						             <div class="modal-title" id="myModalLabe2" style="height:100px;background-color:#316FAF;">
						             			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" width="300px" height="180px" style="position: relative;left: 30px;bottom: 50px;"></img>
						             </div>
						         </div>
						         <div class="modal-body" style="height:400px;">
						        <div style="width:30%;height: 400px;float:right;margin-top:10px;background-image:url(../img/static/cbc3668505e73afb.jpg);
						        background-size:100% 300px;background-repeat:no-repeat;">
						        </div>
						        <div style="width:70%;height:400px;float:left;padding-top:20px;text-align:center;">
						        <p style="font-size:20px;">请将反馈发至邮箱：78495796078</p><br>
						        <p style="font-size:20px;">谢谢您的反馈！</p>
						        </div>
						        </div>
                                <div class="modal-footer">
                                 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>   
                                 </div>
						       </div>
						      </div>
                      </div>
 <li><a onFocus="if(this.blur)this.blur()" href="../regist.jsp" target="_blank"  style="font-size: 16px;color: white;padding-top:10px;">注册</a></li>
                    
		 <c:if test="${user!=null }">
			<li><a onFocus="if(this.blur)this.blur()"
						href="#" style="font-size: 16px;color: white;padding-top:10px;">个人中心</a></li>
					<li><a style="font-size: 16px;color: white;padding-top:10px;"><span>${user.userName }</span></a></li>
					<li><a onFocus="if(this.blur)this.blur()"href="userController?op=logout" style="font-size: 16px;color: white;padding-top:10px;">注销</a></li>
					<li><a style="font-size: 16px;color: white;padding:0px;"><img src="${pageContext.request.contextPath }/img/user/${user.img }" height="45px" width="60px"></a></li>
		</c:if>
		
	        <c:if test="${user == null }">
	              <li><a  style="text-decoration:none;font-size: 16px;color: white;padding-top:10px;" onFocus="if(this.blur)this.blur()" data-toggle="modal" data-target="#myModal">登陆</a></li> 
		          <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	               <div class="modal-dialog">
		            <div class="modal-content">
			            <div class="modal-header" style="height:120px;">
				             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					       &times;
				       </button>
				      <div class="modal-title" id="myModalLabel" style="height:100px;background-color:#316FAF;">
					         <img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" width="300px" height="180px" style="position: relative;left: 30px;bottom: 50px;"></img>
				      </div>
			         </div>
	           <div class="modal-body" style="height:400px;padding-top:40px;padding-left:70px;">
                    <form  id="loginForm"
                    class="form-horizontal" role="form">
                     <div class="form-group">
		        <label for="userName" class="col-md-3 control-label">用户名:</label>
		       <div class="col-md-4">
			    <input type="text" class="form-control" name="userName" id="userName" >
		    </div>
	      </div>
	      <div class="form-group">
		     <label for="pwd" class="col-md-3 control-label">密码:</label>
		     <div class="col-md-4">
			    <input type="password" class="form-control" name="pwd" id="pwd" >
		    </div>
	      </div>
	      <div class="form-group">
		     <label for="checkCode" class="col-md-3 control-label">验证码:</label>
		     <div class="col-md-4">
			    <input type="text" class="form-control" name="checkCode" id="checkCode" >
	            <img id="code" onclick="refreshImg(this)" alt="验证码" src="/OnlineTransducer/checkCode" width="50px" height="20px" style="vertical-align:middle;cursor: pointer;">
                <span>(不区分大小写,点击刷新)</span>
		    </div>
	      </div>
	      <div class="form-group">
	        <div class="col-md-8" style="padding-left:150px;padding-top:20px;">
			  <input type="button" onclick="login()" class="btn btn-primary"  value="登陆">
			  <input type="reset" name="reset" class="btn btn-primary" value="重置"/>
			  <br>
			  <input type="button" onclick="javascript:window.location.href='findPwd.html'" class="btn btn-primary" style="margin-left:220px;margin-top:10px;" value="找回密码">
	       </div>
	     </div>
	     <div class="form-group">
	        <div class="col-md-8" style="padding-left:150px;padding-top:20px;">
			  <p style="color:black;font-size:16px;">没有账号，去<a href="regist.jsp" style="text-decoration:none;" onFocus="if(this.blur)this.blur()" >注册</a></p>
	       </div>
	     </div>
      </form>
   </div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		  </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
     </div>
		</c:if>
		</ul>
	</div>
	</div>

   <div class="container-fluid">
	<div class="col-md-12"
		style="height: 720px; margin-top: 15px;margin-left:50px;">
		<div class="col-md-2" data-spy="scroll" data-target="#myScrollspy" style="float:left;height:200px;">
		<div id="myScrollspy" style="padding-top: 20px;">
			<ul  class="nav nav-tabs nav-stacked" style="width: 140px;margin-top: 20px;border-radius: 4px;
        border: 1px solid #ddd;box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);" data-offset-top="125">

				<li style="margin: 0;border-top: 1px solid #ddd;border-top: none;
				border-radius: 4px 4px 0 0;"class="active"><a onFocus="if(this.blur)this.blur()" href="#home"  data-toggle="tab">
						修改个人资料 </a></li>
				<li style="margin: 0;border-top: 1px solid #ddd;"><a onFocus="if(this.blur)this.blur()" href="#ios" data-toggle="tab">修改密码</a></li>
				<li style="margin: 0;border-top: 1px solid #ddd;border-radius: 0 0 4px 4px;"onclick="showVocab()"><a onFocus="if(this.blur)this.blur()" href="#vocabul"  data-toggle="tab">词汇添加记录</a></li>
			</ul>
		</div>
         </div> 
         
         
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="home">
				<div class="col-md-9" style="height: 700px;float:right;border-top:1px solid black;
				margin-right:60px;">

					<form
						action="${pageContext.request.contextPath }/userController?op=changeBasicInfo"
						method="post" class="form-horizontal"
						enctype="multipart/form-data">
						
						<div class="col-md-2" style="height:720px;float:right;margin-right:70px;padding-top:30px;">
						<!-- 头像上传 -->
						<div style="padding-top:20px;">
								<img height="100px" width="100px" alt="头像" src="${pageContext.request.contextPath }/img/user/${user.img }">
						</div>
						</div>
						
						<div class="col-md-7" style="height:720px;float:right;padding-top:40px;padding-left:30px;">
						
						<!-- 用户id显示 不修改 -->
					<div class="form-group">
						<label for="ID" class="col-md-3 control-label">ID:</label>
						<div id="ID" class="col-md-5" style="padding-top:5px;font-size:16px;">
                               <span>${user.userId }</span>
						</div>
					</div>
						
						<!-- 用户名显示 输入表单修改 -->
					<div class="form-group">
						<label for="firstname" class="col-md-3 control-label">用户名:</label>
						<div class="col-md-5" style="padding-top:5px;font-size:16px;">
							${user.userName }
						</div>
                     </div>
                     
                      <div class="form-group">
                      <label for="newName" class="col-md-3 control-label">新用户名:</label>
							<div class="col-md-5">
								<input name="newName" placeholder="如需修改请填写" type="text" class="form-control" id="newName">
								 <span style="color: red" id="exist">用户名已存在</span>
							</div>
						</div>
						
						
					<div class="form-group" id="userCategory">
							<label for="categories" class="col-md-3 control-label">专业:</label>
							<div class="col-md-5">
								<select class="form-control" id="categories" name="categories"></select>
							</div>
					</div>
                   <!-- 职业 -->
                   <div class="form-group">
						<label for="job" class="col-md-3 control-label">职业:</label>
						<div class="col-md-5">
                        <select class="form-control"
							data-toggle="dropdown" name="job" id="job">
							
						</select>
						</div>
				   </div>
						

						<div class="form-group">
							<label for="email" class="col-md-3 control-label">邮箱:</label>
							<div class="col-md-5">
								<input id="email" class="form-control" type="text" placeholder="选填" name="email">
								<span id="emailCheck" style="color:red;">邮箱格式错误</span>
							</div>
						</div>
                        
                        <div class="form-group">
						   <label for="img" class="col-md-3 control-label">更换头像:</label>
						    <div class="col-md-5">
							   <input type="file" name="img" class="form-control" id="img"/>
						    </div>
						</div>
                        


						<!-- 输入密码验证 -->
						<div class="form-group">
						   <label for="pswd" class="col-md-3 control-label">输入密码:</label>
						    <div class="col-md-5">
							   <input type="password" name="pwd" class="form-control" id="pswd"/><br>
						    </div>
						</div>

						<div class="form-group">
							<div class="col-md-9" style="padding-left:150px;">
								<input type="submit" name="submit" class="btn btn-primary" value="提交" /> <input
									type="reset" name="reset"  class="btn btn-primary" value="重置" />
							</div>
						</div>
						</div>
					</form>
				</div>
			</div>

			<!-- 密码修改 -->
			<div class="tab-pane fade" id="ios">
				<div class="col-md-9" style=" float:right;height: 700px; padding-top: 100px;border-top:1px solid black;margin-right:60px;">
					<form
						action="${pageContext.request.contextPath}/userController?op=changePwd"
						class="form-horizontal" role="form" method="post">
						<div class="form-group">
							<label for="firstname1" class="col-md-3 control-label">旧密码:</label>
							<div class="col-md-5">
								<input type="password" name="oldPwd" class="form-control"
									id="firstname1" />
							</div>
						</div>
						<div class="form-group">
							<label for="firstname" class="col-md-3 control-label">新密码:</label>
							<div class="col-md-5">
								<input type="password" name="newPwd" class="form-control"
									id="firstname" />
							</div>
						</div>
						<div class="form-group">
							<label for="firstname2" class="col-md-3 control-label">确认新密码:</label>
							<div class="col-md-5">
								<input type="password" name="confirmPwd" class="form-control"
									id="firstname2" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8" style="padding-left: 400px;">
								<input type="submit" class="btn btn-primary" value="确认修改" />
							</div>
						</div>
					</form>
				</div>
			</div>
			

			<div class="tab-pane fade" id="vocabul">
			  <div class="col-md-9" style="height:700px;float:right;margin-right:60px;">
			    <div class="table-responsive" id="vseTable" style="height:650px;" >
	                 <table class="table"  id="tb_vses" style="table-layout: fixed;">
				</table>
			   </div>
			   <div style="height:50px;" >
			    	<a onFocus="if(this.blur)this.blur()" href="../CommanderLogin.html" style="text-decoration:none;"><button  type="button" class="btn btn-primary" style="float:right;margin-top:5px;margin-right:8px;">管理员登陆</button></a>
			    	<div class="col-md-4" id="uAdmin"></div>
			   </div >
			  </div>
			</div>


		</div>
	</div>
	</div>

	<div class="col-md-12"
		style="background-color:  #999; height: 200px;margin-top:10px;border-top:1px solid black;">
		<div class="col-md-12" style="height:120px;padding-top:50px;">
		 <ul>
		  <li1><a onFocus="if(this.blur)this.blur()" href="http://m.iciba.com/" style="color:#CCC;text-decoration:none;">金山词霸</a></li1>
		  <li1><a onFocus="if(this.blur)this.blur()" href="https://translate.google.cn/" style="color:#CCC;text-decoration:none;">谷歌翻译</a></li1>
		  <li1><a onFocus="if(this.blur)this.blur()" href="fanyi.baidu.com/" style="color:#CCC;text-decoration:none;">百度翻译</a></li1>
		 </ul>
		 </div>
		 <div class="col-md-12" style="height:80px;padding-top:10px;">
		 <p style="font-size:14px;color:#CCC;">联系我们：0930-7665362367    邮箱： 0239408277557ghrefnwreufri</p>
		 </div>
	</div>

</body>
</html>
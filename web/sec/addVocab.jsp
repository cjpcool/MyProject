<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加词汇</title>

</head>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>
<!-- 获取专业到表单中 -->
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
<script>
function submitForm(){
	var form = new FormData(document.getElementById("addForm"));
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath }/translateController?op=addVocab",
		data:form,
		dataType:"json",
		cache:false,
		processData: false,  
		contentType: false,
		success:function(data){
            var res = JSON.stringify(data);
            if(res.indexOf("addsuc") >= 0){
				alert("单词上传成功，待审核");
            }else{
            	alert("上传失败,请检查信息是否有误!");
            }
		},
        error:function(e){
            alert("请求错误！");
        }

	});
};


</script>
<script type="text/javascript">
<!-- 检查单词格式是否正确-->
$(function(){
	$("#isVocab").hide();
	$("#vocab").blur(function(){
		var vocab = $("#vocab").val();
		$.post("${pageContext.request.contextPath}/translateController?op=isVocab",
				{"vocab":vocab},
				function(data){
					var res = JSON.stringify(data);
					if(res.indexOf("false") >= 0)
						$("#isVocab").show();
					else
						$("#isVocab").hide();
		},"json");
	})
})
</script>
<script>
$(function(){
	$("#vocabExist").hide();
	$("#vocab").blur(function(){
		var vocab = $("#vocab").val();
		$.post("${pageContext.request.contextPath}/translateController?op=vocabExist",
				{"vocab":vocab},
				function(data){
					var res = JSON.stringify(data);
					if(res.indexOf("false") >= 0)
						$("#vocabExist").show();
					else
						$("#vocabExist").hide();
		},"json");
	})
})

</script>
<script type="text/javascript">
<!--检查例句里面是否包含了该单词-->
$(function(){
$("#example").blur(function(){
	var example = $("#example").val();
	var vocab = $("#vocab").val();
	$.post("${pageContext.request.contextPath}/translateController?op=hasThisVocab",
			{
				"example":example,
				"vocab":vocab
			},
			function(data){
				var res = JSON.stringify(data);
				if(res.indexOf("false") >= 0)
					alert("例句不含该单词或其其他形式,请检查");
	}, "json");
})
})

</script>
<style>
.nav-tabs li a {
	font-size: 16px;
	color: white;
	padding-top:10px;
}
p
{
font-family:"宋体";
font-size:20px;
padding-top:12px;
padding-left:20px;
}
label{
  color:black;
  font-size:16px;
}
.boundary {
	height: 1px;
	background-color: black;
	width: 100%;
}
p1
{
font-family:"宋体";
font-size:18px;
color:black;
padding-left:4px;
}
p2
{
font-family:"宋体";
font-size:15px;
color:black;
}
</style>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<body>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<div style="width:100%" >
 <div class="col-md-12" style="background-color:#316FAF;height:120px;">
			<img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" alt="logo" height="350px"   style="  position: relative;left: 30px;bottom: 130px;"></img>
 </div>
 <div class="col-md-12" style="background-color:#b2bec3;height:45px;">
 <ul class="nav nav-tabs navbar-right" style="border-bottom:none;">
  <li><a onFocus="if(this.blur)this.blur()" href="../index.jsp">查询词汇</a></li> 
 <li><a onFocus="if(this.blur)this.blur()" href="#">添加词汇</a></li>
 <li><a onFocus="if(this.blur)this.blur()" data-toggle="modal" data-target="#userresponse">用户反馈</a></li>
                     <div class="modal fade" id="userresponse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                                <div class="modal-content">
                                 <div class="modal-header">
                                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						             <div class="modal-title" id="myModalLabel" style="height:100px;background-color:#316FAF;"><img  src="${pageContext.request.contextPath }/img/static/indexLogo.png" 
						             alt="logo" width="300px" height="180px" style="position: relative;left: 30px;bottom: 50px;"></img></div>
						         </div>
						         <div class="modal-body" style="height:400px;">
						        <div style="width:30%;height: 400px;float:right;margin-top:10px;background-image:url(../img/static/cbc3668505e73afb.jpg);
						        background-size:100% 300px;background-repeat:no-repeat;">
						        </div>
						        <div style="width:70%;height:400px;float:left;padding-top:20px;text-align:center;">
						        <p style="font-size:20px;color:black;">请将反馈发至邮箱：78495796078</p><br>
						        <p style="font-size:20px;color:black;">谢谢您的反馈！</p>
						        </div>
						        </div>
                                <div class="modal-footer">
                                 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>   
                                 </div>
						       </div>
						      </div>
                      </div>
 <li><a onFocus="if(this.blur)this.blur()" href="../regist.jsp" target="_blank">注册</a></li>
                    
		 <c:if test="${user!=null }">
			<li><a onFocus="if(this.blur)this.blur()"
						href="userInfo.jsp" >个人中心</a></li>
					<li><a><span>${user.userName }</span></a></li>
					<li><a onFocus="if(this.blur)this.blur()"href="userController?op=logout">注销</a></li>
					<li><a style="padding: 0px"><img src="${pageContext.request.contextPath }/img/user/${user.img }" height="45px" width="60px"></a></li>
		</c:if>
		
	        <c:if test="${user == null }">
	              <li><a  style="text-decoration:none;" onFocus="if(this.blur)this.blur()" data-toggle="modal" data-target="#myModal">登陆</a></li> 
		          <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	               <div class="modal-dialog">
		            <div class="modal-content">
			            <div class="modal-header" style="height:120px;">
				             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					       &times;
				       </button>
				      <div class="modal-title" id="myModalLabel" style="background-color:#316FAF;height:100px;">
					         <img src="${pageContext.request.contextPath }/img/static/indexLogo.png" 
						             alt="logo" width="300px" height="180px" style="position: relative;left: 30px;bottom: 50px;"></img>
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
	
	<div class="container">
	 <div class="row">
	  <div class="col-md-10 col-md-offset-1" style="height:550px;margin-top:25px;">
        <div class="row">
        <div class=" col-md-8" style=" float:left;height:550px;padding-top:50px;padding-left:30px;">
        <form id="addForm"class="form-horizontal" role="form">
	     <div class="form-group">
		   <label for="vocab" class="col-md-3 control-label">输入词汇:</label>
		   <div class="col-md-5">
		    <input class="form-control"id="vocab" placeholder="必填" type="text" name="vocab" /><span id="isVocab"
		    style="color:red"> 单词填写错误,请检查是否有特殊符号(包括空格)</span>
		    <span id="vocabExist"
		    style="color:red"> 单词已存在</span>
		   </div>
	    </div>
	    <div class="form-group">
		   <label for="trans" class="col-md-3 control-label" >输入释义:</label>
		   <div class="col-md-5" style="height:10px;">
	         <input class="form-control" id="trans" placeholder="必填" type="text" name="trans"/>
		  </div>
	   </div>
	   <div class="form-group">
		  <label for="categories" class="col-md-3 control-label">选择专业:</label>
		   <div class="col-md-5">
			  <select class="form-control" id="categories" name="categoryId"></select>
		  </div>
	  </div>
	   <div class="form-group">
		  <label for="firstname" class="col-md-3 control-label" >输入近义词:</label>
		     <div class="col-md-5">
			   <input class="form-control" id="firstname" placeholder="可选" type="text" name="similar"/>
		   </div>
	  </div>
	  <div class="form-group">
		  <label for="example" class="col-md-3 control-label" >输入例句:</label>
		    <div class="col-md-5">
			 <input class="form-control" id="example" placeholder="可选,长度不超过500" type="text" name="example"/>
		  </div>
	  </div>
	  <div class="form-group">
		  <label for="image" class="col-md-3 control-label" >上传图片:</label>
		    <div class="col-md-5">
			  <input class="form-control" id="image"  type="file" name="img" style="height:40px;"/>
		  </div>
	  </div>
	  <div class="form-group">
	        <div class="col-md-8" style="padding-left:160px;height:20px;">
			  <input type="button"class="btn btn-primary" onclick="submitForm()" value="提交"/>
			  <input type="reset" class="btn btn-primary" name="reset"  value="重置"/>
	       </div>
	 </div>
	</form>
 </div >
 
 <div class="col-md-3" style="float:right;height:300px;border-radius:10px;border:1px solid gray;margin-top:50px;margin-right:40px;"> 
   <div style="padding-top:4px;">
     <p1>词汇添加注意事项:</p1>
     <br>
     <p2>1.若单词填写错误或该单词已在数据库中存在，会出现提示，请修改单词。<br>
     <br>
     2.输入的例句中必须包含该单词，也可以只输入该单词。例句中也可包含其他字符。</p2>
   </div>
 </div>
 </div>
</div>
</div>
</div>
    
   <div class="container-fluid">
   <div class="row">
    <div class="col-md-12" style="background-color: #999; height: 200px;margin-top:4px;border-top:1px solid black;">
	 <div class="col-md-12" style="height:120px;padding-top:50px;">
		 <table >
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
</div>
</div>
</body>
</html>
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
<script src="../js/manage-table.js"></script>
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

<!-- 加载专业 -->
<script type="text/javascript">
$(function() {
			$.get("${pageContext.request.contextPath }/categoryController?op=getCategoryOption",
					function(data){
						var jsonArr = eval(data);
						$.each(jsonArr, function(index, item){
							$("#categories").append(
									"<li><button class=\"list-group-item category\" value=\""+jsonArr[index].categoryId+"\">"
									+jsonArr[index].categoryName+"</button></li>"
									)
						})
			});
			$("#categories").on("click", ".category", function(){
				$("#categories").children().children().removeClass("active");
				$(this).addClass("active");
			})
		})
</script>
<!-- 检测单词是否存在 -->
<script>
$(function(){
	$("#vocabExist").hide();
	$("#modifyVocabText").blur(function(){
		var vocab = $("#modifyVocabText").val();
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

<!-- 检查单词格式是否正确--> 
<script type="text/javascript">
$(function(){
	$("#isVocab").hide();
	$("#modifyVocabText").blur(function(){
		var vocab = $("#modifyVocabText").val();
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

<section class="container">
<div class="row" style="margin-top: 10px;">
	<div class="col-md-2" >
		
		<div class="sidebar">
			<div class="well sidebar-nav">
				<ul id="categories" class="nav nav-list">
					<li class="nav-header">选择专业</li>
					<li><button class="list-group-item active category" value="-1">所有专业</button></li>
				</ul>			
			</div>
		</div>
	</div>		
	<div class="right  col-md-10 row">
		<div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading">查询条件</div>
            <!-- 查询条件  -->
            <div class="panel-body">
                         <div class="form-inline" style="margin-bottom: 15px; margin-top: 15px">
					            <div class="form-group">
					                <label for="queryVocabText">英文：</label>
					                <input id="queryVocabText" class="form-control input-md">
					            </div>
					            <div class="form-group">
					                <label for="queryTranseText">中文：</label>
					                <input id="queryTranseText" class="form-control input-md">
					            </div>
					            <div class="form-group">
					                <label for="queryUserText">用户：</label>
					                <input id="queryUserText" class="form-control input-md">
					            </div>
					            <div style="margin-top:15px; float:right">
						            <button class="btn btn-primary btn-md" id="queryBtn">查询</button>
						            <button class="btn btn-primary btn-md" id="addBtn">新增</button>
						            <button class="btn btn-primary btn-md" id="resetBtn">重置</button>
						        </div>
					        </div>
            </div>
            
         <!-- 修改模态窗 -->
        <div class="modal fade" id="modifyModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">修改信息</h4>
                    </div>
                    <div class="modal-body">
                    	<p id="moifyVocabId"></p>
                    	<div class="form-group">
                    		<img width="100px" height="60px" id="modifyImg" alt="相关图片" >
                    		<br>
                    		<label for="modifyImgText">图片修改：</label>
                    		<input type="file"  id="modifyImgText" class="file input=sm"/>
                    	</div>
                    	
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="modifyVocabText">英文：</label>
                                <input id="modifyVocabText" class="form-control input-sm">
                                <span id="isVocab"style="color:red"> 单词填写错误,请检查是否有特殊符号(包括空格)</span>
		    					<span id="vocabExist"style="color:red"> 单词已存在</span>
                            </div>
                            <div class="form-group">
                                <label for="modifyTranseText">中文：</label>
                                <input id="modifyTranseText" class="form-control input-sm">
                            </div>
                        </div>
	                        <div class="form-group">
	                            <label for="modifyAddTimeText">添加时间：</label>
	                            <a id="modifyAddTimeText" class="form-control input-sm"></a>
	                        </div>
	                        <div class="form-group">
	                            <label for="modifyLastTimeText">最后修改时间：</label>
	                            <a id="modifyLastTimeText" class="form-control input-sm"></a>
                        </div>
                        <div class="form-group">
                            <label  for="modifyCategoryText">所属专业：</label>
                            <a style="width: 200px;" id="modifyCategoryText" class="form-control input-sm"></a>
                            <span style="size: 16px; font-weight: 5px">修改专业: </span>
                            <select id="modifyCategories"></select>
                        </div>
                        <div class="form-group">
                            <label for="modifyStatus">当前状态：</label>
                            <div id="modifyStatus" class="btn-group" data-toggle="buttons">
						        <label class="btn btn-primary" id = "modifyStatus0">
						            <input type="radio" name="status" value="0"> 待审核
						        </label>
						        <label class="btn btn-primary" id = "modifyStatus1">
						            <input type="radio" name="status" value="1"> 审核成功
						        </label>
						       
						    </div>
                        </div>
                        <div class="form-group">
                            <label  for="modifySimilarText">近义词：</label>
                            <div id="modifySimilarText"></div>
                        </div>
                        <div class="form-group">
                            <label for="modifyExamText">例句：</label><span>(例句必须包含单词)</span>
                        	<div id = "modifyExamText"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button class="btn btn-primary" id="saveModify">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>  
            
            
        </div>       

        <table id="tb_vses" style="table-layout: fixed;" ></table>
    </div>
	</div>
	
	
	
</section>
<hr>
<footer class="col-md-12"
		style=" height: 100px;"></footer>
</body>
</html>
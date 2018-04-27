$(function () {
	
	var user;
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});

function modifyPer(row){
	$("#modifyModal").modal("show");
	$("#moifyVocabId").html('<span style="font-size: 18px; font-weight: bolder;">单词ID: <span id="modifyVocabIdText">'+row.voca.vocabId+'</span></span> ');
	$("#modifyImg").attr("src", "../img/vocab/" + row.voca.img);
	var lastTime = new Date(row.voca.lastTime).toLocaleDateString();
	var addTime = new Date(row.voca.addTime).toLocaleDateString();
	var categoryName;
	$.ajax({url: "../categoryController?op=getCategoryById",
		data: {"categoryId":row.voca.categoryId},
		type:'get',
		async:false,
		success: function(res){
			categoryName = res;
		}});
	$("#modifyVocabText").val(row.voca.vocab);
	$("#modifyTranseText").val(row.voca.transe);
	$("#modifyAddTimeText").html(addTime);
	$("#modifyLastTimeText").html(lastTime);
	$("#modifyCategoryText").html(categoryName);
	$("#modifyCategories").html('');
	$.ajax({url:"../categoryController?op=getCategoryOption",
		type:"get",
		async:false,
		success:function(data){
			var jsonArr = eval(data);
			$.each(jsonArr, function(index, item){
				if(jsonArr[index].categoryName == categoryName){
					$("#modifyCategories").html("<option value=\""+jsonArr[index].categoryId+"\">"
							+jsonArr[index].categoryName
							+"</option>"
							+$("#modifyCategories").html())
					}else{
					$("#modifyCategories").html($("#modifyCategories").html()
							+"<option value=\""+jsonArr[index].categoryId+"\">"
							+jsonArr[index].categoryName
							+"</option>")
					}
				})
					
		},
		
	})
	if(row.voca.status == 1){
		$("#modifyStatus1").addClass("active");
	}else{
		$("#modifyStatus0").addClass("active");
	}
	$("#modifySimilarText").html("");
	$("#modifyExamText").html("");
	$.each(row.exams, function(index, item){
		$("#modifyExamText").append('<span>'+item.exampleId+'~</span><input value="'+ item.example +'" class="form-control input-sm "/>');
	})
	$.each(row.similars, function(index, item){
		$("#modifySimilarText").append('<span>'+item.similarId+'~</span><input value="'+ item.similar +'" class="form-control input-sm "/>');
	})
}
function delPer(vocabId){
	bootbox.confirm({message:"确认删除?",
		size:"small",
		callback: function (result) {
			if(result){
				$.ajax({url:"../translateController?op=deleteVocab",
					data:{'vocabId':vocabId},
					type:'get',
					success:function(res){
						if(res=='1'){
							alert("删除成功!");
							$('#tb_vses').bootstrapTable("selectPage","1");
						}
						else{
							alert("删除失败!")
						}
					},
					error:function(){
						alert("请求出错!");
					}
				})
			}
		}
	});
};
var TableInit = function () {
	//var newCategoryId = -1;
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_vses').bootstrapTable({
            url: '../vseController?op=getWaitingVocabs',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            dataType: "text",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）!!!!!!!!!
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "voca.vocabId",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            responseHandler: function(data){
            	data = data.replace(/"userId":/g, "\"userId\":\"");
            	data =data.replace(/,"vocab"/g, "\",\"vocab\"");
            	res = JSON.parse(data);
            	return  res;
            },
            columns: [{
                field: 'voca.vocabId',
                title: 'ID'
            }, {
                field: 'voca.img',
                title: '图片',
                formatter: function(value){
                	var str = "<a  class='thumbnail' style=' margin:0px;height: 50px; width: 60px;'><img style='height: 40px; width: 60px;vertical-align:middle;cursor: pointer;'  alt='imdetail' src='../img/vocab/" + value +"'/></a>";
                	return str;
                }
            }, {
                field: 'voca.vocab',
                title: '英文释义'
            }, {
                field: 'voca.transe',
                title: '中文释义'
            }, {
                field: 'voca.addTime',
                title: '添加时间',
                formatter: function(v){
                	var transTime = new Date(v);
                	var date = new Date(transTime);
                	return transTime.toLocaleDateString();
               }
            }, {
                field: 'voca.categoryId',
                title: '所属专业',
                formatter: function(v){
                	var categoryName;
                	$.ajax({url: "../categoryController?op=getCategoryById",
                			data: {"categoryId":v},
                			type:'get',
                			async:false,
                			success: function(res){
                				categoryName = res;
                			}}
                	)
                	return categoryName;
                }
            }, {
                field: 'voca.userId',
                title: '添加用户',
                formatter: function(v){
                	$.ajax({url:'../userController?op=getUserById',
                			data:{'userId':v},
                			async:false,
                			type: 'get',
                			success: function(data){
                				user = data;
                			}});
                	var obj = eval("("+user+")");
                	return obj.userName;
                }
            }, {
                field: 'voca.status',
                title: '当前状态',
                formatter: function(v, row){
                	var str;
                	if(v == '0'){
                		str = "待审核";
                		return str;
                	}else{
                		str = "审核成功";
                		return str;
                	}
                }
            }, {
            	formatter: function (value, row, index) {
            		var rowStr = JSON.stringify(row);
            		  return [
                          '<a href=\'javascript:modifyPer('+rowStr+')\' >' +
                              '<i class="glyphicon glyphicon-pencil"></i>修改/详情' +
                          '</a><br>',
                          '<a href=\'javascript:delPer(' + row.voca.vocabId + ')\'>' +
                              '<i class="glyphicon glyphicon-remove"></i>删除' +
                          '</a>'
                      ].join('');
                },
                title: '操作'
            }, ],
            rowStyle: function (row, index) {
                var ageClass = '';
                if (row.voca.status == 0) {
                    ageClass = 'text-danger';
                }
                return {classes: ageClass}
            },
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
    	var newCategoryId;
    	newCategoryId = $("#categories li .active").val()!=null?$("#categories li .active").val():-1;
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            pageIndex: params.offset,  //页码
            categoryId: newCategoryId,
            transe: $("#queryTranseText").val(),
            vocab: $("#queryVocabText").val(),
            user: $("#queryUserText").val(),
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    	$("#queryBtn").click(function(){
    		$('#tb_vses').bootstrapTable("selectPage","1");
    	});
    	$("#resetBtn").click(function(){
    		$("#queryTranseText").val("");
    		$("#queryVocabText").val("");
    		$("#queryUserText").val("");
    		$("#categories").children().children().removeClass("active");
			$("#categories").find("li").get(0).children().addClass("active");
    	});
    	$("#addBtn").click(function(){
    		var a = $("<a href='../sec/addVocab.jsp' target='_blank'></a>").get(0);
            
            var e = document.createEvent('MouseEvents');
            e.initEvent( 'click', true, true );
            a.dispatchEvent(e);

    	});
    	$("#saveModify").click(function(){
    		
    		bootbox.confirm({message:"请认真检查,确认保存当前修改",
    			size:"small",
    			callback: function (result) {
    			/**
    			 * 若确认修改:
    			 *    获取模态窗中的所有value 传递给后台
    			 */	
    			if(result){
    				var img =document.getElementById("modifyImgText").files[0];
    				var vocab = $("#modifyVocabText").val();
    				var vocabId = $("#modifyVocabIdText").html();
    				var transe = $("#modifyTranseText").val();
    				var categoryId = $("#modifyCategories").val();
    				var status ;
    				var statusArr = document.getElementsByName("status");
    				var i;
    				for(i=0; i<statusArr.length;i++)  {
    			         if(statusArr[i].checked)  { 
    			             status = statusArr[i].value;
    			         } 
    				}
    				var similars = '';
    				var similarArr;
    				similarArr = $("#modifySimilarText").children();
    				for(i = 0; i < similarArr.length;i++){
    					if(i % 2 == 0){
    						similars += similarArr[i].innerHTML;
    					}else if(i % 2 !=0){
    						similars += similarArr[i].value;
    					}
    					if( i %2 != 0 && i != similarArr.length-1){
    						similars += ';';
    					}
    				}
    				
    				var exams = '';
    				var examArr;
    				examArr = $("#modifyExamText").children();
    				for(i = 0; i < examArr.length;i++){
    					if(i % 2 == 0){
    						exams += examArr[i].innerHTML;
    					}else if(i % 2 !=0){
    						exams += examArr[i].value;
    					}
    					if( i %2 != 0 && i != similarArr.length-1){
    						exams += ';';
    					}
    				}
    				var formData = new FormData();
    				formData.append("vocab", vocab);
    				formData.append("vocabId", vocabId);
    				formData.append("transe", transe);
    				formData.append("categoryId", categoryId);
    				formData.append("status", status);
    				formData.append("similars", similars);
    				formData.append("exams", exams);
    				formData.append("img", img);
    				$.ajax({url:"../vseController?op=saveModify",
    					data:formData,
    					type: 'post', 
    					cache: false,//上传文件无需缓存 
    	                processData: false,//用于对data参数进行序列化处理 这里必须false
    	                contentType: false, //必须 
    					success: function(data){
    						if(data == '1'){
    							$("#modifyModal").hide();
    							alert("修改成功!");
    							window.location.href='manage.jsp';
    						}else if(data == '3'){
    							alert("数据保存失败,请检查例句是否包含单词");
    							
    						}else if(data == '2'){
    							alert("您只能修改本专业词汇");
    							
    						}else{
    							alert("数据保存失败,请检查提交信息是否符合标准");
    						}
    					},
    					error: function(){
    						alert("出错, 请重试");
    					},
    				})
    			
    			}
    		}})
    	})
    	
    };

    return oInit;
};


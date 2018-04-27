$(function () {
	
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});

function delPer(categoryId){
	bootbox.confirm({message:"确认删除该专业?",
		size:"small",
		callback: function (result) {
			if(result){
				$.ajax({url:"../categoryController?op=deleteCategory",
					data:{'categoryId':categoryId},
					type:'get',
					success:function(res){
						if(res=='1'){
							alert("删除成功!");
							$('#tb_categories').bootstrapTable("selectPage","1");
						}
						else if(res == '2'){
							alert("删除失败,只有超管才能执行此操作,您只能添加专业!")
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
        $('#tb_categories').bootstrapTable({
            url: '../categoryController?op=getAllCategories',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            dataType: "text",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）!!!!!!!!!
            sortable: true,                     //是否启用排序
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
            uniqueId: "categoryId",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            responseHandler: function(data){
            	res = JSON.parse(data);
            	return  res;
            },
            columns: [{
                field: 'categoryId',
                title: 'ID'
            }, {
                field: 'categoryName',
                title: '专业名称',
            }, {
                field: 'hotLevel',
                title: '热度'
            },{
            	formatter: function (value, row, index) {
            		var rowStr = JSON.stringify(row);
            		  return [
                          '<a href=\'javascript:delPer(' + row.categoryId + ')\'>' +
                              '<i class="glyphicon glyphicon-remove"></i>删除' +
                          '</a>'
                      ].join('');
                },
                title: '操作'
            }, ],
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
    	var newCategoryId;
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            pageIndex: params.offset,  //页码
            categoryName: $("#queryCategoryNameText").val(),
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
    		$('#tb_categories').bootstrapTable("selectPage","1");
    	});
    	$("#resetBtn").click(function(){
    		$("#queryCategoryNameText").val("");
    	});
    	$("#addBtn").click(function(){
    		$("#modifyModal").modal("show");
    		
    	});
    	$("#saveModify").click(function(){
    		bootbox.confirm({message:"请认真检查,确认添加专业!",
    			size:"small",
    			callback: function (result) {
    			/**
    			 * 若确认修改:
    			 *    获取模态窗中的所有value 传递给后台
    			 */	
    			if(result){
    				var categoryName = $("#modifyCategoryNameText").val();
    				var userName = $("#userName").val();
    				var userPwd = $("#userPwd").html();
    				var adminName = $("#adminName").val();
    				var adminPwd = $("#adminPwd").val();
    				var checkCode = $("#checkCode").val();
    				var email = $("#email").val();
    				var formData = new FormData();
    				formData.append("email", email);
    				formData.append("checkCode", checkCode);
    				formData.append("categoryName", categoryName);
    				formData.append("userName", userName);
    				formData.append("userPwd", userPwd);
    				formData.append("adminName", adminName);
    				formData.append("adminPwd", adminPwd);
    				$.ajax({url:"../categoryController?op=adminAddCategory",
    					data:formData,
    					type: 'post', 
    					cache: false,//上传文件无需缓存 
    	                processData: false,//用于对data参数进行序列化处理 这里必须false
    	                contentType: false, //必须 
    					success: function(data){
    						if(data == '1'){
    							$("#modifyModal").hide();
    							alert("修改成功!");
    							$('#tb_categories').bootstrapTable("selectPage","1");
    						}else if(data == '2'){
    							alert("验证码错误");
    						}else if(data=="3"){
    							alert('邮箱格式错误');
    						}else{
    							alert("数据添加失败,请检查提交信息是否符合标准");
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


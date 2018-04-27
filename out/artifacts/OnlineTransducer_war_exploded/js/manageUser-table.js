$(function () {
	
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    
});

function delSuperAdmin(superadId){
	bootbox.confirm({message:"确认删除该管理员?",
		size:"small",
		callback: function (result) {
			if(result){
				$.ajax({url:"../adminController?op=deleteSuperAdmin",
					data:{'superadId':superadId},
					type:'get',
					success:function(res){
						if(res=='1'){
							alert("删除成功!");
							$('#tb_superAdmin').bootstrapTable("selectPage","1");
						}
						else{
							alert("删除失败!不能删除当前登陆帐户!")
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

function delAdmin(userId){
	bootbox.confirm({message:"确认删除该管理员?",
		size:"small",
		callback: function (result) {
			if(result){
				$.ajax({url:"../adminController?op=deleteAdmin",
					data:{'userId':userId},
					type:'get',
					success:function(res){
						if(res=='1'){
							alert("删除成功!");
							$('#tb_users').bootstrapTable("selectPage","1");
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

function delPer(userId){
	bootbox.confirm({message:"确认删除该用户?",
		size:"small",
		callback: function (result) {
			if(result){
				$.ajax({url:"../userController?op=deleteUser",
					data:{'userId':userId},
					type:'get',
					success:function(res){
						if(res=='1'){
							alert("删除成功!");
							$('#tb_users').bootstrapTable("selectPage","1");
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
        $('#tb_users').bootstrapTable({
            url: '../userController?op=getAllUsers',         //请求后台的URL（*）
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
            uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            responseHandler: function(data){
            	data = data.replace(/"userId":/g, "\"userId\":\"");
            	data =data.replace(/,"userName"/g, "\",\"userName\"");
            	var res = JSON.parse(data);
            	return res;
            },
            columns: [
            {
                field: 'userId',
                title: 'ID',
            
            }, {
                field: 'img',
                title: '头像',
                formatter: function(value){
                	var str = "<a  class='thumbnail' style=' margin:0px;height: 50px; width: 60px;'><img style='height: 40px; width: 60px;vertical-align:middle;cursor: pointer;'  alt='imdetail' src='../img/user/" + value +"'/></a>";
                	return str;
                }
            },  {
                field: 'userName',
                title: '用户名'
            },{
                field: 'categoryId',
                title: '专业',
                formatter: function(v){
                	var categoryName;
                	$.ajax({url: "../categoryController?op=getUserCategory",
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
                field: 'userId',
                title: '上传成功单词',
                formatter: function(v){
                	var num = '0';
                	$.ajax({url:'../userController?op=countUserVocab',
                			data:{'userId':v, 'status': '1'},
                			async:false,
                			type: 'get',
                			success: function(res){
                				num = res;
                			}});
                	return num;
                }
            }, {
                field: 'userId',
                title: '待审核单词',
                formatter: function(v){
                	var num = '0';
                	$.ajax({url:'../userController?op=countUserVocab',
                			data:{'userId':v, 'status': '0'},
                			async:false,
                			type: 'get',
                			success: function(res){
                				num = res;
                			}});
                	return num;
                }
            },{
            	field:'userId',
            	title:'对应管理员Id',
            	formatter: function(value){
            		var adminId = '';
            		$.ajax({
            			url:'../uAdminController?op=getAdminId',
            			data:{'userId': value},
            			async:false,
            			type:'get',
            			dataType:'json',
            			success:function(res){
            				adminId = res[0];
            			}
            		})
            		return adminId;
            	}
            }, {
            	formatter: function (value, row, index) {
            		var userIdStr = JSON.stringify(row.userId);
            		return [
            			  '<a href=\'javascript:delAdmin(' + userIdStr + ')\'>' +
            			  '<i class="glyphicon glyphicon-remove"></i>删除管理员' +
            			  '</a><br>'
                          +'<a href=\'javascript:delPer(' + userIdStr + ')\'>' +
                              '<i class="glyphicon glyphicon-remove"></i>删除用户' +
                          '</a>'
                      ].join('');
                },
                title: '删除用户/管理员'
            },],
            
        });
    };
    $('#tb_superAdmin').bootstrapTable({
		url: '../adminController?op=getAllsuperAdmins',         //请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        dataType:'text',
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
        uniqueId: "superadId",                     //每一行的唯一标识，一般为主键列
        showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        responseHandler: function(data){
        	var res = JSON.parse(data);
        	return res;
        },
        columns: [
        	{
        		field: 'superadId',
                title: 'ID',
        	},{
        		field: 'superadName',
        		title: '昵称',
        	}, {
            	formatter: function (value, row, index) {
            		var superadId = JSON.stringify(row.superadId);
            		return [
            			  '<a href=\'javascript:delSuperAdmin(' + superadId + ')\'>' +
            			  '<i class="glyphicon glyphicon-remove"></i>删除该超管' +
            			  '</a>'
                      ].join('');
                },
                title: '删除超管'
            },
        ],
        
	});
    
    //得到查询的参数
    oTableInit.queryParams = function (params) {
    	var newCategoryId;
    	
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            pageIndex: params.offset,  //页码
            adminId: $("#queryAdminText").val(),
            userName: $("#queryUserText").val(),
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
    		$('#tb_users').bootstrapTable("selectPage","1");
    	});
    	$("#resetBtn").click(function(){
    		$("#queryAdminText").val("");
    		$("#queryUserText").val("");
    	});
    	
    	$("#addBtn").click(function(){
    		var a = $("<a href='superAdminRegist.jsp' target='_blank'></a>").get(0);
            var e = document.createEvent('MouseEvents');
            e.initEvent( 'click', true, true );
            a.dispatchEvent(e);

    	});
    	
    };

    return oInit;
};



$(function () {
	var categoryName;
	var user;
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
});


var TableInit = function () {
	//var newCategoryId = -1;
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_vses').bootstrapTable({
            url: '../vseController?op=showUserVocabs',         //请求后台的URL（*）
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
            	res = JSON.parse(data);
            	return  res;
            },
            columns: [ {
                field: 'voca.img',
                title: '图片',
                formatter: function(value){
                	var str = "<img heigt='20px' width='20px' alt='imdetail' src='../img/vocab/" + value +"'/>";
                	return str;
                }
            },
            {
                field: 'voca.addTime',
                title: '添加时间',
                formatter: function(v){
                	var transTime = new Date(v);
                	var date = new Date(transTime);
                	return transTime.toLocaleDateString();
               }
            }, {
                field: 'voca.lastTime',
                title: '最终修改时间',
                formatter: function(v){
                	var transTime = new Date(v);
                	var date = new Date(transTime);
                	return transTime.toLocaleDateString();
               }
            },{
                field: 'voca.userId',
                title: '所属专业',
                formatter: function(v){
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
            },  
            {
                field: 'voca.vocab',
                title: '英文释义'
            }, {
                field: 'voca.transe',
                title: '中文释义'
            }, {
                field: 'voca.similars',
                title: '近义词'
            },{
                field: 'voca.example',
                title: '例句'
            },{
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
    	newCategoryId = $("#categories li .active").val()==null?$("#categories li .active").val():-1;
    	
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            pageIndex: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};



let myValidate = null;

$(function () {
    $("#jqGrid").jqGrid({
        url: '/user/list', //获取数据的地址
		mtype: 'POST', //ajax提交方式，POST或者GET。默认GET
		ajaxGridOptions: {
			contentType: "application/json",
		},//post请求需要加
		serializeGridData: function(postData) {
			return JSON.stringify(postData);
		},//post请求需要加
		datatype: "json", //从服务器端返回的数据类型，默认为xml。可选类型有：xml，local，json等
        colModel: [
			{ label: '主键', name: 'userId', hidden:true, key: true},
			{ label: '编号', name: 'userNo', index: 'user_no'},
			{ label: '姓名', name: 'userName', index: 'user_name'},
			{ label: '性别', name: 'userSex', sortable: false, formatter: function (value, options, row) {
				if (value === 0) {
					return '<span class="label label-info">男</span>';
				} else if (value === 1) {
					return '<span class="label label-info">女</span>';
				} else {
					return '<span class="label label-warning">未知</span>';
				}
			}},
			{ label: '账号', name: 'loginId', sortable: false},

			{ label: '状态', name: 'userState', sortable: false, formatter: function(value, options, row){
				if (value === '0') {
					return '<span class="label label-success">在用</span>';
				} else {
					return '<span class="label label-danger">冻结</span>';
				}
			}}
        ],
		caption: '用户列表', //表格名称
		viewrecords: true, //定义是否要显示总记录数
        height: 385, //表格高度，可以是数字，像素值或者百分比
        rowNum: 10, //在grid上显示记录条数，这个参数要被传递到后台
		rowList : [10,30,50], //一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
        rownumbers: true, //显示行号
        rownumWidth: 25, //行号宽度
        autowidth:true, //自动宽度
        multiselect: true, //是否多选
        pager: "#jqGridPager", //定义翻页用的导航栏，必须是有效的html元素
        jsonReader : {
			root: "data.list", //包含实际数据的数组
            page: "data.pageNum", //当前页
            total: "data.pages", //总的页数
            records: "data.total" //总的记录数（查出来的总条数）
        },
        prmNames : { // 参数定义
            page:"pageNum",
            rows:"pageSize",
            order: "orderRule"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

	// 加载下拉数据
	selectValid("userSex");

    // 表单校验
	myValidate = $('#userForm').validate({
		rules: {
			userNo:{
				required: true,
				baseValidStd: true
			},
			userName:{
				required: true
			},
			userSex:{
				required: true
			},
			loginId:{
				required: true,
				baseValidStd: true
			},
			email:{
				required: true,
				email: true
			},
			mobile:{
				required: true,
				mobileStd: true
			},
			'roleIdList[]':{
				required: true
			},
			userState:{
				required: true
			}
		}
	});

});

let vm = new Vue({
	el:'#rrapp',
	data:{
		q:{//查询条件对象
			userName: null
		},
		showList: true, //列表控制对象
		title:null, //标题控制对象
		user:{} // 用户操作对象
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			$.ajax({
				type: "POST",
				url: "/role/select",
				data: JSON.stringify(vm.user),
				dataType: "json", //响应数据类型
				contentType: "application/json", //请求数据类型
				success: function(result){
					if(result.code === 200){//存储成功
						vm.showList = false;//展示页面
						vm.title = "新增";//显示标题
						vm.user = {
							roleList: result.data,//角色列表
							roleIdList:[] //选中集合
						};//清理缓存数据
						vm.user.userState = 0; // 赋值操作

						// 重置表单校验器
						myValidate.resetForm();
					}
				}
			});
		},
		setSelectValue: function(){
			// 性别设置
			$("#userSex").selectpicker('val', vm.user.userSex);

		},
		update: function () {
			let userId = getSelectedRow();
			if (!userId) {
				return;
			}

			$.ajax({
				type: "POST",
				url: "/user/get_user_info",
				data: JSON.stringify({
					"userId": userId //获取主键
				}),
				dataType: "json", //响应数据类型
				contentType: "application/json", //请求数据类型
				success: function(result){
					if(result.code === 200){//存储成功
						vm.showList = false; //展示页面
						vm.title = "修改"; //显示标题

						vm.user = result.data; //记载数据
						vm.setSelectValue(); // 下拉框赋值

						// 重置表单校验器
						myValidate.resetForm(); // 重置表单校验器
					}
				}
			});
		},
		del: function () {
			let userIds = getSelectedRows();
			if (!userIds) {
				return;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: "/user/del_user_info",
					data: JSON.stringify(userIds),
					dataType: "json", //响应数据类型
					contentType: "application/json", //请求数据类型
					success: function(result){
						if(result.code === 200){//存储成功
							alert('操作成功', function(){
								vm.reload();
							});
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			if ($("#userForm").valid()) {//表单校验
				$("#saveOrUpdateBtn").attr("disabled", true); // 置灰按钮
				$.ajax({
					type: "POST",
					url: "/user/save_or_update",
					data: JSON.stringify(vm.user),
					dataType: "json", //响应数据类型
					contentType: "application/json", //请求数据类型
					success: function(result){
						if(result.code === 200){//存储成功
							alert("操作成功！", function () {
								vm.reload();
							});
						}else{
							alert("操作失败！", function () {
								$("#saveOrUpdateBtn").attr("disabled", false); // 还原按钮
							});
						}
					}
				});
			}
		},
		reload: function (event) {
			vm.showList = true;
			let page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'userName': vm.q.userName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
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
	$("#searchSelect").selectpicker({liveSearch: true, liveSearchPlaceholder: "数据检索" });

    // 表单校验
	$('#userForm').validate({
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			userName: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{}

	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {userState: 0};
			
			//获取角色信息
			this.getRoleList();
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/delete",
				    data: JSON.stringify(userIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			if ($("#userForm").valid()) {

				// 登陆操作
				$.ajax({
					type: "POST",
					url: "/user/save_or_update",
					data: JSON.stringify(vm.user),
					dataType: "json", //响应数据类型
					contentType: "application/json", //请求数据类型
					success: function(result){
						if(result.data === 1){//存储成功
							alert("操作成功！", function () {
								vm.reload();
							});
						}else{
							alert("操作失败！");
						}
					}
				});


				// var url = vm.user.userId == null ? "../sys/user/save" : "../sys/user/update";
				// $.ajax({
				// 	type: "POST",
				//     url: url,
				//     data: JSON.stringify(vm.user),
				//     success: function(r){
				//     	if(r.code === 0){
				// 			alert('操作成功', function(index){
				// 				vm.reload();
				// 			});
				// 		}else{
				// 			alert(r.msg);
				// 		}
				// 	}
				// });
			}
		},
		getUser: function(userId){
			$.get("../sys/user/info/"+userId, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("/role/select", function(r){
				vm.roleList = r.data;
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'userName': vm.q.userName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
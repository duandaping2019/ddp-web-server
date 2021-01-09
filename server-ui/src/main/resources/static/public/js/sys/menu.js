let myValidate = null;
let ztree = null;

$(function () {
    $("#jqGrid").jqGrid({
		url: '/menu/list', //获取数据的地址
		mtype: 'POST', //ajax提交方式，POST或者GET。默认GET
		ajaxGridOptions: {
			contentType: "application/json",
		},//post请求需要加
		serializeGridData: function(postData) {
			return JSON.stringify(postData);
		},//post请求需要加
		datatype: "json", //从服务器端返回的数据类型，默认为xml。可选类型有：xml，local，json等
        colModel: [
			{ label: '主键', name: 'menuId', hidden:true, key: true},
			{ label: '菜单编码', name: 'menuCode', index: 'menu_code'},
			{ label: '菜单名称', name: 'menuName', sortable: false},
			{ label: '菜单名称(EN)', name: 'menuNameEn', sortable: false},
			{ label: '父级菜单', name: 'parentName', sortable: false},
			{ label: '菜单图标', name: 'menuIcon', sortable: false, formatter: function(value, options, row){
				return value == null ? '' : '<i class="'+value+' fa-lg"></i>';
			}},
			{ label: '菜单路径', name: 'menuUrl', sortable: false },
			{ label: '授权标识', name: 'menuPermission', sortable: false },
			{ label: '菜单类型', name: 'menuType', formatter: function (value, options, row) {
				if(value === 0){
					return '<span class="label label-primary">目录</span>';
				}
				if(value === 1){
					return '<span class="label label-success">菜单</span>';
				}
				if(value === 2){
					return '<span class="label label-warning">按钮</span>';
				}
			}},
			{ label: '菜单排序', name: 'menuIndex', sortable: false}
        ],
		caption: '菜单列表', //表格名称
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

	// 表单校验
	myValidate = $('#menuForm').validate({
		rules: {
			menuType:{
				required: true
			},
			menuCode:{
				required: true
			},
			menuName:{
				required: true
			},
			menuNameEn:{
				required: true
			},
			parentName:{
				required: true
			},
			menuIndex:{
				required: true,
				digits: true
			}
		}
	});

});

let setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			name : "menuName",
			title:"menuName",
			url:"nourl"
		}
	}
};

let vm = new Vue({
	el:'#rrapp',
	data:{
		q:{//查询条件对象
			menuCode: null
		},
		showList: true,
		title: null,
		menu:{
			parentName:null,
			parentId:0,
			menuType:1,
			menuIndex:0
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		treeShow: function () {
			$.ajax({
				type: "POST",
				url: "/menu/select",
				data: JSON.stringify({
					"menuId": null //获取主键
				}),
				dataType: "json", //响应数据类型
				contentType: "application/json", //请求数据类型
				success: function(result){
					if(result.code === 200){//存储成功
						ztree = $.fn.zTree.init($("#menuTree"), setting, result.data.list);
						let node = ztree.getNodeByParam("menuId", vm.menu.parentId);
						ztree.selectNode(node);

						layer.open({
							type: 1,
							offset: '50px',
							skin: 'layui-layer-molv',
							title: "菜单树",
							area: ['300px', '500px'],
							shade: 0,
							shadeClose: false,
							content: jQuery("#menuLayer"),
							btn: false
						});
					}
				}
			});

		},
		getMenu: function(menuId){
			//加载菜单树
			$.ajax({
				type: "POST",
				url: "/menu/select",
				data: JSON.stringify({
					"menuId": menuId //获取主键
				}),
				dataType: "json", //响应数据类型
				contentType: "application/json", //请求数据类型
				success: function(result){
					if(result.code === 200){//存储成功
						ztree = $.fn.zTree.init($("#menuTree"), setting, result.data.list);
						let node = ztree.getNodeByParam("menuId", vm.menu.parentId);
						ztree.selectNode(node);

						vm.menu.parentName = node.menuName;
					}
				}
			});
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.menu = {parentName:null,parentId:0,menuType:1,menuIndex:0};
			vm.getMenu();
		},
		update: function (event) {
			let menuId = getSelectedRow();
			if (!menuId) {
				return;
			}

			$.ajax({
				type: "POST",
				url: "/menu/get_menu_info",
				data: JSON.stringify({
					"menuId": menuId //获取主键
				}),
				dataType: "json", //响应数据类型
				contentType: "application/json", //请求数据类型
				success: function(result){
					if(result.code === 200){//存储成功
						vm.showList = false; //展示页面
						vm.title = "修改"; //显示标题
						vm.menu = result.data; //记载数据
						vm.getMenu(vm.menu.menuId); // 设置弹出框数据

						// 重置表单校验器
						myValidate.resetForm(); // 重置表单校验器
					}
				}
			});
		},
		del: function (event) {
			let menuIds = getSelectedRows();
			if (!menuIds) {
				return;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: "/menu/del_menu_info",
					data: JSON.stringify(menuIds),
					dataType: "json", //响应数据类型
					contentType: "application/json", //请求数据类型
					success: function(result){
						if(result.code === 200){//存储成功
							alert(result.msg, function(){
								vm.reload();
							});
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			if ($("#menuForm").valid()) {//表单校验
				//特别校验内容
				if (!vm.validSpecialPart()){
					return;
				}

				$("#saveOrUpdateBtn").attr("disabled", true); // 置灰按钮
				$.ajax({
					type: "POST",
					url: "/menu/save_or_update",
					data: JSON.stringify(vm.menu),
					dataType: "json", //响应数据类型
					contentType: "application/json", //请求数据类型
					success: function(result){
						if(result.code === 200){//存储成功
							alert("操作成功！", function () {
								$("#saveOrUpdateBtn").attr("disabled", false); // 还原按钮
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
		menuTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择菜单",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#menuLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					let node = ztree.getSelectedNodes();
					//选择上级菜单
					vm.menu.parentId = node[0].menuId;
					vm.menu.parentName = node[0].menuName;
					
					layer.close(index);
	            }
			});
		},
		reload: function (event) {
			vm.showList = true;
			let page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'menuCode': vm.q.menuCode},
                page:page
            }).trigger("reloadGrid");
		},
		validSpecialPart: function () {
			let menuType = parseInt(vm.menu.menuType);
			if ( menuType === 1){
				if (!vm.menu.menuUrl){
					alert("请输入菜单路径！");
					return false;
				}
			} else if (menuType === 2) {
				if (!vm.menu.menuPermission){
					alert("请输入授权标识！");
					return false;
				}
			}

			return true;
		}
	}
});
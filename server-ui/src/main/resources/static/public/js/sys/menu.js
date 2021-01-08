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
});

let ztree = null;

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
		getMenu: function(menuId){
			//加载菜单树
			$.get("/menu/select", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.data.list);
				let node = ztree.getNodeByParam("menuId", vm.menu.parentId);
				ztree.selectNode(node);
				
				vm.menu.parentName = node.menuName;
			})
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.menu = {parentName:null,parentId:0,menuType:1,menuIndex:0};
			vm.getMenu();
		},
		update: function (event) {
			let menuId = getSelectedRow();
			if(menuId == null){
				return ;
			}
			
			$.get("../sys/menu/info/"+menuId, function(r){
				vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;
                
                vm.getMenu();
            });
		},
		del: function (event) {
			let menuIds = getSelectedRows();
			if(menuIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/menu/delete",
				    data: JSON.stringify(menuIds),
				    success: function(r){
				    	if(r.code === 0){
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
			let url = vm.menu.menuId == null ? "../sys/menu/save" : "../sys/menu/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.menu),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
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
                page:page
            }).trigger("reloadGrid");
		}
	}
});
let ztree = null;
let myValidate = null;

$(function () {
    $("#jqGrid").jqGrid({
		url: '/role/list', //获取数据的地址
		mtype: 'POST', //ajax提交方式，POST或者GET。默认GET
		ajaxGridOptions: {
			contentType: "application/json",
		},//post请求需要加
		serializeGridData: function(postData) {
			return JSON.stringify(postData);
		},//post请求需要加
		datatype: "json", //从服务器端返回的数据类型，默认为xml。可选类型有：xml，local，json等
		colModel: [
			{ label: '主键ID', name: 'roleId', hidden:true, key: true},
			{ label: '角色编码', name: 'roleCode', index: 'role_code'},
			{ label: '角色名称', name: 'roleName', sortable: false},
			{ label: '角色说明', name: 'roleDesc', sortable: false}
		],
		caption: '角色列表', //表格名称
		viewrecords: true, //定义是否要显示总记录数
		height: 385, //表格高度，可以是数字，像素值或者百分比
		altRows: true,//单双行样式不同
		altclass: 'differ',
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
	myValidate = $('#userForm').validate({
		rules: {
			roleCode:{
				required: true
			},
			roleName:{
				required: true
			}
		}
	});

});

let setting = {
	check:{
		enable:true,
		nocheckInherit:true
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	}
};

let vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			roleName: null
		},
		showList: true,
		title:null,
		role:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.role = {};
			vm.getMenuTree(null);
		},
		update: function () {
			let roleId = getSelectedRow();
			if(roleId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
            vm.getMenuTree(roleId);
		},
		del: function (event) {
			let roleIds = getSelectedRows();
			if(roleIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/role/delete",
				    data: JSON.stringify(roleIds),
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
		getRole: function(roleId){
            $.get("../sys/role/info/"+roleId, function(r){
            	vm.role = r.role;
                
                //勾选角色所拥有的菜单
    			let menuIds = vm.role.menuIdList;
    			for(let i=0; i<menuIds.length; i++) {
    				let node = ztree.getNodeByParam("menuId", menuIds[i]);
    				ztree.checkNode(node, true, false);
    			}
    		});
		},
		saveOrUpdate: function (event) {
			//获取选择的菜单
			let nodes = ztree.getCheckedNodes(true);
			let menuIdList = new Array();
			for(let i=0; i<nodes.length; i++) {
				menuIdList.push(nodes[i].menuId);
			}
			vm.role.menuIdList = menuIdList;
			
			let url = vm.role.roleId == null ? "../sys/role/save" : "../sys/role/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.role),
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
		getMenuTree: function(roleId) {
			//加载菜单树
			$.get("/menu/perms", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				//展开所有节点
				ztree.expandAll(true);
				
				if(roleId != null){
					vm.getRole(roleId);
				}
			});
	    },
	    reload: function (event) {
	    	vm.showList = true;
			let page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'roleName': vm.q.roleName},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
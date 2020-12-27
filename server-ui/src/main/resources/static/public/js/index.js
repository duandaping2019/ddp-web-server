//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{}},
	template:[
	          '<li>',
	          '<a v-if="item.menuType === 0" href="javascript:;">',
	          '<i v-if="item.menuIcon != null" :class="item.menuIcon"></i>',
	          '<span>{{item.menuName}}</span>',
	          '<i class="fa fa-angle-left pull-right"></i>',
	          '</a>',
	          '<ul v-if="item.menuType === 0" class="treeview-menu">',
	          '<menu-item :item="item" v-for="item in item.list"></menu-item>',
	          '</ul>',
	          '<a v-if="item.menuType === 1" :href="\'#\'+item.menuUrl"><i v-if="item.menuIcon != null" :class="item.menuIcon"></i><i v-else class="fa fa-circle-o"></i> {{item.menuName}}</a>',
	          '</li>'
	].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"sys/main.html",
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/user?_"+$.now(), function(r){
				vm.menuList = r.menuList;
                window.permissions = r.permissions;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					if (!validatePwd(vm.user.newPassword)) {
						return;
					}

					$.ajax({
						type: "POST",
						url: "sys/user/password",
						data: JSON.stringify({
							"password": vm.user.password,
							"newPassword": vm.user.newPassword
						}),
						dataType: "json", //响应数据类型
						contentType: "application/json", //请求数据类型
						success: function(result){
							if(result.code === 200){//存储成功
								layer.close(index);
								layer.alert(result.data, function(index){
									location.reload();
								});
							}
						}
					});
	            }
			});
		}
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		let router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});

function routerList(router, menuList){
	for(let key in menuList){
		let menu = menuList[key];
		if(menu.menuType === 0){
			routerList(router, menu.list);
		}else if(menu.menuType == 1){
			router.add('#'+menu.menuUrl, function() {
				let url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}

/*密码强度校验*/
function validatePwd(newPassword) {
	let regex =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/;
	if(!regex.test(newPassword)){
		alert("至少8-16个字符，至少1个大写字母，1个小写字母和1个数字，其他可以是任意字符");
		return false;
	}
	return true;
}
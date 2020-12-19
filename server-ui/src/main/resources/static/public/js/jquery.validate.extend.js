// 初始化事件处理
$(function () {
    // 下拉框处理事件
    $(".selectpicker").bind("change",function(){
        $(this).closest("form").valid();
    });

});

// 变更配置信息
$.validator.setDefaults({
    // 对隐藏域也进行校验
    ignore : [],
    // 手动触发校验
    onsubmit : false,
    highlight : function(element) {
        if ($(element).is("select")) {// 下拉控件时
            $(element).parent().parent().closest('div').addClass('has-error');
        } else {
            $(element).closest('div').addClass('has-error');
        }
    },
    unhighlight : function(element) {
        if ($(element).is("select")) {// 下拉控件时
            $(element).parent().parent().closest('div').removeClass('has-error');
        } else {
            $(element).closest('div').removeClass('has-error');
        }
    },
    errorElement : 'span',
    errorClass : 'help-block',
    errorPlacement : function(error, element) {
        if (element.is("select")) {// 下拉控件时
            error.appendTo(element.parent().parent());
        } else if (element.is(":radio")) {// 单选框
            error.appendTo(element.parent().parent());
        } else if (element.is(":checkbox")) {// 复选框
            error.appendTo(element.parent().parent());
        } else {
            error.appendTo(element.parent());
        }
    }
});

// 提示信息汉化
$.extend($.validator.messages, {
    required: "该内容为必须项",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: $.validator.format("请输入一个最大为 {0} 的值"),
    min: $.validator.format("请输入一个最小为 {0} 的值")
});

//中文字两个字节
jQuery.validator.addMethod(
    "maxlength",
    function(value, element, param) {
        var length = value.length;
        for(var i = 0; i < value.length; i++){
            if(value.charCodeAt(i) > 127){
                length++;
            }
        }
        return this.optional(element) || (length <= param);
    },
    $.validator.format("请确保输入的值在{0}个字节以内(一个中文字算2个字节)")
);

//非中文
jQuery.validator.addMethod(
    "isNoChinese",
    function(value, element) {
        var length = value.length;
        for(var i = 0; i < value.length; i++){
            if(value.charCodeAt(i) > 127){
                length++;
            }
        }
        return this.optional(element) || (length == value.length);//成立则返回成功，否则输出下面错误信息
    },
    $.validator.format("输入不允许存在中文")
);




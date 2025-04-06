/**
 * 添加用户信息
 */
function addUser(){
    let username = $("#username");
    let email = $("#email");
    let password = $("#password");
    let passwordRe = $("#passwordRe");
    let reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;

    username.removeClass("is-invalid");
    email.removeClass("is-invalid");

    if (username.val() == "") {
        username.addClass("is-invalid");
        $("#usernameMsg").text("请输入用户名!");
        return;
    }
    if (email.val() == "") {
        email.addClass("is-invalid");
        $("#emailMsg").text("请输入邮箱！");
        return;
    }
    if (!reg.test(email.val())) {
        email.addClass("is-invalid");
        $("#emailMsg").text("邮箱格式不正确！");
        return;
    }

    if (password.val() == "") {
        password.addClass("is-invalid");
        passwordRe.addClass("is-invalid");
        $("#passwordMsg").text("请输入密码！");
        return;
    }

    if (passwordRe.val() == "") {
        passwordRe.addClass("is-invalid");
        $("#passwordReMsg").text("请再次输入密码！");
        return;
    }

    if (password.val() != passwordRe.val()) {
        password.addClass("is-invalid");
        passwordRe.addClass("is-invalid");
        $("#passwordReMsg").text("两次输入密码不一致！");
        return;
    }
    $.post(
        "/travel/addUser",
        {"username":username.val(),"email":email.val(),"password":password.val()},
        function (data) {
            data = $.parseJSON(data);

            if (data.code == 0) {
                window.location.reload();
            } else if (data.code == 1) {
                username.addClass("is-invalid");
                $("#usernameMsg").text(data.msg);
            } else if (data.code == 2) {
                email.addClass("is-invalid");
                $("#emailMsg").text(data.msg);
            }
        }

    )
}

//复选框全选判断
$(function(){
    /*全选按钮状态显示判断*/
    $("#checklist").find("input[type='checkbox']").click(function(){
        /*初始化选择为TURE*/
        $("#all")[0].checked = true;
        /*获取未选中的*/
        var nocheckedList = new Array();
        $("#checklist").find('input:not(:checked)').each(function() {
        nocheckedList.push($(this).val());
        });

        /*状态显示*/
        if (nocheckedList.length == $("#checklist").find('input').length) {
            $("#all")[0].checked = false;
        }else if(nocheckedList.length ==0){
        $("#all")[0].checked = true;
        }else if(nocheckedList.length){
        $("#all")[0].checked = false;
        }
    });
    // 全选/取消
    $("#all").click(function(){
        // alert(this.checked);
        if ($(this).is(':checked')) {
            $("#checklist").find('input').each(function(){
                $(this).prop("checked",true);
            });

        } else {
            $("#checklist").find('input').each(function(){
                $(this).removeAttr("checked",false);
                // 根据官方的建议：具有 true 和 false 两个属性的属性，
                // 如 checked, selected 或者 disabled 使用prop()，其他的使用 attr()
                $(this).prop("checked",false);
            });
        }
    });
});


/**
 * 判断是否是是否有数据被选中
 */
function isChecked() {
    let list = [];
    //意思是选择被选中的checkbox
    $.each($('input:checkbox:checked'),function(){
        list.push($(this).val());
    });
    if (list.length == 0) {
        $("#removeRe").text("请选择需要删除的用户！");
    } else {
        $("#removeRe").text("确认删除？");
    }
}


/**
 * 发送post异步请求删除选中的数据
 */
function checkeds() {

    //声明list
    let list = [];

    //意思是选择被选中的checkbox
    $.each($('input:checkbox:checked'),function(){
        list.push($(this).val());
    });
    if (list.length == 0) {
        $("#removeRe").text("请选择需要删除的用户！");
        return;
    }

    $.post(
        "/travel/removeUser",
        {"list":list},
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                window.location.reload();
            } else {
               alert(data.msg);
            }
        }

    )
}


/**
 * 判断是否是是否有数据被选中
 */
function isBanChecked() {
    let list = [];
    //意思是选择被选中的checkbox
    $.each($('input:checkbox:checked'),function(){
        list.push($(this).val());
    });
    if (list.length == 0) {
        $("#banRe").text("请选择需要封禁或解封的用户！");
    } else {
        $("#banRe").text("确认操作？");
    }
}

/**
 * 发送post异步请求封禁选中的数据
 */
function banCheckeds() {

    //声明list
    let list = [];

    //意思是选择被选中的checkbox
    $.each($('input:checkbox:checked'),function(){
        list.push($(this).val());
    });
    if (list.length == 0) {
        $("#banRe").text("请选择用户！");
        return;
    }

    $.post(
        "/travel/banUser",
        {"list":list},
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                window.location.reload();
            } else {
                alert(data.msg);
            }
        }

    )
}


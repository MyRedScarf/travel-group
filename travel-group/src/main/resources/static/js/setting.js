$(function(){
    $("#uploadForm").submit(upload);
});

function upload() {
    // 更新头像访问路径
    $.post(
        CONTEXT_PATH + "/user/header/url",
        {"fileName":$("input[name='key']").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                window.location.reload();
            } else {
                alert(data.msg);
            }
        }
    );
}

function updatePassword() {
    alert(5555);
    if ($("#newPassword").value() != $("#confirmPassword").value()) {
        $("#confirmPassword").text("两次密码不一致！");
        return;
    }
    alert(5555);
    $.post(
        CONTEXT_PATH + "/user/updatePassword",
        {"oldPassword":$("#oldPassword"),"newPassword":$("#oldPassword")},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                countDownTime(data.msg);
            } else {
                $("#oldPassword").text(data.msg);
                return;
            }
        }
    );
    function logout() {
        $.get(
            CONTEXT_PATH + "/logout",
            function (){
                window.location = CONTEXT_PATH + "/login";
            }
        );
    }

    function countDownTime(data) {
        var sec = 2;
        time = setInterval(function() {
            sec --;
            alert(data + " " + sec + "s 后自动跳转到登录页面！" );
            if(sec == 0) {
                clearInterval(time);  // 清除定时器
                logout();
            }
        },1000)
    }
}

$(function(){
    bsCustomFileInput.init();
});
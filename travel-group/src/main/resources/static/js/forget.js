//发起异步请求，发送邮箱验证码
function getVerificationCode() {
    let text = $("#your-email").val();
    if (!text){
        alert("请输入邮箱！");
        return;
    }
    //开启验证码倒计时
    countDownTimer();
    $.post(
        CONTEXT_PATH + "/verificationCode",
        {"email":text},
        function(data) {
            alert("发送成功！");
        }
    );
}

//获取验证码倒计时
function countDownTimer() {
    var sec = 60;
    timer = setInterval(function() {
        // 获取注册按钮的DOM
        let verification = $("#verification");
        verification.addClass("disabled");
        sec --;
        verification.html(sec + "s");
        if(sec == 0) {
            clearInterval(timer);  // 清除定时器
            verification.removeClass("disabled");  // 按钮恢复可用
            verification.html("获取验证码");;
        }
    },1000)
}
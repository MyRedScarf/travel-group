
function addScenic(){
    let scenicName = $("#scenicName").val();
    let merchant = $("#merchant").val();
    let price = $("#price").val();
    let phone = $("#phone").val();
    let address = $("#address").val();
    let scenicIntroduce = $("#scenicIntroduce").val();
    let scenicContent = $("#scenicContent").val();
    let notice = $("#notice").val();

    $("#scenicName").removeClass("is-invalid");
    $("#merchant").removeClass("is-invalid");
    $("#price").removeClass("is-invalid");
    $("#phone").removeClass("is-invalid");
    $("#address").removeClass("is-invalid");
    $("#scenicIntroduce").removeClass("is-invalid");
    $("#scenicContent").removeClass("is-invalid");
    $("#notice").removeClass("is-invalid");

    if (scenicName == "") {
        $("#scenicName").addClass("is-invalid");
        $("#scenicNameMsg").text("请输入景点名称!");
        return;
    }
    if (merchant == "") {
        $("#merchant").addClass("is-invalid");
        $("#merchantMsg").text("请输入经营者!");
        return;
    }
    if (price == "") {
        $("#price").addClass("is-invalid");
        $("#priceMsg").text("请输入景点价格!");
        return;
    }
    if (phone == "") {
        $("#phone").addClass("is-invalid");
        $("#phoneMsg").text("请输入景点电话!");
        return;
    }
    if (address == "") {
        $("#address").addClass("is-invalid");
        $("#addressMsg").text("请输入景区地址!");
        return;
    }
    if (scenicIntroduce == "") {
        $("#scenicIntroduce").addClass("is-invalid");
        $("#scenicIntroduceMsg").text("请输入景点简介!");
        return;
    }

    if (scenicContent == "") {
        $("#scenicContent").addClass("is-invalid");
        $("#scenicContentMsg").text("请输入景点说明");
        return;
    }
    if (notice == "") {
        $("#notice").addClass("is-invalid");
        $("#noticeMsg").text("请输入旅游须知!");
        return;
    }
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
        $("#removeRe").text("确认删除？！");
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
        "/travel/removeScenic",
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
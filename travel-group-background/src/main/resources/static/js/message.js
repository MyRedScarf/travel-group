
function updateStatus(){

    let status = $("#status");
    let badge = $("#badge");

    if (status.val()==1) {
        badge.removeClass("badge-success");
        badge.addClass("badge-danger");
        badge.text("未读");
    } else {
        badge.removeClass("badge-danger");
        badge.addClass("badge-success");
        badge.text("已读");
    }
    $.post(
        "/travel/updateMessageStatus",
        {"status":status.val(),"id":$("#id").val()},
        function (data) {
            data = $.parseJSON(data);
            status.val(data.code);
        }
    )
}
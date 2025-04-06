function select(){
    var res = $("#select").find("option:selected").val();
    $.post(
        CONTEXT_PATH + "/recommend",
        {"recommendScenic":res},
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

function selectDel(){
    var res = $("#selectDel").find("option:selected").val();
    $.post(
        CONTEXT_PATH + "/removeRecommend",
        {"removeRecommend":res},
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
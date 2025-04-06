// 发送异步请求，用户收藏
function getCollection(){
    //获取访问路径中的景点id
    let url = window.location.pathname;
    var scenicId = url.substring(url.lastIndexOf('/') + 1, url.length);

    //发送post异步请求，添加收藏
    $.post(
        CONTEXT_PATH + "/collection",
        {"scenicId":scenicId},
        function(data) {
            //接收json数据
            data = $.parseJSON(data);
            //如果json中的code == 0 说明收藏成功
            if(data.code == 0){
                $("#svgHeart").html(
                    '<path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>'
                );
                $("#favorite_span").html("取消收藏");
                $("#favoriteCount").html(data.msg);

                //如果json中的code == 1 说明取消收藏成功
            } else if (data.code == 1){
                $("#svgHeart").html(
                    '<path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>'
                );
                $("#favorite_span").html("点击收藏");
                $("#favoriteCount").html(data.msg);

            } else {
                //两者都不是，说明收藏失败，提示用户失败的原因
                alert(data.msg);
            }
        }
    );
}

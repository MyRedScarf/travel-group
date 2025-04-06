function refresh_kaptcha(){
    var path = CONTEXT_PATH + "/kaptcha?p=" + Math.random();
    $("#kaptcha").attr("src", path)
}
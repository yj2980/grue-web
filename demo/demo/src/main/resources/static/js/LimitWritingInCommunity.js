
// 로그인한 사람이 아닌 경우 글쓰기 제한
if (sessionStorage.getItem("username") == null || localStorage.getItem("username") == null) {
    $("#btn-primary").css("display", "none");
} else {
    $("#btn-primary").css("display", "block");
}

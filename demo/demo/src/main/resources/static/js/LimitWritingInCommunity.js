
// 로그인한 사람이 아닌 경우 글쓰기 제한
if (sessionStorage.getItem("username") == null && localStorage.getItem("username") == null) {
    console.log("display is none a button");
    $("#btn-write").css("display", "none");
} else {
    console.log("display a button")
    $("#btn-write").css("display", "block");
}

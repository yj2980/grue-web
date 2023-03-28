if (localStorage.getItem("login")) {
    console.log("login 성공 " + "\n  유저 이름 : " + localStorage.getItem("username"));
    $("#board_author").val(localStorage.getItem("username"));
}

if (sessionStorage.getItem("login")) {
    console.log("login 성공 " + "\n  유저 이름 : " + sessionStorage.getItem("username"));
     $("#board_author").val(sessionStorage.getItem("username"));
}

function writing() {
    console.log($("#board_autohr"));

    $("#writingForm").submit();
}

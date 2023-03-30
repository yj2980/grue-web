var username = "";

if (sessionStorage.getItem("username")) {
    console.log("session username : " + sessionStorage.getItem("username"));
    username = sessionStorage.getItem("username");
}
if (localStorage.getItem("username")) {
    console.log("local username : " + localStorage.getItem("username"));
    username = localStorage.getItem("username");
}

if (username == postAuthor) {
    $("#edit-btn").css("display", "block");
    $("#delete-btn").css("display", "block");
} else {
    $("#edit-btn").css("display", "none");
    $("#delete-btn").css("display", "none");
}

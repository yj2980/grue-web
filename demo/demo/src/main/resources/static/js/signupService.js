if (sessionStorage.getItem("user")) {
    var user = JSON.parse(sessionStorage.getItem("user"));

    document.getElementById("username").value = user.name;
    document.getElementById("user_email").value = user.email;
    document.getElementById("user_password").value = user.password;
}

function save_info() {
    let userInfo = {
        name : document.getElementById("username").value,
        email : document.getElementById("user_email").value,
        password : document.getElementById("user_password").value
    }

    sessionStorage.setItem("user", JSON.stringify(userInfo));
}


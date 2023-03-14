if (sessionStorage.getItem("user")) {
    var user = JSON.parse(sessionStorage.getItem("user"));

    document.getElementById("username").value = user.name;
    document.getElementById("user_email").value = user.email;
    document.getElementById("user_password").value = user.password;
    document.getElementById("telephone_number").value = user.phone_number;
}

function save_info() {
    let userInfo = {
        name : document.getElementById("username").value,
        email : document.getElementById("user_email").value,
        password : document.getElementById("user_password").value,
        phone_number : document.getElementById("telephone_number").value
    }

    sessionStorage.setItem("user", JSON.stringify(userInfo));
}


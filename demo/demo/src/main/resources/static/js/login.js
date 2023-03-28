let loginResult = false;

document.getElementById("signup_button").addEventListener("click", login);

function login() {
    var email = $("#email").val();
    var password = $("#password").val();

    if (email == "") {
        alert("아이디를 입력해주세요.");
        return;
    }

    if (password == "") {
        alert("비밀번호를 입력해주세요.");
        return;
    }


    $.ajax({
        url:'./loginCheck',
        type:'post',
        data: {
            'email' : email,
            'password' : password
        },
        datatype: 'json',
        success: function(result) {
            console.log(result);
            if (result == "not_exist") {
                alert("해당 아이디가 없습니다.");
                return;
            }
            if (result == "not_same") {
                alert("비밀번호를 잘 못 입력하셨습니다.");
                return;
            }

            loginResult = true;
            saveLoginResult(result)
            window.location.href = "./";
        }
    });
}

function is_login_checked() {
    const checkbox = document.getElementById('continue_login');

    const is_checked = checkbox.checked;

    // save login info in local storage
        if (is_checked) {
            console.log("로그인 유지를 선택해서, 로컬 스토리지에 저장합니다.");
            localStorage.setItem("login", loginResult);
            sessionStorage.removeItem("login");
            sessionStorage.removeItem("username");
        }

        // save login info in session storage
        if (!is_checked) {
            console.log("세션 스토리지에 정보를 저장합니다.")
            sessionStorage.setItem("login", loginResult);
            localStorage.removeItem("login");
            localStorage.removeItem("username");
        }
}

function saveLoginResult(username) {
    if (localStorage.getItem("login")) {
        localStorage.setItem("login", loginResult);
        localStorage.setItem("username", username);
        console.log("로그인 저장")
        console.log("local :" + localStorage.getItem("username"));
    }

    if (sessionStorage.getItem("login")) {
        sessionStorage.setItem("login", loginResult);
        sessionStorage.setItem("username", username);
              console.log("로그인 저장")
                console.log("session :" + sessionStorage.getItem("username"));
    }
}

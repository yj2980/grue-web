let loginResult = false;

document.getElementById("signup_button").addEventListener("click", login);

function login() {
    var isVisited = false;
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
                isVisited = true;
            }
            if (result == "not_same") {
                alert("비밀번호를 잘 못 입력하셨습니다.");
                isVisited = true;
            }

            if (!isVisited) {
                loginResult = true;
                saveLoginResult()
                window.location.href = "./";
            }
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
        }

        // save login info in session storage
        if (!is_checked) {
            console.log("세션 스토리지에 정보를 저장합니다.")
            sessionStorage.setItem("login", loginResult);
            localStorage.removeItem("login");
        }
}

function saveLoginResult() {
    if (localStorage.getItem("login")) {
        localStorage.setItem("login", loginResult);
    }

    if (sessionStorage.getItem("login")) {
        sessionStorage.setItem("login", loginResult);
    }
}

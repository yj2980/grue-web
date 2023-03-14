const NOTICE = {
    VALID_EMAIL : "사용가능한 아이디 입니다.",
    INVALID_EMAIL : "이미 존재하는 아이디 입니다.",
    INVALID_PW : "비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.",
    CONSECUTIVE_PW : "같은 문자를 4번 이상 사용하실 수 없습니다.",
    KOREAN_PW : "비밀번호에 한글을 사용 할 수 없습니다.",
    SPACE_PW : "비밀번호는 공백 없이 입력해주세요.",
    NOT_SAME_PW : "비밀번호가 일치하지 않습니다.",
    INCORRECT_PHONE_NUMBER : "올바른 전화번호 13자리를 입력해주세요.",
    INPUT_USERNAME : "닉네임을 입력해주세요.",
    INPUT_EMAIL : "이메일 중복 검사를 확인해주세요.",
    INPUT_PASSWORD : "비밀번호를 입력해주세요.",
    INPUT_PHONE_NUMBER : "전화번호를 입력해주세요."
};

let isVisited = false;
let smsCodeCheckResult = false;

// 이메일 검증
function verifyEmail(email) {
    if (isRight(email)) {
        $("#id_overlap_button").css("display", "none");
        $("#email-notice").css("display", "block");
        initInputText(email);
        return false;
    }

    $("#email-notice").css("display", "none");
    $("#id_overlap_button").css("display", "block");
}

function isRight(email) {
   var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

   return (email.value.match(pattern) == null);
}

function isEmailDuplicate() {
    let userEmail = $("#user_email");

    $.ajax({
        url:'./emailDuplicationCheck',
        type:'post',
        data: {
           'email': userEmail.val()
        },
        datatype: 'json',
        success: function (result) {
            if (!result) {
                 alert(NOTICE.VALID_EMAIL);
                 $('#id_overlap_button').hide();
                 isVisited = true;
                 return;
            }
            if (result) {
               alert(NOTICE.INVALID_EMAIL);
               return;
            }
        }
    });
}


// 비밀번호 검증
function verifyPassword(pw){
	var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
	var duplication = /(\w)\1\1\1/;
	var space = /\s/;
	var korean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if (!validPasswordCheck(pw, reg)) {
        noticeReason(NOTICE.INVALID_PW, pw);
        return false;
    }
    if (validPasswordCheck(pw, duplication)) {
        noticeReason(NOTICE.CONSECUTIVE_PW, pw);
	    return false;
    }
    if (validPasswordCheck(pw, space)){
	    noticeReason(NOTICE.SPACE_PW, pw);
	    return false;
	}
	if (validPasswordCheck(pw, korean)){
	    noticeReason(NOTICE.KOREAN_PW, pw);
	    return false;
	}
}

function isSamePassword(pw) {
    if (isNotSame($("#user_password").val(), pw.value)) {
        noticeReason(NOTICE.NOT_SAME_PW, pw);
    }
}

function isNotSame(first, second) {
    return (first !== second);
}

function validPasswordCheck(obj, pattern) {
   return (obj.value.match(pattern) != null);
}

// 전화번호 검증
function hypen(number) {
    number.value = number.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

//문자 보내기 요청
function sendMessage() {
   var phoneNumber = $("#telephone_number");

   if (phoneNumber.val().length != 13) {
       noticeReason(NOTICE.INCORRECT_PHONE_NUMBER, phoneNumber);
       return;
   }

    $.ajax({
        url:'./sendPhoneNumber',
        type:'post',
        data: {
           'phoneNumber': phoneNumber.val()
        },
        datatype: 'json',
        success: function (result) {
            console.log(JSON.stringify(result));

            if (result.statusName == "success") {
                 $("#sms_auth_code").css("display", "block");
                 $("#sms_auth_check").css("display", "block");
            }
        }
    });
}

//문자 인증
function smsAuthenticationCheck() {
   var smsCode = $("#sms_auth_code");

    $.ajax({
        url:'./checkVerificationCode',
        type:'post',
        data: {
           'code': smsCode.val()
        },
        datatype: 'json',
        success: function (result) {
            if (result) {
                smsCodeCheckResult = true;
                alert("인증이 완료되었습니다.");
                smsCode.css("display", "none");
                $("#sms_auth_check").css("display", "none");

                console.log("지우기");
            }
        }
    });
}




// 서버로 데이터 전송
document.getElementById("signup-submit").addEventListener("click", signup);

function signup() {
    if ($("#username").val() == "") {
        alert(NOTICE.INPUT_USERNAME);
        return;
    }

/*
    if (!smsCodeCheckResult) {
        alert("문자 인증을 확인해주세요.");
        return;
    }
*/

    if ($("#telephone_number").val() == "") {
        alert(NOTICE.INPUT_PHONE_NUMBER);
        return;
    }

    if (!isVisited) {
        alert(NOTICE.INPUT_EMAIL);
        return;
    }

    if ($("#user_password").val() == "" || $("#user_correct_password").val() == "") {
        alert(NOTICE.INPUT_PASSWORD);
        return;
    }

    if ($("#user_password").val() == "") {
        alert(NOTICE.INPUT_PHONE_NUMBER);
        return;
    }

    if (sessionStorage.getItem("user")) {
        console.log("remove 'user' data")
        sessionStorage.removeItem("user");
    }

    $("#signupForm").submit();
}

// 결과
function noticeReason(text, obj) {
    alert(text);
    initInputText(obj);
}

function initInputText(obj) {
    obj.value = '';
    obj.focus();
}

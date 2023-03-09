const NOTICE = {
    VALID_EMAIL : "사용가능한 아이디 입니다.",
    INVALID_EMAIL : "이미 존재하는 아이디 입니다.",
    INVALID_PW : "비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.",
    CONSECUTIVE_PW : "같은 문자를 4번 이상 사용하실 수 없습니다.",
    KOREAN_PW : "비밀번호에 한글을 사용 할 수 없습니다.",
    SPACE_PW : "비밀번호는 공백 없이 입력해주세요.",
    NOT_SAME_PW : "비밀번호가 일치하지 않습니다.",
    INPUT_USERNAME : "닉네임을 입력해주세요.",
    INPUT_EMAIL : "이메일 중복 검사를 확인해주세요.",
    INPUT_PASSWORD : "비밀번호를 입력해주세요."
};

let isVisited = false;

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
    })
}

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

function noticeReason(text, obj) {
    alert(text);
    initInputText(obj);
}

function initInputText(obj) {
    obj.value = '';
    obj.focus();
}

document.getElementById("signup-submit").addEventListener("click", signup);

function signup() {
    if ($("#username").val() == "") {
        alert(NOTICE.INPUT_USERNAME);
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

    $("#signupForm").submit();
}


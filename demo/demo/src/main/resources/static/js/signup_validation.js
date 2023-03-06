function validEmail(obj) {
    let emailCheckNotice = document.getElementById('email-notice');
    let emailOverlapButton = document.getElementById('id_overlap_button');
    let validEmailResult = validEmailCheck(obj);

    if (validEmailResult) {
        console.log("email validation is true");
        emailCheckNotice.style.display = "none";
        emailOverlapButton.style.display = "block";
    }
    if (!validEmailResult) {
        console.log("email validation is false");
        emailOverlapButton.style.display = "none";
        emailCheckNotice.style.display = "block";
        obj.value = '';
        obj.focus();
        return false;
    }
}

function validEmailCheck(obj) {
   var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

   return (obj.value.match(pattern) != null);
}

function id_overlap_check() {
    let userId = document.querySelector('input[name="email"]');
    console.log("UserEmail : " + userId.value);
    $.ajax({
        url:'./emailCheck',
        type:'post',
        data: {
           'email': userId.value
        },
        datatype: 'json',
        success: function (overlappedEmail) {
            console.log("email duplication result : " + overlappedEmail)
            // 사용 가능
            if (!overlappedEmail) {
                 alert("사용가능한 아이디 입니다.");
                 $('#id_overlap_button').hide();
                 return;
            }
            if (overlappedEmail) {
               alert("이미 존재하는 아이디 입니다.");
               userId.focus();
               return;
            }
        }
    })
}

function validPassword(){
	var pw = $("#user_password").val();

	var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
	var korean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

	if (!reg.test(pw)) {
	    alert('비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
	}else if (/(\w)\1\1\1/.test(pw)){
	    alert('같은 문자를 4번 이상 사용하실 수 없습니다.');
	    return false;
	}else if (pw.search(/\s/) != -1){
	    alert("비밀번호는 공백 없이 입력해주세요.");
	    return false;
	}else if (korean.test(pw)){
	    alert("비밀번호에 한글을 사용 할 수 없습니다.");
	}else {
	 console.log("통과");
	}

}

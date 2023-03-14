let smsCodeCheckResult = false;

function authenticate() {
    var phoneNumber = $("#phone-number");

    $.ajax({
        url:'./sendPhoneNumber',
        type:'post',
        data: {
           'phoneNumber': phoneNumber.val()
        },
        datatype: 'json',
        success: function (result) {

          if (result.statusName == "success") {
                $("#verification-code").css("display", "block");
                $("#user-authenticator-btn").css("display", "block");
            }
        }
    });
}

function userAuthenticator() {
    var verificationCode = $("#verification-code");

    $.ajax({
        url:'./checkVerificationCode',
        type:'post',
        data: {
           'code': verificationCode.val()
        },
        datatype: 'json',
        success: function (result) {
            if (result) {
                smsCodeCheckResult = true;
                alert("인증이 완료되었습니다.");
                verificationCode.css("display", "none");
                $("#user-authenticator-btn").css("display", "none");

            } else {
                alert("인증 번호가 틀렸습니다.");
            }
        }
    });
}

function findEmailByPhoneNumber() {
    var phoneNumber = $("#phone-number");

    if (!smsCodeCheckResult) {
        alert("본인인증을 진행해주세요.");
        return;
    }
        $.ajax({
            url:'./findEmailByPhoneNumber',
            type:'post',
            data: {
               'phoneNumber': phoneNumber.val()
            },
            datatype: 'json',
            success: function (result) {
                console.log(JSON.stringify(result));

                if (result.statusName == "success") {
                    alert("발송이 완료되었습니다.");
                    window.location.href = "./";
                    return;
                }
                alert("없는 회원입니다.");
            }
        });
}

function hypen(number) {
    number.value = number.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

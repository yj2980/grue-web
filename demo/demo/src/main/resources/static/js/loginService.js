if (sessionStorage.getItem("login") == "true" || localStorage.getItem("login") == "true") {
    console.log("login success");
    document.getElementById("navbar_login").style.display = "none";
    document.getElementById("navbar_mypage").style.display = "block";
    document.getElementById("navbar_logout").style.display = "block";
} else {
    console.log("login false");
    document.getElementById("navbar_login").style.display = "block";
    document.getElementById("navbar_mypage").style.display = "none";
    document.getElementById("navbar_logout").style.display = "none";
}

if (sessionStorage.getItem("login") == "false" && localStorage.getItem("login") == "false") {
    console.log("login false");
    document.getElementById("navbar_login").style.display = "block";
    document.getElementById("navbar_mypage").style.display = "none";
    document.getElementById("navbar_logout").style.display = "none";
}

function logout() {
   console.log("logout");

   if (sessionStorage.getItem("login")) {
        console.log("session reset");
        sessionStorage.setItem("login", "false");
        sessionStorage.removeItem("username");
   }
   if (localStorage.getItem("login")) {
        console.log("local reset");
        localStorage.setItem("login", "false");
        localStorage.removeItem("username");
   }

   window.location.href = "./";
}



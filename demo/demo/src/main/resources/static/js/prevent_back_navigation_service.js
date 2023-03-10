window.history.pushState(null, null, document.URL);

window.addEventListener('popstate', function () {
    let back = confirm("작성 중인 내용이 있습니다. 저장하지 않고 나가시겠습니까?");
    if (!back) {
        window.history.pushState(null, null, document.URL);
    }
    if (back) {
        window.history.back();
    }
});

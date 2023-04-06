var city;
var isVisited = false;
const body = document.querySelector('body');

function search() {
    console.log("submit");
    $("#searchPost").submit();
}

function setSpotModel(data) {
    city = data;
    pickDateRange();
    $(".modal").fadeIn();
    body.style.overflow = 'hidden';
}

function popClose() {
    $(".modal").fadeOut();
    body.style.overflow = 'auto';
}

function planning() {
    localStorage.setItem("city", city);
    if (isVisited == true) {
        location.href = "planningThroughMap";
    } else {
        alert("여행 기간을 입력해주세요.");
    }
}

function pickDateRange() {
    $('#dateRange').daterangepicker({
        "locale": {
            "format": "YYYY-MM-DD",
            "separator": " ~ ",
            "applyLabel": "확인",
            "cancelLabel": "취소",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
            "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
            "firstDay": 1
        },
        "startDate": new Date(),
        "endDate": new Date(),
        "drops": "auto"
    }, function(start, end, label) {
      console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
      localStorage.setItem("durationOfTrip", start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD'));
      console.log(localStorage.getItem("durationOfTrip"));
      isVisited = true;
    });
}


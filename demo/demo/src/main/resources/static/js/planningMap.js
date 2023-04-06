var durationOfTrip = localStorage.getItem("durationOfTrip");
var selectedCity = localStorage.getItem("city");
var geocoder;
var map;
var locations = new Array();
var markerIdx;


function initialize() {
    /* set init value */
    document.getElementById("durationOfTrip-text").innerText = this.durationOfTrip;
    document.getElementById("plan-title").innerText = this.selectedCity + " 여행";
    geocoder = new google.maps.Geocoder();
    var latLng = new google.maps.LatLng(-34.397, 150.644);
    var mapOptions = {
      zoom: 10,
      center: latLng
    }
    map = new google.maps.Map(document.getElementById('map'), mapOptions);

    initMapByCity();
  }

function initMapByCity() {
    var address = this.selectedCity;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == 'OK') {
        map.setCenter(results[0].geometry.location);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
}

function addMakerBySearchResult() {
    var address = $("#address").val();
    geocoder.geocode( {'address': address}, function(results, status) {
      if (status == 'OK') {
        map.setCenter(results[0].geometry.location);
        locations.push(results[0].geometry.location);
        addList(address);
        addMarkerInMap();
      } else {
        // no search result
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
}

function deleteMarker() {
    locations.splice(markerIdx, 1);
    initialize();
    addMarkerInMap();
}

function addMarkerInMap() {
const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        const contentString =
            '<div id = "content">' +
                '<div id="siteNotice">' +
                    "</div>" +
                    "<p> 일정을 취소하시겠습니까? </p>" +
                    '<button type="button" id="yes-btn" onclick="deleteMarker()"'
                    + "</div>" +
                          "</div>";
        const infoWindow = new google.maps.InfoWindow({
            content: contentString,
            ariaLabel: "planning",
        });

    const markers = locations.map((position, i) => {
          const label = labels[i % labels.length];
          const marker = new google.maps.Marker({
            position,
            label,
           });
      google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infoWindow.open({
                    anchor: marker,
                    map,
                });
                markerIdx = i;
            }
      })(marker, i));

      if (marker) {
            marker.addListener('click', function() {
                map.setCenter(marker.getPosition());
                map.setZoom(14);
            });
            return marker;
       }
    });
    const markerCluster = new markerClusterer.MarkerClusterer({ map, markers });
}

// list 동적 할당
function addList(addValue) {
    // 2. 추가할 li element 생성
    const li = document.createElement("li");
    li.setAttribute('id', addValue);
    const textNode = document.createTextNode(addValue);
    li.appendChild(textNode);

    document.getElementById('location')
            .appendChild(li);
}

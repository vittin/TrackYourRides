
var Loading = {
    el: $(".loading-screen"),
    show: function () {
        this.el.show();
    },

    hide: function () {
        this.el.hide();
    }
};



$(document).on("ready", function(){

    Loading.show();
    getData();
});

function getData() {
    $.ajax({
        url: "api/tracks",
        method: "GET",
    })
        .done(function (responseJSON) {
            console.log(responseJSON);
            //var tracks = JSON.parse(responseJSON);
            var tracks = responseJSON;

            tracks.forEach(function(track){
                prependTrack(track);
            });
            enableSorting();
            $("#tracksTable_info").text("").append(newTrackButton());
            Loading.hide();
        })

        .fail(function (response) {
            if (response.status == 401){
                var responseBody = JSON.parse(response.responseText);
                console.log(responseBody.message);
            }
        })
}

function prependTrack(track) {
    var trackElement = createTrackElement(track);
    $("#tracksTableBody").prepend(trackElement);
}

function createTrackElement(track) {

    console.log(track);
    var TRACK_TEMPLATE = `<tr> <td class="id">${track.id}</td> <td class="dist"> ${track.distance} </td> <td class="time">${track.time}</td>` +
        `<td class="avg-speed">${track.averageSpeed}</td> <td class="max-speed">${track.maxSpeed}</td><td class="temp">${track.temperature}</td> </tr>`;
    console.log(TRACK_TEMPLATE);
    TRACK_TEMPLATE = TRACK_TEMPLATE.replace(/undefined/g, "");

    return $($.parseHTML(TRACK_TEMPLATE));
}

function newTrackButton(){
    return $($.parseHTML("<button class=btn onclick='newTrack()'>new Track</button>"));
}

function newTrack(){

}

function enableSorting(){
    $("#tracksTable").DataTable({
        scrollY: 300,
        paging: false,
        searching: false
    });
}

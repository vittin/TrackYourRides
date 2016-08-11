
$(document).on("ready", function(){

    //Loading.show();
    Ajax.getData();
});

var Loading = {
    el: $(".loading-screen"),
    show: function () {
        this.el.show();
    },

    hide: function () {
        this.el.hide();
    }
};

var Navigation = {
    loginPage: function () {
        window.location.replace("/login");
    }
};


var Ajax = {
    getData: function(){
        $.ajax({
            url: "api/tracks",
            method: "GET",
        })
            .done(function (responseJSON) {
                console.log(responseJSON);
                if (!responseJSON){
                    return;
                }
                for (var trackId in responseJSON) {
                    if (responseJSON.hasOwnProperty(trackId)) {
                        TrackDOM.prependTrack(responseJSON[trackId]);
                    }
                }

                TrackDOM.enableSorting();
                //$("#tracksTable_info").text("");
                Loading.hide();
            })

            .fail(function (response) {
                if (response.status == 401){
                    Navigation.loginPage();
                }
            })
    },

    saveTrack: function(track){
        $.ajax({
            url: "api/tracks",
            method: "POST",
            data: track,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })
            //done can also consume request.getResponseHeader("Location") as trackId,
            //then getTrack url should be simply " url: trackId ";
            .done(trackId =>  Ajax.getTrack(trackId, TrackDOM.prependTrack))
            .fail(response => {
                if(response.status == 401){
                    Navigation.loginPage();
                }

            })
    },

    getTrack: function(trackId, callback){
        console.log("trackId: " + trackId);
        $.ajax({
            url: "api/tracks/" + trackId,
            method: "GET",
        })
            .done(track => callback(track))
            .fail(response => {
                if(response.status == 401){
                    Navigation.loginPage();
                }
            })
    }
};

var TrackDOM = {
    prependTrack: function(track){

        if(!track.time){
            track.time = DateService.getTime(track.date);
            track.date = DateService.getDate(track.date);
        }

        var trackElement = Track.create(track);
        $("#tracksTableBody").prepend(trackElement);
    },

    newTrack: function(){
        var newInput = this.newInput;
        var editableTrack = {
            trackId: "new",
            date: newInput("text", 10, null, "yyyy:mm:dd", DateService.getDate()),
            time: newInput("text", 5, null, "hh:ss", DateService.getTime()),
            distance: newInput("number", 4, "km"),
            duration: newInput("text", 5, "min"),
            averageSpeed: newInput("number", 2, "km/h"),
            maxSpeed: newInput("number", 3, "km/h"),
            temperature: newInput("number", 2, "^C")
        };

        TrackDOM.prependTrack(editableTrack);
    },
    newInput: function(type, maxLength, label, placeholder, actualValue){
        type = type || "text";
        placeholder = placeholder || "";
        label = label || "";
        maxLength = maxLength || 20;

        var template1 =
                `<input class="track-input" type=${type} value=${actualValue} maxlength="${maxLength}" placeholder="${placeholder}" />` +
                `<label> ${label} </label>`;



        var template2 =
                `<input class="track-input" type=${type} maxlength="${maxLength}" placeholder="${placeholder}" />` +
                `<label> ${label} </label>`;

        return (actualValue) ? template1 : template2;
    },

    deleteTrack: function (trackId) {
        console.log(trackId);
        $("#track-"+trackId).remove();
    },

    getValues: function(){
        var pseudoMap = {};
        $(".track-input").each(function(id, vanillaElem){
            var elem = $(vanillaElem);
            var parent = elem.parent();

            pseudoMap[parent.attr("class")] = elem.val();
        });
        return pseudoMap;
    },

    enableSorting: function(){
        $("#tracksTable").DataTable({
            scrollX: true,
            scrollCollapse: true,
            paging: false,
            searching: false,
            columnDefs: [
                { width: '10%', targets: 0 },
                { width: '10%', targets: 1 },
                { width: '10%', targets: 2 },
                { width: '10%', targets: 3 },
                { width: '10%', targets: 4 },
                { width: '10%', targets: 5 },
                { width: '10%', targets: 6 }
            ],
            fixedColumns: true,
            language: {
                emptyTable: "You have no rides. <a href='#' onclick=Button.newTrackLink()>Add something.</a>"
            }
        });
    }
};

var Track = {
    create: function(track){
        var TRACK_TEMPLATE = `<tr id="track-${track.trackId}">` +
            `<td class="date">${track.date}</td>` +
            `<td class="time">${track.time}</td>` +
            `<td class="distance"> ${track.distance} </td>` +
            `<td class="duration">${track.duration}</td>` +
            `<td class="averageSpeed">${track.averageSpeed}</td>` +
            `<td class="maxSpeed">${track.maxSpeed}</td>` +
            `<td class="temperature">${track.temperature}</td>` +
            `</tr>`;
        TRACK_TEMPLATE = TRACK_TEMPLATE.replace(/undefined/g, "");

        return $($.parseHTML(TRACK_TEMPLATE));
    },

    toJson: function(){
        var valuesMap = TrackDOM.getValues();
        valuesMap.date = (DateService.parseDateAndTime(valuesMap.date, valuesMap.time)).toString();
        delete valuesMap.time;
        if (valuesMap.trackId == "new"){
            delete valuesMap.trackId;
        }
        return JSON.stringify(valuesMap);
    }
};

var Button = {
    edit: false,
    trigger: function(){
        this.edit = !this.edit;
        var button = $("#addButton");
        if (this.edit){
            button.text("save Track");
            button.attr("onclick", "Button.saveTrack()")
        } else {
            button.text("new Track");
            button.attr("onclick", "Button.newTrack()");
        }
    },

    saveTrack: function() {
        this.trigger();
        var json = Track.toJson();
        console.log(json);

        Ajax.saveTrack(json);

        TrackDOM.deleteTrack("new");
    },

    newTrack: function() {
        this.trigger();
        TrackDOM.newTrack();
    },

    newTrackLink(){
        $(".dataTables_empty").hide();
        this.newTrack();
    }
};



var DateService = {

    DATE: "DATE", TIME: "TIME",

    getDate: function (long){
        long = long || +new Date();
        return this.stringifyDate(long, 0, 10);
    },

    getTime: function (long){
        long = long || +new Date();
        return this.stringifyDate(long, 11, 16);
    },

    stringifyDate: function(long, start, end) {

        var date = new Date(long);
        return date.toISOString().slice(start, end).replace(/-/g, ".");
    },

    parseDateAndTime: function (dateString, timeString){
        if (timeString.length == 4){
            timeString = "0"+timeString;
        }

        var date = dateString.replace(/\./g, "-") + "T" + timeString;
        //+new gives us long value
        return +new Date(date);
    }
};

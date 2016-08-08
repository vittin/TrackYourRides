
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


var Ajax = {
    getData: function(){
        $.ajax({
            url: "api/tracks",
            method: "GET",
        })
            .done(function (responseJSON) {
                console.log(responseJSON);
                //var tracks = JSON.parse(responseJSON);
                var tracks = responseJSON;
                tracks.forEach(function(track){
                    TrackDOM.prependTrack(track);
                });

                TrackDOM.enableSorting();
                //$("#tracksTable_info").text("");
                Loading.hide();
            })

            .fail(function (response) {
                if (response.status == 401){
                    var responseBody = JSON.parse(response.responseText);
                    console.log(responseBody.message);
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
            .done(function(response){console.log(response)})
            .fail(function(response){console.log(response)})
    }
};

var TrackDOM = {
    prependTrack: function(track){
        var trackElement = Track.create(track);
        $("#tracksTableBody").prepend(trackElement);
    },

    newTrack: function(){
        var newInput = this.newInput;
        var editableTrack = {
            date: newInput("text", 8, DateService.getDate()),
            time: newInput("text", 5, DateService.getTime()),
            distance: newInput("number", 4),
            duration: newInput("text", 8),
            averageSpeed: newInput("number", 2),
            maxSpeed: newInput("number", 3),
            temperature: newInput("number", 2)
        };

        TrackDOM.prependTrack(editableTrack);
    },
    newInput: function(type, maxLength, actualValue){
        type = type || text;
        maxLength = maxLength || 20;

        var template1 = `<input class="track-input" type=${type} value=${actualValue} maxlength="${maxLength}" />`;
        var template2 = `<input class="track-input" type=${type} maxlength="${maxLength}" />`;

        return (actualValue) ? template1 : template2;
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
            scrollY: 300,
            paging: false,
            searching: false,
        });
    }
};

var Track = {
    create: function(track){
        var TRACK_TEMPLATE = `<tr>` +
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
        var valuesMap = TrackDOM.getValues();

        valuesMap.date = (DateService.parseDate(valuesMap.date, valuesMap.time)).toString();
        delete valuesMap.time;

        var json = JSON.stringify(valuesMap);
        console.log(json);
        Ajax.saveTrack(json)
    },

    newTrack: function() {
        this.trigger();
        TrackDOM.newTrack();
    }
};



var DateService = {
    getDate: function(){
        return this.stringifyDate(new Date(), 0, 10);
    },

    getTime: function(){
        return this.stringifyDate(new Date(), 11, 16);
    },

    stringifyDate: function(date, start, end) {

        return date.toISOString().slice(start, end).replace(/-/g, ".");
    },

    parseDate: function(dateString, timeString){
        if (timeString.length == 4){
            timeString = "0"+timeString;
        }

        var date = dateString.replace(/\./g, "-") + "T" + timeString;
        //+new gives us long value
        return +new Date(date);
    }
};

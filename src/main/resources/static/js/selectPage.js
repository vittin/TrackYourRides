/**
 * Created by mati on 2016-07-29.
 */

$("#open-app").click(function(){
    $.ajax({
        url: "/api/getTracks",
        method: "GET"
    })
        .done(function(response){
            console.log(response);
            showTracks(response.tracks)
        })
        .fail(function(response){
            console.log(response);
            if (response.status == 401){
                window.location.replace("/login");
            }
        })
});


function showTracks(){
    //todo;
    return 0;
}
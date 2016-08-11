/**
 * Created by mati on 2016-07-29.
 */


$(document).on("ready", function(){
    checkHost();
});

$("#open-app").click(function(){
    $.ajax({
        url: "api/users/logged",
        method: "GET"
    })
        .done(function(response){
            console.log(response);
            response = JSON.parse(response);
                window.location.replace(response.nextPage);

        })
        .fail(function(response){
            console.log(response);
        })
});

function checkHost(){
    var host = window.location.hostname;
    if (host == "localhost"){
        window.location.hostname = "127.0.0.1";
    }
}
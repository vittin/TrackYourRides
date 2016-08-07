/**
 * Created by mati on 2016-07-29.
 */

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


function showTracks(){
    //todo;
    return 0;
}
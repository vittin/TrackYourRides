/**
 * Created by mati on 2016-07-31.
 */
$("#login").click(function(e){
    e.preventDefault();

    var userInfo = {username: null, password: null};
    userInfo.username = $("#inputLogin").val();
    userInfo.password = $("#inputPassword").val();
    var json = JSON.stringify(userInfo);

    $.ajax({
        url: "/api/users/login",
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        data: json
    }).done(function(response) {
        var responseBody = JSON.parse(response);
        console.log(response);
        if (responseBody.nextPage !== undefined){
            window.location.replace(responseBody.nextPage);
        } else {
            window.location.replace("/");
        }

    }).fail(function(response) {
        var responseBody = JSON.parse(response.responseText);
        showMessage(responseBody.message);
    });
});

function showMessage(message){
    $("#info").text(message);
}
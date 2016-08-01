/**
 * Created by mati on 2016-07-31.
 */
$("#login").click(function(e){
    e.preventDefault();

    var userInfo = {login: null, password: null};
    userInfo.login = $("#login").val();
    userInfo.password = $("#password").val();
    var json = JSON.stringify(userInfo);

    $.ajax({
        url: "/api/users/login",
        method: "POST",
        data: json
    }).done(function(response) {
        var responseBody = JSON.parse(response.responseText);
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
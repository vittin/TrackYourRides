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
        url: "/api/login",
        method: "POST",
        data: json
    }).done(function(response) {
        console.log("done, " + json)
        console.log("response: " + response)
    }).fail(function(response) {
        console.log("failed, " + json)
        console.log("response: " + response)
    });
});
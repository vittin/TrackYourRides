/**
 * Created by mati on 2016-07-06.
 */

$(document).on("ready", function(){
    submitHandler();
});

function submitHandler() {
    var form = $("#registerForm");
    form.on("submit", function(){
        if(Form.isValid()){
            Form.send();
        }
        return false;
    });
}

var Form = {

    isValid: function(){
        //check if each field has length > 3;
        $.each(this.toArray(), function(){
            if (this.value.length < 3){
                return false;
            }
        });
        return true;
    },

    send: function(){
        $.ajax({
            url: "/api/users",
            method: "POST",
            data: Form.toJson(),
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .done(function(response){
                var responseBody = JSON.parse(response.responseText);
                window.location.replace(responseBody.nextPage);
            })
            .fail(function(response){
                var responseBody = JSON.parse(response.responseText);
                if (response.status === 409){
                    Form.showMessage(responseBody.message);
                }
                else if (response.status === 422){
                    Form.showMessage(responseBody.message);
                }
            });
    },

    toJson: function(){

        var serializeForm = this.toArray();

        var jsObject = {};
        $.each(serializeForm, function(){
            jsObject[this.name] = this.value;
        });

        return JSON.stringify(jsObject);
    },

    toArray: function(){
        return $("#registerForm").serializeArray();
    },

    showMessage: function(message){
        $("#info").text(message);
    }

};
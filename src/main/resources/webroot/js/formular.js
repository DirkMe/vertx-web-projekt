$(document).ready(function () {
$.ajax({url: "../session", data:
                {
                    typ: "anfrage"
                },
        success: function (data) {
            if (data.typ == "angemeldet") {
                if (data.angemeldet==("ja")) {
                    $("body").html("<input type='button' id='logout' value='abmelden'/>");
                } else if (data.typ == "angemeldet") {
                if (data.angemeldet==("nein")) {
                    $("body").html('Benutzername: <input type="text" id="eingabeName"/><br>   Passwort: <input type="text" id="eingabePasswort"/><br> <input type="button" id="eingabeKnopf" value="O" />');

                }
            }}
        }
    });
});



    

$("#eingabeKnopf").click(function () {
    $.ajax({url: "../anfrage", data:
                {
                    name: $("#eingabeName").val(),
                    passwort: $("#eingabePasswort").val()
                },
        success: function (data) {
            if (data.typ == "anmeldung") {
                if (data.istAngemeldet == true) {
                    $("body").html("<input type='button' id='logout' value='abmelden'/>");

                }
            }
            if (data.typ == "anmeldung") {
                if (data.istAngemeldet == false) {
                    $("body").html('Benutzername: <input type="text" id="eingabeName"/><br>   Passwort: <input type="text" id="eingabePasswort"/><br> <input type="button" id="eingabeKnopf" value="Ok" <br>  <br> Falsche Anmeldedaten</>');

                }
            }


        }
    });
});




$(document).on("click", "#logout", function () {

    $("body").html('Benutzername: <input type="text" id="eingabeName"/><br>   Passwort: <input type="text" id="eingabePasswort"/><br> <input type="button" id="eingabeKnopf" value="Ok" />');

});



 
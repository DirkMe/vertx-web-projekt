$(document).ready(function () {


    $("body").html('Benutzername: <input type="text" id="eingabeName"/><br>   Passwort: <input type="text" id="eingabePasswort"/><br> <input type="button" id="eingabeKnopf" value="Ok" />');

     $(document).on("click", "#eingabeKnopf",function () {
        $.ajax({url: "../anfrage", data:
                    {
                        typ: "namenKnopf",
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
    });


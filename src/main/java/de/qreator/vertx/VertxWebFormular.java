/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.qreator.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.StaticHandler;

public class VertxWebFormular {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route("/anfrage").handler(routingContext -> {
            String typ = routingContext.request().getParam("typ");
            String name = routingContext.request().getParam("name");
            String rpw = "abcd";
            String rbn = "Baum";
            String passwort = routingContext.request().getParam("passwort");
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            JsonObject jo = new JsonObject();

            if (typ.equals("namenKnopf")) {
                jo.put("typ", "antwort");
                jo.put("text", "Dein Benutzername ist: " + name + "Dein Passwort: " + passwort);
            }
  
            if (rpw.equals(passwort)&& rbn.equals(name)) {
                jo.put("typ","anmeldung");
                jo.put("istAngemeldet",true);
                Session session = routingContext.session();
                session.put("angemeldet", "ja");
            } else {
                jo.put("typ","anmeldung");
                jo.put("istAngemeldet",false);
                jo.put("text", "Daten sind falsch");
            }
            
            
            
            
        response.end(Json.encodePrettily(jo));
        });
        router.route("/session").handler(routingContext -> {
            String typ = routingContext.request().getParam("typ");
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            JsonObject jo = new JsonObject();
            if (typ.equals("anfrage")){
                Session session = routingContext.session();
                jo.put("typ", "angemeldet");
                if (session.get("angemeldet")==null){
                    
                    jo.put("angemeldet","nein");
                } else if (session.get("angemeldet").equals("ja")){
                    
                    jo.put("angemeldet","ja");
                }
                response.end(Json.encodePrettily(jo));
            }
        });
    

        // statische html-Dateien werden über den Dateipfad static ausgeliefert
    router.route (

    "/static/*").handler(StaticHandler.create().setDefaultContentEncoding("UTF-8"));

        // router::accept akzeptiert eine Anfrage und leitet diese an den Router weiter
    server.requestHandler (router

::accept).listen(8080);
    }
}

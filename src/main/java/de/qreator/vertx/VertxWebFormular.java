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
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;

public class VertxWebFormular {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        
        router.route().handler(CookieHandler.create());

        SessionStore baum = LocalSessionStore.create(vertx);

        SessionHandler praktikant = SessionHandler.create(baum);

        router.route().handler(praktikant);
        

        

        router.route("/session").handler(routingContext -> {
            String typ = routingContext.request().getParam("typ");
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            JsonObject jo = new JsonObject();
            if (typ.equals("anfrage")) {
                Session session = routingContext.session();
                jo.put("typ", "angemeldet");
                if (session.get("angemeldet").equals("ja")) {

                    jo.put("angemeldet", "ja");
                } else if (session.get("angemeldet") == null) {

                    jo.put("angemeldet", "nein");
                }
                response.end(Json.encodePrettily(jo));
            }
        });

        router.route("/anfrage").handler(routingContext -> {
            String name = routingContext.request().getParam("name");
            String rpw = "b";
            String rbn = "b";
            String passwort = routingContext.request().getParam("passwort");
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            JsonObject jo = new JsonObject();
            

            if (rpw.equals(passwort) && rbn.equals(name)) {
                jo.put("typ", "anmeldung");
                jo.put("istAngemeldet", true);
                Session session = routingContext.session();
                session.put("angemeldet", "ja");

            } else {
                jo.put("typ", "anmeldung");
                jo.put("istAngemeldet", false);
                jo.put("text", "Daten sind falsch");
                Session session = routingContext.session();
                session.put("angemeldet", "nein");
            }

            response.end(Json.encodePrettily(jo));
        }
        );

        // statische html-Dateien werden über den Dateipfad static ausgeliefert
        router.route("/static/*").handler(StaticHandler.create().setDefaultContentEncoding("UTF-8").setCachingEnabled(false));
        // router::accept akzeptiert eine Anfrage und leitet diese an den Router weiter
        server.requestHandler(router::accept).listen(8080);
    }
}

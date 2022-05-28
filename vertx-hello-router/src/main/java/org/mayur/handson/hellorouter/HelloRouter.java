package org.mayur.handson.hellorouter;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class HelloRouter extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(reqHandler -> {
            reqHandler.request().response().end("Hi, From Router Module");
        });
        router.get("/api/v1/hello/:name").handler(reqHandler -> {
            String name = reqHandler.pathParam("name");
            reqHandler.request().response().end(String.format("Hi %s, From Router Module", name));
        });

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }
}

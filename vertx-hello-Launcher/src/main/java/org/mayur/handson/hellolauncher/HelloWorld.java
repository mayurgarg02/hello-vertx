package org.mayur.handson.hellolauncher;

import io.vertx.core.AbstractVerticle;

public class HelloWorld extends AbstractVerticle {

    @Override
    public void start() {
        vertx
                .createHttpServer()
                .requestHandler(req-> {
                    req.response().end("Hello Vertx World. I am here !!");
                })
                .listen(8080);
    }
}

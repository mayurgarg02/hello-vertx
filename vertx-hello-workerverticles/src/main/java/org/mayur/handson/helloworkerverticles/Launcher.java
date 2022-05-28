package org.mayur.handson.helloworkerverticles;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Launcher {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        log.info("Launcher Thread");
        vertx.deployVerticle(new HelloEventBus());
     }
}

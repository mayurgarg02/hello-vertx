package org.mayur.handson.helloworkerverticles;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        log.info("Starting new instance of Bus Verticle !!!!");
        vertx.eventBus()
                .consumer("someStringAddr1",msgHandler -> {
                    log.info("Here in BusVerticle::someStringAddr1");
                    msgHandler.reply("Hello World again from BusVerticle ");
                });
        vertx.eventBus()
                .consumer("someStringAddr2",msgHandler -> {
                    String name = (String) msgHandler.body();
                    log.info("Here in BusVerticle::someStringAddr2");
                    msgHandler.reply(String.format("Hello %s again from BusVerticle",name));
                });
    }
}

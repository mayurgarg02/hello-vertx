package org.mayur.handson.helloconfiguration;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class BusVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        log.info("Starting new instance of Bus Verticle !!!!");

        String verticleId = UUID.randomUUID().toString();
        vertx.eventBus()
                .consumer("someStringAddr1",msgHandler -> {
                    log.info("Here in BusVerticle::someStringAddr1");
                    msgHandler.reply(String.format("Hello World again from BusVerticle %s ",verticleId));
                });
        vertx.eventBus()
                .consumer("someStringAddr2",msgHandler -> {
                    String name = (String) msgHandler.body();
                    log.info("Here in BusVerticle::someStringAddr2");
                    msgHandler.reply(String.format("Hello %s again from BusVerticle %s",name,verticleId));
                });
    }
}

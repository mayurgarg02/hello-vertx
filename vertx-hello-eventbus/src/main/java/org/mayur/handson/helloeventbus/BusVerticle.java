package org.mayur.handson.helloeventbus;

import io.vertx.core.AbstractVerticle;

public class BusVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.eventBus()
                .consumer("someStringAddr1",msgHandler -> {
                    msgHandler.reply("Hello World again from BusVerticle ");
                });
        vertx.eventBus()
                .consumer("someStringAddr2",msgHandler -> {
                    String name = (String) msgHandler.body();
                    msgHandler.reply(String.format("Hello %s again from BusVerticle",name));
                });
    }
}

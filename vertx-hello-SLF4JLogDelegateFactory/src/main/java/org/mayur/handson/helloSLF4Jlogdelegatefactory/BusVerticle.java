package org.mayur.handson.helloSLF4Jlogdelegatefactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

public class BusVerticle extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(BusVerticle.class);

    @Override
    public void start() throws Exception {
        log.info("Starting new instance of Bus Verticle !!!!");

        log.info("BusVerticle Thread..Sending thread to sleep");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("BusVerticle  Thread..Thread back up");

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

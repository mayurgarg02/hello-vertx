package org.mayur.handson.helloSLF4Jlogdelegatefactory;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;
import lombok.extern.slf4j.Slf4j;

public class Launcher {

    public static void main(String[] args) {

        System.setProperty("vertx.logger-delegate-factory-class-name", SLF4JLogDelegateFactory.class.getName());
        Logger log = LoggerFactory.getLogger(Launcher.class);

        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(1);
                /*.setWorkerPoolSize(10);*/

        log.info("Launcher Thread..Starting HelloEventBus Verticle deployment");

        log.info("Launcher Thread..Sending thread to sleep");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Launcher Thread..Thread back up");


        vertx.deployVerticle("org.mayur.handson.helloSLF4Jlogdelegatefactory.HelloEventBus",options);
    }
}

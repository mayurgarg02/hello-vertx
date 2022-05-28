package org.mayur.handson.helloeventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HelloEventBus extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        //Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BusVerticle());

        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::getHelloContextHandler);
        router.get("/api/v1/hello/:name").handler(this::getNamedHelloContextHandler);

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }

       /*    private void getNamedHelloContextHandler(RoutingContext routingContext) {
                String name = routingContext.pathParam("name");
                routingContext.request().response().end(String.format("Hi %s, From Router Module", name));
        }*/

    private void getNamedHelloContextHandler(RoutingContext routingContext) {
        String name = routingContext.pathParam("name");
        vertx.eventBus().request("someStringAddr2",name, getBusResultHandler(routingContext));
    }

    private Handler<AsyncResult<Message<Object>>> getBusResultHandler(RoutingContext routingContext) {
        return reply->{
            routingContext.request().response().end((String)reply.result().body());
        };
    }

/*    private void getHelloContextHandler(RoutingContext routingContext) {
        routingContext.request().response().end("Hi, From Router Module");
    }*/

    private void getHelloContextHandler(RoutingContext routingContext) {
        vertx.eventBus().request("someStringAddr1","",getBusResultHandler(routingContext));
    }
}

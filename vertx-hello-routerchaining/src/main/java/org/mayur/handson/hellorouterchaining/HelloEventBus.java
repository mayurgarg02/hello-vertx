package org.mayur.handson.hellorouterchaining;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class HelloEventBus extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.deployVerticle("org.mayur.handson.hellorouterchaining.BusVerticle");

        Router router = Router.router(vertx);
        router.route().handler(routingContext -> {Authenticate(routingContext);});
        router.get("/api/v1/hello").handler(this::getHelloContextHandler);
        router.get("/api/v1/hello/:name").handler(this::getNamedHelloContextHandler);
        router.route().handler(StaticHandler.create("web").setIndexPage("index.html"));

        ConfigStoreOptions configStoreOptions_Env = new ConfigStoreOptions()
                .setType("env")
                .setConfig(new JsonObject().put("raw-data", true));
        ConfigRetrieverOptions configRetrieverOptions_Env = new ConfigRetrieverOptions()
                .addStore(configStoreOptions_Env);
        ConfigRetriever configRetriever_Env = ConfigRetriever
                .create(vertx,configRetrieverOptions_Env);

        Future<JsonObject> future = configRetriever_Env.getConfig();
        future.onComplete(ar -> {
            if (ar.failed()) {
                // Failed to retrieve the configuration
            } else {
                JsonObject config = ar.result();
            }
        });

        ConfigStoreOptions configStoreOptions_File = new ConfigStoreOptions()
                .setType("file")
                .setFormat("json")
                .setConfig(new JsonObject().put("path","config-" +future.result().getString("myEnvironment") + ".json"));

        ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions()
                .addStore(configStoreOptions_File)
                .addStore(configStoreOptions_Env);

        ConfigRetriever configRetriever = ConfigRetriever
                .create(vertx,configRetrieverOptions);


        configRetriever.getConfig(asyncResult -> {
            extractConfig(startPromise, router, asyncResult);
        });

    }

    private void Authenticate(RoutingContext routingContext) {

        String AuthToken = Optional.ofNullable(routingContext.request().getHeader("AuthToken")).orElse("");
        String httpVerb = routingContext.request().method().toString();
        if (httpVerb.contentEquals("GET") ||  "mysupersecretAuthToken".contentEquals(AuthToken)){
            routingContext.next();
        }
        else {
            routingContext.request().response().setStatusCode(401).end();
        }
    }

    private void extractConfig(Promise<Void> startPromise, Router router, AsyncResult<JsonObject> asyncResult) {
        if(asyncResult.succeeded()){
            JsonObject config = asyncResult.result();
            JsonObject httpBlock = config.getJsonObject("http");
            int httpPort =httpBlock.getInteger("port");
            vertx
                    .createHttpServer()
                    .requestHandler(router)
                    .listen(httpPort);
            log.info(String.format("Starting new instance of HelloEventBus Verticle on port %s!!!!",httpPort));
            startPromise.complete();
        }
        else {
            startPromise.fail("Sorry Couldn't retrieve Config !!");
        }
    }

       /*    private void getNamedHelloContextHandler(RoutingContext routingContext) {
                String name = routingContext.pathParam("name");
                routingContext.request().response().end(String.format("Hi %s, From Router Module", name));
        }*/

    private void getNamedHelloContextHandler(RoutingContext routingContext) {
        String name = routingContext.pathParam("name");
        vertx.eventBus().request("someStringAddr2", name, getBusResultHandler(routingContext));
    }

    private Handler<AsyncResult<Message<Object>>> getBusResultHandler(RoutingContext routingContext) {
        return reply -> {
            routingContext.request().response().end((String) reply.result().body());
        };
    }

/*    private void getHelloContextHandler(RoutingContext routingContext) {
        routingContext.request().response().end("Hi, From Router Module");
    }*/

    private void getHelloContextHandler(RoutingContext routingContext) {
        vertx.eventBus().request("someStringAddr1", "", getBusResultHandler(routingContext));
    }
}

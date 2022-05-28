# Hello-Vertx

Hello Vertx contains a range of basic hello examples needed to onboard the vertx bus. <br>

As always - Start with a hello -> vertx-hello-world.

## vertx-hello-world
vertx-hello-configurataion contains basic hello world example for Vert.x

## vertx-hello-configuration
vertx-hello-configuration contains example to provide a config to your Vert.x application.
It implements a profile/environment specific configuration to Vert.x application.

### Running the project
Define an environment variable "env" through IDE and application will load the corresponding config-{env}.json file.
This is very similar to 'profile-specific properties files' feature available out of the box in spring. 

## vertx-hello-eventbus
vertx-hello-eventbus example implements event bus - the nervous system of Vert.x.

## vertx-hello-infispan
vertx-hello-infispan example implements Vert.x infispan cluster manager

## vertx-hello-router
vertx-hello-router example implements Vert.x routers. Making your resources available over a URI. 

## vertx-hello-routerchaining
vertx-hello-routerchaining example implements Vert.x router chaining

## vertx-hello-SLF4Jlogdelegatefactory
vertx-hello-SLF4Jlogdelegatefactory example implements logging using SLF4JLogDelegateFactory. <br>
This is very helpful in understanding work distribution at thread level. It also alerts any blocking thread operations.

## vertx-hello-workerverticles
vertx-hello-workerverticles example implements worker verticles.



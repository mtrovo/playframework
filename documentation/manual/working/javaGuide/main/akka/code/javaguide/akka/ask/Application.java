/*
 * Copyright (C) 2009-2016 Typesafe Inc. <http://www.typesafe.com>
 */
package javaguide.akka.ask;

import javaguide.akka.HelloActor;
import javaguide.akka.HelloActorProtocol.SayHello;

//#ask
import akka.actor.*;
import play.mvc.*;
import scala.compat.java8.FutureConverters;
import javax.inject.*;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;

@Singleton
public class Application extends Controller {

    final ActorRef helloActor;

    @Inject public Application(ActorSystem system) {
        helloActor = system.actorOf(HelloActor.props);
    }

    public CompletionStage<Result> sayHello(String name) {
        return FutureConverters.toJava(ask(helloActor, new SayHello(name), 1000))
                .thenApply(response -> ok((String) response));
    }
}
//#ask

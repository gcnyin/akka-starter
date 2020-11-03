package com.github.gcnyin.pingpong;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class UserA extends AbstractBehavior<UserA.Greet> {
  public static class Greet {
    public final ActorRef<UserB.Greeted> replyTo;

    public Greet(ActorRef<UserB.Greeted> replyTo) {
      this.replyTo = replyTo;
    }
  }

  public static Behavior<Greet> create() {
    return Behaviors.setup(UserA::new);
  }

  public UserA(ActorContext<Greet> context) {
    super(context);
  }

  @Override
  public Receive<Greet> createReceive() {
    return newReceiveBuilder()
      .onMessage(Greet.class, this::onMessage)
      .build();
  }

  private Behavior<Greet> onMessage(Greet command) {
    getContext().getLog().info("Nice to meet you {}!", command.replyTo);
    command.replyTo.tell(new UserB.Greeted(getContext().getSelf()));
    return this;
  }
}

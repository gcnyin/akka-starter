package com.github.gcnyin.pingpong;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class UserB extends AbstractBehavior<UserB.Greeted> {
  public static class Greeted {
    public final ActorRef<UserA.Greet> replyTo;

    public Greeted(ActorRef<UserA.Greet> replyTo) {
      this.replyTo = replyTo;
    }
  }

  public static Behavior<Greeted> create() {
    return Behaviors.setup(UserB::new);
  }

  private final ActorRef<UserA.Greet> greetActorRef;

  public UserB(ActorContext<Greeted> context) {
    super(context);
    greetActorRef = context.spawn(UserA.create(), "userA");
    greetActorRef.tell(new UserA.Greet(getContext().getSelf()));
  }

  @Override
  public Receive<Greeted> createReceive() {
    return newReceiveBuilder()
      .onMessage(Greeted.class, this::onMessage)
      .build();
  }

  public Behavior<Greeted> onMessage(Greeted command) {
    getContext().getLog().info("Nice to meet you {}, too!", command.replyTo);
    command.replyTo.tell(new UserA.Greet(getContext().getSelf()));
    return this;
  }
}

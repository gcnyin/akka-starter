package com.github.gcnyin.firstsample;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class App extends AbstractBehavior<App.Command> {
  public static class Command {
    final String message;

    public Command(String message) {
      this.message = message;
    }
  }

  public static Behavior<Command> create() {
    return Behaviors.setup(App::new);
  }

  public App(ActorContext<Command> context) {
    super(context);
  }

  @Override
  public Receive<Command> createReceive() {
    return newReceiveBuilder()
      .onMessage(Command.class, this::onMessage)
      .build();
  }

  private Behavior<Command> onMessage(Command command) {
    System.out.println(command.message);
    return this;
  }
}

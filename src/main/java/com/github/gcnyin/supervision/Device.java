package com.github.gcnyin.supervision;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Device extends AbstractBehavior<Device.Ping> {
  public interface Ping {
  }

  public interface Pong {
  }

  public static Behavior<Ping> create() {
    return Behaviors.setup(Device::new);
  }

  @ToString
  @EqualsAndHashCode
  public static class PlusOnePing implements Ping {
    public final ActorRef<Pong> replyTo;

    public PlusOnePing(ActorRef<Pong> replyTo) {
      this.replyTo = replyTo;
    }
  }

  @ToString
  @EqualsAndHashCode
  public static class PlugOnePong implements Pong {
    public final int value;

    public PlugOnePong(int value) {
      this.value = value;
    }
  }

  private int value = 0;

  public Device(ActorContext<Ping> context) {
    super(context);
  }

  @Override
  public Receive<Ping> createReceive() {
    return newReceiveBuilder()
      .onMessage(PlusOnePing.class, this::onPlusOneRequestCommand)
      .build();
  }

  public Behavior<Ping> onPlusOneRequestCommand(PlusOnePing command) {
    value++;
    if (value % 2 == 0) {
      throw new InternalError();
    }
    command.replyTo.tell(new PlugOnePong(value));
    return this;
  }
}

package com.github.gcnyin.supervision;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.Behaviors;
import org.junit.Test;

public class DeviceTest {
  private final ActorTestKit testKit = ActorTestKit.create("test-kit");

  @Test
  public void should_reuse_the_value_after_error_when_SupervisorStrategy_is_resume() {
    TestProbe<Device.Pong> pong = testKit.createTestProbe("pong");
    Behavior<Device.Ping> pingBehavior = Behaviors
      .supervise(Behaviors.setup(Device::new))
      .onFailure(InternalError.class, SupervisorStrategy.resume());
    ActorRef<Device.Ping> device = testKit.spawn(pingBehavior, "device");
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectMessage(new Device.PlugOnePong(1));
    device.tell(new Device.PlusOnePing(pong.getRef()));
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectMessage(new Device.PlugOnePong(3));
  }

  @Test
  public void should_reset_the_value_after_error_when_SupervisorStrategy_is_restart() {
    TestProbe<Device.Pong> pong = testKit.createTestProbe("pong");
    Behavior<Device.Ping> pingBehavior = Behaviors
      .supervise(Behaviors.setup(Device::new))
      .onFailure(InternalError.class, SupervisorStrategy.restart());
    ActorRef<Device.Ping> device = testKit.spawn(pingBehavior, "device");
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectMessage(new Device.PlugOnePong(1));
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectNoMessage();
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectMessage(new Device.PlugOnePong(1));
  }

  @Test
  public void should_stop_after_error_when_SupervisorStrategy_is_stop() {
    TestProbe<Device.Pong> pong = testKit.createTestProbe("pong");
    Behavior<Device.Ping> pingBehavior = Behaviors
      .supervise(Behaviors.setup(Device::new))
      .onFailure(InternalError.class, SupervisorStrategy.stop());
    ActorRef<Device.Ping> device = testKit.spawn(pingBehavior, "device");
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectMessage(new Device.PlugOnePong(1));
    device.tell(new Device.PlusOnePing(pong.getRef()));
    pong.expectNoMessage();
  }
}

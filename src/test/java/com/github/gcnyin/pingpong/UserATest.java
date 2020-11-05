package com.github.gcnyin.pingpong;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import org.junit.Test;

public class UserATest {
  private final ActorTestKit testKit = ActorTestKit.create("test-kit");

  @Test
  public void should_recv_response() {
    final TestProbe<UserB.Greeted> probe = testKit.createTestProbe("control-center");
    ActorRef<UserA.Greet> userA = testKit.spawn(UserA.create(), "userA");
    userA.tell(new UserA.Greet(probe.getRef()));
    probe.expectMessage(new UserB.Greeted(userA));
    probe.stop();
  }
}

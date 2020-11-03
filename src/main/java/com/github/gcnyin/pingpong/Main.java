package com.github.gcnyin.pingpong;

import akka.actor.typed.ActorSystem;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ActorSystem<UserB.Greeted> system = ActorSystem.create(UserB.create(), "userB");
    Thread.sleep(1000);
    system.terminate();
  }
}

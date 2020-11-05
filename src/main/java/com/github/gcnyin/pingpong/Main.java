package com.github.gcnyin.pingpong;

import akka.actor.typed.ActorSystem;

public class Main {
  public static void main(String[] args) {
    ActorSystem<UserB.Greeted> system = ActorSystem.create(UserB.create(), "userB");
    system.terminate();
  }
}

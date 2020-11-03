package com.github.gcnyin.firstsample;

import akka.actor.typed.ActorSystem;

public class Main {
  public static void main(String[] args) {
    ActorSystem<App.Command> system = ActorSystem.create(App.create(), "App");
    system.tell(new App.Command("Hello world"));
    system.terminate();
  }
}

package com.vulinh;

import com.vulinh.game.Game;

public class Main {

  public static void main(String[] args) {
    new Game()
        .start()
        .ifPresentOrElse(
            winner -> System.out.printf("Player %s is the winner!%n", winner.name()),
            () -> System.out.println("No one is the winner!"));
  }
}

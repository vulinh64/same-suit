package com.vulinh;

import com.vulinh.game.Game;

import java.util.Optional;

public class Main {

  public static void main(String[] args) {
    Optional.ofNullable(new Game().start())
        .ifPresentOrElse(
            winner -> System.out.printf("Player %s is the winner!%n", winner.name()),
            () -> System.out.println("No one is the winner!"));
  }
}

package com.vulinh.object;

public record Card(Rank rank, Suit suit) {

  @Override
  public String toString() {
    return "%s of %s".formatted(rank, suit);
  }
}

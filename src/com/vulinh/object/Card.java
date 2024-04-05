package com.vulinh.object;

public record Card(Rank rank, Suit suit) {

  public static Card of(Rank rank, Suit suit) {
    return new Card(rank, suit);
  }

  @Override
  public String toString() {
    return "%s of %s".formatted(rank, suit);
  }
}

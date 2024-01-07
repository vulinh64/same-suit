package com.vulinh.game;

import com.vulinh.object.PileSource;

public enum Settings {
  MIN_PLAYERS(2),
  MAX_PLAYERS(5),
  CARD_INDEX_LOWER_BOUND(0),
  CARD_INDEX_UPPER_BOUND(5),
  INITIAL_NUMBER_OF_CARDS(5),
  CARD_PILE_SOURCE_LOWER(0),
  CARD_PILE_SOURCE_UPPER(PileSource.values().length - 1);

  private final int value;

  Settings(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

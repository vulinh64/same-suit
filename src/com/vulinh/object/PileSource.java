package com.vulinh.object;

public enum PileSource {
  DISCARDED_PILE("Discarded Pile"),
  CARD_PILE("Card Pile");

  private final String displayName;

  PileSource(String displayName) {
    this.displayName = displayName;
  }

  public String displayName() {
    return displayName;
  }
}

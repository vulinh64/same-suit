package com.vulinh.object;

import java.util.ArrayDeque;
import java.util.Deque;

public record CardPile(Deque<Card> pile, PileSource pileSource) {

  public CardPile(PileSource pileSource) {
    this(new ArrayDeque<>(), pileSource);
  }
}

package com.vulinh.object;

import java.util.ArrayDeque;
import java.util.Deque;

public record CardPile(Deque<Card> pile, PileSource pileSource) {

  public static CardPile of(Deque<Card> pile, PileSource pileSource) {
    return new CardPile(pile, pileSource);
  }

  public static CardPile of(PileSource pileSource) {
    return CardPile.of(new ArrayDeque<>(), pileSource);
  }

  public boolean isEmptyPile() {
    return pile.isEmpty();
  }

  public void addCard(Card card) {
    pile.push(card);
  }

  public Card popCard() {
    return pile.pop();
  }
}

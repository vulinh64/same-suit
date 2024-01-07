package com.vulinh.object;

import com.vulinh.game.Settings;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record Player(String name, List<Card> cards) {

  private static final int CARD_INDEX_LOWER_BOUND = Settings.CARD_INDEX_LOWER_BOUND.getValue();
  private static final int CARD_INDEX_UPPER_BOUND = Settings.CARD_INDEX_UPPER_BOUND.getValue();

  @Override
  public String toString() {
    var index = new AtomicInteger(1);

    return "Player %s is currently holding these cards: %s%n"
        .formatted(
            name,
            cards.stream()
                .map(card -> "%s (%d)".formatted(card, index.getAndIncrement()))
                .toList());
  }

  public Card discard(int cardIndex) {
    if (cardIndex < CARD_INDEX_LOWER_BOUND || cardIndex > CARD_INDEX_UPPER_BOUND) {
      throw new IllegalArgumentException(
          "Card index must be between %s and %s inclusively!"
              .formatted(CARD_INDEX_LOWER_BOUND, CARD_INDEX_UPPER_BOUND));
    }

    var removed = cards.remove(cardIndex);

    System.out.printf("Player %s has discarded card %s%n%n", name, removed);

    return removed;
  }

  public void draw(CardPile source) {
    var pile = source.pile();

    if (pile.isEmpty()) {
      throw new IllegalArgumentException(
          "Card source %s has been emptied!".formatted(source.pileSource()));
    }

    var drawnCard = pile.pop();

    cards.add(drawnCard);

    System.out.printf(
        "Player %s has drawn card %s from %s%n%n",
        name, drawnCard, source.pileSource().displayName());
  }
}

package com.vulinh.game;

import com.vulinh.object.CardPile;
import com.vulinh.object.PileSource;
import com.vulinh.object.Player;
import com.vulinh.utils.GameUtils;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

  private static final Scanner SCANNER = new Scanner(System.in);

  private final CardPile discardedPile = new CardPile(PileSource.DISCARDED_PILE);
  private final CardPile cardPile;

  public Game() {
    cardPile = new CardPile(GameUtils.generateCardPile(), PileSource.CARD_PILE);
  }

  public Player start() {
    var numberOfPlayers = GameUtils.generateNumberOfPlayers(SCANNER);
    var players = GameUtils.generatePlayers(numberOfPlayers, cardPile, SCANNER);

    System.out.println("\nGAME ON!\n");

    var playerIndex = new AtomicInteger();

    while (true) {
      var currentPlayer = players.get(playerIndex.get() % numberOfPlayers);

      System.out.println(currentPlayer);

      if (GameUtils.isWinner(currentPlayer)) {
        return currentPlayer;
      }

      if (cardPile.pile().isEmpty()) {
        return null;
      }

      var source = GameUtils.generatePileSource(discardedPile, SCANNER);

      switch (source) {
        case DISCARDED_PILE -> currentPlayer.draw(discardedPile);
        case CARD_PILE -> currentPlayer.draw(cardPile);
        default -> throw new IllegalStateException("Invalid source value: %s".formatted(source));
      }

      System.out.println(currentPlayer);

      var discardedCardIndex = GameUtils.generateDiscardedCardIndex(currentPlayer, SCANNER);

      var discarded = currentPlayer.discard(discardedCardIndex);

      discardedPile.pile().add(discarded);

      if (GameUtils.isWinner(currentPlayer)) {
        return currentPlayer;
      }

      System.out.printf(
          "Switched to next player: %s%n",
          players.get(playerIndex.incrementAndGet() % numberOfPlayers).name());

      SCANNER.nextLine();
    }
  }
}

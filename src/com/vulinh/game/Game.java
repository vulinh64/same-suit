package com.vulinh.game;

import com.vulinh.object.CardPile;
import com.vulinh.object.PileSource;
import com.vulinh.object.Player;
import com.vulinh.utils.GameUtils;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

  private static final Scanner SCANNER = new Scanner(System.in);

  private final CardPile discardedPile;
  private final CardPile cardPile;

  private final int numberOfPlayers;
  private final List<Player> players;

  public Game() {
    discardedPile = new CardPile(PileSource.DISCARDED_PILE);
    cardPile = new CardPile(GameUtils.generateCardPile(), PileSource.CARD_PILE);
    numberOfPlayers = GameUtils.generateNumberOfPlayers(SCANNER);
    players = GameUtils.generatePlayers(numberOfPlayers, cardPile, SCANNER);
  }

  public Player start() {
    System.out.println("\nGAME ON!\n");

    var playerIndex = new AtomicInteger();

    while (true) {
      var currentPlayer = players.get(playerIndex.get() % numberOfPlayers);

      System.out.println(currentPlayer);

      if (GameUtils.isWinner(currentPlayer)) {
        return currentPlayer;
      }

      if (cardPile.isEmptyPile()) {
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

      discardedPile.addCard(discarded);

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

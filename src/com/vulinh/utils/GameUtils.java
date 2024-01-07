package com.vulinh.utils;

import static com.vulinh.game.Settings.INITIAL_NUMBER_OF_CARDS;

import com.vulinh.game.Settings;
import com.vulinh.object.*;
import java.util.*;

public class GameUtils {

  private static final int MIN_PLAYERS = Settings.MIN_PLAYERS.getValue();
  private static final int MAX_PLAYERS = Settings.MAX_PLAYERS.getValue();

  private static final int CARD_INDEX_LOWER_BOUND = Settings.CARD_INDEX_LOWER_BOUND.getValue();
  private static final int CARD_INDEX_UPPER_BOUND = Settings.CARD_INDEX_UPPER_BOUND.getValue();

  private static final int CARD_PILE_SOURCE_LOWER = Settings.CARD_PILE_SOURCE_LOWER.getValue();
  private static final int CARD_PILE_SOURCE_UPPER = Settings.CARD_PILE_SOURCE_UPPER.getValue();

  private GameUtils() {
    throw new UnsupportedOperationException(
        "No %s for you!".formatted(Settings.class.getCanonicalName()));
  }

  public static Deque<Card> generateCardPile() {
    var array = new ArrayList<Card>();

    for (var rank : Rank.values()) {
      for (var suit : Suit.values()) {
        array.add(new Card(rank, suit));
      }
    }

    Collections.shuffle(array);

    return new ArrayDeque<>(array);
  }

  public static int generateNumberOfPlayers(Scanner scanner) {
    while (true) {
      try {
        System.out.print("Enter number of players: ");

        var result = Integer.parseInt(scanner.nextLine());

        if (result < MIN_PLAYERS || result > MAX_PLAYERS) {
          System.out.printf(
              "%nNumber of players must be between %s and %s, please try again!%n%n",
              MIN_PLAYERS, MAX_PLAYERS);

          continue;
        }

        System.out.println();

        return result;
      } catch (NumberFormatException exception) {
        displayInvalidInput();
      }
    }
  }

  public static List<Player> generatePlayers(
      int numberOfPlayers, CardPile cardPile, Scanner scanner) {
    if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
      throw new IllegalArgumentException(
          "Number of players must be between %s and %s inclusively!"
              .formatted(MIN_PLAYERS, MAX_PLAYERS));
    }

    var result = new ArrayList<Player>(5);

    for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
      var cards = new ArrayList<Card>();

      for (var cardIndex = 0; cardIndex < INITIAL_NUMBER_OF_CARDS.getValue(); cardIndex++) {
        cards.add(cardPile.pile().pop());
      }

      System.out.printf("Enter player #%d's name: ", playerIndex + 1);

      result.add(new Player(scanner.nextLine(), cards));
    }

    return result;
  }

  public static int generateDiscardedCardIndex(Player player, Scanner scanner) {
    while (true) {
      try {
        System.out.printf("Card index (1-6) player %s wishes to remove: ", player.name());

        var result = Integer.parseInt(scanner.nextLine()) - 1;

        if (result < CARD_INDEX_LOWER_BOUND || result > CARD_INDEX_UPPER_BOUND) {
          System.out.printf(
              "Card index to discard must be between %s and %s, please try again!%n",
              CARD_INDEX_LOWER_BOUND, CARD_INDEX_UPPER_BOUND);

          continue;
        }

        System.out.println();

        return result;
      } catch (NumberFormatException exception) {
        displayInvalidInput();
      }
    }
  }

  public static PileSource generatePileSource(CardPile discardedPile, Scanner scanner) {
    if (discardedPile.pile().isEmpty()) {
      return PileSource.CARD_PILE;
    }

    while (true) {
      try {
        System.out.printf(
            "Option: Pick up the last discard card (%s) or pick up from pile (%s): ",
            CARD_PILE_SOURCE_LOWER, CARD_PILE_SOURCE_UPPER);

        var index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < CARD_PILE_SOURCE_LOWER || index > CARD_PILE_SOURCE_UPPER) {
          System.out.printf(
              "%nOption must either be between %s and %s, please try again%n",
              CARD_PILE_SOURCE_LOWER + 1, CARD_PILE_SOURCE_UPPER + 1);

          continue;
        }

        System.out.println();

        return PileSource.values()[index];
      } catch (NumberFormatException exception) {
        displayInvalidInput();
      }
    }
  }

  public static boolean isWinner(Player player) {
    var cards = player.cards();

    var firstCardSuit = cards.get(0).suit();

    for (int cardIndex = 1; cardIndex < cards.size(); cardIndex++) {
      if (firstCardSuit != cards.get(cardIndex).suit()) {
        return false;
      }
    }

    return true;
  }

  private static void displayInvalidInput() {
    System.out.println("\nInvalid input, please try again!\n");
  }
}

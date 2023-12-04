package com.enblom.solvers;

import com.enblom.utils.Helpers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4Solver extends Solver {

  private final Map<Integer, List<Card>> cardGroups = new HashMap<>();
  private AtomicInteger currentGroupIndex;

  public Day4Solver(String input) {
    super(input);
  }

  @Override
  public Integer solveFirstPuzzle() {
    int sum = 0;
    for (String line : input.lines().toList()) {
      int cardSum = 0;
      boolean foundFirst = false;
      final var cardAndData = line.split(":", 2);
      final var listStrings = cardAndData[1].split("\\|", 2);
      final var winningNumbers = Helpers.findAllIntegers(listStrings[0]);
      final var yourNumbers = Helpers.findAllIntegers(listStrings[1]);
      for (int candidate : yourNumbers) {
        if (winningNumbers.contains(candidate)) {
          if (foundFirst) {
            cardSum *= 2;
          } else {
            foundFirst = true;
            cardSum = 1;
          }
        }
      }
      sum += cardSum;
    }
    return sum;
  }

  @Override
  public Integer solveSecondPuzzle() {
    populateCards();
    var optionalCardGroup = getNextCardGroup();
    while (optionalCardGroup.isPresent()) {
      var cardGroup = optionalCardGroup.get();
      cardGroup.forEach(
          card -> {
            for (int i = card.number; i < card.number + card.matchingNumberCount; i++) {
              addCopy(i + 1);
            }
          });
      optionalCardGroup = getNextCardGroup();
    }
    return cardGroups.values().stream().map(List::size).reduce(0, Integer::sum);
  }

  private void addCopy(int cardNumber) {
    try {
      final var cardList = cardGroups.get(cardNumber);
      cardList.add(cardList.get(0));
      cardGroups.put(cardNumber, cardList);
    } catch (Exception e) {
      // no-op
    }
  }

  private Optional<List<Card>> getNextCardGroup() {
    try {
      return Optional.of(cardGroups.get(currentGroupIndex.getAndIncrement()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private void populateCards() {
    cardGroups.clear();
    for (String line : input.lines().toList()) {
      final var cardAndData = line.split(":", 2);
      final var listStrings = cardAndData[1].split("\\|", 2);
      final var winningNumbers = Helpers.findAllIntegers(listStrings[0]);
      final var yourNumbers = Helpers.findAllIntegers(listStrings[1]);
      int cardSum = 0;
      for (int candidate : yourNumbers) {
        if (winningNumbers.contains(candidate)) {
          cardSum++;
        }
      }
      int cardNumber = Helpers.grabInteger(cardAndData[0]);
      cardGroups.put(cardNumber, new ArrayList<>(List.of(new Card(cardNumber, cardSum))));
    }
    currentGroupIndex = new AtomicInteger(1);
  }

  private record Card(int number, int matchingNumberCount) {}
}

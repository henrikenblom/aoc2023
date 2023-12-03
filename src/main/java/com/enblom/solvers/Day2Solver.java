package com.enblom.solvers;

import static com.enblom.Utils.extractNonDigits;
import static com.enblom.Utils.grabIntegers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2Solver extends Solver {

  public Day2Solver(String input) {
    super(input);
  }

  @Override
  public Integer solveFirstPuzzle() {
    return parseToGameList(input).stream().filter(Game::isPossible).mapToInt(Game::getId).sum();
  }

  @Override
  public Integer solveSecondPuzzle() {
    return parseToGameList(input).stream().mapToInt(Game::getPowerOfMinimumSet).sum();
  }

  private List<Game> parseToGameList(String input) {
    return input
        .lines()
        .map(
            line -> {
              var gameAndSetStrings = line.split(":", 2);
              var sets =
                  Arrays.stream(gameAndSetStrings[1].replaceAll("\\s", "").split(";"))
                      .map(
                          setString -> {
                            Map<String, Integer> colorCountMap =
                                new HashMap<>(Map.of("red", 0, "green", 0, "blue", 0));
                            Arrays.stream(setString.split(","))
                                .forEach(
                                    entry ->
                                        colorCountMap.put(
                                            extractNonDigits(entry), grabIntegers(entry)));
                            return new GameSet(
                                colorCountMap.get("red"),
                                colorCountMap.get("green"),
                                colorCountMap.get("blue"));
                          })
                      .toList();
              return new Game(gameAndSetStrings[0], sets);
            })
        .toList();
  }

  private record GameSet(int red, int green, int blue) {}

  private record Game(String name, List<GameSet> sets) {
    int getId() {
      return grabIntegers(name);
    }

    boolean isPossible() {
      return sets.stream().noneMatch(set -> set.red > 12 || set.green > 13 || set.blue > 14);
    }

    int getPowerOfMinimumSet() {
      AtomicInteger red = new AtomicInteger();
      AtomicInteger green = new AtomicInteger();
      AtomicInteger blue = new AtomicInteger();
      sets.forEach(
          set -> {
            red.set(Math.max(red.get(), set.red));
            green.set(Math.max(green.get(), set.green));
            blue.set(Math.max(blue.get(), set.blue));
          });
      return red.get() * green.get() * blue.get();
    }
  }
}

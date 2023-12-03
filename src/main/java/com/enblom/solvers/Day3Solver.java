package com.enblom.solvers;

import com.enblom.utils.CharMatrix;
import java.util.concurrent.atomic.AtomicInteger;

public class Day3Solver extends Solver {

  public Day3Solver(String input) {
    super(input);
  }

  @Override
  public Integer solveFirstPuzzle() {
    final var charMatrix = CharMatrix.fromInput(input);
    AtomicInteger sum = new AtomicInteger();
    charMatrix
        .getAllNumbers()
        .forEach(
            number -> {
              final var clip =
                  charMatrix.clip(
                      number.position().x() - 1, number.position().y() - 1, number.length() + 2, 3);
              if (clip.matches("^.*[^\\d.].*$")) {
                sum.addAndGet(number.value());
              }
            });
    return sum.get();
  }

  @Override
  public Integer solveSecondPuzzle() {
    final var charMatrix = CharMatrix.fromInput(input);
    final var asterixPositions = charMatrix.getPositionsFor("*");
    return asterixPositions.stream()
        .map(
            position -> {
              var numbers = charMatrix.findAllNumbersAroundPosition(position, 1);
              return numbers.size() == 2 ? numbers.get(0).value() * numbers.get(1).value() : 0;
            })
        .reduce(0, Integer::sum);
  }
}

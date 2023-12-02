package com.enblom.solvers;

import static com.enblom.Utils.extractDigits;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Day1Solver extends Solver<Integer> {
  @Override
  Integer solve(String input, boolean firstPuzzle) {
    AtomicInteger sum = new AtomicInteger(0);
    input
        .lines()
        .map(line -> !firstPuzzle ? resolveSpelledOutDigits(line) : line)
        .forEach(
            line -> {
              String numerals = extractDigits(line);
              if (!numerals.isEmpty()) {
                sum.addAndGet(
                    Integer.parseInt(
                        String.copyValueOf(
                            new char[] {
                              numerals.charAt(0), numerals.charAt(numerals.length() - 1)
                            })));
              }
            });
    return sum.get();
  }

  private String resolveSpelledOutDigits(String input) {
    String retval = input;
    int replacement = 0;
    for (var digit :
        List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")) {
      retval = retval.replaceAll(digit, digit + replacement++ + digit.charAt(digit.length() - 1));
    }
    return retval;
  }
}

package com.enblom.solvers;

import com.enblom.utils.Helpers;

public class Day6Solver extends Solver {

  public Day6Solver(String input) {
    super(input);
  }

  @Override
  public Long solveFirstPuzzle() {
    final var times = Helpers.findAllIntegers(input.lines().toList().get(0));
    final var records = Helpers.findAllIntegers(input.lines().toList().get(1));
    Long result = null;
    for (int i = 0; i < times.size(); i++) {
      long winningOptions = calculateWinningOptions(times.get(i), records.get(i));
      if (result == null) {
        result = winningOptions;
      } else {
        result *= winningOptions;
      }
    }
    return result;
  }

  @Override
  public Long solveSecondPuzzle() {
    final var time = Helpers.grabLong(input.replaceAll(" ", "").lines().toList().get(0));
    final var record = Helpers.grabLong(input.replaceAll(" ", "").lines().toList().get(1));

    return calculateWinningOptions(time, record);
  }

  private long calculateWinningOptions(long time, long record) {
    int waysToWin = 0;
    for (long charge = 1; charge < time; charge++) {
      long remainingTime = time - charge;
      long distance = remainingTime * charge;
      if (distance > record) {
        waysToWin++;
      }
    }
    return waysToWin;
  }

}

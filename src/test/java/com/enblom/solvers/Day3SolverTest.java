package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day3SolverTest {
  @Test
  void solveFirst() {
    String testInput =
        """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
        """;
    var classUnderTest = new Day3Solver(testInput);

    assertEquals(4361, classUnderTest.solveFirstPuzzle());
  }

  @Test
  void solveSecond() {
    String testInput =
        """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
        """;
    var classUnderTest = new Day3Solver(testInput);

    assertEquals(467835, classUnderTest.solveSecondPuzzle());
  }
}

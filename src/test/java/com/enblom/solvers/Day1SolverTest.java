package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day1SolverTest {

  @Test
  void solveFirst() {
    String testInput =
        """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""";
    var classUnderTest = new Day1Solver(testInput);

    assertEquals(142, classUnderTest.solveFirstPuzzle());
  }

  @Test
  void solveSecond() {
    String testInput =
        """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen""";
    var classUnderTest = new Day1Solver(testInput);

    assertEquals(281, classUnderTest.solveSecondPuzzle());
  }
}

package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day1SolverTest {

  Day1Solver classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new Day1Solver();
  }

  @Test
  void solveFirst() {
    String testInput =
        """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""";
    assertEquals(142, classUnderTest.solve(testInput, true));
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
    assertEquals(281, classUnderTest.solve(testInput, false));
  }
}

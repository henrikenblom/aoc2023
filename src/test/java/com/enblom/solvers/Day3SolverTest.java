package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day3SolverTest {
  @Test
  void solveFirst() {
    String testInput = """

        """;
    var classUnderTest = new Day3Solver(testInput);

    assertEquals(0, classUnderTest.solveFirstPuzzle());
  }

  @Test
  void solveSecond() {
    String testInput = """

        """;
    var classUnderTest = new Day3Solver(testInput);

    assertEquals(0, classUnderTest.solveSecondPuzzle());
  }
}

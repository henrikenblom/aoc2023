package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day3SolverTest {
  Day3Solver classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new Day3Solver();
  }

  @Test
  void solveFirst() {
    String testInput = """

        """;
    assertEquals(0, classUnderTest.solve(testInput, true));
  }

  @Test
  void solveSecond() {
    String testInput = """

        """;
    assertEquals(0, classUnderTest.solve(testInput, false));
  }
}

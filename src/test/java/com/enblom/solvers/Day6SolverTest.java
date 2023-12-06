package com.enblom.solvers;

class Day6SolverTest extends SolverTestBase<Day6Solver> {

  @Override
  void solvesFirst() {
    solvesFirst("""
        Time:      7  15   30
        Distance:  9  40  200
        """, 288);
  }

  @Override
  void solvesSecond() {
    solvesSecond("""
        Time:      71530
        Distance:  940200
        """, 71503);
  }
}

package com.enblom.solvers;

class Day1SolverTest extends SolverTestBase<Day1Solver> {

  @Override
  void solvesFirst() {
    solvesFirst(
        """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""", 142);
  }

  @Override
  void solvesSecond() {
    solvesSecond(
        """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen""",
        281);
  }
}

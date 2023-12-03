package com.enblom.solvers;

class Day3SolverTest extends SolverTestBase<Day3Solver> {

  @Override
  void solvesFirst() {
    solvesFirst(
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
        """,
        4361);
  }

  @Override
  void solvesSecond() {
    solvesSecond(
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
        """,
        467835);
  }
}

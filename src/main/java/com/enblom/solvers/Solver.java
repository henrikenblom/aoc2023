package com.enblom.solvers;

public abstract class Solver {
  final String input;

  public Solver(String input) {
    this.input = input;
  }

  public abstract Integer solveFirstPuzzle();

  public abstract Integer solveSecondPuzzle();
}

package com.enblom.solvers;

public abstract class Solver {
  final String input;

  public Solver(String input) {
    this.input = input;
  }

  public abstract Long solveFirstPuzzle();

  public abstract Long solveSecondPuzzle();
}

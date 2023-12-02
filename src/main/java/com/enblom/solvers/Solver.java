package com.enblom.solvers;

public abstract class Solver<T> {
  final String input;

  public Solver(String input) {
    this.input = input;
  }

  public abstract T solveFirstPuzzle();

  public abstract T solveSecondPuzzle();
}

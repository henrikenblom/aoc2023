package com.enblom.solvers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.ParameterizedType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

abstract class SolverTestBase<T extends Solver> {

  abstract void solvesFirst();

  abstract void solvesSecond();

  @Test
  @DisplayName(" 🎁 Solves the first puzzle")
  void callSolvesFirst() {
    this.solvesFirst();
  }

  @Test
  @DisplayName(" 🎁 Solves the second puzzle")
  void callSolvesSecond() {
    this.solvesSecond();
  }

  void solvesFirst(String input, long expected) {
    assertEquals(expected, getSolver(input).solveFirstPuzzle());
  }

  void solvesSecond(String input, long expected) {
    assertEquals(expected, getSolver(input).solveSecondPuzzle());
  }

  @SuppressWarnings("unchecked")
  T getSolver(String input) {
    try {
      return (T) reflectClassType().getConstructor(String.class).newInstance(input);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings({"rawtypes"})
  private Class reflectClassType() {
    return ((Class)
        ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }
}

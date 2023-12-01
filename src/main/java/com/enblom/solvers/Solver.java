package com.enblom.solvers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class Solver<T> {

  public T solveFromResource(String resource, boolean firstPuzzle)
      throws URISyntaxException, IOException {
    return solve(
        Files.readString(
            Paths.get(
                Objects.requireNonNull(Solver.class.getClassLoader().getResource(resource))
                    .toURI())),
        firstPuzzle);
  }

  abstract T solve(String input, boolean firstPuzzle);
}

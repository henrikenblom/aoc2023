package com.enblom;

import com.enblom.solvers.Day1Solver;
import com.enblom.solvers.Day2Solver;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
  public static void main(String[] args) throws URISyntaxException, IOException {
    solveDay1();
    solveDay2();
  }

  static void solveDay1() throws URISyntaxException, IOException {
    final var day1Solver = new Day1Solver();
    System.out.println(day1Solver.solveFromResource("day1/input.txt", true));
    System.out.println(day1Solver.solveFromResource("day1/input.txt", false));
  }

  static void solveDay2() throws URISyntaxException, IOException {
    final var day2Solver = new Day2Solver();
    System.out.println(day2Solver.solveFromResource("day2/input.txt", true));
    System.out.println(day2Solver.solveFromResource("day2/input.txt", false));
  }
}

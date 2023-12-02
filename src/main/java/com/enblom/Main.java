package com.enblom;

import static com.enblom.Utils.grabIntegers;

import com.enblom.solvers.Day1Solver;
import com.enblom.solvers.Day2Solver;
import com.enblom.solvers.Day3Solver;
import com.enblom.solvers.Solver;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    runAndOutput(new Day1Solver());
    runAndOutput(new Day2Solver());
    runAndOutput(new Day3Solver());
  }

  private static void runAndOutput(Solver<Integer> solver) throws URISyntaxException, IOException {
    var day = extractDay(solver);
    var resourceName = "day%s/input.txt".formatted(day);
    System.out.println();
    output("********* Day " + String.format("%2d", day) + " *********", true);
    output("1st puzzle: " + solver.solveFromResource(resourceName, true), false);
    output("2nd puzzle: " + solver.solveFromResource(resourceName, false), false);
    output("**************************", true);
    System.out.println();
    System.out.println("               " + getBling());
  }

  @SuppressWarnings("rawtypes")
  private static int extractDay(Solver solver) {
    return grabIntegers(solver.getClass().getSimpleName());
  }

  private static void output(String line, boolean divider) {
    if (divider) {
      System.out.println(getBling() + " " + line + " " + getBling());
    } else {
      System.out.println("     " + line);
    }
  }

  private static String getBling() {
    return switch (new Random().nextInt(11)) {
      case 1 -> "🎅🏼";
      case 2 -> "🎄";
      case 3 -> "✨";
      case 4 -> "🎁";
      case 5 -> "🔔";
      case 6 -> "❄️";
      case 7 -> "🤶🏽";
      case 8 -> "🕯️";
      default -> "  ";
    };
  }
}

package com.enblom;

import static com.enblom.Utils.getResourceContent;

import com.enblom.solvers.Solver;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    findSolverClassNames().stream().map(Utils::grabIntegers).sorted().forEach(Main::runAndOutput);
  }

  @SuppressWarnings("unchecked")
  private static void runAndOutput(int day) {
    try {
      var className = "com.enblom.solvers.Day%sSolver".formatted(day);
      var resourceName = "day%s/input.txt".formatted(day);
      var solver =
          (Solver<Integer>)
              Class.forName(className)
                  .getConstructor(String.class)
                  .newInstance(getResourceContent(resourceName));

      System.out.println();
      output("********* Day " + String.format("%2d", day) + " *********", true);
      output("1st puzzle: " + solver.solveFirstPuzzle(), false);
      output("2nd puzzle: " + solver.solveSecondPuzzle(), false);
      output("**************************", true);
      System.out.println();
      System.out.println("               " + getBling());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
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

  public static Set<String> findSolverClassNames() {
    InputStream stream =
        ClassLoader.getSystemClassLoader().getResourceAsStream("com/enblom/solvers");
    assert stream != null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader
        .lines()
        .filter(line -> line.endsWith("Solver.class") && line.startsWith("Day"))
        .collect(Collectors.toSet());
  }
}

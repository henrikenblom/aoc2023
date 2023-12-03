package com.enblom;

import static com.enblom.utils.Utils.getInput;

import com.enblom.solvers.Solver;
import com.enblom.utils.Utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    sprinkleTinsel();
    findSolverClassNames().stream().map(Utils::grabIntegers).sorted().forEach(Main::runAndOutput);
  }

  private static void runAndOutput(int day) {
    try {
      var className = "com.enblom.solvers.Day%sSolver".formatted(day);
      var solver =
          (Solver) Class.forName(className).getConstructor(String.class).newInstance(getInput(day));

      output("********* Day " + "%2d".formatted(day) + " *********");
      output("  1st puzzle: " + solver.solveFirstPuzzle());
      output("  2nd puzzle: " + solver.solveSecondPuzzle());
      output("**************************");
      sprinkleTinsel();

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void output(String text) {
    System.out.println("   " + text);
  }

  private static void sprinkleTinsel() {
    System.out.println("\n               " + getTinsel() + "\n");
  }

  private static String getTinsel() {
    return switch (new Random().nextInt(8)) {
      case 1 -> "🎅🏼";
      case 2 -> "🎄";
      case 3 -> "✨";
      case 4 -> "🎁";
      case 5 -> "🔔";
      case 6 -> "❄️";
      case 7 -> "🤶🏽";
      default -> "🕯️";
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

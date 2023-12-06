package com.enblom;

import com.enblom.solvers.Solver;
import com.enblom.utils.Helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    var optionalDay =
        findSolverClassNames().stream().map(Helpers::grabInteger).max(Comparator.naturalOrder());
    if (optionalDay.isPresent()) {
      sprinkleTinsel();
      runAndOutput(optionalDay.get());
    }
  }

  private static void runAndOutput(int day) {
    try {
      var className = "com.enblom.solvers.Day%sSolver".formatted(day);
      var solver =
          (Solver) Class.forName(className).getConstructor(String.class).newInstance(getInput(day));

      output("**************** Day " + "%2d".formatted(day) + " ****************");
      output("  1st puzzle: " + time(solver::solveFirstPuzzle));
      output("  2nd puzzle: " + time(solver::solveSecondPuzzle));
      output("****************************************");
      sprinkleTinsel();

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private static String time(Supplier<Long> supplier) {
    final var start = System.currentTimeMillis();
    return "%s - Took: %sms".formatted(supplier.get(), System.currentTimeMillis() - start);
  }

  private static void output(String text) {
    System.out.println("   " + text);
  }

  private static void sprinkleTinsel() {
    System.out.println("\n                      " + getTinsel() + "\n");
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

  private static Set<String> findSolverClassNames() {
    InputStream stream =
        ClassLoader.getSystemClassLoader().getResourceAsStream("com/enblom/solvers");
    assert stream != null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader
        .lines()
        .filter(line -> line.endsWith("Solver.class") && line.startsWith("Day"))
        .collect(Collectors.toSet());
  }

  private static String getInput(int day) throws IOException {
    var inputPath =
        Paths.get(System.getProperty("user.dir"), "input", "day%s/input.txt".formatted(day));
    if (!Files.exists(inputPath)) {
      downloadInput(day, inputPath);
    }
    return Files.readString(inputPath);
  }

  private static void downloadInput(int day, Path destination) throws IOException {
    HttpURLConnection connection =
        (HttpURLConnection)
            new URL("https://adventofcode.com/2023/day/%s/input".formatted(day)).openConnection();
    connection.setRequestMethod("GET");
    connection.addRequestProperty("Cookie", "session=" + getSessionCookie());
    Files.copy(connection.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
  }

  private static String getSessionCookie() {
    var sessionCookiePath = Paths.get(System.getProperty("user.dir"), ".session_cookie");
    try {
      return Files.readAllLines(sessionCookiePath).get(0);
    } catch (Exception e) {
      throw new RuntimeException(
          "Could not read the session cookie. Save the AoC session cookie to %s, after logging in"
              .formatted(sessionCookiePath));
    }
  }
}

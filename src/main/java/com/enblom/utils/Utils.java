package com.enblom.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static String extractDigits(String input) {
    return input.replaceAll("\\D", "");
  }

  public static String extractNonDigits(String input) {
    return input.replaceAll("\\d", "");
  }

  public static int grabIntegers(String input) {
    return Integer.parseInt(extractDigits(input));
  }

  public static String getInput(int day) throws IOException {
    final var inputPath =
        Paths.get(System.getProperty("user.dir"), "input", "day%s/input.txt".formatted(day));
    if (!Files.exists(inputPath)) {
      downloadInput(day, inputPath);
    }
    return Files.readString(inputPath);
  }

  public static void downloadInput(int day, Path destination) throws IOException {
    HttpURLConnection connection =
        (HttpURLConnection)
            new URL("https://adventofcode.com/2023/day/%s/input".formatted(day)).openConnection();
    connection.setRequestMethod("GET");
    connection.addRequestProperty("Cookie", "session=" + getSessionCookie());
    Files.copy(connection.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
  }

  private static String getSessionCookie() {
    final var sessionCookePath = Paths.get(System.getProperty("user.dir"), ".session_cookie");
    try {
      return Files.readAllLines(sessionCookePath).get(0);
    } catch (Exception e) {
      throw new RuntimeException(
          "Could not read the session cookie. Save the AoC session cookie to %s, after logging in"
              .formatted(sessionCookePath));
    }
  }

  public static List<Integer> findAllIntegers(String input) {
    List<Integer> integers = new ArrayList<>();
    boolean isMatching = false;
    int length = 0;
    var characters = input.chars().mapToObj(c -> (char) c).toList();
    for (int x = 0; x < characters.size(); x++) {
      String letter = String.valueOf(characters.get(x));
      if (letter.matches("\\d")) {
        length++;
        isMatching = true;
      } else {
        if (isMatching) {
          int start = x - length;
          StringBuilder numberString = new StringBuilder();
          for (int v = start; v < start + length; v++) {
            numberString.append(characters.get(v));
          }
          integers.add(grabIntegers(numberString.toString()));
        }
        isMatching = false;
        length = 0;
      }
    }
    return integers;
  }
}

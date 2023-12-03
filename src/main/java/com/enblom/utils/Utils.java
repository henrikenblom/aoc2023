package com.enblom.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  public static String getResourceContent(String resourceName)
      throws URISyntaxException, IOException {
    return Files.readString(
        Paths.get(
            Objects.requireNonNull(Utils.class.getClassLoader().getResource(resourceName))
                .toURI()));
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

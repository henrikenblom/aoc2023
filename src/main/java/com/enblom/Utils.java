package com.enblom;

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
}

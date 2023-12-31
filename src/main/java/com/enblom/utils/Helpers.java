package com.enblom.utils;

import java.util.ArrayList;
import java.util.List;

public class Helpers {

  public static String extractDigits(String input) {
    return input.replaceAll("\\D", "");
  }

  public static String extractNonDigits(String input) {
    return input.replaceAll("\\d", "");
  }

  public static int grabInteger(String input) {
    return Integer.parseInt(extractDigits(input));
  }

  public static long grabLong(String input) {
    return Long.parseLong(extractDigits(input));
  }

  public static List<Integer> findAllIntegers(String input) {
    List<Integer> integers = new ArrayList<>();
    boolean isMatching = false;
    int length = 0;
    var characters = (input + " ").chars().mapToObj(c -> (char) c).toList();
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
          integers.add(grabInteger(numberString.toString()));
        }
        isMatching = false;
        length = 0;
      }
    }
    return integers;
  }

  public static List<Long> findAllLongs(String input) {
    List<Long> longs = new ArrayList<>();
    boolean isMatching = false;
    int length = 0;
    var characters = (input + " ").chars().mapToObj(c -> (char) c).toList();
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
          longs.add(grabLong(numberString.toString()));
        }
        isMatching = false;
        length = 0;
      }
    }
    return longs;
  }
}

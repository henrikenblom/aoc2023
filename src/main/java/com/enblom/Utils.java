package com.enblom;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  public static String getResourceContent(String resourceName) throws URISyntaxException, IOException {
    return Files.readString(
        Paths.get(
            Objects.requireNonNull(Utils.class.getClassLoader().getResource(resourceName))
                .toURI()));
  }
}

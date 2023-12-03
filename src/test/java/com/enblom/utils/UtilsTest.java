package com.enblom.utils;

import org.junit.jupiter.api.Test;

class UtilsTest {

  @Test
  void findAllIntegers() {
    final var allIntegers = Utils.findAllIntegers("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
        """);
    allIntegers.forEach(System.out::println);
  }
}

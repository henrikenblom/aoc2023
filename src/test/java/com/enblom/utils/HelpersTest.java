package com.enblom.utils;

import org.junit.jupiter.api.Test;

class HelpersTest {

  @Test
  void findAllIntegers() {
    final var allIntegers = Helpers.findAllIntegers("""
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

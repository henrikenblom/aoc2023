package com.enblom.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.enblom.utils.CharMatrix.Number;
import com.enblom.utils.CharMatrix.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharMatrixTest {

  CharMatrix charMatrix;

  @BeforeEach
  void setUp() {
    charMatrix =
        CharMatrix.fromInput(
            """
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
  }

  @Test
  void letterAt() {
    assertEquals("7", charMatrix.letterAt(2, 4));
  }

  @Test
  void integerAt() {
    assertEquals(1, charMatrix.integerAt(1, 4));
  }

  @Test
  void clip() {
    final var clipped = charMatrix.clip(2, 1, 4, 5);
    assertEquals("""
CharMatrix{
.*..
35..
....
7*..
...+
}""", clipped.toString());
    assertEquals(4, clipped.width());
    assertEquals(5, clipped.height());
  }

  @Test
  void width() {
    assertEquals(11, charMatrix.width());
  }

  @Test
  void height() {
    assertEquals(10, charMatrix.height());
  }

  @Test
  void contains() {
    assertTrue(charMatrix.contains("*"));
  }

  @Test
  void getPositionsForCharacter() {
    final var asterixPositions = charMatrix.getPositionsFor("*");
    assertEquals(new Position(3, 1), asterixPositions.get(0));
    assertEquals(new Position(3, 4), asterixPositions.get(1));
    assertEquals(new Position(5, 8), asterixPositions.get(2));
  }

  @Test
  void findAllNumbers() {
    final var allNumbers = charMatrix.findAllNumbers();
    allNumbers.forEach(System.out::println);
  }

  @Test
  void findAllNumbersWithinClip() {
    final var allNumbersWithinClip = charMatrix.findAllNumbersWithinClip(2, 0, 3, 3);
    allNumbersWithinClip.forEach(System.out::println);
  }

  @Test
  void findAllNumbersAroundPosition() {
    final var allNumbersAroundPosition = charMatrix.findAllNumbersAroundPosition(3, 1, 1);
    allNumbersAroundPosition.forEach(System.out::println);
  }
}

package com.enblom.utils;

import static com.enblom.utils.Helpers.grabIntegers;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharMatrix {
  private final List<String> rows;
  private final List<List<Character>> matrix;
  private final List<Number> allNumbers;

  private CharMatrix(String input, boolean relaxed) {
    this.rows = input.lines().toList();
    this.matrix =
        input
            .lines()
            .map(line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
            .toList();
    if (!relaxed) {
      assertValid();
    }
    this.allNumbers = parseOutAllNumbers();
  }

  private CharMatrix(List<List<Character>> matrix) {
    this.matrix = matrix;
    this.allNumbers = parseOutAllNumbers();
    this.rows =
        matrix.stream()
            .map(row -> row.stream().map(String::valueOf).collect(Collectors.joining()))
            .toList();
  }

  public static CharMatrix fromInput(String input) {
    return fromInput(input, false);
  }

  public static CharMatrix fromInput(String input, boolean relaxed) {
    return new CharMatrix(input, relaxed);
  }

  private void assertValid() {
    var messageTemplate = "Input is not eligible for a matrix. %s";
    if (matrix.isEmpty()) {
      throw new RuntimeException(messageTemplate.formatted("Input was empty."));
    }
    int firstLineLength = matrix.get(0).size();
    if (matrix.stream().map(List::size).anyMatch(length -> length != firstLineLength)) {
      throw new RuntimeException(messageTemplate.formatted("Line lengths varies."));
    }
  }

  public List<List<Character>> getRows() {
    return matrix;
  }

  public List<Character> getRow(int y) {
    return matrix.get(y);
  }

  public int width() {
    return matrix.get(0).size();
  }

  public int height() {
    return matrix.size();
  }

  public String letterAt(int x, int y) {
    return letterAt(new Position(x, y));
  }

  public Integer integerAt(int x, int y) {
    return integerAt(new Position(x, y));
  }

  public String letterAt(Position position) {
    return String.valueOf(matrix.get(position.y()).get(position.x()));
  }

  public Integer integerAt(Position position) {
    return Integer.parseInt(letterAt(position));
  }

  public CharMatrix clip(int x, int y, int width, int height) {
    return clip(new Position(x, y), width, height);
  }

  public CharMatrix clip(Position position, int width, int height) {
    int startX = max(0, position.x());
    int startY = max(0, position.y());
    int endX = min(matrix.get(0).size() - 1, position.x + width);
    int endY = min(matrix.size() - 1, position.y + height);
    return new CharMatrix(
        matrix.subList(startY, endY).stream().map(list -> list.subList(startX, endX)).toList());
  }

  public boolean matches(String regex) {
    return rows.stream().anyMatch(row -> row.matches(regex));
  }

  public boolean contains(String letter) {
    return contains(toChar(letter));
  }

  public boolean contains(char c) {
    return matrix.stream().anyMatch(list -> list.contains(c));
  }

  public List<Position> getPositionsFor(String letter) {
    return getPositionsFor(toChar(letter));
  }

  public List<Position> getPositionsFor(char c) {
    List<Position> positions = new ArrayList<>();
    IntStream.range(0, height())
        .forEach(
            y -> {
              var row = getRow(y);
              IntStream.range(0, row.size())
                  .forEach(
                      x -> {
                        if (row.get(x).equals(c)) {
                          positions.add(new Position(x, y));
                        }
                      });
            });
    return positions;
  }

  public List<Number> getAllNumbers() {
    return allNumbers;
  }

  public List<Number> findAllNumbersWithinSquare(Position position, int size) {
    return findAllNumbersWithinBox(position, size, size);
  }

  public List<Number> findAllNumbersWithinBox(int x, int y, int width, int height) {
    return findAllNumbersWithinBox(new Position(x, y), width, height);
  }

  public List<Number> findAllNumbersWithinBox(Position position, int width, int height) {
    List<Number> matchedNumbers = new ArrayList<>();
    var allNumbers = getAllNumbers();
    IntStream.range(position.y, position.y + height)
        .forEach(
            y ->
                matchedNumbers.addAll(
                    allNumbers.stream()
                        .filter(
                            number ->
                                number.position.y == y
                                    && number.rightmostX() >= position.x
                                    && number.leftmostX() <= position.x + width - 1)
                        .toList()));
    return matchedNumbers;
  }

  public List<Number> findAllNumbersAroundPosition(int x, int y, int margin) {
    return findAllNumbersAroundPosition(new Position(x, y), margin);
  }

  public List<Number> findAllNumbersAroundPosition(Position position, int margin) {
    if (margin < 1) {
      throw new IllegalArgumentException(
          "Margin needs to be at least 1. (Perhaps getNumberAtPosition does what you want?)");
    }
    return findAllNumbersWithinSquare(
        new Position(position.x - margin, position.y - margin), margin * 2 + 1);
  }

  public Optional<Number> getNumberAtPosition(int x, int y) {
    return getNumberAtPosition(new Position(x, y));
  }

  public Optional<Number> getNumberAtPosition(Position position) {
    return findAllNumbersWithinSquare(position, 1).stream().findFirst();
  }

  @Override
  public String toString() {
    var builder = new StringBuilder("CharMatrix{\n");
    matrix.forEach(
        line -> {
          line.forEach(builder::append);
          builder.append("\n");
        });
    builder.append("}");
    return builder.toString();
  }

  private char toChar(String letter) {
    if (letter.length() != 1) {
      throw new IllegalArgumentException("Only strings of length 1 is supported");
    }
    return letter.charAt(0);
  }

  private List<Number> parseOutAllNumbers() {
    List<Number> candidates = new ArrayList<>();
    int y = 0;
    for (var row : getRows()) {
      boolean isMatching = false;
      int length = 0;
      for (int x = 0; x < row.size(); x++) {
        String letter = String.valueOf(row.get(x));
        if (letter.matches("\\d")) {
          length++;
          isMatching = true;
        } else {
          if (isMatching) {
            int start = x - length;
            StringBuilder numberString = new StringBuilder();
            for (int v = start; v < start + length; v++) {
              numberString.append(row.get(v));
            }
            candidates.add(
                new Number(new Position(start, y), length, grabIntegers(numberString.toString())));
          }
          isMatching = false;
          length = 0;
        }
      }
      y++;
    }
    return candidates;
  }

  public record Position(int x, int y) {}

  public record Number(Position position, int length, int value) {

    public int y() {
      return position.y();
    }

    public int leftmostX() {
      return position.x;
    }

    public int rightmostX() {
      return position.x + length - 1;
    }

    @Override
    public String toString() {
      return "Number{"
          + "position="
          + position
          + ", leftmostX="
          + leftmostX()
          + ", rightmostX="
          + rightmostX()
          + ", length="
          + length
          + ", value="
          + value
          + '}';
    }
  }
}

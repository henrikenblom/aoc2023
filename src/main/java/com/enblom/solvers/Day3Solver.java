package com.enblom.solvers;

import com.enblom.utils.CharMatrix;
import com.enblom.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class Day3Solver extends Solver {

  public Day3Solver(String input) {
    super(input);
  }

  @Override
  public Integer solveFirstPuzzle() {
    int sum = 0;
    var matrix = CharMatrix.fromInput(input);
    var candidates = getCandidates(matrix);

    for (Candidate candidate : candidates) {
      int x = candidate.x;
      int y = candidate.y;
      int length = candidate.length;
      if (isHorizontallyAdjacent(x, y, length, matrix)
          || isVerticallyAdjacent(x, y, length, matrix)
          || isDiagonallyAdjacent(x, y, length, matrix)) {
        sum += candidate.value;
      }
    }
    return sum;
  }

  @Override
  public Integer solveSecondPuzzle() {
    final var charMatrix = CharMatrix.fromInput(input);
    final var asterixPositions = charMatrix.getPositionsFor("*");
    return asterixPositions.stream()
        .map(
            position -> {
              var numbers = charMatrix.findAllNumbersAroundPosition(position, 1);
              return numbers.size() == 2 ? numbers.get(0).value() * numbers.get(1).value() : 0;
            })
        .reduce(0, Integer::sum);
  }

  private List<Candidate> getCandidates(CharMatrix matrix) {
    List<Candidate> candidates = new ArrayList<>();
    int y = 0;
    for (var row : matrix.getRows()) {
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
                new Candidate(start, y, length, Utils.grabIntegers(numberString.toString())));
          }
          isMatching = false;
          length = 0;
        }
      }
      y++;
    }
    return candidates;
  }

  private boolean isDiagonallyAdjacent(int x, int y, int length, CharMatrix matrix) {
    if (y > 0) {
      if (isHorizontallyAdjacent(x, y - 1, length, matrix)) {
        return true;
      }
    }
    if (y < matrix.height() - 1) {
      return isHorizontallyAdjacent(x, y + 1, length, matrix);
    }
    return false;
  }

  private boolean isVerticallyAdjacent(int x, int y, int length, CharMatrix matrix) {
    if (y > 0) {
      List<Character> rowOver = matrix.getRow(y - 1);
      for (int i = x; i <= x + length; i++) {
        if (!String.valueOf(rowOver.get(i)).equals(".")) {
          return true;
        }
      }
    }
    if (y < matrix.height() - 1) {
      List<Character> rowBelow = matrix.getRow(y + 1);
      for (int i = x; i < x + length; i++) {
        final var s = String.valueOf(rowBelow.get(i));
        if (!s.equals(".")) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isHorizontallyAdjacent(int x, int y, int length, CharMatrix matrix) {
    List<Character> row = matrix.getRow(y);
    int right = x + length;
    if (right < row.size()) {
      if (!String.valueOf(row.get(right)).equals(".")) {
        return true;
      }
    }
    if (x > 0) {
      return !String.valueOf(row.get(x - 1)).equals(".");
    }
    return false;
  }

  private record Candidate(int x, int y, int length, int value) {}
}

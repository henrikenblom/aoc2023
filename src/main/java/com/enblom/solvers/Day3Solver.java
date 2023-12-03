package com.enblom.solvers;

import com.enblom.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day3Solver extends Solver<Integer> {

  public Day3Solver(String input) {
    super(input);
  }

  @Override
  public Integer solveFirstPuzzle() {
    int sum = 0;
    var matrix = getMatrix();
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
    AtomicInteger sum = new AtomicInteger();
    var matrix = getMatrix();
    var candidates = getCandidates(matrix);
    var multipliers = getMultipliers(matrix);

    List<GearSet> gearSets = new ArrayList<>();

    for (Multiplier multiplier : multipliers) {
      List<Candidate> gearMembers = new ArrayList<>();
      for (int i = -1; i < 2; i++) {
        for (Candidate candidate : candidates) {
          if (candidate.y == (multiplier.y + i)
              && isHorizontallyAdjacent(
                  candidate.x, candidate.x + candidate.length - 1, multiplier.x)) {
            gearMembers.add(candidate);
          }
        }
      }
      if (gearMembers.size() == 2) {
        final var gearSet = new GearSet(gearMembers.get(0), gearMembers.get(1));
        gearSets.add(gearSet);
      }
    }

    gearSets.forEach(gearSet -> sum.addAndGet(gearSet.getRatio()));

    return sum.get();
  }

  private boolean isHorizontallyAdjacent(int leftX, int rightX, int comparedX) {
    return comparedX >= leftX - 1 && comparedX <= rightX + 1;
  }

  private List<Multiplier> getMultipliers(Map<Integer, List<Character>> matrix) {
    List<Multiplier> multipliers = new ArrayList<>();
    for (int y = 0; y < matrix.size(); y++) {
      final var row = matrix.get(y);
      for (int x = 0; x < row.size() - 1; x++) {
        if (String.valueOf(row.get(x)).equals("*")) {
          multipliers.add(new Multiplier(x, y));
        }
      }
    }
    return multipliers;
  }

  private List<Candidate> getCandidates(Map<Integer, List<Character>> matrix) {
    List<Candidate> candidates = new ArrayList<>();
    for (var row : matrix.keySet()) {
      boolean isMatching = false;
      int length = 0;
      final var characters = matrix.get(row);
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
            candidates.add(
                new Candidate(start, row, length, Utils.grabIntegers(numberString.toString())));
          }
          isMatching = false;
          length = 0;
        }
      }
    }
    return candidates;
  }

  private Map<Integer, List<Character>> getMatrix() {
    Map<Integer, List<Character>> matrix = new HashMap<>();
    final var lines = input.split("\n");
    for (int i = 0; i < lines.length; i++) {
      matrix.put(i, (lines[i] + ".").chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    }
    return matrix;
  }

  private boolean isDiagonallyAdjacent(
      int x, int y, int length, Map<Integer, List<Character>> matrix) {
    if (y > 0) {
      if (isHorizontallyAdjacent(x, y - 1, length, matrix)) {
        return true;
      }
    }
    if (y < matrix.size() - 1) {
      return isHorizontallyAdjacent(x, y + 1, length, matrix);
    }
    return false;
  }

  private boolean isVerticallyAdjacent(
      int x, int y, int length, Map<Integer, List<Character>> matrix) {
    if (y > 0) {
      List<Character> rowOver = matrix.get(y - 1);
      for (int i = x; i <= x + length; i++) {
        if (!String.valueOf(rowOver.get(i)).equals(".")) {
          return true;
        }
      }
    }
    if (y < matrix.size() - 1) {
      List<Character> rowBelow = matrix.get(y + 1);
      for (int i = x; i < x + length; i++) {
        final var s = String.valueOf(rowBelow.get(i));
        if (!s.equals(".")) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isHorizontallyAdjacent(
      int x, int y, int length, Map<Integer, List<Character>> matrix) {
    List<Character> row = matrix.get(y);
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

  private record Multiplier(int x, int y) {}

  private record GearSet(Candidate candidateOne, Candidate candidateTwo) {
    private int getRatio() {
      return candidateOne.value * candidateTwo.value;
    }
  }
}

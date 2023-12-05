package com.enblom.solvers;

import com.enblom.utils.Helpers;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.LongStream;

public class Day5Solver extends Solver {

  List<Long> seeds;
  List<SeedRange> seedRanges = new ArrayList<>();
  List<List<RangeMap>> rangeMaps = new ArrayList<>();

  public Day5Solver(String input) {
    super(input);
    populateSeeds();
    populateMaps();
  }

  @Override
  public Long solveFirstPuzzle() {
    return seeds.stream().map(this::lookupDestination).min(Comparator.naturalOrder()).get();
  }

  @Override
  public Long solveSecondPuzzle() {
    for (int i = 0; i < seeds.size(); i += 2) {
      seedRanges.add(new SeedRange(seeds.get(i), seeds.get(i + 1)));
    }
    List<CompletableFuture<Long>> tasks = new ArrayList<>();
    for (var seedRange : seedRanges) {
      tasks.add(
          CompletableFuture.supplyAsync(
              () ->
                  LongStream.range(seedRange.start, seedRange.end + 1)
                      .map(this::lookupDestination)
                      .min()
                      .getAsLong()));
    }

    return tasks.stream()
        .map(
            task -> {
              try {
                return task.get();
              } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
              }
            })
        .min(Comparator.naturalOrder())
        .get();
  }
  
  private long lookupDestination(long seed) {
    long currentSource = seed;
    for (List<RangeMap> map : rangeMaps) {
      for (RangeMap rangeMap : map) {
        var destinationOptional = rangeMap.getDestinationForSource(currentSource);
        if (destinationOptional.isPresent()) {
          currentSource = destinationOptional.get();
          break;
        }
      }
    }
    return currentSource;
  }

  private void populateSeeds() {
    seeds = Helpers.findAllLongs(input.lines().findFirst().get());
  }

  private void populateMaps() {
    List<RangeMap> rangeMapList = new ArrayList<>();
    for (var line : input.lines().toList()) {
      if (line.contains("map")) {
        if (!rangeMapList.isEmpty()) {
          rangeMaps.add(rangeMapList);
          rangeMapList = new ArrayList<>();
        }
      } else if (!line.contains("seeds")) {
        final var longs = Helpers.findAllLongs(line);
        if (longs.size() == 3) {
          rangeMapList.add(new RangeMap(longs.get(0), longs.get(1), longs.get(2)));
        }
      }
    }
    rangeMaps.add(rangeMapList);
  }

  private static class SeedRange {
    final long start;
    final long end;

    public SeedRange(long start, long length) {
      this.start = start;
      this.end = start + length - 1;
    }

    public long getStart() {
      return start;
    }

    public long getEnd() {
      return end;
    }

    @Override
    public String toString() {
      return "SeedRange{" + "start=" + start + ", end=" + end + '}';
    }
  }

  private static class RangeMap {
    final long destRangeStart;
    final long sourceRangeStart;
    final long destRangeEnd;
    final long sourceRangeEnd;
    final long length;

    public RangeMap(long destRangeStart, long sourceRangeStart, long length) {
      this.destRangeStart = destRangeStart;
      this.sourceRangeStart = sourceRangeStart;
      this.length = length;
      this.destRangeEnd = destRangeStart + length - 1;
      this.sourceRangeEnd = sourceRangeStart + length - 1;
    }

    Optional<Long> getDestinationForSource(long source) {
      if (source >= sourceRangeStart && source <= sourceRangeEnd) {
        return Optional.of(destRangeStart + (source - sourceRangeStart));
      } else {
        return Optional.empty();
      }
    }

    @Override
    public String toString() {
      return "RangeMap{"
          + "destRangeStart="
          + destRangeStart
          + ", sourceRangeStart="
          + sourceRangeStart
          + ", destRangeEnd="
          + destRangeEnd
          + ", sourceRangeEnd="
          + sourceRangeEnd
          + ", length="
          + length
          + '}';
    }
  }
}

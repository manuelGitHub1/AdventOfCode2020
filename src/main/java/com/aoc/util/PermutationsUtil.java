package com.aoc.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jetbrains.annotations.NotNull;


public class PermutationsUtil {


   @NotNull
   public Set<SortedSet<Integer>> getPermutations( List<Integer> integers ) {
      final Set<SortedSet<Integer>> permutations = new HashSet<>();
      fillTheFirstTier(permutations, integers);

      final int size = integers.size();
      // Go through all sizes
      for ( int i = 2; i <= size; i++ ) {
         for ( Integer integer : integers ) {
            SortedSet<Integer> newPermutation = new TreeSet<>();
            newPermutation.add(integer);
            fillUp(permutations, i, newPermutation, getNumberQueue(integers), integer);
         }
      }
      return permutations;
   }

   private void fillTheFirstTier( Set<SortedSet<Integer>> permutations, List<Integer> integers ) {
      for ( Integer integer : integers ) {
         SortedSet<Integer> sortedSet = new TreeSet<>();
         sortedSet.add(integer);
         permutations.add(sortedSet);
      }
   }

   @NotNull
   private void fillUp( Set<SortedSet<Integer>> permutations, int maxSize, SortedSet<Integer> newPermutations, Queue<Integer> numberQueue, Integer base ) {
      final Integer current = numberQueue.poll();
      if ( current == null ) {
         return;
      }

      if ( !newPermutations.contains(current) ) {
         newPermutations.add(current);
      }

      if ( newPermutations.size() == maxSize ) {
         permutations.add(newPermutations);
         newPermutations = new TreeSet<>();
         newPermutations.add(base);
      }
      fillUp(permutations, maxSize, newPermutations, numberQueue, base);
   }

   @NotNull
   private Queue<Integer> getNumberQueue( List<Integer> integers ) {
      Queue<Integer> numberQueue = new LinkedList<>();
      numberQueue.addAll(integers);
      return numberQueue;
   }

}

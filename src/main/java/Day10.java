import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.aoc.util.PermutationsUtil;


public class Day10 {

   private static final Logger _logger = Logger.getLogger(Day10.class.getName());

   public static void main( String[] args ) {
      part1();

      final List<Integer> sortedInput = Collections.unmodifiableList(getPreparedInput());

      final List<Integer> removableNumbers = findRemovableNumbers(sortedInput);
      System.out.println("removable numbers found: " + removableNumbers.size() + " = " + removableNumbers);

      final Set<SortedSet<Integer>> permutations = new PermutationsUtil().getPermutations(removableNumbers);

      System.out.println(permutations);

      System.out.println(permutations.size() + 1);
      //
      //      // alternative
      //      final List<Integer> removableNumbers = findRemovableNumbers(sortedInput);

      //
      //      final HashMap<Integer, Combination> knownCombinations = new HashMap<>();
      //      knownCombinations.put(sortedInput.hashCode(), new Combination(sortedInput, "[]"));
      //
      //      for ( Integer currentNumber : removableNumbers ) {
      //         final List<Integer> removedNumbers = new ArrayList<>();
      //         final List<Integer> filteredNumbers = sortedInput.stream().filter(n -> !n.equals(currentNumber)).collect(Collectors.toList());
      //         if ( !validateNumbers(filteredNumbers) ) {
      //            System.err.println("Not valid: " + filteredNumbers);
      //            continue;
      //         }
      //
      //         final int hashCode = filteredNumbers.hashCode();
      //         if ( knownCombinations.containsKey(hashCode) ) {
      //            System.out.println("This combination is already known: " + filteredNumbers);
      //            continue;
      //         }
      //
      //         removedNumbers.add(currentNumber);
      //         knownCombinations.put(hashCode, new Combination(filteredNumbers, removedNumbers.toString()));
      //         extracted(removableNumbers, knownCombinations, removedNumbers, filteredNumbers);
      //      }
      //
      //      knownCombinations.entrySet().forEach(System.out::println);
      //
      //      System.out.println("Part2: Distinct arrangements= " + knownCombinations.size());
   }

   @NotNull
   private static List<Integer> copyList( List<Integer> src ) {
      List<Integer> consumableList = new ArrayList<>(src);
      Collections.copy(consumableList, src);
      return consumableList;
   }

   private static void extracted( List<Integer> removableNumbers, HashMap<Integer, Combination> hashes, List<Integer> removedNumbers,
         List<Integer> incomingNumbers ) {
      List<Integer> workingList = copyList(incomingNumbers);

      for ( int i = 0; i < removableNumbers.size(); i++ ) {

         final Integer currentNumber = removableNumbers.get(i);
         if ( removedNumbers.contains(currentNumber) ) {
            continue;
         }

         final List<Integer> tempList = copyList(workingList);
         workingList.remove(currentNumber);
         if ( validateNumbers(workingList) ) {
            removedNumbers.add(currentNumber);
            final int hashCode = workingList.hashCode();
            if ( !hashes.containsKey(hashCode) ) {
               hashes.put(hashCode, new Combination(copyList(workingList), removedNumbers.toString()));
            } else {
               System.out.println(
                     "This combination is already known: " + workingList + ", removed numbers " + removedNumbers.toString() + ", hashCode is " + hashCode
                           + " which is already know for " + hashes.get(hashCode));
               workingList = tempList;
            }
         } else {
            workingList = tempList;
         }
      }
   }

   private static List<Integer> findRemovableNumbers( List<Integer> integers ) {
      List<Integer> possibleToRemove = new ArrayList<>();
      int previous = 0;
      for ( int i = 0; i < integers.size() - 1; i++ ) {
         final Integer next = integers.get(i + 1);
         final Integer current = integers.get(i);
         if ( 3 >= next - previous ) {
            possibleToRemove.add(current);
         }
         previous = current;
      }
      return possibleToRemove.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
   }

   @NotNull
   private static List<Integer> getPreparedInput() {
      final List<String> strings = Util.getAoCInput(Day10.class.getName());
      final List<Integer> list = strings.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
      list.add(0, 0);
      list.add(list.size(), list.get(list.size() - 1) + 1);
      return list;
   }

   private static void part1() {
      final List<Integer> integers = getPreparedInput();
      HashMap<Integer, Integer> counter = new HashMap<>();
      int lastValue = 0;
      for ( Integer currentValue : integers ) {
         int difference = currentValue - lastValue;
         counter.merge(difference, 1, Integer::sum);
         lastValue = currentValue;

      }
      counter.merge(3, 1, Integer::sum); // because of reasons
      System.out.println(counter);
      final int result = counter.get(1) * counter.get(3);
      System.out.println("Part1 " + result);
   }

   private static boolean validateNumbers( List<Integer> integers ) {
      int lastValue = 0;
      for ( Integer currentValue : integers ) {
         int difference = currentValue - lastValue;
         int maxDifference = 3;
         if ( difference > maxDifference ) {
            _logger.info("Difference too big: " + difference + ". Current value: " + currentValue + ", last value: " + lastValue);
            return false;
         }
         lastValue = currentValue;
      }
      return true;
   }

   private static class Combination {

      private final List<Integer> _combination;
      //      private List<Integer> _removedKeys;
      private final String        _removedKeys;

      public Combination( List<Integer> combination, String removedKeys ) {
         _combination = combination;
         _removedKeys = removedKeys;
      }

      @Override
      public String toString() {
         return "Combination{" + "_combination=" + _combination + ", _removedKeys='" + _removedKeys + '\'' + '}';
      }
   }

}

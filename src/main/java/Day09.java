import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Day09 {

   private static final Logger _logger = Logger.getLogger(Day09.class.getName());

   public static void main( String[] args ) {
      final List<String> strings = Util.getAoCInput(Day09.class.getName());
      final Queue<Long> preambleQueue = new LinkedList<>();
      int preambleLength = 25;
      strings.subList(0, preambleLength).stream().map(Long::parseLong).forEach(preambleQueue::add);
      final List<Long> values = strings.subList(preambleLength, strings.size()).stream().map(Long::parseLong).collect(Collectors.toList());
      final Long invalidValue = findInvalidValue(preambleQueue, values);
      System.out.println("Part1: Found invalid value: " + invalidValue);
      assert Long.valueOf(400480901L).equals(invalidValue);

      // Part 2
      part2(strings, 127L);
      part2(strings, 400480901L);
   }

   private static Long findInvalidValue( Queue<Long> preambleQueue, List<Long> values ) {
      for ( Long value : values ) {
         if ( !isValid(preambleQueue, value) ) {
            return value;
         }
         preambleQueue.poll();
         preambleQueue.add(value);
      }
      return null;
   }

   private static boolean isValid( Queue<Long> preambleQueue, Long lookupValue ) {
      for ( Long preambleValue : preambleQueue ) {
         final long result = lookupValue - preambleValue;
         if ( preambleQueue.contains(result) ) {
            return true;
         }
      }
      return false;
   }

   private static void part2( List<String> strings, Long lookupNumber ) {
      final List<Long> allValues = strings.stream().map(Long::parseLong).collect(Collectors.toList());
      _logger.info("Lookup list contains " + allValues.size() + " elements");

      final Map.Entry<Long, Long> resultNumberPair = secondPart(allValues, lookupNumber);
      if ( resultNumberPair != null ) {
         System.out.println(
               "Part2: Result for lookupNumber " + lookupNumber + " : " + resultNumberPair.getKey() + " + " + resultNumberPair.getValue() + " = " + (
                     resultNumberPair.getKey() + resultNumberPair.getValue()));
      } else {
         System.out.println("Part2: No result found");
      }
   }

   private static Map.Entry<Long, Long> secondPart( List<Long> values, Long lookupNumber ) {
      final LinkedList<Long> possibleNumbers = new LinkedList<>();
      final Iterator<Long> iterator = values.iterator();
      long sum;

      while ( iterator.hasNext() ) {
         final Long next = iterator.next();
         possibleNumbers.add(next);

         sum = possibleNumbers.stream().mapToLong(Long::longValue).sum();
         if ( sum == lookupNumber ) {
            System.out.println("Found the solution: " + possibleNumbers);
            return getMinAndMaxValue(possibleNumbers);
         } else if ( sum > lookupNumber ) {
            possibleNumbers.poll();
            final List<Long> subList = new ArrayList<>(possibleNumbers.size());
            Long result = 0L;
            for ( Long collectedNumber : possibleNumbers ) {
               result += collectedNumber;
               subList.add(collectedNumber);
               if ( result.equals(lookupNumber) ) {
                  return getMinAndMaxValue(subList);
               }
            }
         }
      }
      return null;
   }

   private static Map.Entry<Long, Long> getMinAndMaxValue(List<Long> longs){
      final Long min = longs.stream().min(Long::compareTo).orElse(null);
      assert min != null;
      final Long max = longs.stream().max(Long::compareTo).orElse(null);
      return Map.entry(min, max);
   }

}

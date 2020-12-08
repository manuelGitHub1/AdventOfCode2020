import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day05 {

   private static final Logger _logger = Logger.getLogger(Day05.class.getName());

   public static void main( String[] args ) {

      final List<String> strings = Util.fileAsStrings("src/main/resources/day05/input.txt");
      final List<Integer> seatIds = new ArrayList<>();
      for ( String line : strings ) {
         final List<Integer> rows = IntStream.range(0, 128).boxed().collect(Collectors.toList());
         final List<Integer> seats = IntStream.range(0, 8).boxed().collect(Collectors.toList());
         final char[] chars = line.toCharArray();
         for ( char currentChar : chars ) {
            final int rowsDivider = rows.size() / 2;
            final int seatsDivider = seats.size() / 2;
            if ( currentChar == 'F' ) {
               // lower half
               rows.subList(rowsDivider, rows.size()).clear();
            } else if ( currentChar == 'B' ) {
               // upper half
               rows.subList(0, rowsDivider).clear();
            } else if ( currentChar == 'R' ) {
               // upper half
               seats.subList(0, seatsDivider).clear();
            } else if ( currentChar == 'L' ) {
               // lower half
               seats.subList(seatsDivider, seats.size()).clear();
            }
         }
         assert rows.size() == 1;
         assert seats.size() == 1;
         final Integer rowNumber = rows.get(0);
         final Integer seatNumber = seats.get(0);
         final Integer seatId = rowNumber * 8 + seatNumber;
         _logger.fine("" + rowNumber + ":" + seatNumber + ", seatId: " + seatId);
         seatIds.add(seatId);
      }
      final Integer maxSeatId = seatIds.stream().max(Integer::compareTo).orElse(null);
      System.out.println("Part1: Max seat id is " + maxSeatId);

      seatIds.sort(Integer::compareTo);
      int lastSeatId = 0;
      for ( final Integer currentSeatId : seatIds ) {
         if ( lastSeatId > 0 && currentSeatId - lastSeatId > 1 ) {
            _logger.info("found a gap between " + currentSeatId + " and " + lastSeatId);
            System.out.println("Part2: Your seat id is " + (currentSeatId - 1));
         }
         lastSeatId = currentSeatId;
      }

   }

}

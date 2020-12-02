import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Day01 {

   static int twentytwenty = 2020;

   static final List<Integer> ints = getIntegers();

   public static void main( String[] args ) {
      firstPart(ints);

      secondPart(ints);
   }

   private static boolean findTheNumber( int twentytwenty, Collection<Integer> ints, Integer... anInt ) {
      final int sum = Arrays.stream(anInt).mapToInt(Integer::intValue).sum();
      final int subtractResult = twentytwenty - sum;
      if ( ints.contains(subtractResult) ) {
         System.out.println("Hit: " + Arrays.toString(anInt) + ", " + subtractResult);
         int product = 1;
         for ( Integer integer : anInt ) {
            product = integer * product;
         }
         System.out.println("Multiplication Result: " + product * subtractResult);
         return true;
      }
      return false;
   }

   private static void firstPart( List<Integer> ints ) {
      for ( Integer anInt : ints ) {
         if ( findTheNumber(twentytwenty, ints, anInt) ) {
            return;
         }
      }
   }



   private static List<Integer> getIntegers() {
      List<Integer> ints = new ArrayList<>();
      Scanner scanner = new Scanner(Util.getFileFromResourceAsStream("day01/input.txt"));
      while ( scanner.hasNextInt() ) {
         final int nextInt = scanner.nextInt();
         ints.add(nextInt);
      }
      return Collections.unmodifiableList(ints);
   }

   private static void secondPart( List<Integer> ints ) {
      for ( int i = 0; i < ints.size(); i++ ) {
         int first = ints.get(i);
         for ( int j = i + 1; j < ints.size(); j++ ) {
            int second = ints.get(j);
            final int firstAdditionResult = first + second;
            if ( firstAdditionResult < twentytwenty ) {
               if ( findTheNumber(twentytwenty, ints, first, second) ) {
                  return;
               }
            }
         }
      }
   }

}

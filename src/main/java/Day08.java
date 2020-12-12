import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class Day08 {

   private static final Logger _logger = Logger.getLogger(Day08.class.getName());

   public static void main( String[] args ) {
      part1();
      part2();
   }

   private static Integer correctAndProcessInstructions( List<Integer> alreadyChangedInstructions ) {
      final List<String> strings = Util.getAoCInput(Day08.class.getName());
      for ( int i = 0; i < strings.size(); i++ ) {
         final String currentInstruction = strings.get(i);
         if ( currentInstruction.contains("acc") || alreadyChangedInstructions.contains(i) ) {
            continue;
         }

         if ( currentInstruction.contains("nop") ) {
            _logger.fine("Changed instruction " + (i + 1) + " from nop to jmp");
            strings.set(i, currentInstruction.replace("nop", "jmp"));
         } else if ( currentInstruction.contains("jmp") ) {
            _logger.fine("Changed instruction " + (i + 1) + " from jmp to nop");
            strings.set(i, currentInstruction.replace("jmp", "nop"));
         }
         alreadyChangedInstructions.add(i);
         final Integer accumulator = processInstructions(strings);
         if ( accumulator != null ) {
            return accumulator;
         } else {
            return correctAndProcessInstructions(alreadyChangedInstructions);
         }
      }
      return null;
   }

   private static void part1() {
      final List<String> strings = Util.getAoCInput(Day08.class.getName());
      Integer accumulator = processInstructions(strings);
      if ( accumulator == null ) {
         System.out.println("Program would run infinitely");
      } else {
         System.out.println("Part1: Result is " + accumulator);
      }
   }

   private static void part2() {
      final List<Integer> alreadyChangedInstructions = new ArrayList<>();
      final Integer integer = correctAndProcessInstructions(alreadyChangedInstructions);
      System.out.println("Part2: End result is " + integer);
   }

   private static Integer processInstructions( List<String> instructions ) {
      int accumulator = 0;
      final Set<Integer> executedInstructions = new HashSet<>();
      int steps = 0;
      for ( int i = 0; i < instructions.size(); i++ ) {
         steps++;
         if ( executedInstructions.contains(i) ) {
            _logger.warning("Instruction " + i + " was already executed before. Accumulator is " + accumulator);
            return null;
         }
         executedInstructions.add(i);
         final String currentInstruction = instructions.get(i);
         _logger.fine("Current instruction " + currentInstruction + ", current step: " + steps + " current index: " + i);
         final String[] split = currentInstruction.split(" ");
         assert split.length == 2;
         final String operation = split[0];
         final int argument = Integer.parseInt(split[1]);
         if ( "acc".equals(operation) ) {
            accumulator += argument;
         } else if ( "jmp".equals(operation) ) {
            if ( argument > 1 || argument < 0 ) {
               i += argument - 1;
            }
         }

      }
      return accumulator;
   }

}

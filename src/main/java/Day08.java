import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class Day08 {

   private static final Logger _logger = Logger.getLogger(Day08.class.getName());

   public static void main( String[] args ) {
      final List<String> strings = Util.getAoCInput(Day08.class.getName());
      int accumulator = 0;
      Set<Integer> executedInstructions = new HashSet<>();
      int steps = 0;
      for ( int i = 0; i < strings.size(); i++ ) {
         steps++;
         if ( executedInstructions.contains(i) ) {
            _logger.warning("Instruction " + i + " was already executed before");
            break;
         }
         executedInstructions.add(i);
         final String currentInstruction = strings.get(i);
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
      System.out.println("End result: " + accumulator);
   }

}

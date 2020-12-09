import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class Day06 {

   private static final Logger _logger = Logger.getLogger(Day06.class.getName());

   public static void main( String[] args ) {
      final List<String> strings = Util.getAoCInput(Day06.class.getName());
      final List<GroupAnswer> groupAnswers = new ArrayList<>();
      GroupAnswer groupAnswer = new GroupAnswer();
      for ( String line : strings ) {
         if ( line.isBlank() ) {
            groupAnswers.add(groupAnswer);
            groupAnswer = new GroupAnswer();
         }
         groupAnswer.addAnswer(line);
      }
      if ( !groupAnswer.hasAnswers() ) {
         groupAnswers.add(groupAnswer);
      }

      final int sum = groupAnswers.stream().mapToInt(GroupAnswer::getAllPositiveAnswers).sum();
      assert sum == 6351;
      System.out.println("Part1: Sum of positive answers: " + sum);
   }

   static class GroupAnswer {

      private final List<String> _allGroupAnswers = new ArrayList<>();

      public boolean hasAnswers(){
         return !_allGroupAnswers.isEmpty();
      }

      public void addAnswer( String string ) {
         _allGroupAnswers.add(string);
      }

      public int getAllPositiveAnswers() {
         final Set<Character> _answers = new HashSet<>();
         _allGroupAnswers.forEach(a -> {
            final char[] chars = a.toCharArray();
            for ( Character aChar : chars ) {
               _answers.add(aChar);
            }
         });
         return _answers.size();
      }
   }

}

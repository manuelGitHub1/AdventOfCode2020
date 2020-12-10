import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day06 {


   public static void main( String[] args ) {
      final List<String> strings = Util.getAoCInput(Day06.class.getName());
      final List<GroupAnswer> groupAnswers = new ArrayList<>();
      GroupAnswer groupAnswer = new GroupAnswer();
      for ( String line : strings ) {
         if ( line.isBlank() ) {
            groupAnswers.add(groupAnswer);
            groupAnswer = new GroupAnswer();
         } else {
            groupAnswer.addAnswer(line);
         }
      }
      if ( groupAnswer.hasAnswers() ) {
         groupAnswers.add(groupAnswer);
      }

      final int sum = groupAnswers.stream().mapToInt(GroupAnswer::getAllPositiveAnswers).sum();
      assert sum == 6351;
      System.out.println("Part1: Sum of positive answers: " + sum);
      final long sumAnswersEveryOneVotedFor = groupAnswers.stream().mapToLong(GroupAnswer::getAllPositiveAnswersEveryoneVotedFor).sum();
      System.out.println("Part2: Sum of positive answers everyone of a group voted for: " + sumAnswersEveryOneVotedFor);
   }

   static class GroupAnswer {

      private final List<String> _allGroupAnswers = new ArrayList<>();

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

      public long getAllPositiveAnswersEveryoneVotedFor() {
         final int numberOfGroupMembers = _allGroupAnswers.size();
         final HashMap<Character, Integer> answerToFrequencyMap = new HashMap<>();
         _allGroupAnswers.forEach(a -> {
            final char[] chars = a.toCharArray();
            for ( Character aChar : chars ) {
               answerToFrequencyMap.merge(aChar, 1, Integer::sum);
            }
         });
         return answerToFrequencyMap.entrySet().stream().filter(e -> e.getValue() == numberOfGroupMembers).count();
      }

      public boolean hasAnswers() {
         return !_allGroupAnswers.isEmpty();
      }
   }

}

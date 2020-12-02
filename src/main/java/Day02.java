import java.util.List;


public class Day02 {

   public static void main( String[] args ) {
      final List<String> strings = Util.fileAsStrings("src/main/resources/day02/input.txt");
      // first part
      final long count = strings.stream().map(Day02::stringToObject).filter(Day02::validateOldRule).count();
      System.out.printf("Part1: Found %s valid passwords", count);
      // second part
      final long count2 = strings.stream().map(Day02::stringToObject).filter(Day02::validateNewRule).count();
      System.out.printf("\nPart2: Found %s valid passwords", count2);
   }

   private static RuleAndPassword stringToObject( String input ) {
      assert input != null;
      final String[] split = prepareRule(input, " ", 3);
      final RuleAndPassword rap = new RuleAndPassword();
      rap.setRule(split[0]);
      rap.setCharacter(split[1].substring(0, 1));
      rap.setPassword(split[2]);
      return rap;
   }

   private static boolean validateNewRule( RuleAndPassword rap ) {
      final String[] split = prepareRule(rap.getRule(), "-", 2);
      int firstIndex = Integer.parseInt(split[0]);
      int secondIndex = Integer.parseInt(split[1]);
      int lookUpChar = rap.getCharacter().charAt(0);
      final String password = rap.getPassword();
      final boolean firstLookupMatches = password.charAt(firstIndex - 1) == lookUpChar;
      final boolean secondLookupMatches = password.charAt(secondIndex - 1) == lookUpChar;
      return firstLookupMatches != secondLookupMatches;
   }

   private static String[] prepareRule( String rule, String s, int i ) {
      final String[] split = rule.split(s);
      assert split.length == i;
      return split;
   }

   private static boolean validateOldRule( RuleAndPassword rap ) {
      final String[] split = prepareRule(rap.getRule(), "-", 2);
      int min = Integer.parseInt(split[0]);
      int max = Integer.parseInt(split[1]);
      int lookUpChar = rap.getCharacter().charAt(0);
      final long appearances = rap.getPassword().chars().filter(c -> c == lookUpChar).count();
      return appearances >= min && max >= appearances;
   }


   private static class RuleAndPassword {

      String _rule;
      String _character;
      String _password;

      public String getCharacter() {
         return _character;
      }

      public String getPassword() {
         return _password;
      }

      public String getRule() {
         return _rule;
      }

      public void setCharacter( String character ) {
         _character = character;
      }

      public void setPassword( String password ) {
         _password = password;
      }

      public void setRule( String rule ) {
         _rule = rule;
      }
   }

}

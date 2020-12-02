import java.util.List;
import java.util.Objects;


public class Day02 {

   public static void main( String[] args ) {
      final List<String> strings = Util.fileAsStrings("src/main/resources/day02/input.txt");
      final long count = strings.stream().map(Day02::stringToObject).filter(Objects::nonNull).filter(Day02::validate).count();
      System.out.printf("Found %s invalid passwords", count);
   }

   private static boolean validate(RuleAndPassword rap) {
      final String[] split = rap.getCount().split("-");
      assert split.length == 2;
      int min = Integer.parseInt(split[0]);
      int max = Integer.parseInt(split[1]);

      Integer lookUpChar = (int) rap.getCharacter().charAt(0);

      final long appearances = rap.getPassword().chars().filter(c -> c == lookUpChar).count();

      return appearances >= min && max >= appearances;
   }

   private static RuleAndPassword stringToObject( String input ) {
      assert input != null;
      // example: 4-7 z: zzzfzlzzz
      final String[] split = input.split(" ");
      assert split.length == 3;
      final RuleAndPassword rap = new RuleAndPassword();
      rap.setCount(split[0]);
      rap.setCharacter(split[1].substring(0, 1));
      rap.setPassword(split[2]);
      return rap;
   }

   private static class RuleAndPassword {

      String _count;
      String _character;
      String _password;

      public String getCharacter() {
         return _character;
      }

      public String getCount() {
         return _count;
      }

      public String getPassword() {
         return _password;
      }

      public void setCharacter( String character ) {
         _character = character;
      }

      public void setCount( String count ) {
         _count = count;
      }

      public void setPassword( String password ) {
         _password = password;
      }
   }

}

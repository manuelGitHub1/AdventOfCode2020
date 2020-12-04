import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class Day04 {

   private static final Logger _logger = Logger.getLogger(Day04.class.getName());


   public static void main( String[] args ) {

      final List<String> strings = Util.fileAsStrings("src/main/resources/day04/input.txt");
      final List<Passport> passports = new ArrayList<>();
      Passport passport = new Passport();
      for ( String line : strings ) {
         if ( !line.isBlank() ) {
            passport.addFields(line);
         } else {
            passports.add(passport);
            passport = new Passport();
         }
      }
      if ( passport._fields.size() > 0 ){
         passports.add(passport);
      }
      _logger.info("Collected " + passports.size() + " passports");
      final long invalidPassports = passports.stream().filter(p -> !p.validate()).count();
      final long validPassports = passports.stream().filter(Passport::validate).count();
      assert validPassports == 182;
      _logger.info("Part1 - debug - : Number of invalid passports " + invalidPassports);
      _logger.info("Part1: Number of valid passports " + validPassports);
   }

   static class Passport {

      private final Map<Field, String> _fields = new HashMap<>();

      public void addFields( String string ) {
         final String[] kvPairs = string.split(" ");
         assert kvPairs.length > 0;
         for ( String kvPair : kvPairs ) {
            final String[] split = kvPair.split(":");
            assert split.length == 2;
            final Field field = Field.valueOf(split[0]);
            _fields.put(field, split[1]);
         }
      }

      @Override
      public String toString() {
         return "Passport{" + "_fields=" + _fields + '}';
      }

      public boolean validate() {
         for ( Field field : Field.values() ) {
            if ( field.isRequired() && !_fields.containsKey(field) ) {
               _logger.fine("Invalid passport detected: Field " + field + " is missing: " + this);
               return false;
            }
         }
         return true;
      }
   }

}

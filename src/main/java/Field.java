import java.util.regex.Pattern;

public enum Field {

      byr(Pattern.compile("19[2-9][0-9]|200[0-2]")), // (Birth Year)
      iyr(Pattern.compile("201[0-9]|2020")), // (Issue Year)
      eyr(Pattern.compile("202[0-9]|2030")), // (Expiration Year)
      hgt(Pattern.compile("1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in")), // (Height)
      hcl(Pattern.compile("#[0-9a-f]{6}")), // (Hair Color)
      ecl(Pattern.compile("amb|blu|brn|gry|grn|hzl|oth")), // (Eye Color)
      pid(Pattern.compile("[0-9]{9}")), // (Passport ID)
      cid(null); // (Country ID)

      private final Pattern validationPattern;

      Field( Pattern pattern ) {
         this.validationPattern = pattern;
      }

      public boolean validateFieldContent( final String input ) {
         if ( this.validationPattern == null ) {
            return true;
         }
         return this.validationPattern.matcher(input).matches();
      }

      public boolean isRequired() {
         return Field.cid != this;
      }
   }

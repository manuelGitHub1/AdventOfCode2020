import java.util.regex.Pattern;

public enum Field {

      byr(Pattern.compile("19[2-9][0-9]|200[1-2]")), // (Birth Year)
      iyr(Pattern.compile("")), // (Issue Year)
      eyr(Pattern.compile("")), // (Expiration Year)
      hgt(Pattern.compile("")), // (Height)
      hcl(Pattern.compile("")), // (Hair Color)
      ecl(Pattern.compile("")), // (Eye Color)
      pid(Pattern.compile("")), // (Passport ID)
      cid(Pattern.compile("")); // (Country ID)

      private final Pattern validationPattern;

      Field( Pattern pattern ) {
         this.validationPattern = pattern;
      }

      public boolean validateFieldContent( final String input ) {
         return this.validationPattern.matcher(input).matches();
      }

      public boolean isRequired() {
         return Field.cid != this;
      }
   }

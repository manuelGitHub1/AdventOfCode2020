import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day07 {

   private static final Logger _logger = Logger.getLogger(Day07.class.getName());

   // example: clear purple bags contain 5 faded indigo bags, 3 muted purple bags.
   private static final Pattern _bagPattern = Pattern.compile("(^.+?) bags(.*)$");

   // example:  contain 5 faded indigo bags, 3 muted purple bags.
   private static final Pattern contentPattern = Pattern.compile("([1-9])(.+?) bag");

   public static void main( String[] args ) {
      final List<String> strings = Util.getAoCInput(Day07.class.getName());
      Set<String> bagTypes = new HashSet<>();
      final HashMap<String, HashMap<String, Integer>> bagHierarchy = getStringListHashMap(strings, bagTypes);
      bagHierarchy.entrySet().stream().forEach(System.out::println);
      _logger.info("Different types of bags: " + bagTypes.size());
      _logger.info("Different types of bags: " + bagHierarchy.size());
      // lookup for shiny gold
      final Set<String> bagsThatCanContainShinyGold = new HashSet<>();
      final Set<String> directContainer = lookupForPossibleContainerBags(bagHierarchy, "shiny gold");
      bagsThatCanContainShinyGold.addAll(directContainer);
      _logger.info("Bags that can contain shiny gold directly: " + bagsThatCanContainShinyGold.size());
      final Set<String> secondInstance = new HashSet<>();
      for ( String bagType : directContainer ) {
         secondInstance.addAll(lookupForPossibleContainerBags(bagHierarchy, bagType));
      }
      bagsThatCanContainShinyGold.addAll(secondInstance);
      for ( String bagType : secondInstance ) {
         bagsThatCanContainShinyGold.addAll(lookupForPossibleContainerBags(bagHierarchy, bagType));
      }
      System.out.println("Part1: Bags that can contain shiny gold: " + (bagsThatCanContainShinyGold.size()));
   }

   private static HashMap<String, HashMap<String, Integer>> getStringListHashMap( List<String> strings, Set<String> bagTypes ) {
      HashMap<String, HashMap<String, Integer>> bagHierarchy = new HashMap<>();
      for ( String line : strings ) {
         final Matcher matcher = _bagPattern.matcher(line);
         if ( matcher.matches() ) {
            final String bagType = matcher.group(1);
            bagTypes.add(bagType);
            if ( !bagHierarchy.containsKey(bagType) ) {
               bagHierarchy.put(bagType, new HashMap<>());
            }

            final String contentRaw = matcher.group(2);
            final Matcher contentMatcher = contentPattern.matcher(contentRaw);
            int matcherStartIndex = 0;
            while ( contentMatcher.find(matcherStartIndex) ) {
               final String contentBagType = contentMatcher.group(2);
               assert contentBagType != null;
               final String group = contentMatcher.group(1);
               assert group != null;
               bagHierarchy.get(bagType).put(contentBagType.trim(), Integer.valueOf(group));
               final String wholeMatch = contentMatcher.group();
               if ( _logger.isLoggable(Level.INFO) ) {
                  _logger.info("The whole match is: " + wholeMatch + ", length " + wholeMatch.length() + ", current matchStartIndex: " + matcherStartIndex);
               }
               matcherStartIndex += wholeMatch.length();
            }

         }
      }
      return bagHierarchy;
   }

   @org.jetbrains.annotations.NotNull
   private static Set<String> lookupForPossibleContainerBags( HashMap<String, HashMap<String, Integer>> bagHierarchy, String lookupType ) {
      final Set<String> bagsThatCanContainShinyGold = bagHierarchy.entrySet()
            .stream()
            .filter(e -> e.getValue().containsKey(lookupType))
            .map(e -> e.getKey())
            .collect(Collectors.toSet());
      return bagsThatCanContainShinyGold;
   }

}

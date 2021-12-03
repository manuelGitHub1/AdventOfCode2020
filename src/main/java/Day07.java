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

import org.jetbrains.annotations.NotNull;


public class Day07 {

   private static final Logger _logger = Logger.getLogger(Day07.class.getName());

   // example: clear purple bags contain 5 faded indigo bags, 3 muted purple bags.
   private static final Pattern _bagPattern = Pattern.compile("(^.+?) bags(.*)$");

   // example:  contain 5 faded indigo bags, 3 muted purple bags.
   private static final Pattern contentPattern = Pattern.compile("([1-9])(.+?) bag");

   public static void main( String[] args ) {
      final String shiny_gold = "shiny gold";
      final List<String> strings = Util.getAoCInput(Day07.class.getName());
      final HashMap<String, HashMap<String, Integer>> bagHierarchy = buildBagHierarchy(strings);
      _logger.info("Different types of bags: " + bagHierarchy.size());
      final Set<String> bagsThatCanContainLookupBag = new HashSet<>();
      lookupForPossibleContainerBags(bagsThatCanContainLookupBag, bagHierarchy, shiny_gold);
      System.out.println("Part1: Bags that can contain shiny gold (direct/indirect): " + (bagsThatCanContainLookupBag.size()));
      //      assert bagsThatCanContainLookupBag.size() == 185;
      // Part2
      final HashMap<String, Integer> bagsInShinyGold = bagHierarchy.get(shiny_gold);
      Integer sum = 0;

      final HashMap<String, Integer> bagsToNumber = mapBagsToNumbers(bagHierarchy);
      System.out.println(bagsToNumber);

      sumUpAllContentBags(bagsInShinyGold, bagHierarchy, sum);
      System.out.println("Part2: " + shiny_gold + " must contain " + sum + " other bags");
   }

   private static HashMap<String, HashMap<String, Integer>> buildBagHierarchy( List<String> strings ) {
      HashMap<String, HashMap<String, Integer>> bagHierarchy = new HashMap<>();
      for ( String line : strings ) {
         final Matcher matcher = _bagPattern.matcher(line);
         if ( matcher.matches() ) {
            final String bagType = matcher.group(1);
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
               if ( _logger.isLoggable(Level.FINE) ) {
                  _logger.fine("The whole match is: " + wholeMatch + ", length " + wholeMatch.length() + ", current matchStartIndex: " + matcherStartIndex);
               }
               matcherStartIndex += wholeMatch.length();
            }

         }
      }
      return bagHierarchy;
   }

   private static void lookupForPossibleContainerBags( Set<String> result, HashMap<String, HashMap<String, Integer>> bagHierarchy, String lookupType ) {
      final Set<String> bagsThatCanContainLookupType = bagHierarchy.entrySet()
            .stream()
            .filter(e -> e.getValue().containsKey(lookupType))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
      result.addAll(bagsThatCanContainLookupType);
      if ( bagsThatCanContainLookupType.isEmpty() ) {
         return;
      }
      bagsThatCanContainLookupType.forEach(t -> lookupForPossibleContainerBags(result, bagHierarchy, t));

   }

   @NotNull
   private static HashMap<String, Integer> mapBagsToNumbers( HashMap<String, HashMap<String, Integer>> bagHierarchy ) {
      final HashMap<String, Integer> bagsToNumber = new HashMap<>();
      for ( Map.Entry<String, HashMap<String, Integer>> entry : bagHierarchy.entrySet() ) {
         if ( entry.getValue().isEmpty() ) {
            bagsToNumber.put(entry.getKey(), 1);
//            final Set<String> containerBags = new HashSet<>();
////            lookupForPossibleContainerBags(containerBags, bagHierarchy, entry.getKey());
////            for ( String containerBag : containerBags ) {
////               if ( !bagsToNumber.containsKey(containerBag) ) {
////                  bagsToNumber.put(containerBag, factor);
////               } else {
////                  final Integer oldValue = bagsToNumber.get(containerBag);
////                  bagsToNumber.put(containerBag, factor + oldValue);
////               }
////            }
         }
      }
      return bagsToNumber;
   }

   private static void sumUpAllContentBags( final HashMap<String, Integer> contentBags, final HashMap<String, HashMap<String, Integer>> bagHierarchy,
         Integer sum ) {
      for ( Map.Entry<String, Integer> entry : contentBags.entrySet() ) {
         final Integer numberOfSubbagsInParentBag = entry.getValue();
         final HashMap<String, Integer> subBags = bagHierarchy.get(entry.getKey());
         if ( subBags.isEmpty() ) {
            sum += numberOfSubbagsInParentBag;
            continue;
         }
         sumUpAllContentBags(subBags, bagHierarchy, sum);
      }
   }
}

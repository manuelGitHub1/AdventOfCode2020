import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Day03 {

   public static void main( String[] args ) {
      final List<String> strings = Util.fileAsStrings("src/main/resources/day03/input.txt");
      final HashMap<Integer, List<Integer>> treesExpanded = extrapolateData(findTrees(strings), 7);

      final int hits = travelAndHitTrees(3, 1, treesExpanded);
      System.out.println("Part1: You hit " + hits + " trees");

      int product = hits;
      product *= travelAndHitTrees(1, 1, treesExpanded);
      product *= travelAndHitTrees(5, 1, treesExpanded);
      product *= travelAndHitTrees(7, 1, treesExpanded);
      product *= travelAndHitTrees(1, 2, treesExpanded);

      System.out.println("Part1: Multiplication result of hitted trees is " + product);
   }

   private static HashMap<Integer, List<Integer>> extrapolateData( HashMap<Integer, List<Integer>> trees,
         @SuppressWarnings("SameParameterValue") int maxHorizontalSteps ) {
      final HashMap<Integer, List<Integer>> treesExpanded = new HashMap<>();
      int maxHorizontalPosition = maxHorizontalSteps * trees.size();
      for ( Integer key : trees.keySet() ) {
         final List<Integer> treesInRow = trees.get(key);
         final ArrayList<Integer> treesInRowExpanded = new ArrayList<>(treesInRow);
         for ( Integer integer : treesInRow ) {
            int newTreePosition = integer + 31;
            while ( newTreePosition <= maxHorizontalPosition ) {
               treesInRowExpanded.add(newTreePosition);
               newTreePosition += 31;
            }
         }
         treesExpanded.put(key, treesInRowExpanded);
      }
      return treesExpanded;
   }

   private static HashMap<Integer, List<Integer>> findTrees( List<String> strings ) {
      HashMap<Integer, List<Integer>> trees = new HashMap<>();
      for ( int i = 0; i < strings.size(); i++ ) {
         final String string = strings.get(i);
         final List<Integer> treesInRow = new ArrayList<>();
         int treePosition = string.indexOf('#');
         while ( treePosition > -1 ) {
            treesInRow.add(treePosition);
            treePosition = string.indexOf('#', treePosition + 1);
         }
         assert !treesInRow.isEmpty();
         trees.put(i, treesInRow);
         System.out.println(string + " Trees: " + trees.get(i));
      }
      return trees;
   }

   private static int travelAndHitTrees( int horizontalSteps, int verticalSteps, HashMap<Integer, List<Integer>> trees ) {
      int hittedTrees = 0;
      int currentX = horizontalSteps;
      int currentY = verticalSteps;
      final int lastRowIndex = trees.keySet().size() - 1;

      while ( (currentY <= lastRowIndex) ) {
         hittedTrees += trees.get(currentY).contains(currentX) ? 1 : 0;
         currentX += horizontalSteps;
         currentY += verticalSteps;
      }

      return hittedTrees;
   }
}

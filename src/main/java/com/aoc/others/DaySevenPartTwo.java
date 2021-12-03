package com.aoc.others;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// solution from https://www.reddit.com/user/forrealbro/
public class DaySevenPartTwo {

   private static final Pattern childNumberPattern = Pattern.compile("\\d");
   private static final Pattern childColorPattern = Pattern.compile("[a-zA-Z]+ [a-zA-Z]+");

   public static void main(String[] args) {
      File inputFile = new File("src/main/resources/day07/input.txt");
      Pattern parentPattern = Pattern.compile("^\\w+ \\w+");
      Pattern childPattern = Pattern.compile("\\d+ \\w+ \\w+");
      HashMap<String, List<String>> bagContainers = new HashMap<>();

      try{
         Scanner fileScanner = new Scanner(inputFile);
         while(fileScanner.hasNext()){
            String nextLine = fileScanner.nextLine();
            Matcher parentMatch = parentPattern.matcher(nextLine);
            parentMatch.find();
            String parent = parentMatch.group();

            Matcher childMatch = childPattern.matcher(nextLine);
            List<String> child = new LinkedList<>();
            while(childMatch.find()){
               child.add(childMatch.group());
            }
            bagContainers.put(parent,child);

         }

      }catch (Exception e){
         e.printStackTrace();
      }finally{
         final int result = totalBags(bagContainers);
         assert result == 89084;
         System.out.println(result);
      }
   }

   public static int totalBags(HashMap<String, List<String>> myBags){
      int totalBags = 0;
      Queue<String> searchQ = new LinkedList<>();


      totalBags = buildStartingQueue(myBags, totalBags, searchQ, childNumberPattern, childColorPattern);
      //main search q
      //basically just navigating through the entire tree and counting all nodes.
      while(!searchQ.isEmpty()){
         String nextSearch = searchQ.poll();
         //int totalInside = 0;
         for(String child : myBags.get(nextSearch)){
            Matcher childNumberMatcher = childNumberPattern.matcher(child);
            Matcher childColorMatcher = childColorPattern.matcher(child);
            childNumberMatcher.find();
            childColorMatcher.find();


            int childNumber = Integer.parseInt(childNumberMatcher.group());
            String childColor = childColorMatcher.group();

            totalBags+= childNumber;

            for(int i = 0; i< childNumber;i++){
               searchQ.add(childColor);
            }
         }
      }
      return totalBags;
   }

   private static int buildStartingQueue( HashMap<String, List<String>> myBags, int totalBags, Queue<String> searchQ, Pattern childNumberPattern,
         Pattern childColorPattern ) {
      //find our shiny gold and add its children to the bag...also math
      //this will build our starting queue
      for(String child : myBags.get("shiny gold")){

         Matcher childNumberMatcher = childNumberPattern.matcher(child);
         Matcher childColorMatcher = childColorPattern.matcher(child);
         childNumberMatcher.find();
         childColorMatcher.find();


         int childNumber = Integer.parseInt(childNumberMatcher.group());
         String childColor = childColorMatcher.group();

         totalBags+= childNumber;

         for(int i = 0; i< childNumber;i++){
            searchQ.add(childColor);
         }
      }
      return totalBags;
   }
}
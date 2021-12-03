import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;


public class Util {

   private static final Logger _logger = Logger.getLogger(Util.class.getName());

   public static List<String> fileAsStrings( String filePath ) {
      final Path path = Paths.get(filePath);
      _logger.info("Read file from Path " + path);
      try {
         final List<String> lines = Files.readAllLines(path);
         _logger.info("Read " + lines.size() + " lines from file");
         return lines;
      }
      catch ( IOException e ) {
         throw new RuntimeException(e);
      }
   }

   public static List<String> getAoCInput(final String day ) {
      assert day != null;
      return getInput(day, "/input.txt");
   }

   public static List<String> getAoCInputTest(final String day ) {
      assert day != null;
      return getInput(day, "/test.txt");
   }

   @NotNull
   private static List<String> getInput( String day, String s ) {
      return fileAsStrings("src/main/resources/" + day.toLowerCase() + s);
   }

   // copied from https://mkyong.com/java/java-read-a-file-from-resources-folder/
   // get a file from the resources folder
   // works everywhere, IDEA, unit test and JAR file.
   public static InputStream getFileFromResourceAsStream( String fileName ) {

      // The class loader that loaded the class
      ClassLoader classLoader = Util.class.getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream(fileName);

      // the stream holding the file content
      if ( inputStream == null ) {
         throw new IllegalArgumentException("file not found! " + fileName);
      } else {
         return inputStream;
      }

   }

}

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class FieldTest {

   @Test
   void validateFieldContent_byr() {
      assertFalse(Field.byr.validateFieldContent("2020"));
      assertFalse(Field.byr.validateFieldContent("202"));
      assertTrue(Field.byr.validateFieldContent("2002"));
      assertTrue(Field.byr.validateFieldContent("1920"));
   }
}
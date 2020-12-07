import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class FieldTest {

   @Test
   void validateFieldContent_byr() {
      assertFalse(Field.byr.validateFieldContent("2020"));
      assertFalse(Field.byr.validateFieldContent("202"));
      assertTrue(Field.byr.validateFieldContent("2002"));
      assertTrue(Field.byr.validateFieldContent("1920"));
      assertTrue(Field.byr.validateFieldContent("2000"));
   }

   @Test
   void validateFieldContent_iyr() {
      final Field sut = Field.iyr;
      assertTrue(sut.validateFieldContent("2010"));
      assertTrue(sut.validateFieldContent("2020"));

      assertFalse(sut.validateFieldContent("22020"));
      assertFalse(sut.validateFieldContent("202"));
      assertFalse(sut.validateFieldContent("2009"));
      assertTrue(sut.validateFieldContent("2015"));
   }

   @Test
   void validateFieldContent_eyr() {
      final Field sut = Field.eyr;
      assertTrue(sut.validateFieldContent("2020"));
      assertTrue(sut.validateFieldContent("2030"));

      assertFalse(sut.validateFieldContent("22020"));
      assertFalse(sut.validateFieldContent("202"));
      assertFalse(sut.validateFieldContent("2009"));
      assertFalse(sut.validateFieldContent("2015"));
      assertTrue(sut.validateFieldContent("2025"));
   }

   @Test
   void validateFieldContent_hgt() {
      final Field sut = Field.hgt;
      assertTrue(sut.validateFieldContent("190cm"));
      assertTrue(sut.validateFieldContent("150cm"));
      assertTrue(sut.validateFieldContent("193cm"));
      assertTrue(sut.validateFieldContent("59in"));
      assertTrue(sut.validateFieldContent("76in"));

      assertFalse(sut.validateFieldContent("194cm"));
      assertFalse(sut.validateFieldContent("100in"));
   }

   @Test
   void validateFieldContent_ecl() {
      final Field sut = Field.ecl;
      final List<String> eyeColors = Arrays.asList("amb","blu","brn","gry","grn","hzl","oth");
      final long valids = eyeColors.stream().filter(Field.ecl::validateFieldContent).count();
      assertEquals(eyeColors.size(), valids);
      assertFalse(sut.validateFieldContent("blue"));
   }

   @Test
   void validateFieldContent_pid() {
      final Field sut = Field.pid;
      final long number = System.currentTimeMillis();
      final String base = String.valueOf(number);
      assertTrue(sut.validateFieldContent(base.substring(0, 9)));
      assertFalse(sut.validateFieldContent(base.substring(0, 10)));
      assertFalse(sut.validateFieldContent(base.substring(0, 8)));

   }
}
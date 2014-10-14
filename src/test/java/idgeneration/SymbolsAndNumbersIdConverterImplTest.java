package idgeneration;


import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SymbolsAndNumbersIdConverterImplTest {
    @BeforeClass
    public static void ClassSetUp() {
        converter = new AlphabetBasedLongIdToStringConverterImpl(new SymbolsWithNumbersAlphabetImpl(), 8);
    }

    @Test
    public void zeroConversionTest() {
        AssertConversion("00000000", 0);
    }

    @Test
    public void usualNumbersConvertionTest() {
        AssertConversion("00000CUt", 48043);
        AssertConversion("UKjrqjzq", 106823716192542L);
    }

    @Test
    public void lastPossibleNumberConversionTest() {
        AssertConversion("zzzzzzzz", 218340105584895L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNumberIsGreaterThanCanBeConverted_ShouldThrowException() {
        AssertConversion("HUGE_NUM", 218340105584895L + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNumberIsLessThanZero_ShouldThrowException() {
        AssertConversion("LESS_THAN_ZERO", -12);
    }

    private void AssertConversion(String expected, long givenId) {
        String converted = converter.convert(givenId);
        assertEquals(expected, converted);
    }

    private static AlphabetBasedLongIdToStringConverterImpl converter;
}

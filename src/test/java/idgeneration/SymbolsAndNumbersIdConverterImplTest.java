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
    public void ZeroConversionTest() {
        AssertConversion("00000000", 0);
    }

    @Test
    public void UsualNumbersConvertionTest() {
        AssertConversion("00000CUt", 48043);
        AssertConversion("UKjrqjzq", 106823716192542L);
    }

    @Test
    public void LastPossibleNumberConversionTest() {
        AssertConversion("zzzzzzzz", 218340105584895L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WhenNumberIsGreaterThanCanBeConverted_ShouldThrowException() {
        AssertConversion("HUGE_NUM", 218340105584895L + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WhenNumberIsLessThanZero_ShouldThrowException() {
        AssertConversion("LESS_THAN_ZERO", -12);
    }

    private void AssertConversion(String expected, long givenId) {
        String converted = converter.Convert(givenId);
        assertEquals(expected, converted);
    }

    private static AlphabetBasedLongIdToStringConverterImpl converter;
}

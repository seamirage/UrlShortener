package validation;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class UriValidatorTest {

    @Test
    @Parameters({"http://google.com, true", "http://gooooooooooooooole.com, false", "http://shortener.com/, false"})
    public void testValidator(String inputUrl, boolean expected) {
        assertEquals(expected, validator.isValid(inputUrl));
    }

    private static final UriValidator validator = new UriValidator(25, "shortener.com");
}

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class StringTranslatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = {"validate_false.csv", "validate_true.csv"})
    void validateTest(String input, boolean expectedOutput) {
        StringTranslator stringTranslator = StringTranslator.getInstance();
        boolean output = stringTranslator.validateInput(input);
        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"translate.csv"})
    void translateTest(String input, String expectedOutput) {
        StringTranslator stringTranslator = StringTranslator.getInstance();
        String output = stringTranslator.translate(input);
        expectedOutput = expectedOutput == null ? "" : expectedOutput;
        assertEquals(expectedOutput, output);
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"validate_false.csv"})
    void translateIllegalArgumentTest(String input) {
        StringTranslator stringTranslator = StringTranslator.getInstance();
        assertThrows(IllegalArgumentException.class, () -> stringTranslator.translate(input));
    }
}
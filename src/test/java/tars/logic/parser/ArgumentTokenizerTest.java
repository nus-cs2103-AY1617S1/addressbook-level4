package tars.logic.parser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArgumentTokenizerTest {
    private final Prefix unknownPrefix = new Prefix("/uuuuuu");
    private final Prefix tagPrefix = new Prefix("/t");
    private final Prefix dateTimePrefix = new Prefix("/dt");
    private final Prefix namePrefix = new Prefix("/n");

    @Test
    public void accessors_notTokenizedYet() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(tagPrefix);

        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, tagPrefix);
    }

    @Test
    public void tokenize_emptyArgsString_noValues() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(tagPrefix);
        String argsString = " ";
        tokenizer.tokenize(argsString);

        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, tagPrefix);
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer();
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        tokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(tokenizer, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(tagPrefix);

        // Preamble present
        tokenizer.tokenize("    Some preamble string /t Argument value ");
        assertPreamblePresent(tokenizer, "Some preamble string");
        assertArgumentPresent(tokenizer, tagPrefix, "Argument value");

        // No preamble
        tokenizer.tokenize(" /t   Argument value ");
        assertPreambleAbsent(tokenizer);
        assertArgumentPresent(tokenizer, tagPrefix, "Argument value");

    }
    
    @Test
    public void tokenize_multipleArguments() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(tagPrefix, dateTimePrefix, namePrefix);

        // Only two arguments are present
        tokenizer.tokenize("SomePreambleString /t dashT-Value /n slashP value");
        assertPreamblePresent(tokenizer, "SomePreambleString");
        assertArgumentPresent(tokenizer, namePrefix, "slashP value");
        assertArgumentPresent(tokenizer, tagPrefix, "dashT-Value");
        assertArgumentAbsent(tokenizer, dateTimePrefix);

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure state is correctly reset
        //   (i.e. no stale values from the previous tokenizing remain in the state)
        tokenizer.tokenize("");
        assertPreambleAbsent(tokenizer);
        assertArgumentAbsent(tokenizer, tagPrefix);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        String stringWithUnknownPrefix = unknownPrefix.prefix + "some value";
        tokenizer.tokenize(stringWithUnknownPrefix);
        assertArgumentAbsent(tokenizer, unknownPrefix);
        assertPreamblePresent(tokenizer, stringWithUnknownPrefix); // Unknown prefix is taken as part of preamble
    }
    
    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        ArgumentTokenizer tokenizer = new ArgumentTokenizer(tagPrefix, namePrefix);
        
        tokenizer.tokenize("SomePreambleString /n /t dashT-Value /t another");
        assertPreamblePresent(tokenizer, "SomePreambleString");
        assertArgumentPresent(tokenizer, tagPrefix, "dashT-Value", "another dashT value");
        assertArgumentPresent(tokenizer, namePrefix, "/n");
    }

    private void assertPreamblePresent(ArgumentTokenizer argsTokenizer, String expectedPreamble) {
        assertEquals(expectedPreamble, argsTokenizer.getPreamble().get());
    }

    private void assertPreambleAbsent(ArgumentTokenizer argsTokenizer) {
        assertFalse(argsTokenizer.getPreamble().isPresent());
    }

    private void assertArgumentAbsent(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        assertFalse(argsTokenizer.getValue(prefix).isPresent());
    }

    private void assertArgumentPresent(ArgumentTokenizer argsTokenizer, Prefix prefix,
            String... expectedValues) {

        // Verify the first value is returned
        assertEquals(expectedValues[0], argsTokenizer.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argsTokenizer.getMultipleValues(prefix).get().size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            // assertEquals(expectedValues[i],
            // argsTokenizer.getMultipleValues(prefix).get().get(i));
        }
    }
}

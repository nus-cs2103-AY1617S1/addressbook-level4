package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeParserTest {

    private static final LocalTime TEST_TIME = LocalTime.of(3, 14);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TimeParser parser;

    @Before
    public void setupParser() {
        parser = new TimeParser(TEST_TIME);
    }

    @Test
    public void constructor() {
        assertEquals(TEST_TIME, parser.getReferenceTime());
    }

    @Test
    public void parse() throws Exception {
        assertParse("  4am  ", LocalTime.of(4, 0));
        assertParse("1.23pm", LocalTime.of(13, 23));
        assertParse("2:45am", LocalTime.of(2, 45));
        assertParse("12am", LocalTime.of(0, 0));
        assertParse("12pm", LocalTime.of(12, 0));
        assertParseFail("13am");
        assertParseFail("4:99am");
        assertParseFail("5:am");
        assertParseFail("not a time at all");
    }

    private void assertParse(String str, LocalTime expected) throws Exception {
        final LocalTime actual = parser.parse(str);
        assertEquals(expected, actual);
    }

    private void assertParseFail(String str) throws Exception {
        thrown.expect(IllegalValueException.class);
        parser.parse(str);
    }

}

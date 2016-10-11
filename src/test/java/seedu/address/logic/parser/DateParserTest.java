package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateParserTest {

    private static final LocalDate TEST_DATE = LocalDate.of(2015, 3, 14);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DateParser parser;

    @Before
    public void setupParser() {
        parser = new DateParser(TEST_DATE);
    }

    @Test
    public void constructor() {
        assertEquals(TEST_DATE, parser.getReferenceDate());
    }

    @Test
    public void parse() throws Exception {
        assertParse("tdy", TEST_DATE);
        assertParse("tmr", TEST_DATE.plusDays(1));
        assertParse("yst", TEST_DATE.minusDays(1));
        assertParse(" 1 ", LocalDate.of(2015, 3, 1));
        assertParse("1/2", LocalDate.of(2015, 2, 1));
        assertParse("1/2/2014", LocalDate.of(2014, 2, 1));
        assertParseFail("0/1/2016"); // No such day
        assertParseFail("1/0/2015"); // No such month
        assertParseFail("1/13/2015"); // No such month
        assertParse("31/12/2014", LocalDate.of(2014, 12, 31)); // December has 31 days
        assertParseFail("31/9/2016"); // September only has 30 days
        assertParse("29/2/2016", LocalDate.of(2016, 2, 29)); // A leap year
        assertParseFail("29/2/2015"); // Not a leap year
        assertParseFail("1/2/14"); // We don't support 2-digit years
        assertParseFail("not a date at all");
    }

    private void assertParse(String str, LocalDate expected) throws Exception {
        final LocalDate actual = parser.parse(str);
        assertEquals(expected, actual);
    }

    private void assertParseFail(String str) throws Exception {
        thrown.expect(IllegalValueException.class);
        parser.parse(str);
    }

}

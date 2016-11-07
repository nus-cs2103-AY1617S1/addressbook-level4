package seedu.task.model;

import static org.junit.Assert.*;

import java.time.DateTimeException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todolist.model.parser.DateParser;;

public class DateParserTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void invalidMonth_exceptionThrown() throws Exception {
        thrown.expect(DateTimeException.class);
        DateParser.parseDate("25/20/2016");
        fail();
    }

}
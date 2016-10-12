package seedu.todo.logic.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.testutil.EventsCollector;

public class ExitCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new ExitCommand();
    }
    
    @Test
    public void testExecute() throws IllegalValueException, ValidationException {
        EventsCollector eventCollector = new EventsCollector();
        execute();
        assertCommandSuccess();
        assertTrue(eventCollector.get(0) instanceof ExitAppRequestEvent);
    }

}

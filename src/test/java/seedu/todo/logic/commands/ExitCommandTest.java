package seedu.todo.logic.commands;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.testutil.EventsCollector;

//@@author A0135817B
public class ExitCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new ExitCommand();
    }
    
    @Test
    public void testExecute() throws IllegalValueException, ValidationException {
        EventsCollector eventCollector = new EventsCollector();
        execute(true);
        assertThat(eventCollector.get(0), instanceOf(ExitAppRequestEvent.class));
    }

}

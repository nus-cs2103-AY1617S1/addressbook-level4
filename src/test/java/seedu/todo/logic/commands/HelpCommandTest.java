package seedu.todo.logic.commands;

import org.junit.Test;

import seedu.todo.commons.events.ui.ShowHelpEvent;
import seedu.todo.testutil.EventsCollector;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

//@@author A0135817B
public class HelpCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new HelpCommand();
    }
    
    @Test
    public void testExecute() throws Exception {
        EventsCollector eventsCollector = new EventsCollector();
        execute(true);
        assertThat(eventsCollector.get(0), instanceOf(ShowHelpEvent.class));
    }

}

package seedu.agendum.logic.commands;

import org.junit.Test;
import seedu.agendum.commons.core.Messages;

import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void getName()  {
        assertNull(Command.getName());
    }

    @Test
    public void getFormat()  {
        assertNull(Command.getFormat());
    }

    @Test
    public void getDescription()  {
        assertNull(Command.getDescription());
    }

    @Test
    public void getMessageForTaskListShownSummary()  {
        assertEquals(Command.getMessageForTaskListShownSummary(100), String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 100));
    }
}
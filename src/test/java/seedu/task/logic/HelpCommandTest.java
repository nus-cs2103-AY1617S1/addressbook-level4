package seedu.task.logic;

import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.HelpCommand;

public class HelpCommandTest extends CommandTest {
    /******************************Pre and Post set up*****************************/
    @Before
    public void setup() {
        super.setup();
    }

    @After
    public void teardown() {
        super.teardown();
    }
    
    /************************************Test cases*****************************/
    
    @Ignore
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior_task("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
    
}

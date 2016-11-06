package tars.logic;

import static org.junit.Assert.assertTrue;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import tars.logic.commands.HelpCommand;

/**
 * Logic command test for help
 */
public class HelpLogicCommandTest extends LogicCommandTest {
    
    @Test
    public void execute_help_successful() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
    
    @Test
    public void execute_help_invalidArgs() throws Exception {
        assertCommandBehavior("help hello world", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void execute_help_showAddUsageSuccessful() throws Exception {
        assertCommandBehavior("help add", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
}

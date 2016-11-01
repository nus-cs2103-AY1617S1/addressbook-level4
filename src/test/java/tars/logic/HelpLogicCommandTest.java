package tars.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tars.logic.commands.HelpCommand;

/**
 * Logic command test for help
 */
public class HelpLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
}

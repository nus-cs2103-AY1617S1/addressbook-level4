package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import tars.logic.commands.RedoCommand;

/**
 * Logic command test for redo
 * 
 * @@author A0139924W
 */
public class RedoLogicCommandTest extends LogicCommandTest {
    
    @Test
    public void execute_redo_emptyCmdHistStack() throws Exception {
        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                RedoCommand.MESSAGE_EMPTY_REDO_CMD_HIST);
    }

    @Test
    public void execute_redo_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RedoCommand.MESSAGE_USAGE);
        assertCommandBehavior("redo EXTRA ARGUMENTS", expectedMessage);
        assertCommandBehavior("redo 123", expectedMessage);
    }
}

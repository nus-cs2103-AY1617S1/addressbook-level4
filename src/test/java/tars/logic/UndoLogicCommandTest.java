package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import tars.logic.commands.UndoCommand;

/**
 * Logic command test for undo
 *
 * @@author A0139924W
 */
public class UndoLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_undo_emptyCmdHistStack() throws Exception {
        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                UndoCommand.MESSAGE_EMPTY_UNDO_CMD_HIST);
    }

    @Test
    public void execute_undo_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UndoCommand.MESSAGE_USAGE);
        assertCommandBehavior("undo EXTRA ARGUMENTS", expectedMessage);
        assertCommandBehavior("undo 123", expectedMessage);
    }
    
}

package guitests;

import org.junit.Test;

import seedu.taskitty.logic.ToolTip;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.ClearCommand;
import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.DeleteCommand;
import seedu.taskitty.logic.commands.DoneCommand;
import seedu.taskitty.logic.commands.EditCommand;
import seedu.taskitty.logic.commands.ExitCommand;
import seedu.taskitty.logic.commands.FindCommand;
import seedu.taskitty.logic.commands.HelpCommand;
import seedu.taskitty.logic.commands.UndoCommand;
import seedu.taskitty.logic.commands.ViewCommand;

import static seedu.taskitty.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import static org.junit.Assert.assertTrue;

public class ToolTipTest extends TaskManagerGuiTest {

    @Test
    public void validInput_commandBeginning() {
        assertToolTip("a", AddCommand.MESSAGE_USAGE);
        assertToolTip("v", ViewCommand.MESSAGE_USAGE);
        assertToolTip("f", FindCommand.MESSAGE_USAGE);
        assertToolTip("ed", EditCommand.MESSAGE_USAGE);
        assertToolTip("de", DeleteCommand.MESSAGE_USAGE);
        assertToolTip("do", DoneCommand.MESSAGE_USAGE);
        assertToolTip("u", UndoCommand.MESSAGE_USAGE);
        assertToolTip("c", ClearCommand.MESSAGE_USAGE);
        assertToolTip("h", HelpCommand.MESSAGE_USAGE);
        assertToolTip("ex", ExitCommand.MESSAGE_USAGE);
    }
    
    @Test
    public void validInput_commandFull() {
        assertToolTip("add test 12 oct 3pm", AddCommand.MESSAGE_USAGE);
        assertToolTip("edit e 1 hello", EditCommand.MESSAGE_USAGE);
    }
    
    @Test
    public void validInput_multipleMatch() {
        assertToolTip("e", buildToolTip("edit", "exit"));
        assertToolTip("d", buildToolTip("delete", "done"));
        assertToolTip("", buildToolTip());
    }
    
    @Test
    public void unknownCommand() {
        assertToolTip("adds", MESSAGE_UNKNOWN_COMMAND);
    }
    
    private String buildToolTip() {
        return buildToolTip(Command.ALL_COMMAND_WORDS);
    }
    
    private String buildToolTip(String... expectedCommandWords) {
        StringBuilder builder = new StringBuilder();
        builder.append(expectedCommandWords[0]);
        
        for (int i = 1; i < expectedCommandWords.length; i++) {
            builder.append(ToolTip.TOOLTIP_DELIMITER + expectedCommandWords[i]);
        }
        return builder.toString();
    }

    private void assertToolTip(String input, String expectedToolTip) {
        commandBox.enterCommand(input);

        //confirm the tooltip shown is correct
        assertTrue(expectedToolTip.equals(resultDisplay.getText()));
    }

}

package guitests;

import org.junit.Test;

import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.logic.commands.EditCommand;

import static org.junit.Assert.assertTrue;

public class ToolTipTest extends TaskManagerGuiTest {

    @Test
    public void validInput() {
        assertToolTip("a", AddCommand.MESSAGE_USAGE);
        assertToolTip("ed", EditCommand.MESSAGE_USAGE);
    }

    private void assertToolTip(String input, String expectedToolTip) {
        commandBox.enterCommand(input);

        //confirm the tooltip shown is correct
        assertTrue(expectedToolTip.equals(resultDisplay.getText()));
    }

}

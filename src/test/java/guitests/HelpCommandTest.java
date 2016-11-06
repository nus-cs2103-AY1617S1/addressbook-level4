package guitests;

//@@author A0135805H

import guitests.guihandles.HelpViewHandle;
import org.junit.Test;
import seedu.todo.logic.commands.CommandMap;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the display of help command.
 */
public class HelpCommandTest extends TodoListGuiTest {

    @Test
    public void help_showHelpPanelWithFull() {
        //Should display help with the complete "help" word.
        runCommand("help");
        assertHelpViewDisplayed();
    }

    @Test
    public void help_showHelpPanelWithShort() {
        //Should display help with an incomplete word of "help".
        runCommand("h");
        assertHelpViewDisplayed();
    }

    /**
     * Helper method to check if the help view is shown, and the content is correctly displayed.
     */
    private void assertHelpViewDisplayed() {
        HelpViewHandle handle = mainGui.getHelpView();
        assertTrue(handle.isVisible());
        assertTrue(handle.isHelpItemsDisplayedCorrectly(CommandMap.getAllCommandSummaryArray()));
    }
}

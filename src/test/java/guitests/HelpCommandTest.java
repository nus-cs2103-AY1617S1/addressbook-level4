package guitests;

import org.junit.Test;
import seedu.todo.logic.commands.CommandMap;
import seedu.todo.logic.commands.CommandSummary;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

//@@author A0135805H
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
        assertTrue(helpView.isVisible());
        assertTrue(helpView.isHelpItemsDisplayedCorrectly(getAllCommandSummaryArray()));
    }

    /* Helper Methods */
    /**
     * Get a representation of the command summary by a list of array strings, where each item has the
     * following format:
     *      [Scenario, Command, Arguments]
     */
    private List<String[]> getAllCommandSummaryArray() {
        return CommandMap.getAllCommandSummary().stream()
                .map(CommandSummary::toArray)
                .collect(Collectors.toList());
    }
}

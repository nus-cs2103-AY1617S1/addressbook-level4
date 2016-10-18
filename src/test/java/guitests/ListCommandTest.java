package guitests;

import org.junit.Test;

import harmony.mastermind.logic.commands.ListCommand;

import static harmony.mastermind.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    //@@author A0124797R
    public void list() {

        //start with the Home tab
        String targetTab = "Home";
        assertCurrentTab(targetTab);

        //list floating tasks
        targetTab = "Tasks";
        assertListSuccess(targetTab);

        //list events
        targetTab = "Events";
        assertListSuccess(targetTab);
        
        //list deadlines
        targetTab = "Deadlines";
        assertListSuccess(targetTab);

        //list archives
        targetTab = "Archives";
        assertListSuccess(targetTab);
        
        //ensure the list command is not case sensitive
        targetTab = "Events";
        commandBox.runCommand("list events");
        assertCurrentTab(targetTab);

        //list an invalid tab
        targetTab = "event";
        commandBox.runCommand("list " + targetTab);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    /**
     * Runs the list command to show the specific tab and confirms the current tab is correct.
     * param tab e.g. to show the Events tab, events should be given as the target tab.
     */
    //@@author A0124797R
    private void assertListSuccess(String targetTab) {
        commandBox.runCommand("list " + targetTab);

        //confirm the current view is in the correct tab
        assertCurrentTab(targetTab);
    }

}

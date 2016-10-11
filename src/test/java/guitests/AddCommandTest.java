package guitests;

import guitests.guihandles.ActivityCardHandle;
import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.logic.commands.AddCommand;
import seedu.manager.testutil.TestActivity;
import seedu.manager.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends ActivityManagerGuiTest {

    @Test
    public void add() {
        //add one activity
        TestActivity[] currentList = ta.getTypicalActivities();
        TestActivity activityToAdd = ta.groceries;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add another activity
        activityToAdd = ta.reading;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add duplicate activity
        // TODO: Re-implement only if duplicate entries are not allowed
        // commandBox.runCommand(ta.groceries.getAddCommand());
        // assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        // assertTrue(activityListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(ta.groceries);
        // TODO: check if groceries is correct

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        commandBox.runCommand(activityToAdd.getAddCommand());

        //confirm the new card contains the right data
        ActivityCardHandle addedCard = activityListPanel.navigateToPerson(activityToAdd.getName());
        assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestActivity[] expectedList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(expectedList));
    }

}

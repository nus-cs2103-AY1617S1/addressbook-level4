package guitests;

import guitests.guihandles.ActivityCardHandle;
import org.junit.Test;

import seedu.lifekeeper.commons.core.Messages;
import seedu.lifekeeper.logic.commands.AddCommand;
import seedu.lifekeeper.testutil.TestActivity;
import seedu.lifekeeper.testutil.TestEvent;
import seedu.lifekeeper.testutil.TestUtil;
import seedu.lifekeeper.testutil.TypicalTestActivities;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {

    	//section 1: test for adding basic activities, tasks and events.
    	
    	//add an activity
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity activityToAdd = TypicalTestActivities.findHoon;

        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        //add a task
        activityToAdd = TypicalTestActivities.findIda;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(currentList));
 
        //add an event
        activityToAdd = TypicalTestActivities.findJodie;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        //add an event without specifying end time
        activityToAdd = TypicalTestActivities.findKenny;
        assertAddSuccess(((TestEvent) activityToAdd).getAddCommandWithNoEndTime(),activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        //add a recurring event
        activityToAdd = TypicalTestActivities.findMoney;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        commandBox.runCommand("delete 1");

      //add a recurring event without specifying end time
        activityToAdd = TypicalTestActivities.findMoney;
        assertAddSuccess(((TestEvent) activityToAdd).getAddCommandWithNoEndTime(),activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        
        //section 2: test for adding duplicates of activities, tasks and events/
        
        //add duplicate activity
        commandBox.runCommand(TypicalTestActivities.findHoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(activityListPanel.isListMatching(currentList));

        //add duplicate task
        commandBox.runCommand(TypicalTestActivities.findIda.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        //add duplicate event
        commandBox.runCommand(TypicalTestActivities.findJodie.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(activityListPanel.isListMatching(currentList));

        //section 3:
 
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestActivities.findAlice.getAddCommand(),TypicalTestActivities.findAlice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //testing for date formatting
        commandBox.runCommand("");
        
    }

    private void assertAddSuccess(String command, TestActivity activityToAdd, TestActivity... currentList) {
    	commandBox.runCommand(command);
        //confirm the new card contains the right data
        ActivityCardHandle addedCard = activityListPanel.navigateToActivity(activityToAdd.getName().fullName);
        assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new activity
        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(expectedList));
    }

}

package guitests;

import guitests.guihandles.ActivityCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {

    	//section 1: test for adding basic activities, tasks and events.
    	
    	//add an activity
        TestActivity[] currentList = td.getTypicalPersons();
        TestActivity activityToAdd = td.findHoon;

         assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add a task
        activityToAdd = td.findIda;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(personListPanel.isListMatching(currentList));
 
        //add an event
        activityToAdd = td.findJodie;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);
    
        //section 2: test for adding duplicates of activities, tasks and events/
        
        //add duplicate activity
        commandBox.runCommand(td.findHoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add duplicate task
        commandBox.runCommand(td.findIda.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        //add duplicate event
        commandBox.runCommand(td.findJodie.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //section 3:
 
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.findAlice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //testing for date formatting
        commandBox.runCommand("");
        
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {


    	commandBox.runCommand(activityToAdd.getAddCommand());
        //confirm the new card contains the right data
        ActivityCardHandle addedCard = personListPanel.navigateToActivity(activityToAdd.getName().fullName);
        assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestActivity[] expectedList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}

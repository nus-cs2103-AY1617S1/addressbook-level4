package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS;

public class EditCommandTest extends AddressBookGuiTest {
    
    @Test
    public void edit() {

        TestTask[] currentList = td.getTypicalTasks();
        assertClearCommandSuccess();
       
        TestTask aliceTask = new TestTask(td.alice);
        assertAddSuccess(aliceTask);
        
        //test to check each parameter
        commandBox.runCommand("edit 1 Call Alice from 2pm to 3pm repeat every day -high");
        aliceTask.setName(new Name("Call Alice"));
        aliceTask.setStartDate(DateTime.convertStringToDate("2pm"));
        aliceTask.setEndDate(DateTime.convertStringToDate("3pm"));
        try {
            aliceTask.setRecurrence(new RecurrenceRate("1", "day"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceTask.setPriority(Priority.HIGH);        
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        //test to check that edit allows edit of combinations of parameters
        commandBox.runCommand("edit 1 Do stuff by 10pm -m");
        aliceTask.setName(new Name("Do stuff"));
        aliceTask.setEndDate(DateTime.convertStringToDate("10pm"));      
        aliceTask.setPriority(Priority.MEDIUM);       
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        //test to check reset function
        commandBox.runCommand("edit 1 -reset start end repeat");
        aliceTask.setStartDate(null);
        aliceTask.setEndDate(null);       
        aliceTask.setRecurrence(null);        
        assertTrue(personListPanel.isListMatching(aliceTask));

        //edit of priority parameter only
        commandBox.runCommand("edit 1 -low");
        aliceTask.setPriority(Priority.LOW);             
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        //ensure that reset command will overwrite any previous edit
        commandBox.runCommand("edit 1 from 10am -reset start");
        aliceTask.setStartDate(DateTime.convertStringToDate("10am"));
        aliceTask.setStartDate(null);
        assertTrue(personListPanel.isListMatching(aliceTask));

        //test to edit only the name
        commandBox.runCommand("edit 1 Trying out new things from the list");
        aliceTask.setName(new Name("Trying out new things from the list"));
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        
        commandBox.runCommand("edit 1 Visit distant relative at 1pm repeat every 3 years -h");
        aliceTask.setName(new Name("Visit distant relative"));
        aliceTask.setStartDate(DateTime.convertStringToDate("1pm"));
        try {
            aliceTask.setRecurrence(new RecurrenceRate("3", "years"));
        } catch (IllegalValueException e) {
            assert false : "The test data provided cannot be invalid";
        }
        aliceTask.setPriority(Priority.HIGH);        
        assertTrue(personListPanel.isListMatching(aliceTask));
        
        //invalid command format
        commandBox.runCommand("edit " + 10 + " " + "testing");
        assertResultMessage("The item index provided is invalid");

    }

    
    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = personListPanel.navigateToFloatingTask(personToAdd.getName().name);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addFloatingTasksToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    
    
    private void assertClearCommandSuccess() {
    	commandBox.runCommand("clear");
    	assertListSize(0);
    	assertResultMessage("Task Manager has been cleared!");
    }

}


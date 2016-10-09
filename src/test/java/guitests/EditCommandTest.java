package guitests;

import static org.junit.Assert.*;
import static seedu.jimi.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.task.Name;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;

public class EditCommandTest extends AddressBookGuiTest{

    @Test
    public void edit() throws IllegalValueException {
        TestFloatingTask[] currentList = td.getTypicalTasks();
        
        //edit the first task in list with all details
        int targetIndex = 1;
        
        //task to be changed to
        final String newName = "Get rich or die coding";
        TestFloatingTask expectedTask = new TestFloatingTask();
        expectedTask.setName(new Name(newName)); //set up newTask with changed name
        
        assertEditTaskSuccess(currentList, expectedTask, targetIndex);

        //edit the last task in list with only name changes
        currentList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex);
        targetIndex = currentList.length;
        assertEditTaskSuccess(currentList, expectedTask, targetIndex);

        //edit the middle task of the list with only name changes
        currentList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex);
        targetIndex = currentList.length/2;
        assertEditTaskSuccess(currentList, expectedTask, targetIndex);

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        //TODO: edit the first event in the list with all details
    }
    
    /**
     * Runs the edit command on the targetTask and checks if the edited task matches with changedTask.
     * Expected new task is to be at the top of the list. //TODO: change to match previous index of edited task
     * 
     * @param targetIndex Index of target task to be edited.
     * @param currentList List of tasks to be edited.
     * @throws IllegalValueException 
     */
    private void assertEditTaskSuccess(TestFloatingTask[] currentList,TestFloatingTask expectedTask, int targetIndex) throws IllegalValueException{

        //edit the name of the target task with the newName
        commandBox.runCommand("edit " + targetIndex + " Get rich or die coding");
        
        //confirm the list now contains all previous persons except the deleted person
        //TODO: change from checking last index to previous index of changed task
        assertTrue(taskListPanel.isListMatching(expectedTask));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, expectedTask));
    }

}

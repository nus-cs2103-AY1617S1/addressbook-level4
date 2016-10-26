package guitests;

import static org.junit.Assert.*;
import static seedu.jimi.logic.commands.EditCommand.MESSAGE_EDIT_SUCCESS;

import org.junit.Test;

import guitests.guihandles.FloatingTaskCardHandle;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.task.Name;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;
//@@author A0138915X
public class EditCommandTest extends AddressBookGuiTest{

    @Test
    public void edit() throws IllegalValueException {
        TestFloatingTask[] currentList = td.getTypicalTasks();
        
        //edit the first task in list with all details
        int targetIndex = 1;
        
        //task to be changed to
        String newName = "Get rich or die coding";

        //edit the first task in list with only name changes
        assertEditTaskSuccess(currentList, targetIndex, newName);
        
        // edit last task in list with only name changes
        newName = "Don't die poor";
        targetIndex = currentList.length;
        assertEditTaskSuccess(currentList, targetIndex, newName);

        //invalid index
        commandBox.runCommand("edit t" + currentList.length + 1 + " \"Change invalid stuff\"");
        assertResultMessage("The task index provided is invalid");
        
        //TODO: edit the first event in the list with all details
    }
    
    /**
     * Runs the edit command on the targetTask and checks if the edited task matches with changedTask.
     * Expected new task is to be at the top of the list. 
     * 
     * @param targetIndex Index of target task to be edited.
     * @param currentList List of tasks to be edited.
     * @throws IllegalValueException 
     */
    private void assertEditTaskSuccess(TestFloatingTask[] currentList, int targetIndex, String newName) throws IllegalValueException{

        //task to be replaced with
        TestFloatingTask expectedTask = new TestFloatingTask();
        expectedTask.setName(new Name(newName));
        
        if (targetIndex <= currentList.length && targetIndex > 0) {
            expectedTask.setTags(currentList[targetIndex - 1].getTags());
        }
        
        expectedTask.setPriority(currentList[targetIndex - 1].getPriority());
        
        //edit the name of the target task with the newName
        commandBox.runCommand("edit " + "t" + targetIndex + " \"" + newName + "\" ");
        
        //confirm the edited card contains the right data
        FloatingTaskCardHandle addedCard = taskListPanel.navigateToTask(expectedTask.getName().fullName);
        assertMatching(expectedTask, addedCard);

        //confirm the list now contains all previous tasks plus the edited task
        TestFloatingTask[] expectedList = TestUtil.replaceTaskFromList(currentList, expectedTask, targetIndex - 1);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_SUCCESS, expectedTask));
    }

}

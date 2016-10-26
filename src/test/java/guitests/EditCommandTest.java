package guitests;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Description;
import seedu.task.model.task.Title;

import static org.junit.Assert.assertTrue;

public class EditCommandTest extends TaskManagerGuiTest {
	private TestTask[] backup;
	
	@Test
	public void edit() {
		TestTask[] currentList = td.getTypicalTasks();
		
		assertEditTitleSuccess(1, currentList, "NEWNAME");
		assertMultipleParametersSuccess(7, currentList, "NEWNAME", "NEWDESC");

	}
	
	/**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before edit).
     * @param name the new task title
     */
    private void assertEditTitleSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String name) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask expectedEditedTask = new TestTask();
        try {
			expectedEditedTask.setTitle(new Title(name));
		} catch (IllegalValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        expectedEditedTask.setDescription(taskToEdit.getDescription());
        expectedEditedTask.setDueDate(taskToEdit.getDueDate());
        expectedEditedTask.setInterval(taskToEdit.getInterval());
        expectedEditedTask.setStartDate(taskToEdit.getStartDate());
        expectedEditedTask.setStatus(taskToEdit.getStatus());
        expectedEditedTask.setTimeInterval(taskToEdit.getTimeInterval());
        backup = currentList;
        backup[targetIndexOneIndexed-1] = expectedEditedTask;
        TestTask[] expectedNewList = backup;

        commandBox.runCommand("edit " + targetIndexOneIndexed + " t/" + name);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedNewList));

        //confirm the result message is correct
        assertResultMessage(String.format("The data has been successfully edited.", taskToEdit));
    }
    
    private void assertMultipleParametersSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String name, String desc) {
    	TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask expectedEditedTask = new TestTask();
        try {
			expectedEditedTask.setTitle(new Title(name));
			expectedEditedTask.setDescription(new Description(desc));
		} catch (IllegalValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        expectedEditedTask.setDueDate(taskToEdit.getDueDate());
        expectedEditedTask.setInterval(taskToEdit.getInterval());
        expectedEditedTask.setStartDate(taskToEdit.getStartDate());
        expectedEditedTask.setStatus(taskToEdit.getStatus());
        expectedEditedTask.setTimeInterval(taskToEdit.getTimeInterval());
        backup = currentList;
        backup[targetIndexOneIndexed-1] = expectedEditedTask;
        TestTask[] expectedNewList = backup;
        
        commandBox.runCommand("edit " + targetIndexOneIndexed + " d/" + desc + " t/" + name);
        
        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedNewList));
        
        //confirm the result message is correct
        assertResultMessage(String.format("The data has been successfully edited.", taskToEdit));
    }

}

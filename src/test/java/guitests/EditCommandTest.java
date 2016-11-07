package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.toDoList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.model.task.Deadline;
import seedu.toDoList.model.task.EventDate;
import seedu.toDoList.model.task.Name;
import seedu.toDoList.model.task.ReadOnlyTask;
import seedu.toDoList.testutil.TestTask;

//@@author A0138717X

public class EditCommandTest extends TaskManagerGuiTest {

    //test for use scenario 1: edit by a unique name in the toDoList
	@Test
    public void editByName_successful() throws IllegalValueException {
		TestTask[] currentList = td.getTypicalTasks();
		currentList[6].setDate(new EventDate("12.10.2016-10","11.10.2016-12"));
		assertEditSuccess("Project meeting","s/","12.10.2016-10", currentList);

		
		currentList[3].setName(new Name("Borrow a book from library"));
		assertEditSuccess("Read book","n/","Borrow a book from library", currentList);
	}

	//test for use scenario 2: edit by a index in the last shown list
	@Test
    public void editByIndex_successful() throws IllegalValueException{
		TestTask[] currentList = td.getTypicalTasks();
		currentList[1].setDate(new Deadline("20.10.2016"));
		assertEditSuccess("Meet old friends","d/","20.10.2016",2,currentList);
	}

    /**
     * Runs the edit command to edit the task or event using a unique name and confirms the result is correct.
     * @param name The name of a task or event that is to be edited
     * @param type The type of field that is to be edited e.g. to edit a name field, /n should be given as the type
     * @param details The values that is be replaced to the current value
     * @param currentList A copy of the current list of tasks (before making any changes).
     */
	private void assertEditSuccess(String name, String type, String details, TestTask[] currentList) {
		commandBox.runCommand("edit "+ name + " " + type + details);
		if(type.equals("e/") || type.equals("d/") || type.equals("s/"))
			taskListPanel.navigateToTask(name).getDate().equals(details);
		else if(type.equals("n/"))
			taskListPanel.navigateToTask(details).getName().equals(details);
		else
			assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
	}

    /**
     * Runs the edit command to edit the task or event at specified index and confirms the result is correct.
     * @param name The name of a task or event that is to be edited
     * @param type The type of field that is to be edited e.g. to edit a name field, /n should be given as the type
     * @param details The values that is be replaced to the current value
     * @param index e.g. to edit the first task in the list, 1 should be given as the index.
     * @param currentList A copy of the current list of tasks (before making any changes).
     */
	private void assertEditSuccess(String name, String type, String details, int index, TestTask[] currentList) {
		commandBox.runCommand("edit "+ name + " " + type + details + " i/" + index);
		if(type.equals("e/") || type.equals("d/") || type.equals("s/"))
			taskListPanel.getTask(index).getDate();
		else if(type.equals("n/"))
			taskListPanel.getTask(index).getName();
		else
			assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
    }
	
}

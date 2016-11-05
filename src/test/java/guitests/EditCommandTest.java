package guitests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Name;
import seedu.address.testutil.TestTask;

//@@author A0138717X
public class EditCommandTest extends TaskManagerGuiTest {

	@Test
    public void edit_by_name() throws IllegalValueException {
		TestTask[] currentList = td.getTypicalTasks();
		currentList[6].setDate(new EventDate("12.10.2016-10","11.10.2016-12"));
		assertEditSuccess("Project meeting","s/","12.10.2016-10", currentList);
		currentList[3].setName(new Name("Borrow a book from library"));
		assertEditSuccess("Read book","n/","Borrow a book from library", currentList);
	}

	@Test
    public void edit_by_index() throws IllegalValueException{
		TestTask[] currentList = td.getTypicalTasks();
		currentList[1].setDate(new Deadline("20.10.2016"));
		assertEditSuccess("Meet old friends","d/","20.10.2016",2,currentList);
	}

	private void assertEditSuccess(String name, String type, String details, TestTask[] currentList) {
		commandBox.runCommand("edit "+ name + " " + type + details);
		if(type.equals("e/") || type.equals("d/") || type.equals("s/"))
			taskListPanel.navigateToTask(name).getDate().equals(details);
		else if(type.equals("n/"))
			taskListPanel.navigateToTask(details).getName().equals(details);
		else
			assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
	}


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

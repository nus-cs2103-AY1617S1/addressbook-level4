package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.model.task.TaskDate;

public class ListCommandTest extends AddressBookGuiTest {

	@Test
	public void listAll() {
		TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
		assertFindCommandSuccess("list", currentList);
	}
	
	@Test
	public void listToday() {
		TestTaskList currentList = new TestTaskList(new TestTask[] {td.eventWithoutParameters});
		assertFindCommandSuccess("list today", currentList);
	}
	
	@Test
	public void listWeek() {
		TestTaskList currentList = new TestTaskList(new TestTask[] {td.eventWithoutParameters, td.eventWithLocation});
		assertFindCommandSuccess("list week", currentList);
	}
	
	@Test
	public void listMonth() {
		TestTaskList currentList = new TestTaskList(new TestTask[] {td.eventWithoutParameters, td.eventWithLocation});
		assertFindCommandSuccess("list month", currentList);
	}
	
	@Test
	public void listValidDate() {
		TestTaskList currentList = new TestTaskList(new TestTask[] {td.eventWithRemarks, td.eventWithLocationAndRemarks});
		assertFindCommandSuccess("list 28 dec 2016", currentList);
	}
	
	@Test
	public void listInvalidDate() {
		commandBox.runCommand("list 32 oct 2016");
		assertResultMessage(TaskDate.MESSAGE_DATE_INVALID);
	}
	
	@Test
	public void listInvalidFilter() {
		commandBox.runCommand("list tomorrow");
		assertResultMessage(ListCommand.MESSAGE_FILTER_INVALID);
	}
	
	@Test
	public void listInvalidCommand() {
		commandBox.runCommand("listtoday");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}
	
	private void assertFindCommandSuccess(String command, TestTaskList expectedList) {
		commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedList.getCompleteList()));
        if ("list".equals(command)) {
        	assertResultMessage(ListCommand.MESSAGE_ALLTASKS_SUCCESS);
        }
        else {
        	assertResultMessage(ListCommand.MESSAGE_FILTER_SUCCESS);
        }
	}
}

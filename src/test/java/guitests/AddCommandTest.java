package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.task.testutil.TestUtil;
import seedu.todolist.commons.core.Messages;

import static org.junit.Assert.assertTrue;

//@@author A0138601M
public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        TestTask taskToAdd = td.event;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.deadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        taskToAdd = td.eventWithLocationAndRemarks;
        currentList.clear();
        assertAddSuccess(taskToAdd, currentList);

        //invalid command
        commandBox.runCommand("adds invalidcommand");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTaskList currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTaskList expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList.getIncompleteList()));
        
        //confirm no new task is added to completed pane
        assertCompleteListSize(0);
    }

}

package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.AddCommand;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task8;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.task9;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.task8.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.task1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("add task1 s/12129999 e/01010000 ");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_TIME_ENTRY);
        commandBox.runCommand("add task2 s/2359 e/0000 ");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_TIME_ENTRY);
        commandBox.runCommand("add task3 s/01019999 0000 e/01019999 0000");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_TIME_ENTRY);
        commandBox.runCommand("add task3 s/01019999 2359 e/01019999 0000");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_TIME_ENTRY);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTitle().fullTitle);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
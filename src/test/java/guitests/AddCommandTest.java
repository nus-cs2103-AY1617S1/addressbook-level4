package guitests;

import guitests.guihandles.TaskCardHandle;
import seedu.todoList.commons.core.Messages;
import seedu.todoList.logic.commands.*;
import seedu.todoList.testutil.TestUtil;
import seedu.todoList.testutil.TestTask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TodoListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicaltasks();
        TestTask taskToAdd = td.a6;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.a7;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.a6.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.a1);

        //invalid command
        commandBox.runCommand("adds assignment 66");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getTodo());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}

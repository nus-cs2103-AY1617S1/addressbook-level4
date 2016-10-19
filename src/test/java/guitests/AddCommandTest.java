package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.AddCommand;
import seedu.taskitty.testutil.TestTask;
import seedu.taskitty.testutil.TestTaskList;
import seedu.taskitty.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        TestTask taskToAdd = td.todo;
        assertAddSuccess(taskToAdd, td.todo.getNumArgs(), currentList);

        //add another task
        taskToAdd = td.deadline;
        assertAddSuccess(taskToAdd, td.deadline.getNumArgs(), currentList);

        //add duplicate task
        commandBox.runCommand(td.deadline.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(currentList.isListMatching(taskListPanel));

        //add to empty list
        commandBox.runCommand("clear");
        currentList.clear();
        assertAddSuccess(td.deadline, td.deadline.getNumArgs(), currentList);

        //invalid command
        commandBox.runCommand("adds party");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, int type, TestTaskList currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName, type);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        currentList.addTaskToList(taskToAdd);
        assertTrue(currentList.isListMatching(taskListPanel));
    }

}

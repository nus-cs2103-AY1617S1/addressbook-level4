//@@author A0144919W
package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;
import seedu.tasklist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.task8;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.task9;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add overlapping task
        taskToAdd = TypicalTestTasks.task8;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS + AddCommand.MESSAGE_OVERLAP, taskToAdd.toString()));
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.task1);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
        //confirm if add message is returned
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
    }

}

package guitests;

import guitests.guihandles.TaskRowHandle;
import seedu.taskman.commons.core.Messages;
import seedu.taskman.logic.commands.AddCommand;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.Task;
import seedu.taskman.testutil.TestTask;
import seedu.taskman.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddCommandTest extends TaskManGuiTest {

    //@Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.taskCS2102;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.taskCS2104;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.taskCS2102.getAddCommand());
        Activity[] expectedList = new Activity[currentList.length];
        for(int i = 0; i < expectedList.length; i++){
            expectedList[i] = new Activity(new Task(currentList[i]));
        }
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(taskListPanel.isListMatching(expectedList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.taskCS2101);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new row contains the right data
        TaskRowHandle addedRow = taskListPanel.navigateToTask(taskToAdd.getTitle().title);
        assertMatching(new Activity(new Task(taskToAdd)), addedRow);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        Activity[] expectedActivityList = new Activity[currentList.length];
        for(int i = 0; i < expectedActivityList.length; i++){
            expectedActivityList[i] = new Activity(new Task(expectedList[i]));
        }
        assertTrue(taskListPanel.isListMatching(expectedActivityList));
    }

}

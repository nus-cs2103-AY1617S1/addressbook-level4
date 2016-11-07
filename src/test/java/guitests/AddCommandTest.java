package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;
import harmony.mastermind.testutil.TypicalTestTasks;

public class AddCommandTest extends TaskManagerGuiTest {

    //@@author A0124797R
    @Test
    public void add() {
        //add one floating task with tags
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.task5;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another floating task without tags
        taskToAdd = TypicalTestTasks.task6;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.task3);

        //invalid command
        commandBox.runCommand("adds Laundry");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND+": adds Laundry");
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
        
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

    }

}

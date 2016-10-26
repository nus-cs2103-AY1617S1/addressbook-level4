package guitests;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.logic.commands.AddCommand;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {
/*
    @Test
    //@@author A0124797R
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task5;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.task6;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
//        commandBox.runCommand(td.task5.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.task3);

        //invalid command
        commandBox.runCommand("adds Laundry");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND+": adds Laundry");
    }

    //@@author A0124797R
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
        
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

    }
*/
}

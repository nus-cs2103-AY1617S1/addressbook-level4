package guitests;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.logic.commands.AddCommand;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    //@@author A0124797R
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task5;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.out.println("test1");

        //add another task
        taskToAdd = td.task6;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.out.println("test2");

        //add duplicate task
//        commandBox.runCommand(td.task5.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.task3);
        System.out.println("test3");

        //invalid command
        commandBox.runCommand("adds Laundry");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        System.out.println("test4");
    }

    //@@author A0124797R
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        System.out.println("inTest1");
        commandBox.runCommand(taskToAdd.getAddCommand());
        System.out.println("inTest2");
        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.out.println("inTest3");
        assertTrue(taskListPanel.isListMatching(expectedList));
        System.out.println("inTest4");
    }

}

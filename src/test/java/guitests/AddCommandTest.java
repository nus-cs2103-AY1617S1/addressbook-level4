package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
    	//TODO
    	/*
        //add one task 
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.task1;




        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.task2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(TypicalTestTasks.task1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.task3);

        //invalid command
        commandBox.runCommand("adds Homework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        */
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

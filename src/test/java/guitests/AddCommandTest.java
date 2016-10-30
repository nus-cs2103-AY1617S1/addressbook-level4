package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.gtd.commons.core.Messages;
import seedu.gtd.logic.commands.AddCommand;
import seedu.gtd.testutil.TestTask;
import seedu.gtd.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {
	
    @Test
    public void add() {
        //add one task
    	//add success modified takes into account date format changes when using Natty
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        TestTask taskChanged = td.hoonChanged;
        assertAddSuccessModified(taskToAdd, taskChanged, currentList);
        //assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskChanged);
        System.out.println("HOON ADDED");

        //add another task
        taskToAdd = td.ida;
        taskChanged = td.idaChanged;
        assertAddSuccessModified(taskToAdd, taskChanged, currentList);
        //assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskChanged);
        System.out.println("IDA ADDED");

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        System.out.println(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        System.out.println("DUPLICATE REJECTED");

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccessModified(td.alice, td.aliceChanged);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        System.out.println("FULL NAME: " + taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddSuccessModified(TestTask taskToAdd, TestTask taskChanged, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskChanged, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskChanged);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}

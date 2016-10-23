package guitests;

//@@author A0147967J-reused
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.model.task.TaskComponent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskMasterGuiTest {

    @Test
    public void add() {
        //add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another floatingTask
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(currentList)));
        
        //@@author A0147967J
        //add one non-floating task
        taskToAdd = td.project;
        assertAddNonFloatingSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add deadline task
        taskToAdd = td.paper;
        assertAddNonFloatingSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add task with overlapping slot allowed
        taskToAdd = td.movie;
        assertAddNonFloatingSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add task with illegal time slot
        commandBox.runCommand("add illegal timeslot from 2 oct 2pm to 2 oct 1pm");
        assertResultMessage(AddNonFloatingCommand.MESSAGE_ILLEGAL_TIME_SLOT);
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(currentList)));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.trash);
        currentList = new TestTask[]{td.trash};

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //======Cases for handling recurring tasks==================================================
        
        //Out dated Recurring task got updated
        taskToAdd = td.daily;
        assertAddCommandSuccess("add Daily Task from yesterday 8pm to yesterday 11pm daily", 
        		taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //Up to date remain stayed
        taskToAdd = td.weekly;
        assertAddCommandSuccess(taskToAdd.getAddRecurringCommand(), 
        		taskToAdd, currentList);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
    	assertAddCommandSuccess(taskToAdd.getAddFloatingCommand(), taskToAdd, currentList);
    }
    
    private void assertAddNonFloatingSuccess(TestTask taskToAdd, TestTask... currentList) {
        assertAddCommandSuccess(taskToAdd.getAddNonFloatingCommand(), taskToAdd, currentList);
    }
    
    private void assertAddCommandSuccess(String command, TestTask taskToAdd, TestTask... currentList){
    	
    	commandBox.runCommand(command);
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd.getTaskDateComponent().get(0), addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        TaskComponent[] taskComponents = TestUtil.convertTasksToDateComponents(expectedList);
        assertTrue(taskListPanel.isListMatching(taskComponents));
    }
}

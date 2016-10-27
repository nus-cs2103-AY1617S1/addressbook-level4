package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.AddCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

//@@author A0139661Y
public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.car;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.dog;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add task with date/time range
        taskToAdd = td.vacation;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //invalid detail parameter
        commandBox.runCommand("add 'ppp");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("add ppp'");
        assertResultMessage(Messages.MESSAGE_ENCAPSULATE_DETAIL_WARNING);
        commandBox.runCommand("add ''");
        assertResultMessage(Messages.MESSAGE_BLANK_DETAIL_WARNING);
        
        //invalid priority parameter
        commandBox.runCommand("add 'new' /yolo");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY);
        commandBox.runCommand("add 'new'/high");
        assertResultMessage(Messages.MESSAGE_INVALID_PRIORITY_SPACE);
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.house);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        if (taskToAdd.getDueByDate().isRange() || taskToAdd.getDueByTime().isRange())
        	commandBox.runCommand(taskToAdd.getAddRangeCommand());
        else commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDetail().details);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        //confirm
        assertResultMessage(String.format(MESSAGE_SUCCESS,taskToAdd));
    }

}

package guitests;

import guitests.guihandles.TaskCardHandle;

import org.junit.Test;

import seedu.savvytasker.logic.commands.AddCommand;
import seedu.savvytasker.logic.commands.HelpCommand;
import seedu.savvytasker.testutil.TestTask;
import seedu.savvytasker.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.savvytasker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

//@@author A0139915W
public class AddCommandTest extends SavvyTaskerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.happy;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.haloween;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.highPriority);

        //invalid command
        commandBox.runCommand("adds Bad Command Task");
        assertResultMessage(String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
        
        //invalid start end date
        commandBox.runCommand("add bad start-end pair s/31-12-2015 e/30-12-2015");
        assertResultMessage(String.format(AddCommand.MESSAGE_INVALID_START_END));
        
        commandBox.runCommand("clear");
        //add recurring tasks
        commandBox.runCommand("add recurring yall s/04-11-2016 e/05-11-2016 l/home r/daily p/high n/5 c/recurs d/AHAHA");
        assertResultMessage("New task added:  Id: 0 Task Name: recurring yall Archived: false Start: Fri Nov 04 00:00:00 SGT 2016 End: Sat Nov 05 23:59:59 SGT 2016 Location: home Priority: High Category: recurs Description: AHAHA");
        
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
//@@author

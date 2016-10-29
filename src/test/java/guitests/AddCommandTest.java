package guitests;

import guitests.guihandles.TaskCardHandle;

import org.junit.Test;

import seedu.savvytasker.logic.commands.AddCommand;
import seedu.savvytasker.logic.commands.HelpCommand;
import seedu.savvytasker.testutil.TestTask;
import seedu.savvytasker.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.savvytasker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Date start = getDate("31/12/2015");
        Date end = getDate("30/12/2015");
        commandBox.runCommand("add bad start-end pair s/" + getLocaleDateString(start) + 
                " e/" + getLocaleDateString(end));
        assertResultMessage(String.format(AddCommand.MESSAGE_INVALID_START_END));
        
        commandBox.runCommand("clear");
        //add recurring tasks
        start = getDate("04/11/2016");
        end = getDate("05/11/2016");
        commandBox.runCommand("add recurring yall s/" + getLocaleDateString(start) + 
                " e/" + getLocaleDateString(end) + " l/home r/daily p/high n/5 c/recurs d/AHAHA");
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


    private DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private String getLocaleDateString(Date date) {
        try {
            return formatter.format(date);
        } catch (Exception e) {
            assert false; //should not get an invalid date....
        }
        return null;
    }

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Date getDate(String ddmmyyyy) {
        try {
            return format.parse(ddmmyyyy);
        } catch (Exception e) {
            assert false; //should not get an invalid date....
        }
        return null;
    }
}
//@@author

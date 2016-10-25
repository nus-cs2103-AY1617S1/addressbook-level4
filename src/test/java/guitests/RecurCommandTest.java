package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import seedu.taskscheduler.logic.commands.CommandHistory;
import seedu.taskscheduler.logic.commands.RecurCommand;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.testutil.TestUtil;

//@@author A0148145E

public class RecurCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void add() {
        
        //invalid recur task
        CommandHistory.setModTask(null);
        commandBox.runCommand("recur every 3 days until next week");
        assertResultMessage(RecurCommand.MESSAGE_MISSING_TASK);
        
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(td.event.getAddCommand());
        
        //recur without index
        commandBox.runCommand("recur every 3 days until next week");
        long dateInterval = 3 * 24 * 3600 * 1000; // 3 days
        long dateLimit = 7 * 24 * 3600 * 1000; // 1 week

        currentList = TestUtil.addTasksToList(currentList, td.event);
        currentList = generateExpectedList(td.event, currentList, dateInterval, dateLimit);
        assertTrue(taskListPanel.isListMatching(currentList));
        
        //assert undo works for recur command
        commandBox.runCommand("undo");
        currentList = td.getTypicalTasks();
        currentList = TestUtil.addTasksToList(currentList, td.event);
        assertTrue(taskListPanel.isListMatching(currentList));

        //recur with index
        commandBox.runCommand("recur 1 every 3 days until next week");
        dateInterval = 3 * 24 * 3600 * 1000; // 3 days
        Date dateNow = new Date();
        Date taskDate = td.alice.getEndDate().getDate();
        dateLimit = dateNow.getTime() - taskDate.getTime() + 7 * 24 * 3600 * 1000l; // 1 week later
        
        currentList = generateExpectedList(td.alice, currentList, dateInterval, dateLimit);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private TestTask[] generateExpectedList(TestTask task, TestTask[] currentList, long dateInterval, long dateLimit) {
        for (long i = dateInterval; i <= dateLimit; i += dateInterval) {
            TestTask taskToAdd = task.copy();
            taskToAdd.addDuration(i);
            currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        }
        return currentList;
    }
}

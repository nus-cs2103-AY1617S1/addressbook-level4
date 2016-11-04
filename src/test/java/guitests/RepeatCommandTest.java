package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.task.logic.commands.RepeatCommand.MESSAGE_REPEAT_TASK_SUCCESS;
import static seedu.task.logic.commands.RepeatCommand.MESSAGE_INVALID_INTERVAL;

public class RepeatCommandTest extends TaskManagerGuiTest {

    @Test
    public void repeat() {

        // repeat the first in the list daily
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertRepeatSuccess(targetIndex, currentList, "daily");
        
        // repeat the first in the list weekly
        assertRepeatSuccess(targetIndex, currentList, "weekly");
        
        // repeat the first in the list fortnightly
        assertRepeatSuccess(targetIndex, currentList, "fortnightly");
        
        // repeat the first in the list monthly
        assertRepeatSuccess(targetIndex, currentList, "monthly");
        
        // repeat the first in the list yearly
        assertRepeatSuccess(targetIndex, currentList, "yearly");
        
        // stop repeating the task
        assertRepeatSuccess(targetIndex, currentList, "stop");
        
        // repeat the first in the list daily
        assertRepeatSuccess(targetIndex, currentList, "d");
        
        // repeat the first in the list weekly
        assertRepeatSuccess(targetIndex, currentList, "w");
        
        // repeat the first in the list fortnightly
        assertRepeatSuccess(targetIndex, currentList, "f");
        
        // repeat the first in the list monthly
        assertRepeatSuccess(targetIndex, currentList, "m");
        
        // repeat the first in the list yearly
        assertRepeatSuccess(targetIndex, currentList, "y");
        
        // stop repeating the task
        assertRepeatSuccess(targetIndex, currentList, "end");
        
        //incorrect index
        assertRepeatWrongIndexFailure(50, currentList, "daily");
        
        //incorrect interval
        assertRepeatWrongIntervalFailure(targetIndex, currentList, "incorrect interval");
    }

    /**
     * Runs the repeat command to delete the task at specified index and
     * confirms the result is correct.
     * 
     * @param targetIndexOneIndexed
     *            e.g. to repeat the first task in the list, 1 should be given
     *            as the target index.
     * @param currentList
     *            A copy of the current list of tasks (before repeat command).
     */
    private void assertRepeatSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String input) {
        TestTask taskToRepeat = currentList[targetIndexOneIndexed - 1]; // -1 because array uses zero indexing

        // confirm initial recurring parameter for task is different
        assertFalse(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals(input));

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // change value of input as command may store data as different value for "stop" input
        if (input.equals("stop") || input.equals("end")) {
            input = "false";
        } else if (input.equals("d")) {
            input = "daily";
        } else if (input.equals("w")) {
            input = "weekly";
        } else if (input.equals("f")) {
            input = "fortnightly";
        } else if (input.equals("m")) {
            input = "monthly";
        } else if (input.equals("y")) {
            input = "yearly";
        }

        // task
        assertTrue(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals(input));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_REPEAT_TASK_SUCCESS, taskToRepeat.getName().toString(), input));
    }
    
    private void assertRepeatWrongIndexFailure(int targetIndexOneIndexed, final TestTask[] currentList, String input) {

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);
        
        // confirm the result message is correct
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    private void assertRepeatWrongIntervalFailure(int targetIndexOneIndexed, final TestTask[] currentList, String input) {

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_INVALID_INTERVAL, input));
    }

}

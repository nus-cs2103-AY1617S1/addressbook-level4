package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.task.logic.commands.RepeatCommand.MESSAGE_REPEAT_TASK_SUCCESS;

import java.util.concurrent.ThreadLocalRandom;

import static seedu.task.logic.commands.RepeatCommand.MESSAGE_INVALID_INTERVAL;

// @@author A0147944U
public class RepeatCommandTest extends TaskManagerGuiTest {

    @Test
    public void repeat() {
        
        TestTask[] currentList = td.getTypicalTasks();

        // repeat a random task in the list daily
        int targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "daily");

        // repeat a random task in the list weekly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "weekly");

        // repeat a random task in the list fortnightly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "fortnightly");

        // repeat a random task in the list monthly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "monthly");

        // repeat a random task in the list yearly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "yearly");

        // stop repeating the task that was just repeated
        assertRepeatSuccess(targetIndex, currentList, "stop");

        // repeat a random task in the list daily
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "d");

        // repeat a random task in the list weekly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "w");

        // repeat a random task in the list fortnightly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "f");

        // repeat a random task in the list monthly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "m");

        // repeat a random task in the list yearly
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatSuccess(targetIndex, currentList, "y");

        // stop repeating the task that was just repeated
        assertRepeatSuccess(targetIndex, currentList, "end");

        // incorrect index
        assertRepeatWrongIndexFailure(50, currentList, "daily");

        // incorrect interval
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
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
        if (input.equals("stop") || input.equals("end")) {
            assertFalse(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals("false"));
        } else {
            assertFalse(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals(input));
        }

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // change value of input as command may store data as different value
        // for "stop" input
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

    private void assertRepeatWrongIntervalFailure(int targetIndexOneIndexed, final TestTask[] currentList,
            String input) {

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_INVALID_INTERVAL, input));
    }

}

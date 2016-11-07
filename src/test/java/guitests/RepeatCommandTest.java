package guitests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.RepeatCommand;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.task.logic.commands.RepeatCommand.MESSAGE_REPEAT_TASK_SUCCESS;

import java.util.concurrent.ThreadLocalRandom;

import static seedu.task.logic.commands.RepeatCommand.MESSAGE_INVALID_INTERVAL;

// @@author A0147944U
public class RepeatCommandTest extends TaskManagerGuiTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void repeatCommandTest() {

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
        assertRepeatWrongIndexFailure(50, "daily");

        // incorrect interval
        targetIndex = ThreadLocalRandom.current().nextInt(1, (currentList.length + 1));
        assertRepeatWrongIntervalFailure(targetIndex, "incorrect interval");

    }

    /**
     * Check if a recurring task will correctly repeat itself. If it does repeat
     * itself, getTask(1) will give IndexOutOfBoundsException.
     */
    @Test
    public void recurringTaskFunctionality() {
        commandBox.runCommand("clear");
        commandBox.runCommand("add task to repeat, at now");
        commandBox.runCommand("repeat 1 weekly");
        commandBox.runCommand("done 1");
        assertTrue(taskListPanel.getTask(0).getStartTime().compareTo(taskListPanel.getTask(1).getStartTime()) < 0);
    }

    /**
     * Check if parser correctly rejects repeat command on floating tasks.
     */
    @Test
    public void rejectRepeatOnFloatingTask() {
        commandBox.runCommand("clear");
        commandBox.runCommand("add floating task");
        commandBox.runCommand("repeat 1");
        assertResultMessage(RepeatCommand.MESSAGE_INVALID_FOR_FLOATING_TASK);
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
        // -1 because array uses zero indexing
        TestTask taskToRepeat = currentList[targetIndexOneIndexed - 1];
        String parsedInput = input;

        // confirm initial recurring parameter for task is different
        if ("stop".equals(parsedInput) || "end".equals(parsedInput)) {
            assertFalse(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals("false"));
        } else {
            assertFalse(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals(parsedInput));
        }

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + parsedInput);

        // change value of input as command may store data as different value
        // for "stop" input
        if ("stop".equals(parsedInput) || "end".equals(parsedInput)) {
            parsedInput = "false";
        } else if ("d".equals(parsedInput)) {
            parsedInput = "daily";
        } else if ("w".equals(parsedInput)) {
            parsedInput = "weekly";
        } else if ("f".equals(parsedInput)) {
            parsedInput = "fortnightly";
        } else if ("m".equals(parsedInput)) {
            parsedInput = "monthly";
        } else if ("y".equals(parsedInput)) {
            parsedInput = "yearly";
        }

        // task
        assertTrue(taskListPanel.getTask(targetIndexOneIndexed - 1).getRecurring().toString().equals(parsedInput));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_REPEAT_TASK_SUCCESS, taskToRepeat.getName().toString(), parsedInput));
    }

    private void assertRepeatWrongIndexFailure(int targetIndexOneIndexed, String input) {

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // confirm the result message is correct
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertRepeatWrongIntervalFailure(int targetIndexOneIndexed, String input) {

        commandBox.runCommand("repeat " + targetIndexOneIndexed + " " + input);

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_INVALID_INTERVAL, input));
    }

}

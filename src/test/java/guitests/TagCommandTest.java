package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.taskscheduler.logic.commands.TagCommand.MESSAGE_SUCCESS;

//@@author A0148145E

public class TagCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void tag() {

        //put a single tag
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String tagArgs = "Priority";
        assertTagSuccess(targetIndex, tagArgs, currentList);

        //replace with multiple tags
        tagArgs = "School Urgent";
        assertTagSuccess(targetIndex, tagArgs, currentList);

        //invalid index
        commandBox.runCommand("tag " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    /**
     * Runs the tag command to rag the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to tag the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks.
     */
    private void assertTagSuccess(int targetIndexOneIndexed, String tagArgs, final TestTask[] currentList) {
        TestTask taskToTag = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing

        commandBox.runCommand("tag " + targetIndexOneIndexed + " " + tagArgs);

        System.out.println(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getTags());
        System.out.println(convertArgsToTagString(tagArgs));
        assertTrue(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getTags()
                .equals(convertArgsToTagString(tagArgs)));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, taskToTag));
    }
    
    private String convertArgsToTagString(String tagArgs) {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        for (String tag : tagArgs.split("\\s+")) {
            buffer.append("[" + tag + "]").append(separator);
        }
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
}


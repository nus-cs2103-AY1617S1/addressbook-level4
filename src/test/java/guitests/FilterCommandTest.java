package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.task.Deadline;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0146123R
/**
 * gui tests for filter command.
 */
public class FilterCommandTest extends TaskManagerGuiTest {

    @Test
    public void filter_nonEmptyList_success() {
        assertFilterResult("filter d/12.10.2016"); // no results
        assertFilterResult("filter d/11.10.2016", td.friendEvent, td.lunch, td.work, td.movie); 
        assertFilterResult("filter s/11.10.2016", td.meeting, td.travel);
        assertFilterResult("filter e/11.10.2016-12", td.meeting);
        assertFilterResult("filter t/friends", td.friend, td.friendEvent, td.lunch);
        assertFilterResult("filter p/1", td.lunch);

        // filter after add one result
        TestTask taskToAdd = td.lecture;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertFilterResult("filter r/weekly", taskToAdd);

    }

    @Test
    public void filter_emptyList_success() {
        commandBox.runCommand("clear");
        assertFilterResult("filter d/11.10.2016"); // no results
    }

    @Test
    public void filter_invalidCommand_fail() {
        commandBox.runCommand("filterd/11.10.2016");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("filter");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        commandBox.runCommand("filter d/ddd");
        assertResultMessage(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }

    /**
     * Runs the filter command to filter the list and confirms the filtered list
     * is correct.
     */
    private void assertFilterResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
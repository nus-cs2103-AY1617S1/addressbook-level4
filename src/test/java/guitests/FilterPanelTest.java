package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Recurring;
import seedu.address.testutil.TestTask;
import seedu.address.ui.FilterPanel;

public class FilterPanelTest extends TaskManagerGuiTest {

    @Test
    public void filterPanel_toggleButtonStays() {
        filterPanel.runCommandForEvent();
        assertEquals(filterPanel.getEventInput(), true);
    }

    @Test
    public void filterPanel_textStays() {
        filterPanel.runCommandForDeadline("3.12.2016");
        assertEquals(filterPanel.getDeadlineInput(), "3.12.2016");
    }

    @Test
    public void filterPanel_events() {
        filterPanel.runCommandForEvent();
        assertFilterResult_success(td.meeting, td.travel);
        filterPanel.runCommandForEvent(); // Click twice to disable the button
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_tasks() {
        filterPanel.runCommandForTask();
        assertFilterResult_success(td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie);
        filterPanel.runCommandForTask(); // Click twice to disable the button
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_done() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForDone();
        assertFilterResult_success(td.friend);
        filterPanel.runCommandForDone(); // Click twice to disable the button
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_undone() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForUndone();
        assertFilterResult_success(td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting, td.travel);
        filterPanel.runCommandForUndone(); // Click twice to disable the button
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_deadline() {
        filterPanel.runCommandForDeadline("11.10.2016");
        assertFilterResult_success(td.friendEvent, td.work);
        filterPanel.runCommandForDeadline("hi");
        assertFilterResult_fail(Deadline.MESSAGE_DEADLINE_CONSTRAINTS, td.friendEvent, td.work);
        filterPanel.runCommandForDeadline("nil");
        assertFilterResult_success(td.friend, td.book);
        filterPanel.runCommandForDeadline("");
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_startDate() {
        filterPanel.runCommandForStartDate("11.10.2016");
        assertFilterResult_success(td.travel);
        filterPanel.runCommandForStartDate("hi");
        assertFilterResult_fail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, td.travel);
        filterPanel.runCommandForStartDate("");
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_endDate() {
        filterPanel.runCommandForEndDate("15.10.2016");
        assertFilterResult_success(td.travel);
        filterPanel.runCommandForEndDate("hi");
        assertFilterResult_fail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, td.travel);
        filterPanel.runCommandForEndDate("");
        assertFilterResult_success(td.getTypicalTasks());
    }

    @Test
    public void filterPanel_recurring() {
        commandBox.runCommand(td.lecture.getAddCommand());
        filterPanel.runCommandForRecurring("weekly");
        assertFilterResult_success(td.lecture);
        filterPanel.runCommandForRecurring("hi");
        assertFilterResult_fail(Recurring.MESSAGE_RECURRING_CONSTRAINTS, td.lecture);
    }
    
    @Test
    public void filterPanel_tag() {
        filterPanel.runCommandForTag("friends");
        assertFilterResult_success(td.friend, td.friendEvent, td.lunch);
        filterPanel.runCommandForTag("");
        assertFilterResult_success(td.getTypicalTasks());
    }
    
    @Test
    public void filterPanel_integral() {
        filterPanel.runCommandForTag("friends");
        assertFilterResult_success(td.friend, td.friendEvent, td.lunch);
        filterPanel.runCommandForEvent();
        assertFilterResult_success();
        filterPanel.runCommandForTag("");
        assertFilterResult_success(td.meeting, td.travel);
        filterPanel.runCommandForDone();
        assertFilterResult_success();
        filterPanel.runCommandForUndone();
        assertFilterResult_success(td.meeting, td.travel);
        filterPanel.runCommandForStartDate("11.10.2016");
        assertFilterResult_success(td.travel);
        filterPanel.runCommandForEndDate("11.10.2016");
        assertFilterResult_success();
        filterPanel.runCommandForStartDate("");
        filterPanel.runCommandForEndDate("");
        filterPanel.runCommandForTask();
        assertFilterResult_success(td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie);
        filterPanel.runCommandForDeadline("11.10.2016-12");
        assertFilterResult_success(td.lunch);
    }
    
    private void assertFilterResult_success(TestTask... expectedHits) {
        assertFilterResult(expectedHits);
        assertResultMessage(FilterPanel.SUCCESS_FILTER);
    }

    private void assertFilterResult_fail(String message, TestTask... expectedHits) {
        assertFilterResult(expectedHits);
        assertResultMessage(FilterPanel.INVALID_FILTER + message);
    }

    private void assertFilterResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}

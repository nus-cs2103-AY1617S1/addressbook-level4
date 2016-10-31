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
    public void filterPanelToggleButtonStays() {
        filterPanel.runCommandForEvent();
        assertEquals(filterPanel.getEventInput(), true);
    }

    @Test
    public void filterPanelTextStays() {
        filterPanel.runCommandForDeadline("3.12.2016");
        assertEquals(filterPanel.getDeadlineInput(), "3.12.2016");
    }

    @Test
    public void filterEvents() {
        filterPanel.runCommandForEvent();
        assertFilterSuccess(td.meeting, td.travel);
        filterPanel.runCommandForEvent(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterTasks() {
        filterPanel.runCommandForTask();
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie);
        filterPanel.runCommandForTask(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterDone() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForDone();
        assertFilterSuccess(td.friend);
        filterPanel.runCommandForDone(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterUndone() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForUndone();
        assertFilterSuccess(td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting, td.travel);
        filterPanel.runCommandForUndone(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterDeadline() {
        filterPanel.runCommandForDeadline("11.10.2016");
        assertFilterSuccess(td.friendEvent, td.work);
        filterPanel.runCommandForDeadline("hi");
        assertFilterFail(Deadline.MESSAGE_DEADLINE_CONSTRAINTS, td.friendEvent, td.work);
        filterPanel.runCommandForDeadline("nil");
        assertFilterSuccess(td.friend, td.book);
        filterPanel.runCommandForDeadline("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterStartDate() {
        filterPanel.runCommandForStartDate("11.10.2016");
        assertFilterSuccess(td.travel);
        filterPanel.runCommandForStartDate("hi");
        assertFilterFail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, td.travel);
        filterPanel.runCommandForStartDate("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterEndDate() {
        filterPanel.runCommandForEndDate("15.10.2016");
        assertFilterSuccess(td.travel);
        filterPanel.runCommandForEndDate("hi");
        assertFilterFail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, td.travel);
        filterPanel.runCommandForEndDate("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filterRecurring() {
        commandBox.runCommand(td.lecture.getAddCommand());
        filterPanel.runCommandForRecurring("weekly");
        assertFilterSuccess(td.lecture);
        filterPanel.runCommandForRecurring("hi");
        assertFilterFail(Recurring.MESSAGE_RECURRING_CONSTRAINTS, td.lecture);
    }
    
    @Test
    public void filterTag() {
        filterPanel.runCommandForTag("friends");
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch);
        filterPanel.runCommandForTag("");
        assertFilterSuccess(td.getTypicalTasks());
    }
    
    @Test
    public void filterIntegral() {
        filterPanel.runCommandForTag("friends");
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch);
        filterPanel.runCommandForEvent();
        assertFilterSuccess();
        filterPanel.runCommandForTag("");
        assertFilterSuccess(td.meeting, td.travel);
        filterPanel.runCommandForDone();
        assertFilterSuccess();
        filterPanel.runCommandForUndone();
        assertFilterSuccess(td.meeting, td.travel);
        filterPanel.runCommandForStartDate("11.10.2016");
        assertFilterSuccess(td.travel);
        filterPanel.runCommandForEndDate("11.10.2016");
        assertFilterSuccess();
        filterPanel.runCommandForStartDate("");
        filterPanel.runCommandForEndDate("");
        filterPanel.runCommandForTask();
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie);
        filterPanel.runCommandForDeadline("11.10.2016-12");
        assertFilterSuccess(td.lunch);
    }
    
    private void assertFilterSuccess(TestTask... expectedHits) {
        assertFilterResult(expectedHits);
        assertResultMessage(FilterPanel.SUCCESS_FILTER);
    }

    private void assertFilterFail(String message, TestTask... expectedHits) {
        assertFilterResult(expectedHits);
        assertResultMessage(FilterPanel.INVALID_FILTER + message);
    }

    private void assertFilterResult(TestTask... expectedHits) {
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}

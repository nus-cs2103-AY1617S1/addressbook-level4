package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Recurring;
import seedu.address.testutil.TestTask;
import seedu.address.ui.FilterPanel;

//@@author A0146123R
/**
 * gui tests for filter panel.
 */
public class FilterPanelTest extends TaskManagerGuiTest {

    @Test
    public void filterPanel_runCommandForButton_buttonStays() {
        filterPanel.runCommandForEvent();
        assertTrue(filterPanel.getEventInput());
        assertTrue(commandBox.isFocused());
    }

    @Test
    public void filterPanel_runCommandForTextfield_textStays() {
        filterPanel.runCommandForDeadline("3.12.2016");
        assertEquals("3.12.2016", filterPanel.getDeadlineInput());
        assertTrue(commandBox.isFocused());
    }

    @Test
    public void filter_runCommandForChoiceBox_choiceBoxStays() {
        filterPanel.runCommandForPriority("2");
        assertEquals(filterPanel.getPriorityInput(), "2");
        assertTrue(commandBox.isFocused());
    }

    @Test
    public void update_listCommand_buttonUpdates() {
        commandBox.runCommand("list tasks");
        assertTrue(filterPanel.getTaskInput());
        commandBox.runCommand("list events");
        assertTrue(filterPanel.getEventInput());
        assertFalse(filterPanel.getTaskInput());
        commandBox.runCommand("list done");
        assertTrue(filterPanel.getDoneInput());
        commandBox.runCommand("list undone");
        assertTrue(filterPanel.getUndoneInput());
        commandBox.runCommand("list");
        assertFalse(filterPanel.getEventInput() || filterPanel.getTaskInput() || filterPanel.getDoneInput()
                || filterPanel.getUndoneInput());
    }

    @Test
    public void updatee_filterCommand_textFieldUpdates() {
        commandBox.runCommand("filter d/06.11.2016 t/tag");
        assertEquals("06.11.2016", filterPanel.getDeadlineInput());
        assertEquals("tag", filterPanel.getTagInput());
        commandBox.runCommand("filter s/06.11.2016-12 e/06.11.2016-16");
        assertEquals("06.11.2016-12", filterPanel.getStartDateInput());
        assertEquals("06.11.2016-16", filterPanel.getEndDateInput());
        commandBox.runCommand("filter r/daily p/3");
        assertEquals("daily", filterPanel.getRecurringInput());
        assertEquals("3", filterPanel.getPriorityInput());
    }

    @Test
    public void filter_events_successs() {
        filterPanel.runCommandForEvent();
        assertFilterSuccess(td.meeting, td.travel);
        filterPanel.runCommandForEvent(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_tasks_success() {
        filterPanel.runCommandForTask();
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie);
        filterPanel.runCommandForTask(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_done_success() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForDone();
        assertFilterSuccess(td.friend);
        filterPanel.runCommandForDone(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_undone_success() {
        commandBox.runCommand("done 1");
        td.friend.markAsDone();
        filterPanel.runCommandForUndone();
        assertFilterSuccess(td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting, td.travel);
        filterPanel.runCommandForUndone(); // Click twice to disable the button
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_invaliidTextFieldInput_fail() {
        commandBox.runCommand("list tasks");
        TestTask[] tasks = { td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie };

        filterPanel.runCommandForDeadline("hi");
        assertFilterFail(Deadline.MESSAGE_DEADLINE_CONSTRAINTS, tasks);

        filterPanel.runCommandForDeadline("");
        filterPanel.runCommandForStartDate("hi");
        assertFilterFail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, tasks);

        filterPanel.runCommandForStartDate("");
        filterPanel.runCommandForEndDate("hi");
        assertFilterFail(EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS, tasks);

        filterPanel.runCommandForEndDate("");
        filterPanel.runCommandForRecurring("hi");
        assertFilterFail(Recurring.MESSAGE_RECURRING_CONSTRAINTS, tasks);
    }

    @Test
    public void filter_deadline_success() {
        filterPanel.runCommandForDeadline("11.10.2016-16");
        assertFilterSuccess(td.movie);
        filterPanel.runCommandForDeadline("nil");
        assertFilterSuccess(td.friend, td.book);
        filterPanel.runCommandForDeadline("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_startDate_success() {
        filterPanel.runCommandForStartDate("11.10.2016");
        assertFilterSuccess(td.meeting, td.travel);
        filterPanel.runCommandForStartDate("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_endDate_success() {
        filterPanel.runCommandForEndDate("15.10.2016");
        assertFilterSuccess(td.travel);
        filterPanel.runCommandForEndDate("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_recurring_success() {
        commandBox.runCommand(td.lecture.getAddCommand());
        filterPanel.runCommandForRecurring("weekly");
        assertFilterSuccess(td.lecture);
    }

    @Test
    public void filter_tag_success() {
        filterPanel.runCommandForTag("friends");
        assertFilterSuccess(td.friend, td.friendEvent, td.lunch);
        filterPanel.runCommandForTag("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_priority_success() {
        filterPanel.runCommandForPriority("1");
        assertFilterSuccess(td.lunch);
        filterPanel.runCommandForPriority("");
        assertFilterSuccess(td.getTypicalTasks());
    }

    @Test
    public void filter_integral_success() {
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
        filterPanel.runCommandForStartDate("11.10.2016-10");
        assertFilterSuccess(td.meeting);
        filterPanel.runCommandForEndDate("12.10.2016");
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
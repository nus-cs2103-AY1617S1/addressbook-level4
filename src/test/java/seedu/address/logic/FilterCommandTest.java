package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Recurring;
import seedu.address.model.task.Task;

/**
 * Responsible for testing the correct execution of FindCommand
 */
public class FilterCommandTest extends CommandTest {

    /*
     * Filter Command format: filter [s/START_DATE] [e/END_DATE] [d/DEADLINE]
     * [r/RECURRING] [p/PRIORITY] [t/TAG]...
     * 
     * Equivalence partitions for parameters: empty parameter, invalid
     * parameter, s/START_DATE, e/END_DATE, d/DEADLINE, r/RECURRING, p/PRIORITY,
     * t/TAG..., combination of parameters
     */
    // -------------------------test for invalid commands------------------------------------------------

    @Test
    public void execute_filterInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertAbsenceKeywordFormatBehaviorForCommand("filter", expectedMessage);
        assertCommandBehavior("filter a/", expectedMessage);
    }

    @Test
    public void execute_filterInvalidParameter_errorMessageShown() throws Exception {
        assertCommandBehavior("filter d/ddd", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        assertCommandBehavior("filter s/sss", EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
        assertCommandBehavior("filter e/eee", EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
        assertCommandBehavior("filter r/rrr", Recurring.MESSAGE_RECURRING_CONSTRAINTS);
        assertCommandBehavior("filter p/-1", Priority.MESSAGE_INVALID_PRIORITY_LEVEL);
        assertCommandBehavior("filter p/4", Priority.MESSAGE_INVALID_PRIORITY_LEVEL);
    }

    // ------------------------------test for valid cases------------------------------------------------

    /*
     * Filter by start date
     * Expected: filter s/11 Nov should return all events whose start date is in the day 11.11.2016
     */
    @Test
    public void execute_filteStartDate_matchesStartDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneEventWithStartDate("11.11.2016");
        Task pTarget2 = helper.generateUndoneEventWithStartDate("11.11.2016-12");
        Task p1 = helper.generateUndoneEventWithStartDate("04.12.2016");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter s/11 Nov", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Filter by end date
     * Expected: filter e/12 Nov should return all events whose start date is in the day 12.11.2016
     */
    @Test
    public void execute_filterEndDate_matchesEndDate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneEventWithEndDate("12.11.2016");
        Task pTarget2 = helper.generateUndoneEventWithEndDate("12.11.2016-12");
        Task p1 = helper.generateUndoneEventWithEndDate("04.12.2016");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter e/12 Nov", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Filter by deadline 
     * Expected: filter d/11 Nov should return all events whose deadline is in the day 11.11.2016
     */
    @Test
    public void execute_filterDeadline_matchesDeadline() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithDeadline("11.11.2016");
        Task pTarget2 = helper.generateUndoneTaskWithDeadline("11.11.2016-12");
        Task p1 = helper.generateUndoneTaskWithDeadline("04.12.2016");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter d/11 Nov", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Filter by recurring
     */
    @Test
    public void execute_filterRecurring_matchesRecurring() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithDeadline("11.11.2016");
        pTarget1.setRecurring(new Recurring("daily"));
        Task pTarget2 = helper.generateUndoneEventWithEndDate("11.11.2016-12");
        pTarget2.setRecurring(new Recurring("daily"));
        Task p1 = helper.generateUndoneTaskWithDeadline("04.12.2016");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter r/daily", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Filter by priority
     */
    @Test
    public void execute_filterPriority_matchesPriority() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithDeadline("11.11.2016");
        pTarget1.setPriorityLevel(new Priority(3));
        Task pTarget2 = helper.generateUndoneEventWithEndDate("11.11.2016-12");
        pTarget2.setPriorityLevel(new Priority(3));
        Task p1 = helper.generateUndoneTaskWithDeadline("04.12.2016");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter p/3", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    /*
     * Filter by tags
     */
    @Test
    public void execute_filterTags_matchesTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithTag("cs2103");
        Task pTarget2 = helper.generateUndoneTaskWithTag("project");
        Task p1 = helper.generateUndoneTaskWithTag("random");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter t/cs2103 t/project",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

    /*
     * Filter by multiple parameters
     */
    @Test
    public void execute_filterMultiple_matchesMultipleParameters() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithTag("cs2103");
        pTarget1.setDate(new Deadline("03.12.2016"));
        Task p1 = helper.generateUndoneTaskWithDeadline("03.12.2016");
        Task p2 = helper.generateUndoneTaskWithTag("cs2103");

        List<Task> threeTasks = helper.generateTaskList(p1, pTarget1, p2);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("filter d/03.12.2016 t/cs2103",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

}
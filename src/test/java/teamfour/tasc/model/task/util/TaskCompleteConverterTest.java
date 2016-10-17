package teamfour.tasc.model.task.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;
import teamfour.tasc.testutil.TaskBuilder;
import teamfour.tasc.testutil.TestTask;

public class TaskCompleteConverterTest {

    private Date firstDayOfDecember;
    private Date firstDayOfDecember3am;
    private Date firstDayOfDecember5am;

    private Date eighthDayOfDecember;
    private Date eighthDayOfDecember3am;
    private Date eighthDayOfDecember5am;

    private Date now;
    private String completedAtNowString;

    @Before
    public void setUp() throws Exception {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2000, 11, 1, 0, 0, 0);
        firstDayOfDecember = calendar.getTime();

        calendar.set(2000, 11, 1, 3, 0, 0);
        firstDayOfDecember3am = calendar.getTime();

        calendar.set(2000, 11, 1, 5, 0, 0);
        firstDayOfDecember5am = calendar.getTime();

        calendar.set(2000, 11, 8, 0, 0, 0);
        eighthDayOfDecember = calendar.getTime();

        calendar.set(2000, 11, 8, 3, 0, 0);
        eighthDayOfDecember3am = calendar.getTime();

        calendar.set(2000, 11, 8, 5, 0, 0);
        eighthDayOfDecember5am = calendar.getTime();

        calendar.set(2010, 0, 1, 0, 0, 0);
        now = calendar.getTime();
        completedAtNowString = String.format(TaskCompleteConverter.TASK_NAME_COMPLETED_SUFFIX, now);
    }

    /*
     * --- Test for TaskCompleteConverter's constructor method
     * 
     * Equivalence partitions are: 1. null a. null task b. null date 2.
     * completed - [no sub-partitions] (EP: Does not matter if the completed
     * tasks have a deadline, period or even recurrence. As long as it is
     * completed, converter should always throw error) - uncompleted 3. deadline
     * & period with no recurrence 4. deadline only with 1 recurrence 5. period
     * only with 2 recurrence (Heuristics: No need to test period with 1
     * recurrence or deadline with 2 recurrence, because whether there is
     * deadline or period only has nothing to do with recurrence count)
     * (Boundary: 0, 1, >1) (Heuristics: No need to test > 2, not going to be
     * different)
     * 
     * (Heuristics: Only test weekly. This is because testing for daily and
     * monthly should be done by a different test (the test case responsible for
     * testing Recurrence.getNextDateAfterRecurrence())
     */

    // 1a
    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullTaskInput_throwsException()
            throws IllegalArgumentException, TaskAlreadyCompletedException, IllegalValueException {

        TaskCompleteConverter uselessConverter = new TaskCompleteConverter(null, now);
    }

    // 1b
    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullDateInput_throwsException()
            throws IllegalArgumentException, TaskAlreadyCompletedException, IllegalValueException {

        TestTask task = new TaskBuilder().build();
        TaskCompleteConverter uselessConverter = new TaskCompleteConverter(task, null);
    }

    // 2
    @Test(expected = TaskAlreadyCompletedException.class)
    public void constructor_completedTask_throwsException()
            throws IllegalValueException, IllegalArgumentException, TaskAlreadyCompletedException {

        TestTask completedTask = new TaskBuilder().withCompleteStatus(true).build();
        TaskCompleteConverter converter = new TaskCompleteConverter(completedTask, now);
    }

    // 3
    @Test
    public void constructor_deadlineAndPeriodButNoRecurrence_returnsCompletedTaskOnly()
            throws IllegalValueException, IllegalArgumentException, TaskAlreadyCompletedException {

        TestTask noRecurrenceTask = new TaskBuilder().withName("No recurrence")
                .withDeadline(firstDayOfDecember)
                .withPeriod(firstDayOfDecember3am, firstDayOfDecember5am).build();

        TestTask noRecurrenceTaskCompleted = new TaskBuilder()
                .withName("No recurrence" + completedAtNowString).withDeadline(firstDayOfDecember)
                .withPeriod(firstDayOfDecember3am, firstDayOfDecember5am).withCompleteStatus(true)
                .build();

        TaskCompleteConverter converter = new TaskCompleteConverter(noRecurrenceTask, now);

        assertSameTask(noRecurrenceTaskCompleted, converter.getCompletedTask());
        assertSameTask(null, converter.getUncompletedRemainingRecurringTask());
    }

    // 4
    @Test
    public void constructor_deadlineWithOneRecurrence_returnsCompletedAndRemaining()
            throws IllegalValueException, IllegalArgumentException, TaskAlreadyCompletedException {
        TestTask oneRecurrenceTask = new TaskBuilder().withName("One recurrence")
                .withDeadline(firstDayOfDecember).withRecurrence(Recurrence.Pattern.WEEKLY, 1)
                .build();

        TestTask oneRecurrenceTaskCompleted = new TaskBuilder()
                .withName("One recurrence" + completedAtNowString).withDeadline(firstDayOfDecember)
                // no recurrence for completed
                .withCompleteStatus(true).build();

        TestTask remainingTask = new TaskBuilder().withName("One recurrence")
                .withDeadline(eighthDayOfDecember)
                // no recurrence left
                .withCompleteStatus(false).build();

        TaskCompleteConverter converter = new TaskCompleteConverter(oneRecurrenceTask, now);

        assertSameTask(oneRecurrenceTaskCompleted, converter.getCompletedTask());
        assertSameTask(remainingTask, converter.getUncompletedRemainingRecurringTask());
    }

    // 5
    @Test
    public void constructor_periodWithTwoRecurrences_returnsCompletedAndRemaining()
            throws IllegalValueException, IllegalArgumentException, TaskAlreadyCompletedException {

        TestTask twoRecurrenceTask = new TaskBuilder().withName("Two recurrences")
                .withPeriod(firstDayOfDecember3am, firstDayOfDecember5am)
                .withRecurrence(Recurrence.Pattern.WEEKLY, 2).build();

        TestTask twoRecurrenceTaskCompleted = new TaskBuilder()
                .withName("Two recurrences" + completedAtNowString)
                .withPeriod(firstDayOfDecember3am, firstDayOfDecember5am)
                // no recurrence for completed
                .withCompleteStatus(true).build();

        TestTask remainingTask = new TaskBuilder().withName("Two recurrences")
                .withPeriod(eighthDayOfDecember3am, eighthDayOfDecember5am)
                .withRecurrence(Recurrence.Pattern.WEEKLY, 1).withCompleteStatus(false).build();

        TaskCompleteConverter converter = new TaskCompleteConverter(twoRecurrenceTask, now);

        assertSameTask(twoRecurrenceTaskCompleted, converter.getCompletedTask());
        assertSameTask(remainingTask, converter.getUncompletedRemainingRecurringTask());
    }

    /**
     * Check that task1 has the same content as task2.
     */
    private void assertSameTask(ReadOnlyTask task1, ReadOnlyTask task2) {
        if (task1 == null && task2 == null) {
            // both null
            return;
        }

        if (task1 == null) {
            fail("task1 is null but task2 is NOT null");
        }

        if (task2 == null) {
            fail("task1 is NOT null but task2 is null");
        }

        assertTrue("Expected: <" + task1 + "> but was <" + task2 + ">", task1.isSameStateAs(task2));
    }
}

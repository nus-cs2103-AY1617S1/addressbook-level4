package seedu.agendum.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;

//@@author A0133367E
public class TaskTest {

    private Task floatingTask;
    private Task eventTask;
    private Task deadlineTaskDueYesterday;
    private Task deadlineTaskDueTomorrow;
    private Optional<LocalDateTime> noTimeSpecified = Optional.empty();
    private Optional<LocalDateTime> yesterday = Optional.ofNullable(LocalDateTime.now().minusDays(1));
    private Optional<LocalDateTime> tomorrow = Optional.ofNullable(LocalDateTime.now().plusDays(1));

    /**
     * Convenient helper method to generate a task with Name name,
     * the specified completion status, start and end time (specified by optional).
     * Last updated time is set to yesterday.
     */
    private Task generateTask(String name, boolean isCompleted, Optional<LocalDateTime> startTime,
            Optional<LocalDateTime> endTime) throws Exception {
        Name taskName = new Name(name);
        Task task = new Task(taskName, startTime, endTime);
        if (isCompleted) {
            task.markAsCompleted();
        }
        task.setLastUpdatedTime(yesterday.get());
        return task;
    }
    
    @Before
    public void setUp() throws Exception {
        floatingTask = generateTask("task", false, noTimeSpecified, noTimeSpecified);
        eventTask = generateTask("task", false, yesterday, tomorrow);
        deadlineTaskDueYesterday = generateTask("task", false, noTimeSpecified, yesterday);
        deadlineTaskDueTomorrow = generateTask("task", false, noTimeSpecified, tomorrow);   
    }
    
    @Test
    public void isOverdue_floatingTask_returnsFalse() {
        assertFalse(floatingTask.isOverdue());
    }

    @Test
    public void isOverdue_completedTask_returnsFalse() {
        // testing for completion status, give valid (overdue) end date time
        deadlineTaskDueYesterday.markAsCompleted();
        assertFalse(deadlineTaskDueYesterday.isOverdue());
    }

    @Test
    public void isOverdue_uncompletedFutureTask_returnsFalse() {
        assertFalse(deadlineTaskDueTomorrow.isOverdue());
    }

    @Test
    public void isOverdue_uncompletedTaskFromYesterday_returnsTrue() {
        assertTrue(deadlineTaskDueYesterday.isOverdue());
        assertTrue(eventTask.isOverdue());
    }

    @Test
    public void isUpcoming_floatingTask_returnsFalse() {
        assertFalse(floatingTask.isUpcoming());
    }

    @Test
    public void isUpcoming_completedTask_returnsFalse() {
        // testing for completion status, give valid (upcoming) end date time
        deadlineTaskDueTomorrow.markAsCompleted();
        assertFalse(deadlineTaskDueTomorrow.isUpcoming());
    }

    @Test
    public void isUpcoming_taskWithEndTimeYesterday_returnsFalse() {
        assertFalse(deadlineTaskDueYesterday.isUpcoming());
    }

    @Test
    public void isUpcoming_uncompletedTaskFromNextYear_returnsFalse() {
        LocalDateTime nextMonth = LocalDateTime.now().plusMonths(1);
        floatingTask.setEndDateTime(Optional.ofNullable(nextMonth));
        assertFalse(floatingTask.isUpcoming());
    }

    @Test
    public void isUpcoming_uncompletedTaskFromTomorrow_returnsTrue() {
        assertTrue(deadlineTaskDueTomorrow.isUpcoming());
    }

    @Test
    public void isEvent_floatingTask_returnsFalse() {
        assertFalse(floatingTask.isEvent());
    }

    @Test
    public void isEvent_taskWithNoStartTime_returnsFalse() {
        assertFalse(deadlineTaskDueTomorrow.isEvent());
    }

    @Test
    public void isEvent_taskHasStartAndEndTime_returnsTrue() {
        assertTrue(eventTask.isEvent());
    }

    @Test
    public void hasDeadline_floatingTask_returnsFalse() {
        assertFalse(floatingTask.hasDeadline());
    }

    @Test
    public void hasDeadline_taskWithEndTimeButNoStartTime_returnsTrue() {
        assertTrue(deadlineTaskDueTomorrow.hasDeadline());
    }

    @Test
    public void hasDeadline_taskHasStartAndEndTime_returnsFalse() {
        assertFalse(eventTask.hasDeadline());
    }

    @Test
    public void setName_updateNameAndPreserveProperties() throws Exception {
        Task copiedTask = new Task(eventTask);
        eventTask.setName(new Name("updated task"));
 
        assertEquals(eventTask.getName().toString(), "updated task");
        assertEquals(eventTask.getStartDateTime(), copiedTask.getStartDateTime());
        assertEquals(eventTask.getEndDateTime(), copiedTask.getEndDateTime());
        assertEquals(eventTask.isCompleted(), copiedTask.isCompleted());
        // should change the last updated time
        assertTrue(eventTask.getLastUpdatedTime()
                .isAfter(copiedTask.getLastUpdatedTime()));
    }

    @Test
    public void setStartTime_updateStartTimeAndPreserveProperties() {
        Task copiedTask = new Task(eventTask);
        eventTask.setStartDateTime(Optional.empty());
        
        assertEquals(eventTask.getStartDateTime(), Optional.empty());
        assertEquals(eventTask.getName(), copiedTask.getName());
        assertEquals(eventTask.getEndDateTime(), copiedTask.getEndDateTime());
        assertEquals(eventTask.isCompleted(), copiedTask.isCompleted());
        assertTrue(eventTask.getLastUpdatedTime()
                .isAfter(copiedTask.getLastUpdatedTime()));
    }

    @Test
    public void setEndTime_updateEndTimeAndPreserveProperties() {
        Task copiedTask = new Task(eventTask);
        eventTask.setEndDateTime(yesterday);

        assertEquals(eventTask.getEndDateTime(), yesterday);
        assertEquals(eventTask.getName(), copiedTask.getName());
        assertEquals(eventTask.getStartDateTime(), copiedTask.getStartDateTime());
        assertEquals(eventTask.isCompleted(), copiedTask.isCompleted());
        assertTrue(eventTask.getLastUpdatedTime()
                .isAfter(copiedTask.getLastUpdatedTime()));
    }

    @Test
    public void markAsCompleted_markAsCompletedAndPreserveProperties() {
        Task copiedTask = new Task(eventTask);
        eventTask.markAsCompleted();

        assertTrue(eventTask.isCompleted());
        assertEquals(eventTask.getName(), copiedTask.getName());
        assertEquals(eventTask.getStartDateTime(), copiedTask.getStartDateTime());
        assertEquals(eventTask.getEndDateTime(), copiedTask.getEndDateTime());
        assertTrue(eventTask.getLastUpdatedTime()
                .isAfter(copiedTask.getLastUpdatedTime()));
    }
    
    @Test
    public void equals_tasksWithOnlyDifferentName_returnsFalse() throws Exception {
        Task anotherTask = generateTask("new task", false, noTimeSpecified, noTimeSpecified);
        assertFalse(floatingTask.equals(anotherTask));
    }

    @Test
    public void equals_tasksWithOnlyDifferentCompletionStatus_returnsFalse() throws Exception {
        Task anotherTask = generateTask("task", true, noTimeSpecified, noTimeSpecified);
        assertFalse(floatingTask.equals(anotherTask));
    }

    @Test
    public void equals_tasksWithOnlyDifferentTaskTime_returnsFalse() throws Exception {
        assertFalse(deadlineTaskDueTomorrow.equals(deadlineTaskDueYesterday));
    }

    @Test
    public void equals_tasksWithOnlyDifferentUpdatedTme_returnsTrue() throws Exception {
        Task copiedTask = new Task(floatingTask);
        copiedTask.setLastUpdatedTimeToNow();
        assertEquals(floatingTask, copiedTask);
    }

    @Test
    public void compareTo_uncompletedAndCompletedTasks_uncompletedFirst() throws Exception {
        // tasks with same time
        Task completedTask = generateTask("task", true, noTimeSpecified, noTimeSpecified);
        assertTrue(floatingTask.compareTo(completedTask) < 0);

        // tasks with different end time and updated time
        deadlineTaskDueYesterday.markAsCompleted();
        assertTrue(floatingTask.compareTo(deadlineTaskDueYesterday) < 0);
    }
 
    /**
     * Compare uncompleted tasks based on their task time (start time if present, else end time)
     */
    @Test
    public void compareTo_uncompletedTasks_earlierAndPresentTaskTimeFirst() {
        assertTrue(deadlineTaskDueYesterday.compareTo(deadlineTaskDueTomorrow) < 0);
        assertTrue(deadlineTaskDueTomorrow.compareTo(floatingTask) < 0);
        assertTrue(floatingTask.compareTo(deadlineTaskDueYesterday) > 0);
    }

    /**
     * Compare completed tasks based on their last updated time
     * (Regardless of the start and end time associated with the task)
     */
    @Test
    public void compareTo_completedTasks_earlierUpdatedTimeFirst() {

        deadlineTaskDueTomorrow.markAsCompleted();
        deadlineTaskDueTomorrow.setLastUpdatedTime(yesterday.get());

        deadlineTaskDueYesterday.markAsCompleted();
        deadlineTaskDueYesterday.setLastUpdatedTime(tomorrow.get());

        assertTrue(deadlineTaskDueYesterday.compareTo(deadlineTaskDueTomorrow) < 0);
    }

    @Test
    public void compareTo_tasksWithOnlyDifferentNames_lexicographicalOrder() throws Exception {
        Task anotherTask = generateTask("another task", false, noTimeSpecified, noTimeSpecified);
        assertTrue(anotherTask.compareTo(floatingTask) < 0);
    }

}

package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.DateUtil;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.status.EventStatus;
import teamfour.tasc.testutil.TaskBuilder;
import teamfour.tasc.testutil.TestTask;

public class TaskTest {

    @Test
    public void isOverdue_noDeadline_returnsFalse() throws IllegalValueException {
        TestTask noDeadlineTask = new TaskBuilder().withName("No deadline").build();

        assertFalse(noDeadlineTask.isOverdue(DateUtil.getCurrentTime()));
    }

    @Test
    public void isOverdue_deadlineEarlierThanCurrentTime_returnsTrue() throws IllegalValueException {
        Date currentTime = new Date(1);
        TestTask lateTask = new TaskBuilder().withDeadline(new Date(0)).build();

        assertTrue(lateTask.isOverdue(currentTime));
    }

    @Test
    public void isOverdue_deadlineLaterThanCurrentTime_returnsFalse() throws IllegalValueException {
        Date currentTime = new Date(1);
        TestTask notLateTask = new TaskBuilder().withDeadline(new Date(2)).build();

        assertFalse(notLateTask.isOverdue(currentTime));
    }

    @Test
    public void getEventStatus_noPeriod_returnsNotAnEvent() throws IllegalValueException {
        TestTask noPeriod = new TaskBuilder().withName("No period").build();

        assertEquals(EventStatus.NOT_AN_EVENT, noPeriod.getEventStatus(DateUtil.getCurrentTime()));
    }

    @Test
    public void getEventStatus_eventNotStartedYet_returnsNotStarted() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask futureEvent = new TaskBuilder().withPeriod(new Date(20), new Date(30)).build();
        
        assertEquals(EventStatus.NOT_STARTED, futureEvent.getEventStatus(currentTime));
    }
    
    @Test
    public void getEventStatus_eventHappeningNow_returnsInProgress() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask happeningNow = new TaskBuilder().withPeriod(new Date(5), new Date(15)).build();
        
        assertEquals(EventStatus.IN_PROGRESS, happeningNow.getEventStatus(currentTime));
    }

    @Test
    public void getEventStatus_eventIsOver_returnsEnded() throws IllegalValueException {
        Date currentTime = new Date(10);
        TestTask eventEnded = new TaskBuilder().withPeriod(new Date(2), new Date(7)).build();

        assertEquals(EventStatus.ENDED, eventEnded.getEventStatus(currentTime));
    }

    @Test
    public void isFloatingTask_noPeriodAndDeadline_returnsTrue() throws IllegalValueException {
        TestTask floatingTask = new TaskBuilder().withName("Floating task").build();

        assertTrue(floatingTask.isFloatingTask());
    }

    @Test
    public void isFloatingTask_havePeriod_returnsFalse() throws IllegalValueException {
        TestTask event = new TaskBuilder().withName("Event").withPeriod(new Date(0), new Date(1))
                .build();

        assertFalse(event.isFloatingTask());
    }

    @Test
    public void isFloatingTask_haveDeadline_returnsFalse() throws IllegalValueException {
        TestTask taskWithDeadline = new TaskBuilder().withName("Submission")
                .withDeadline(new Date(0)).build();

        assertFalse(taskWithDeadline.isFloatingTask());
    }
    
    @Test
    public void constructor_floatingTaskWithRecurrence_recurrenceIsRemoved() throws IllegalValueException {
        Task floatingTask = new Task(new Name("Floating Task"),
                new Complete(false),
                new Deadline(),
                new Period(),
                new Recurrence(Recurrence.Pattern.DAILY, 3),
                new UniqueTagList());
        
        assertEquals(false, floatingTask.getRecurrence().hasRecurrence());
    }
}

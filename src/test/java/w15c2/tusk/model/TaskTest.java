package w15c2.tusk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
public class TaskTest {

    @Test
    public void toString_noDescription() {
        Task t1 = new FloatingTask("");
        
        assertEquals(t1.toString(), "");
    }
    
    @Test
    public void toString_hasDescription() {
        Task t1 = new FloatingTask("test");
        
        assertEquals(t1.toString(), "test");
    }
    
    @Test
    public void compareTo_identicalTasks() {
        Task t1 = new FloatingTask("test");
        Task t2 = t1;
        
        assertEquals(t1.compareTo(t2), 0);
    }
    
    @Test
    public void compareTo_differentFloatingTasks() {
        Task t1 = new FloatingTask("test");
        Task t2 = new FloatingTask("test2");
        
        assertEquals(t1.compareTo(t2), 0);
    }
    
    @Test
    public void compareTo_overDueVersusFloating() {
       
        Task t1 = getOverdueTask();
        Task t2 = new FloatingTask("test2");
        
        assertEquals(t1.compareTo(t2), -1);
    }
    
    @Test
    public void compareTo_twoOverdueTasksOneMoreOverdue() {
       
        Task t1 = getOverdueTask();
        Task t2 = getMoreOverdueTask();
        
        assertEquals(t1.compareTo(t2), 1);
    }
    
    @Test
    public void compareTo_overdueVsPin() {
       
        Task t1 = getOverdueTask();
        Task t2 = t1.copy();
        t2.setAsPin();
        
        assertEquals(t1.compareTo(t2), 1);
    }
    
    @Test
    public void compareTo_floatingVsPin() {
       
        Task t1 = new FloatingTask("test");
        Task t2 = getOverdueTask();
        t2.setAsPin();
        
        assertEquals(t1.compareTo(t2), 1);
    }
    
    @Test
    public void compareTo_twoDeadlineTasksOneLater() {
       
        Task t1 = getDeadlineTask();
        Task t2 = getLaterDeadlineTask();
        
        assertEquals(t1.compareTo(t2), -1);
    }
    
    @Test
    public void compareTo_twoEventTasksOneLater() {
       
        Task t1 = getEventTask();
        Task t2 = getLaterEventTask();
        
        assertEquals(t1.compareTo(t2), -1);
    }
    
    @Test
    public void equals_oneTaskoneNull() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = null;
        
        assertNotEquals(t1, t2);
    }
    
    @Test
    public void equals_twoFloatingTasksSameDescription() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = new FloatingTask("hello world");
        
        assertEquals(t1, t2);
    }
    
    @Test
    public void equals_twoFloatingTasks_differentDescriptions() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = new FloatingTask("hello world 2");
        
        assertNotEquals(t1, t2);
    }
    
    @Test
    public void equals_twoFloatingTasks_differentPinStatus() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = new FloatingTask("hello world");
        t2.setAsPin();
        
        assertNotEquals(t1, t2);
    }
    
    @Test
    public void equals_twoFloatingTasks_differentCompletedStatus() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = new FloatingTask("hello world");
        t2.setAsComplete();
        
        assertNotEquals(t1, t2);
    }
    
    @Test
    public void equals_oneFloatingoneEvent() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = getEventTask();
        
        assertNotEquals(t1, t2);
    }
    
    
    @Test
    public void equals_oneFloatingoneDeadline() {
        Task t1 = new FloatingTask("hello world");
        Task t2 = getDeadlineTask();
        
        assertNotEquals(t1, t2);
    }
    
    
    @Test
    public void equals_twoDeadlineTasks_sameDeadline() {
        Task t1 = getDeadlineTask();
        Task t2 = t1.copy();
        
        assertEquals(t1, t2);
    }
    
    @Test
    public void equals_twoDeadlineTasks_differentDeadlines() {
        Task t1 = getDeadlineTask();
        Task t2 = getLaterDeadlineTask();
        
        assertNotEquals(t1, t2);
    }
    
    @Test
    public void equals_twoEventTasks_sameDates() {
        Task t1 = getEventTask();
        Task t2 = t1.copy();
        
        assertEquals(t1, t2);
    }
    
    @Test
    public void equals_twoEventTasks_differentDates() {
        Task t1 = getEventTask();
        Task t2 = getLaterEventTask();
        
        assertNotEquals(t1, t2);
    }
    
    
    /* Utility functions */
    private Task getOverdueTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        
        return new DeadlineTask("test", yesterday);
    }
    
    private Task getMoreOverdueTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -2);
        Date dayBeforeYesterday = cal.getTime();
        
        return new DeadlineTask("test", dayBeforeYesterday);
    }
    
    private Task getDeadlineTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();
        
        return new DeadlineTask("test", tomorrow);
    }
    
    
    private Task getLaterDeadlineTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 2);
        Date dayAfterTomorrow = cal.getTime();
        
        return new DeadlineTask("test", dayAfterTomorrow);
    }
    
    private Task getEventTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();
        cal.add(Calendar.DATE, 2);
        Date dayAfterTomorrow = cal.getTime();
        
        return new EventTask("test", tomorrow, dayAfterTomorrow);
    }
    
    
    private Task getLaterEventTask() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 2);
        Date dayAfterTomorrow = cal.getTime();
        cal.add(Calendar.DATE, 3);
        Date threeDaysLater = cal.getTime();
        
        return new EventTask("test", dayAfterTomorrow, threeDaysLater);
    }
    
  
}

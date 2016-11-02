package w15c2.tusk.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Calendar;

import org.junit.Test;

import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
public class TaskTest {

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
    public void compareTo_twoOverdueTasks_oneMoreOverdue() {
       
        Task t1 = getOverdueTask();
        Task t2 = getMoreOverdueTask();
        
        assertEquals(t1.compareTo(t2), 1);
    }
    
    @Test
    public void compareTo_overdueVsPin() {
       
        Task t1 = getOverdueTask();
        Task t2 = getOverdueTask();
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
    public void compareTo_twoDeadlineTasks_oneLater() {
       
        Task t1 = getDeadlineTask();
        Task t2 = getLaterDeadlineTask();
        
        assertEquals(t1.compareTo(t2), -1);
    }
    
    @Test
    public void compareTo_twoEventTasks_oneLater() {
       
        Task t1 = getEventTask();
        Task t2 = getLaterEventTask();
        
        assertEquals(t1.compareTo(t2), -1);
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

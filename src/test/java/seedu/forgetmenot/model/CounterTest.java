package seedu.forgetmenot.model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.TaskManager;
import seedu.forgetmenot.model.task.Done;
import seedu.forgetmenot.model.task.Name;
import seedu.forgetmenot.model.task.Recurrence;
import seedu.forgetmenot.model.task.Task;
import java.util.Calendar;
//@@author A0139211R
public class CounterTest {
  
	
	private DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//    @Test
//    public void counter_updateFloatingNumber_isCorrect() throws Exception {
//    	Task floating1 = new Task(new Name("floating1"), new Done(false), new Time(""), new Time(""), new Recurrence(""));
//    	Task floating2 = new Task(new Name("floating2"), new Done(false), new Time(""), new Time(""), new Recurrence(""));
//    	Task floating3 = new Task(new Name("floating3"), new Done(false), new Time(""), new Time(""), new Recurrence(""));
//        TaskManager ab = new TaskManager();
//        ab.addTask(floating1);
//        ab.addTask(floating2);
//        ab.addTask(floating3);
//        
//        assertTrue(ab.getFloatingCounter() == 3);
//    }

//    @Test
//    public void counter_updateOverDueNumber_isCorrect() throws Exception {
//    	Calendar start = Calendar.getInstance();
//    	Calendar end = Calendar.getInstance();
//    	start.add(Calendar.HOUR, -2);
//    	end.add(Calendar.HOUR, -1);
//    	String startTime = dateFormat.format(start.getTime());
//    	String endTime = dateFormat.format(end.getTime());
//    	Task overdue1 = new Task(new Name("aaa"), new Done(false), new Time("yesterday"), new Time("today"), new Recurrence(""));
//    	Task overdue2 = new Task(new Name("bbb"), new Done(false), new Time("20/10/16"), new Time("2/12/16"), new Recurrence(""));
//    	Task overdue3 = new Task(new Name("ccc"), new Done(false), new Time("yesterday"), new Time(startTime), new Recurrence(""));
//    	Task overdue4 = new Task(new Name("ddd"), new Done(false), new Time(startTime), new Time(endTime), new Recurrence(""));
//    	Task overdue5 = new Task(new Name("ddd"), new Done(false), new Time("tomorrow"), new Time("day after tomorrow"), new Recurrence("")); //not overdue
//        TaskManager ab = new TaskManager();
//        ab.addTask(overdue1);
//        ab.addTask(overdue2);
//        ab.addTask(overdue3);
//        ab.addTask(overdue4);
//        ab.addTask(overdue5);
//        
//        assertTrue(ab.getOverdueCounter() == 4);
//    }
    
    @Test
    public void counter_updateTomorrowCounter_isCorrect() throws Exception {
    	Task tomorrow1 = new Task(new Name("aaa"), new Done(false), new Time("tomorrow"), new Time("2 days after tomorrow"), new Recurrence(""));
    	Task tomorrow2 = new Task(new Name("bbb"), new Done(false), new Time("yesterday"), new Time("today"), new Recurrence("")); //not tomorrow
    	Task tomorrow3 = new Task(new Name("ccc"), new Done(false), new Time("today"), new Time("tomorrow"), new Recurrence(""));
    	Task tomorrow4 = new Task(new Name("ddd"), new Done(false), new Time("tomorrow 5pm"), new Time("tomorrow 8pm"), new Recurrence(""));
    	Task tomorrow5 = new Task(new Name("ddd"), new Done(false), new Time("yesterday"), new Time("tomorrow"), new Recurrence(""));
        TaskManager ab = new TaskManager();
        ab.addTask(tomorrow1);
        ab.addTask(tomorrow2);
        ab.addTask(tomorrow3);
        ab.addTask(tomorrow4);
        ab.addTask(tomorrow5);
        
        assertTrue(ab.getTomorrowCounter() == 4);
    	
    }    
}

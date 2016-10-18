package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;

/**
 * Tests the functionalities of Task class
 * 
 * @author User
 *
 */
public class TaskTest {
   private Task task;
    
   @Test
   public void create_floatingTask_success() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList());
       assertEquals(task.getTaskType(),TaskType.FLOATING);
   }
   
   @Test
   public void create_floatingTask_failure() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), new TaskDate(10), new TaskDate(20), RecurringType.NONE);
       assertNotEquals(task.getTaskType(),TaskType.FLOATING);
   }
   
   @Test 
   public void create_nonFloatingTask_success() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), new TaskDate(10), new TaskDate(20), RecurringType.NONE);
       assertEquals(task.getTaskType(),TaskType.NON_FLOATING);
   }
   
   @Test
   public void create_nonFloatingTask_failire() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList());
       assertNotEquals(task.getTaskType(),TaskType.NON_FLOATING);       
   }
   
   @Test
   public void set_floatingTask_recurring_failure() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList());
       try{
           task.setRecurringType(RecurringType.DAILY);
           Assert.fail();
       } catch (AssertionError error) {
           assertTrue(true);
       }
   }
   
   @Test
   public void set_nonFloatingTask_recurring_success() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), new TaskDate(10), new TaskDate(20), RecurringType.NONE);
       task.setRecurringType(RecurringType.DAILY);
       assertEquals(task.getRecurringType(), RecurringType.DAILY);
   }   
}

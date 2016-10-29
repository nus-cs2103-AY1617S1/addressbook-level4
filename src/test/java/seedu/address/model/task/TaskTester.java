package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;

//@@author A0135782Y
/**
 * Unit Tester that tests the methods of the Task class
 *
 */
public class TaskTester {
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
       } catch (AssertionError error) {
           assertTrue(true);
       }
   }
   
   @Test
   public void set_nonFloatingTask_recurring_successful() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), new TaskDate("11 oct 11pm"), new TaskDate("11 oct 12pm"), RecurringType.NONE);
       task.setRecurringType(RecurringType.DAILY);
       assertEquals(task.getRecurringType(), RecurringType.DAILY);
   }   
   
   @Test
   public void set_TaskType_sucessful() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), new TaskDate("11 oct 11pm"), new TaskDate("11 oct 12pm"), RecurringType.NONE);
       task.setTaskType(TaskType.COMPLETED);
       assertEquals("Task type should be mutated", task.getTaskType(), TaskType.COMPLETED);
   }
   
   @Test
   public void append_taskComponent_toNonRecurringTask_notAllowed() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), 
               new TaskDate("11 oct 11pm"), new TaskDate("11 oct 12pm"), RecurringType.NONE);
       try {
           task.appendRecurringDate(new TaskOcurrence(task,new TaskDate(), new TaskDate()));
       } catch(AssertionError ae) {
           assertTrue(true);
       }
   }
   
   @Test
   public void getLastAppendedComponent_success() throws Exception {
       task = new Task(new Name("Name"), new UniqueTagList(), 
               new TaskDate("11 oct 11pm"), new TaskDate("11 oct 12pm"), RecurringType.DAILY);
       TaskOcurrence toAppend = new TaskOcurrence(task, new TaskDate("12oct 11pm"), new TaskDate("12 oct 11.01pm"));
       task.appendRecurringDate(toAppend);
       TaskOcurrence component = task.getLastAppendedComponent();
       assertEquals("Task component just appended must be the last appended component", toAppend, component);
   }
}

package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
//@@author A0142184L
public class TypicalTestTasks {

    public static TestTask someday1, someday2, someday3,
    					   deadline1, deadline2, deadlineToday, deadlineTomorrow, deadlineIn7Days, deadlineIn30Days, 
                           event1, event2, eventToday, eventTomorrow, eventIn7Days, eventIn30Days, 
                           somedayAdd,
                           deadlineTodayAdd, deadlineTomorrowAdd, deadlineIn7DaysAdd, deadlineIn30DaysAdd, 
                           eventTodayAdd, eventTomorrowAdd, eventIn7DaysAdd, eventIn30DaysAdd, eventStartDateTimeAfterEndDateTime, eventStartDateTimeEqualsEndDateTime ;

    public TypicalTestTasks() {
        try {
            //currentList
            someday1 = new TaskBuilder().withName("hw 1").withStatus("done").withTaskType("someday").build();
            someday2 = new TaskBuilder().withName("lab 1").withStatus("pending").withTaskType("someday").build();
            someday3 = new TaskBuilder().withName("hw 0").withStatus("done").withTaskType("someday").build();
            deadline1 = new TaskBuilder().withName("hw 2").withStatus("done").withTaskType("deadline").withEndDate(LocalDateTime.of(2012, 1, 2, 3, 4)).build();
            deadline2 = new TaskBuilder().withName("hw 3").withStatus("overdue").withTaskType("deadline").withEndDate(LocalDateTime.of(2014, 1, 2, 3, 4)).build();
            deadlineToday = new TaskBuilder().withName("lab 2").withStatus("done").withTaskType("deadline").withEndDate(LocalDateTime.now()).build();
            deadlineTomorrow = new TaskBuilder().withName("lab 3").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(1)).build();
            deadlineIn7Days = new TaskBuilder().withName("lab 4").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(5)).build();
            deadlineIn30Days = new TaskBuilder().withName("lab 5").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(17)).build();
            event1 = new TaskBuilder().withName("hw 4").withStatus("done").withTaskType("event").withStartDate(LocalDateTime.of(2013, 2, 2, 4, 6)).withEndDate(LocalDateTime.of(2013, 2, 3, 4, 6)).build();
            event2 = new TaskBuilder().withName("hw 5").withStatus("overdue").withTaskType("event").withStartDate(LocalDateTime.of(2014, 2, 2, 4, 6)).withEndDate(LocalDateTime.of(2014, 2, 3, 4, 6)).build();
            eventToday = new TaskBuilder().withName("attend meeting").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().withHour(23)).withEndDate(LocalDateTime.now().plusDays(1)).build();
            eventIn7Days = new TaskBuilder().withName("lab 6").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(5)).withEndDate(LocalDateTime.now().plusDays(6)).build();
            eventTomorrow = new TaskBuilder().withName("lab 7").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(1)).withEndDate(LocalDateTime.now().plusDays(2)).build();
            eventIn30Days = new TaskBuilder().withName("hw 7").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(20)).withEndDate(LocalDateTime.now().plusDays(22)).build();

            //Sample tasks
            somedayAdd = new TaskBuilder().withName("report 5").withStatus("pending").withTaskType("someday").build();
            deadlineTodayAdd = new TaskBuilder().withName("report 1").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now()).build();
            deadlineTomorrowAdd = new TaskBuilder().withName("report 2").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(1)).build();
            deadlineIn7DaysAdd = new TaskBuilder().withName("report 9").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(3)).build();
            deadlineIn30DaysAdd = new TaskBuilder().withName("report 10").withStatus("pending").withTaskType("deadline").withEndDate(LocalDateTime.now().plusDays(24)).build();
            eventTodayAdd = new TaskBuilder().withName("report 7").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now()).withEndDate(LocalDateTime.now().plusDays(2)).build();
            eventTomorrowAdd = new TaskBuilder().withName("report 8").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(1)).withEndDate(LocalDateTime.now().plusDays(2)).build();
            eventIn7DaysAdd = new TaskBuilder().withName("report 3").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(5)).withEndDate(LocalDateTime.now().plusDays(6)).build();
            eventIn30DaysAdd = new TaskBuilder().withName("report 4").withStatus("pending").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(13)).withEndDate(LocalDateTime.now().plusDays(14)).build();
            eventStartDateTimeAfterEndDateTime = new TaskBuilder().withName("exam 1").withStatus("not done").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(8)).withEndDate(LocalDateTime.now().plusDays(1)).build();
            eventStartDateTimeEqualsEndDateTime = new TaskBuilder().withName("exam 2").withStatus("not done").withTaskType("event").withStartDate(LocalDateTime.now().plusDays(1)).withEndDate(LocalDateTime.now().plusDays(1)).build();          
        
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(someday1));
            ab.addTask(new Task(someday2));
            ab.addTask(new Task(someday3));
            ab.addTask(new Task(deadline1));
            ab.addTask(new Task(deadline2));
            ab.addTask(new Task(deadlineToday));
            ab.addTask(new Task(deadlineTomorrow));
            ab.addTask(new Task(deadlineIn7Days));
            ab.addTask(new Task(deadlineIn30Days));
            ab.addTask(new Task(event1));            
            ab.addTask(new Task(event2));
            ab.addTask(new Task(eventToday));
            ab.addTask(new Task(eventTomorrow));
            ab.addTask(new Task(eventIn7Days));
            ab.addTask(new Task(eventIn30Days));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    //@@author A0141019U
    public TestTask[] getTypicalTasks() {
    	return new TestTask[]{deadline2, event2, eventToday, deadlineTomorrow, 
    			eventTomorrow, deadlineIn7Days, eventIn7Days, deadlineIn30Days, 
    			eventIn30Days, someday2, deadline1, event1, 
    			deadlineToday, someday3, someday1};
    }
    
    //@@author
    
    public TestTask[] getUnsortedTypicalTasks() {
    	return new TestTask[]{someday1, someday2, someday3,
				   deadline1, deadline2, deadlineToday, deadlineTomorrow, deadlineIn7Days, deadlineIn30Days, 
                   event1, event2, eventToday, eventTomorrow, eventIn7Days, eventIn30Days};
    }
    public TestTask[] getTodayTasks() {
        return new TestTask[] {eventToday, deadlineToday};
    }
    
    public TestTask[] getTomorrowTasks() {
        return new TestTask[] {deadlineTomorrow, eventTomorrow};
    }
    
    public TestTask[] getIn7DaysTasks() {
        return new TestTask[] {deadlineIn7Days, eventIn7Days};
    }
    
    public TestTask[] getIn30DaysTasks() {
        return new TestTask[] {deadlineIn30Days, eventIn30Days};
    }
    
    public TestTask[] getSomedayTasks() {
        return new TestTask[] {someday2, someday3, someday1};
    }
    
    public TestTask[] getDeadlineTasks() {
        return new TestTask[] {deadline2, deadlineTomorrow, deadlineIn7Days, deadlineIn30Days, deadline1, deadlineToday};
    }
    
    public TestTask[] getEventTasks() {
        return new TestTask[] {event2, eventToday, eventTomorrow, eventIn7Days, eventIn30Days, event1};
    }
    
    public TestTask[] getDoneTasks() {
        return new TestTask[] {deadline1, event1, deadlineToday, someday3, someday1};
    }
    
    public TestTask[] getNotDoneTasks() {
        return new TestTask[] {eventToday, deadlineTomorrow, eventTomorrow, deadlineIn7Days, eventIn7Days, deadlineIn30Days, eventIn30Days, someday2};
    }
    
    public TestTask[] getOverdueTasks() {
    	return new TestTask[] {deadline2, event2};
    }
    
    public TestTask[] getDoneAndDeadlineTasks() {
        return new TestTask[] {deadline1, deadlineToday};
    }
    
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
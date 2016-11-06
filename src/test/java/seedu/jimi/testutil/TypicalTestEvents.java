package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * @@author A0143471L
 * 
 * Example test cases for events
 *
 */

public class TypicalTestEvents {
    
    public static TestEvent wedding, tuition, nightClass;
    
    public TypicalTestEvents() {
        try {
            wedding =  new EventBuilder().withName("wedding dinner").withPriority("MED").withStartTmr().withEndOneHourLater().build();
            tuition = new EventBuilder().withName("go to tuition").withTags("Math").withPriority("LOW").withStartNow().withEndOneHourLater().build();            
            
            //Manually added
            nightClass = new EventBuilder().withName("night class").withPriority("HIGH").withStartTmr().withEndOneHourLater().build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    
    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Event(wedding, wedding.getStart(), wedding.getEnd()));
            ab.addTask(new Event(tuition, tuition.getStart(), tuition.getEnd()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }
    
    public ReadOnlyTask[] getTypicalTasks() {
        return new ReadOnlyTask[]{wedding};
    }
    
    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}

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
            wedding =  new EventBuilder().withName("wedding dinner").withPriority("MED").withStart("2016-11-03 19:00").withEnd("2016-11-03 22:00").build();
            
            //Manually added
            tuition = new EventBuilder().withName("go tuition").withTags("Math").withPriority("LOW").withStart("2016-11-01 19:00").withEnd("2016-11-01 22:00").build();
            nightClass = new EventBuilder().withName("night class").withPriority("HIGH").withStart("2016-10-31 19:00").withEnd("2016-10-31 22:00").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    
    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Event(wedding, wedding.getStart(), wedding.getEnd()));
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

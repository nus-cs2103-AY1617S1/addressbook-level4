package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * @@author A0143471L
 * 
 * Example test cases for deadline tasks
 *
 */

public class TypicalTestDeadlineTasks {
    
    public static TestDeadlineTask groceries, ideas, homework;
    
    public TypicalTestDeadlineTasks() {
        try {
            groceries =  new DeadlineTaskBuilder().withName("buy groceries").withTags("NTUC").withPriority("LOW").withDeadline("2016-11-02 21:00").build();
            ideas = new DeadlineTaskBuilder().withName("brainstorm ideas").withTags("project").withPriority("LOW").withDeadline("2016-10-31 19:00").build();
            
            //Manually added
            homework = new DeadlineTaskBuilder().withName("finish homework").withPriority("HIGH").withDeadline("2016-10-31 19:00").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    
    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new DeadlineTask(groceries, groceries.getDeadline()));
            ab.addTask(new DeadlineTask(ideas, ideas.getDeadline()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }
    
    public ReadOnlyTask[] getTypicalTasks() {
        return new ReadOnlyTask[]{groceries};
    }
    
    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}

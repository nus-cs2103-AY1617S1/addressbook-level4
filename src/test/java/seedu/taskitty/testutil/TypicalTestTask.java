package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.TaskManager;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    //@@author A0139930B
    public static TestTask todo, deadline, comingEvent, todayEvent, addTodo, addDeadline, overDeadline, addEvent, overEvent, 
                           editedDeadline, editedEvent;
    
    public TypicalTestTask() {
        try {
            todo = new TaskBuilder().withName("todo").withTags("random").build();
            deadline = new TaskBuilder().withName("upcoming deadline")
                    .withDeadline("31/12/2016", "15:00")
                    .build();
            comingEvent = new TaskBuilder().withName("upcoming event")
                    .withEvent("12/12/2016", "10:00", "12/12/2016", "19:00")
                    .build();
            todayEvent = new TaskBuilder().withName("today event")
                    .withEvent("25/12/2016", "18:30", "26/12/2016", "02:00")
                    .withTags("random").build();
            
            //manually added
            addTodo = new TaskBuilder().withName("added todo").withTags("added").build();
            addDeadline = new TaskBuilder().withName("added deadline")
                    .withDeadline("23/12/2016", "08:00")
                    .withTags("added").build();
            addEvent = new TaskBuilder().withName("added event")
                    .withEvent("13/12/2016", "13:00", "15/12/2016", "10:00")
                    .withTags("added").build();
            
            overDeadline = new TaskBuilder().withName("over deadline")
                    .withDeadline("23/10/2016", "08:00")
                    .withTags("added", "over").build();
            overEvent = new TaskBuilder().withName("over Event")
                    .withEvent("02/01/2016", "12:00", "03/01/2016", "12:00")
                    .withTags("added", "over").build();
            
            editedDeadline = new TaskBuilder().withName("added deadline")
                    .withDeadline("24/12/2016", "08:00")
                    .withTags("added").build();
            editedEvent = new TaskBuilder().withName("upcoming event")
                    .withEvent("12/12/2016", "10:00", "17/12/2016", "19:00")
                    .build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(todo));
            ab.addTask(new Task(deadline));
            ab.addTask(new Task(comingEvent));
            ab.addTask(new Task(todayEvent));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{todo, deadline, comingEvent, todayEvent};
    }
    
    //@@author
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

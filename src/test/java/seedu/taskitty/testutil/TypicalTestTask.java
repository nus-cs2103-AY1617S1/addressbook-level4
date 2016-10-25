package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.TaskManager;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    //@@author A0139930B
    public static TestTask read, spring, shop, dinner, todo, deadline, event, overEvent;
    
    public TypicalTestTask() {
        try {
            read = new TaskBuilder().withName("read clean code").withTags("important").build();
            spring = new TaskBuilder().withName("spring cleaning")
                    .withDeadline("31/12/2016", "15:00")
                    .build();
            shop = new TaskBuilder().withName("shop for xmas")
                    .withEvent("12/12/2016", "10:00", "12/12/2016", "19:00")
                    .build();
            dinner = new TaskBuilder().withName("xmas dinner")
                    .withEvent("25/12/2016", "18:30", "26/12/2016", "02:00")
                    .withTags("drinking").build();
            
            //manually added
            todo = new TaskBuilder().withName("todo").withTags("generic").build();
            deadline = new TaskBuilder().withName("deadline")
                    .withDeadline("23/12/2016", "08:00")
                    .withTags("generic").build();
            event = new TaskBuilder().withName("event")
                    .withEvent("13/12/2016", "13:00", "15/12/2016", "10:00")
                    .withTags("generic").build();
            overEvent = new TaskBuilder().withName("overEvent")
                    .withEvent("02/01/2016", "12:00", "03/01/2016", "12:00")
                    .withTags("generic").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(read));
            ab.addTask(new Task(spring));
            ab.addTask(new Task(shop));
            ab.addTask(new Task(dinner));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{read, spring, shop, dinner};
    }
    
    //@@author
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

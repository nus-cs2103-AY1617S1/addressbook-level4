package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.TaskManager;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask read, spring, shop, dinner, todo, deadline, event;
    
    public TypicalTestTask() {
        try {
            read = new TaskBuilder().withName("read clean code").withTags("important").build();
            spring = new TaskBuilder().withName("spring cleaning").withEndDate("31/12/2016").withEndTime("15:00")
                    .build();
            shop = new TaskBuilder().withName("shop for xmass").withStartDate("12/12/2016").withStartTime("10:00")
                    .withEndDate("12/12/2016").withEndTime("19:00").build();
            dinner = new TaskBuilder().withName("xmass dinner").withStartDate("25/12/2016").withStartTime("18:30")
                    .withEndDate("26/12/2016").withEndTime("02:00").withTags("drinking").build();
            
            //manually added
            todo = new TaskBuilder().withName("todo").withTags("generic").build();
            deadline = new TaskBuilder().withName("deadline").withEndDate("23/12/2016").withEndTime("08:00")
                    .withTags("generic").build();
            event = new TaskBuilder().withName("event").withStartDate("13/12/2016").withStartTime("13:00")
                    .withEndDate("15/12/2016").withEndTime("10:00").withTags("generic").build();
            
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

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

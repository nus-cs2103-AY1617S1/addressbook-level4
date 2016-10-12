package harmony.mastermind.testutil;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask task1, task2, task3, task4, task5;

    //@@author A0124797R
    public TypicalTestTask() {
        
        try {
            task1 =  new TaskBuilder().withName("do laundry")
                    .withTags("chores").build();
            task2 = new TaskBuilder().withName("finish assignment").build();
            task3 = new TaskBuilder().withName("do past year papers")
                    .withTags("examPrep").build();
            task4 = new TaskBuilder().withName("complete cs2103 lecture quiz")
                    .withTags("homework").build();
            task5 = new TaskBuilder().withName("complete cs2105 assignment")
                    .withTags("homework").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
        
    }

    //@@author A0124797R
    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(task1));
            ab.addTask(new Task(task2));
            ab.addTask(new Task(task3));
            ab.addTask(new Task(task4));
            ab.addTask(new Task(task5));
            
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    //@@author A0124797R
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

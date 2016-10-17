package harmony.mastermind.testutil;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask task1, task2, task3, task4, task5, task6;

    //@@author A0124797R
    public TypicalTestTasks() {
        
        try {
            task1 =  new TaskBuilder().withName("do laundry")
                    .withTags("chores").build();
            task2 = new TaskBuilder().withName("finish assignment").build();
            task3 = new TaskBuilder().withName("cs2105 assignment")
                    .withTags("examPrep").build();
            task4 = new TaskBuilder().withName("complete cs2103 lecture quiz")
                    .withTags("homework").build();
            
            
            //manual inputs
            task5 = new TaskBuilder().withName("do past year papers")
                    .withTags("homework").build();
            task6 = new TaskBuilder().withName("sweep floor").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
        
    }

    //@@author A0124797R
    public static void loadTaskManagerWithSampleData(TaskManager tm) {

        try {
            tm.addTask(new Task(task1));
            tm.addTask(new Task(task2));
            tm.addTask(new Task(task3));
            tm.addTask(new Task(task4));
            
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

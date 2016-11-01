package harmony.mastermind.testutil;

import java.util.ArrayList;
import java.util.List;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.task.*;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0124797R
/**
 * to generate tasks for testing purposes
 */
public class TypicalTestTasks {

    public static TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9, task10;

    public TypicalTestTasks() {
        
        try {
            task1 = new TaskBuilder().withName("complete cs2103 lecture quiz")
                    .withEndDate("25 oct at 2359")
                    .withTags("homework").build();
            task2 = new TaskBuilder().withName("cs2105 assignment")
                    .withStartDate("23 oct 1pm").withEndDate("23 oct 5pm")
                    .withTags("homework").build();
            task3 =  new TaskBuilder().withName("laundry")
                    .withTags("chores").build();
            task4 = new TaskBuilder().withName("finish assignment").build();
            
            
            //manual inputs
            task5 = new TaskBuilder().withName("past year papers")
                    .withTags("examPrep").build();
            task6 = new TaskBuilder().withName("sweep floor").build();
            
            //completed tasks
            task7 = new TaskBuilder().withName("lecture").build();
            task8 = new TaskBuilder().withName("submit PR").withEndDate("22 oct at 2359").build();
            
            //reurring inputs
            task9 = new TaskBuilder().withName("pick up grocery")
                    .withEndDate("23 Oct 6pm").withRecur("weekly").build();
            task10 = new TaskBuilder().withName("pick up grocery")
                    .withEndDate("30 Oct 6pm").withRecur("weekly").build();
            
        } catch (IllegalValueException e) {
            assert false : "should not reach here";
        }
        
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {

        try {
            tm.addTask(new Task(task1));
            tm.addTask(new Task(task2));
            tm.addTask(new Task(task3));
            tm.addTask(new Task(task4));
            tm.addTask(new Task(task7));
            tm.markTask(new Task(task7));
            tm.addTask(new Task(task8));
            tm.markTask(new Task(task8));
            
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Should not reach here";
        } catch (TaskNotFoundException e) {
            assert false : "task is added before marking";            
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4};
    }
    
    public TestTask[] getTypicalArchivedTasks() {
        return new TestTask[]{task7.mark(),task8.mark()};
    }

    //@@author generated
    public TaskManager getTypicalTaskManager(){
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}

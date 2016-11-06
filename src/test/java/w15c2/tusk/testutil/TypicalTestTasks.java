package w15c2.tusk.testutil;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.task.TaskManager;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask task1, task2, task3, task4, task5, task6, task7, extraTask1, extraTask2;
    public static Alias alias1, alias2, extraAlias;

    public TypicalTestTasks() {
        try {
            task1 = new TaskBuilder().withDescription("meeting1").setCompleted(true)
                    .setPinned(true).build();
            task2 = new TaskBuilder().withDescription("dinner event").setCompleted(true)
            		.setPinned(false).build();
            task3 = new TaskBuilder().withDescription("conference from 11am to 12pm").setCompleted(false)
                    .setPinned(true).build();
            task4 = new TaskBuilder().withDescription("meeting2").setCompleted(false)
                    .setPinned(false).build();
            task5 = new TaskBuilder().withDescription("meeting3").setCompleted(false)
                    .setPinned(false).build();
            task6 = new TaskBuilder().withDescription("meeting4").setCompleted(false)
                    .setPinned(false).build();
            task7 = new TaskBuilder().withDescription("meeting5").setCompleted(false)
                    .setPinned(true).build();
            
            extraTask1 =  new TaskBuilder().withDescription("meeting with boss").setCompleted(false)
                    .setPinned(true).build();
            extraTask2 =  new TaskBuilder().withDescription("meet client").setCompleted(true)
                    .setPinned(true).build();
            
            alias1 = new Alias("am", "add meeting");
            alias2 = new Alias("ad", "add dinner");
            
            extraAlias = new Alias("ae", "add event");
            
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        try {
            tm.addTask(new TestTask(task1));
            tm.addTask(new TestTask(task2));
            tm.addTask(new TestTask(task3));
            tm.addTask(new TestTask(task4));
            tm.addTask(new TestTask(task5));
            tm.addTask(new TestTask(task6));
            tm.addTask(new TestTask(task7));
            tm.addAlias(new Alias(alias1));
            tm.addAlias(new Alias(alias2));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4, task5, task6, task7};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}


package seedu.tasklist.testutil;

import java.util.Arrays;
import java.util.Collections;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.*;

public class TypicalTestTasks {

    public static TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11;

    public TypicalTestTasks() {
        try {
            task1 =  new TaskBuilder().withTaskDetails("Buy Eggs").withUniqueID(1)
                    .withEndTime("").withStartTime("")
                    .withPriority("low").withRecurringFrequency("").build();
            task2 = new TaskBuilder().withTaskDetails("Study for Midterms").withUniqueID(2)
                    .withEndTime("").withStartTime("")
                    .withPriority("low").withRecurringFrequency("").build();

            task3 = new TaskBuilder().withTaskDetails("Do laundry").withStartTime("9pm").withEndTime("10pm").withUniqueID(3).withPriority("low").withRecurringFrequency("").build();
            task4 = new TaskBuilder().withTaskDetails("Complete Project Manual").withStartTime("21 dec 2pm").withEndTime("21 dec 4pm").withUniqueID(4).withPriority("low").withRecurringFrequency("").build();
            task5 = new TaskBuilder().withTaskDetails("Visit Singapore Zoo").withStartTime("23 dec 5pm").withEndTime("23 dec 11:59pm").withUniqueID(5).withPriority("low").withRecurringFrequency("").build();
            task6 = new TaskBuilder().withTaskDetails("Complete PC1432 Lab Assignment").withStartTime("23 dec 6pm").withEndTime("23 dec 8pm").withUniqueID(6).withPriority("low").withRecurringFrequency("").build();
            task7 = new TaskBuilder().withTaskDetails("Start working on GES1025 essay").withStartTime("25 dec 7pm").withEndTime("25 dec 11:59pm").withUniqueID(7).withPriority("low").withRecurringFrequency("").build();

            //Manually added
            task8 = new TaskBuilder().withTaskDetails("Work on CS2103T Project").withStartTime("29 dec 5pm").withEndTime("29 dec 7pm").withUniqueID(8).withPriority("low").withRecurringFrequency("").build();
            task9 = new TaskBuilder().withTaskDetails("Buy groceries").withStartTime("30 dec 6pm").withEndTime("30 dec 11:59pm").withUniqueID(9).withPriority("low").withRecurringFrequency("").build();
            task10 = new TaskBuilder().withTaskDetails("Study for EE2021").withStartTime("31 dec 6pm").withEndTime("31 dec 11:59pm").withUniqueID(10).withPriority("low").withRecurringFrequency("").build();
            task11 = new TaskBuilder().withTaskDetails("Work on CS2101 Project").withStartTime("31/01/2017 6pm").withEndTime("31/01/2017 11:59pm").withUniqueID(11).withPriority("low").withRecurringFrequency("").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Task(task1));
            ab.addTask(new Task(task2));
            ab.addTask(new Task(task3));
            ab.addTask(new Task(task4));
            ab.addTask(new Task(task5));
            ab.addTask(new Task(task6));
            ab.addTask(new Task(task7));
            ab.addTask(new Task(task10));
            ab.addTask(new Task(task11));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "Some of the tasks could not be added!";
        }
        Collections.sort(ab.getTasks());
    }

    public TestTask[] getTypicalTasks() {
    	TestTask[] result = new TestTask[]{task1, task2, task3, task4, task5, task6, task7, task10, task11};
    	Arrays.sort(result, (a,b)->{return a.compareTo(b);});
        return result;
    }

    public TaskList getTypicalTaskList(){
        TaskList tl = new TaskList();
        loadTaskListWithSampleData(tl);
        return tl;
    }
}

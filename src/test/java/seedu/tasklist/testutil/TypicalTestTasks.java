package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11;

    public TypicalTestTasks() {
        try {
            task1 =  new TaskBuilder().withTaskDetails("Buy Eggs").withUniqueID(1)
                    .withEndTime(null).withStartTime("5pm")
                    .withPriority("high").build();
            task2 = new TaskBuilder().withTaskDetails("Study for Midterms").withUniqueID(2)
                    .withEndTime("9pm").withStartTime("6pm")
                    .withPriority("high").build();

            task3 = new TaskBuilder().withTaskDetails("Do laundry").withStartTime(null).withEndTime("14 Nov 7pm").withUniqueID(3).withPriority("high").build();
            task4 = new TaskBuilder().withTaskDetails("Complete Project Manual").withStartTime(null).withEndTime("11pm").withUniqueID(4).withPriority("low").build();
            task5 = new TaskBuilder().withTaskDetails("Visit Singapore Zoo").withStartTime("14 Nov 12pm").withEndTime("2pm").withUniqueID(5).withPriority("med").build();
            task6 = new TaskBuilder().withTaskDetails("Complete PC1432 Lab Assignment").withStartTime(null).withEndTime("10pm").withUniqueID(6).withPriority("high").build();
            task7 = new TaskBuilder().withTaskDetails("Start working on GES1025 essay").withStartTime("14 Nov 9am").withEndTime(null).withUniqueID(7).withPriority("low").build();

            //Manually added
            task8 = new TaskBuilder().withTaskDetails("Work on CS2103T Project").withStartTime("1 Oct 6pm").withEndTime("9pm").withUniqueID(8).withPriority("high").build();
            task9 = new TaskBuilder().withTaskDetails("Buy groceries").withStartTime("5pm").withEndTime("").withUniqueID(9).withPriority("med").build();
            task10 = new TaskBuilder().withTaskDetails("Study for EE2021").withStartTime("").withEndTime("").withUniqueID(10).withPriority("med").build();
            task11 = new TaskBuilder().withTaskDetails("Work on CS2101 Project").withStartTime("10/10/15 5pm").withEndTime("21/09/2016").withUniqueID(11).withPriority("high").build();
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
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4, task5, task6, task7, task10, task11};
    }

    public TaskList getTypicalTaskList(){
        TaskList tl = new TaskList();
        loadTaskListWithSampleData(tl);
        return tl;
    }
}

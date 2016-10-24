package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI;

    public TypicalTestTasks() {
      //@@author A0147944U-reused
        try {
            taskA =  new TaskBuilder().withName("Accompany mom to the doctor").withDeadline("02.30pm")
                    .withEndTime("12.00am").withStartTime("10.00pm")
                    .withTags("gwsMum").build();
            taskB = new TaskBuilder().withName("Borrow software engineering book").withDeadline("02.30pm")
                    .withEndTime("02.00pm").withStartTime("01.00am")
                    .withTags("study", "seRocks").build();
            taskC = new TaskBuilder().withName("Call Jim").withStartTime("11.00am").withEndTime("01.00pm").withDeadline("02.30pm").build();
            taskD = new TaskBuilder().withName("Do homework").withStartTime("02.00am").withEndTime("03.00pm").withDeadline("02.30pm").build();
            taskE = new TaskBuilder().withName("Edit AddressBook file").withStartTime("03.00am").withEndTime("06.00pm").withDeadline("02.30pm").build();
            taskF = new TaskBuilder().withName("Finish up the project").withStartTime("04.00am").withEndTime("07.00pm").withDeadline("02.30pm").build();
            taskG = new TaskBuilder().withName("Go for a jog").withStartTime("07.00am").withEndTime("08.00pm").withDeadline("02.30pm").build();

            //Manually added
            taskH = new TaskBuilder().withName("Help Jim with his task").withStartTime("08.00am").withEndTime("09.00pm").withDeadline("02.30pm").build();
            taskI = new TaskBuilder().withName("Iron new clothes").withStartTime("09.00am").withEndTime("08.00pm").withDeadline("02.30pm").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
      //@@author
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {

        try {
            tm.addTask(new Task(taskA));
            tm.addTask(new Task(taskB));
            tm.addTask(new Task(taskC));
            tm.addTask(new Task(taskD));
            tm.addTask(new Task(taskE));
            tm.addTask(new Task(taskF));
            tm.addTask(new Task(taskG));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

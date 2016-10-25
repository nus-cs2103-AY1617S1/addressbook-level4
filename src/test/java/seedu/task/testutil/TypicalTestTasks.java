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
            taskA =  new TaskBuilder().withName("Accompany mom to the doctor").withDeadline("2016-10-27 15:00")
                    .withEndTime("2016-10-26 17:00").withStartTime("2016-10-25 02:00")
                    .withTags("gwsMum").withStatus(false, false).build();
            taskB = new TaskBuilder().withName("Borrow software engineering book").withDeadline("2016-10-27 16:00")
                    .withEndTime("2016-10-26 16:00").withStartTime("2016-10-25 03:00")
                    .withTags("study", "seRocks").withStatus(false, false).build();
            taskC = new TaskBuilder().withName("Call Jim").withStartTime("2016-10-25 04:00").withEndTime("2016-10-26 15:00").withDeadline("2016-10-27 17:00").withStatus(false, false).build();
            taskD = new TaskBuilder().withName("Do homework").withStartTime("2016-10-25 05:00").withEndTime("2016-10-26 14:00").withDeadline("2016-10-27 18:00").withStatus(false, false).build();
            taskE = new TaskBuilder().withName("Edit AddressBook file").withStartTime("2016-10-25 06:00").withEndTime("2016-10-26 13:49").withDeadline("2016-10-27 19:00").withStatus(false, false).build();
            taskF = new TaskBuilder().withName("Finish up the project").withStartTime("2016-10-25 07:00").withEndTime("2016-10-26 13:23").withDeadline("2016-10-27 20:00").withStatus(false, false).build();
            taskG = new TaskBuilder().withName("Go for a jog").withStartTime("2016-10-25 08:00").withEndTime("2016-10-26 12:00").withDeadline("2016-10-27 20:59").withStatus(false, false).build();
            taskH = new TaskBuilder().withName("Help Jim with his task").withStartTime("2016-10-25 09:00").withEndTime("2016-10-26 11:00").withDeadline("2016-10-27 21:00").withStatus(false, false).build();
            taskI = new TaskBuilder().withName("Iron new clothes").withStartTime("2016-10-25 02:59").withEndTime("2016-10-26 10:00").withDeadline("2016-10-27 22:00").withStatus(false, false).build();
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

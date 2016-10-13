package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.person.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI;

    public TypicalTestTasks() {
        try {
            taskA =  new TaskBuilder().withName("Accompany mom to the doctor").withTask("Khoo Teck Puat Hospital")
                    .withEndTime("12:00").withStartTime("10:00")
                    .withTags("gwsMum").build();
            taskB = new TaskBuilder().withName("Borrow software engineering book").withTask("National Library")
                    .withEndTime("14:00").withStartTime("13:00")
                    .withTags("study", "seRocks").build();
            taskC = new TaskBuilder().withName("Call Jim").withStartTime("12:00").withEndTime("13:00").withTask("NUS").build();
            taskD = new TaskBuilder().withName("Do homework").withStartTime("14:00").withEndTime("15:00").withTask("Home").build();
            taskE = new TaskBuilder().withName("Edit AddressBook file").withStartTime("15:00").withEndTime("16:00").withTask("Home").build();
            taskF = new TaskBuilder().withName("Finish up the project").withStartTime("16:00").withEndTime("17:00").withTask("Home").build();
            taskG = new TaskBuilder().withName("Go for a jog").withStartTime("17:00").withEndTime("18:00").withTask("Botanic Gardens").build();

            //Manually added
            taskH = new TaskBuilder().withName("Help Jim with his task").withStartTime("18:00").withEndTime("19:00").withTask("Jim house").build();
            taskI = new TaskBuilder().withName("Iron new clothes").withStartTime("19:00").withEndTime("20:00").withTask("Home").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
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

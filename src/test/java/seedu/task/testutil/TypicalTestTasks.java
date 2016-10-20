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
        try {
            taskA =  new TaskBuilder().withName("Accompany mom to the doctor").withTask("Khoo Teck Puat Hospital")
                    .withEndTime("1200hrs").withStartTime("1000hrs")
                    .withTags("gwsMum").build();
            taskB = new TaskBuilder().withName("Borrow software engineering book").withTask("National Library")
                    .withEndTime("1400hrs").withStartTime("1300hrs")
                    .withTags("study", "seRocks").build();
            taskC = new TaskBuilder().withName("Call Jim").withStartTime("1200hrs").withEndTime("1300hrs").withTask("NUS").build();
            taskD = new TaskBuilder().withName("Do homework").withStartTime("1400hrs").withEndTime("1500hrs").withTask("Home").build();
            taskE = new TaskBuilder().withName("Edit AddressBook file").withStartTime("1500hrs").withEndTime("1600hrs").withTask("Home").build();
            taskF = new TaskBuilder().withName("Finish up the project").withStartTime("1600hrs").withEndTime("1700hrs").withTask("Home").build();
            taskG = new TaskBuilder().withName("Go for a jog").withStartTime("1700hrs").withEndTime("1800hrs").withTask("Botanic Gardens").build();

            //Manually added
            taskH = new TaskBuilder().withName("Help Jim with his task").withStartTime("1800hrs").withEndTime("1900hrs").withTask("Jim house").build();
            taskI = new TaskBuilder().withName("Iron new clothes").withStartTime("1900hrs").withEndTime("2000hrs").withTask("Home").build();
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

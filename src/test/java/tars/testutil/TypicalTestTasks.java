package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.Tars;
import tars.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI;

    public TypicalTestTasks() {
        try {
            taskA =  new TaskBuilder().withName("Task A").withDateTime("01/09/2016 1400" , "02/09/2016 1400")
                    .withPriority("h")
                    .withTags("test").build();
            taskB = new TaskBuilder().withName("Task B").withDateTime("02/09/2016 1400" , "03/09/2016 1400")
                    .withPriority("m")
                    .withTags("tars", "test").build();
            taskC = new TaskBuilder().withName("Task C").withDateTime("03/09/2016 1400" , "04/09/2016 1400").withPriority("l").build();
            taskD = new TaskBuilder().withName("Task D").withDateTime("04/09/2016 1400" , "05/09/2016 1400").withPriority("h").build();
            taskE = new TaskBuilder().withName("Task E").withDateTime("05/09/2016 1400" , "06/09/2016 1400").withPriority("m").build();
            taskF = new TaskBuilder().withName("Task F").withDateTime("06/09/2016 1400" , "07/09/2016 1400").withPriority("l").build();
            taskG = new TaskBuilder().withName("Task G").withDateTime("07/09/2016 1400" , "08/09/2016 1400").withPriority("h").withStatus().build();

            //Manually added
            taskH = new TaskBuilder().withName("Task H").withDateTime("08/09/2016 1400" , "09/09/2016 1400").withPriority("m").build();
            taskI = new TaskBuilder().withName("Task I").withDateTime("09/09/2016 1400" , "10/09/2016 1400").withPriority("l").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTarsWithSampleData(Tars ab) {

        try {
            ab.addTask(new Task(taskA));
            ab.addTask(new Task(taskB));
            ab.addTask(new Task(taskC));
            ab.addTask(new Task(taskD));
            ab.addTask(new Task(taskE));
            ab.addTask(new Task(taskF));
            ab.addTask(new Task(taskG));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG};
    }

    public Tars getTypicalTars(){
        Tars ab = new Tars();
        loadTarsWithSampleData(ab);
        return ab;
    }
}

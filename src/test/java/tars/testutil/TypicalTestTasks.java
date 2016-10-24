package tars.testutil;

import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.model.Tars;
import tars.model.task.*;
import tars.model.task.rsv.RsvTask;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI, cfmTaskA;
    public static TestRsvTask rsvTaskA, rsvTaskB, rsvTaskC, rsvTaskD;

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
            rsvTaskA = new RsvTaskBuilder().withName("Rsv Task A").withDateTime("01/10/2016 1400", "02/10/2016 1400").withDateTime("03/10/2016 1400", "04/10/2016 1400").build();
            rsvTaskB = new RsvTaskBuilder().withName("Rsv Task B").withDateTime("05/10/2016 1400", "06/10/2016 1400").build();

            //Manually added
            taskH = new TaskBuilder().withName("Task H").withDateTime("08/09/2016 1400" , "09/09/2016 1400").withPriority("m").build();
            taskI = new TaskBuilder().withName("Task I").withDateTime("09/09/2016 1400" , "10/09/2016 1400").withPriority("l").build();
            cfmTaskA = new TaskBuilder().withName("Rsv Task A").withDateTime("03/10/2016 1400", "04/10/2016 1400").withPriority("h").build();
            rsvTaskC = new RsvTaskBuilder().withName("Rsv Task C").withDateTime("07/10/2016 1400", "08/10/2016 1400").withDateTime("09/10/2016 1400", "10/10/2016 1400").build();
            rsvTaskD = new RsvTaskBuilder().withName("Rsv Task D").withDateTime("11/10/2016 1400", "12/10/2016 1400").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTarsWithSampleData(Tars tars) {

        try {
            tars.addTask(new Task(taskA));
            tars.addTask(new Task(taskB));
            tars.addTask(new Task(taskC));
            tars.addTask(new Task(taskD));
            tars.addTask(new Task(taskE));
            tars.addTask(new Task(taskF));
            tars.addTask(new Task(taskG));
            tars.addRsvTask(new RsvTask(rsvTaskA));
            tars.addRsvTask(new RsvTask(rsvTaskB));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG};
    }
    
    public TestRsvTask[] getTypicalRsvTasks() {
        return new TestRsvTask[]{rsvTaskA, rsvTaskB};
    }

    public Tars getTypicalTars(){
        Tars tars = new Tars();
        loadTarsWithSampleData(tars);
        return tars;
    }
}

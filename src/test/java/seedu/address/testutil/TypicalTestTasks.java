package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask apples, bananas, chores, dinner, event, fishing, garden, hunt, ikea;

    public TypicalTestTasks() {
        try {
            apples =  new TaskBuilder().withName("buy apples").withTime("").withDate("").build();
            bananas = new TaskBuilder().withName("buy bananas").withTime("").withDate("").build();
            chores = new TaskBuilder().withName("sweep").withDate("").withTime("").build();
            dinner = new TaskBuilder().withName("dinner with daniel").withDate("01/10/16").withTime("5:30pm to 6:00pm").build();
            event = new TaskBuilder().withName("EE2020 discussion").withDate("03/9/16").withTime("5:30pm to 6:00pm").build();
            fishing = new TaskBuilder().withName("fishing trip in malaysia truly asia").withDate("13/11/17").withTime("1:30am to 9:47pm").build();
            garden = new TaskBuilder().withName("gardening").withDate("11/11/16").withTime("").build();

            //Manually added
            hunt = new TaskBuilder().withName("Hunting").withDate("31/12/16").withTime("").build();
            ikea = new TaskBuilder().withName("go ikea").withDate("12/12/16").withTime("").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(apples));
            ab.addTask(new Task(bananas));
            ab.addTask(new Task(chores));
            ab.addTask(new Task(dinner));
            ab.addTask(new Task(event));
            ab.addTask(new Task(fishing));
            ab.addTask(new Task(garden));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{apples, bananas, chores, dinner, event, fishing, garden};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

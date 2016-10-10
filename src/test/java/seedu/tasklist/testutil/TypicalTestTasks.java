package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("CS2103")
                    .withDescription("pretutorial activity").withStartDate("18122016").withDueDate("20122016")
                    .withTags("urgent").build();
            benson = new TaskBuilder().withTitle("CS1010")
                    .withDescription("take hime lab").withStartDate("20062016").withDueDate("31062016")
                    .withTags("extremely", "important").build();
            carl = new TaskBuilder().withTitle("CS1020").withDescription("sit in lab").withStartDate("28062016").withDueDate("30062016").build();
            daniel = new TaskBuilder().withTitle("GER1000").withDescription("mid term test").withStartDate("15072016").withDueDate("18062016").build();
            elle = new TaskBuilder().withTitle("MA1505").withDescription("tutorial").withStartDate("30092016").withDueDate("01102016").build();
            fiona = new TaskBuilder().withTitle("MA1506").withDescription("mid term test").withStartDate("08072016").withDueDate("09072016").build();
            george = new TaskBuilder().withTitle("PC1222").withDescription("lab assignmnet").withStartDate("10102016").withDueDate("17102016").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("CG1001").withDescription("Report deadline").withStartDate("09082016").withDueDate("12082016").build();
            ida = new TaskBuilder().withTitle("EE2021").withDescription("homework dealine").withStartDate("10092016").withDueDate("14092016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskList getTypicalTaskList(){
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}

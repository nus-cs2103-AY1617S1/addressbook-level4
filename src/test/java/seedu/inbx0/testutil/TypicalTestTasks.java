package seedu.inbx0.testutil;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.TaskList;
import seedu.inbx0.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withStartDate("4th Nov")
                    .withStartTime("3pm").withEndDate("6th Nov").withEndTime("4pm").withImportance("green")
                    .withTags("lunch").build();
            benson = new TaskBuilder().withName("Benson Meier").withStartDate("6th September")
                    .withStartTime("noon").withEndDate("7th September").withEndTime("9pm").withImportance("y")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withStartDate("tomorrow").withStartTime("3pm").withEndDate("tomorrow").withEndTime("9pm").withImportance("r").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withStartDate("next week").withStartTime("12pm").withEndDate("next week").withEndTime("9pm").withImportance("red").build();
            elle = new TaskBuilder().withName("Elle Meyer").withStartDate("next wednesday").withStartTime("12pm").withEndDate("next friday").withEndTime("3pm").withImportance("yellow").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withStartDate("next month").withStartTime("12pm").withEndDate("next month").withEndTime("5pm").withImportance("g").build();
            george = new TaskBuilder().withName("George Best").withStartDate("4th November").withStartTime("0930").withEndDate("5th November").withEndTime("3pm").withImportance("R").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withStartDate("next week").withStartTime("1000").withEndDate("next week").withEndTime("1300").withImportance("G").build();
            ida = new TaskBuilder().withName("Ida Mueller").withStartDate("tmr").withStartTime("1000").withEndDate("next week").withEndTime("12pm").withImportance("Yellow").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) throws IllegalValueException {

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

    public TaskList getTypicalTaskList() throws IllegalValueException{
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}

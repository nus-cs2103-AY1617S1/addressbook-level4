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
            alice =  new TaskBuilder().withName("Alice Pauline").withStartDate("4-10-2016")
                    .withStartTime("3pm").withEndDate("6-10-2016").withEndTime("4pm").withImportance("green")
                    .withTags("lunch").build();
            benson = new TaskBuilder().withName("Benson Meier").withStartDate("311, Clementi Ave 2, #02-25")
                    .withStartTime("johnd@gmail.com").withEndDate("98765432").withImportance("y")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withStartDate("tomorrow").withStartTime("3pm").withEndDate("tomorrow").withEndTime("9pm").withImportance("r").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withStartDate("next week").withStartTime("12pm").withEndDate("next week").withEndTime("9pm").withImportance("red").build();
            elle = new TaskBuilder().withName("Elle Meyer").withStartDate("next wednesday").withStartTime("12pm").withEndDate("next friday").withEndTime("3pm").withImportance("yellow").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withStartDate("4-6-2017").withStartTime("12pm").withEndDate("6-2-2017").withEndTime("5pm").withImportance("g").build();
            george = new TaskBuilder().withName("George Best").withStartDate("4th November").withStartTime("0930").withEndDate("5th November").withEndTime("3pm").withImportance("R").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withStartDate("4/5/2010").withStartTime("1000").withEndDate("4/5/2010").withEndTime("0300").withImportance("G").build();
            ida = new TaskBuilder().withName("Ida Mueller").withStartDate("tmr").withStartTime("1000").withEndDate("next week").withEndTime("12pm").withImportance("Yellow").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) throws IllegalValueException {

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

    public TaskList getTypicalAddressBook() throws IllegalValueException{
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

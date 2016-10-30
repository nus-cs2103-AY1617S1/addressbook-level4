package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * @@author A0138993L
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withEnd("2359")
                    .withStart("1100").withDate("12.12.23")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withEnd("6pm")
                    .withStart("2am").withDate("01.05.23")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("12/12/16").withStart("default").withEnd("2359").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("15.11.23").withStart("7am").withEnd("11pm").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("29/05/12").withStart("0001").withEnd("1212").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("no date").withStart("no start").withEnd("no end").build();
            george = new TaskBuilder().withName("George Best").withDate("210223").withStart("1111").withEnd("1212").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("010123").withStart("no start").withEnd("no end").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("101023").withStart("default").withEnd("default").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalAddressBook(){
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

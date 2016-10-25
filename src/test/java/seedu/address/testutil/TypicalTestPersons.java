package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * @@author A0138993L
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withEnd("2359")
                    .withStart("1100").withDate("12.12.23")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withEnd("6pm")
                    .withStart("2am").withDate("1.5.23")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withDate("1-2-23").withStart("1000").withEnd("1300").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withDate("15.11.23").withStart("7am").withEnd("1100").build();
            elle = new PersonBuilder().withName("Elle Meyer").withDate("290523").withStart("0000").withEnd("1212").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withDate("120223").withStart("1111").withEnd("1212").build();
            george = new PersonBuilder().withName("George Best").withDate("210223").withStart("1111").withEnd("1212").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withDate("010123").withStart("1212").withEnd("1500").build();
            ida = new PersonBuilder().withName("Ida Mueller").withDate("101023").withStart("1212").withEnd("1700").build();
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

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalAddressBook(){
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

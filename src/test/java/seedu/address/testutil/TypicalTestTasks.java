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
                    .withStart("1100").withDate("12.12.23").withTaskCat(1).withIsCompleted(false)
                    .withOverdue(0).withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withEnd("6pm")
                    .withStart("2am").withDate("01.05.23").withTaskCat(1).withIsCompleted(false)
                    .withOverdue(0).withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("12/12/16").withStart("default").withEnd("2350")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("15.11.23").withStart("7am").withEnd("11pm")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("29/05/36").withStart("0001").withEnd("1212")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("12/12/16").withStart("1000").withEnd("1300")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
            george = new TaskBuilder().withName("George Best").withDate("210223").withStart("1111").withEnd("1212")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("010123").withStart("10am").withEnd("2pm")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("101023").withStart("2am").withEnd("4pm")
            		.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
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

    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

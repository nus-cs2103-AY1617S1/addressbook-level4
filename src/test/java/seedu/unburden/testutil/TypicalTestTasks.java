package seedu.unburden.testutil;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, luhua, haha, hahaha, hahahaha;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withName("Alice Pauline").withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").build();
            daniel = new TaskBuilder().withName("Daniel Meier").build();
            elle = new TaskBuilder().withName("Elle Meyer").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").build();
            george = new TaskBuilder().withName("George Best").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").build();
            
            ida = new TaskBuilder().withName("Ida Mueller").build();
            
            luhua = new TaskBuilder().withName("Luhua Yang").withDate("21-11-2016").build();
            
            haha = new TaskBuilder().withName("haha").build();
            
            hahaha = new TaskBuilder().withName("hahaha").withDate("31-10-2016").withEndTime("2100").build();
            
            hahahaha = new TaskBuilder().withName("hahahaha").withDate("31-10-2016").withStartTime("1900").withEndTime("2100").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadListOfTaskWithSampleData(ListOfTask ab) throws IllegalValueException {

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

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ListOfTask getTypicalListOfTask() throws IllegalValueException {
        ListOfTask ab = new ListOfTask();
        loadListOfTaskWithSampleData(ab);
        return ab;
    }
}

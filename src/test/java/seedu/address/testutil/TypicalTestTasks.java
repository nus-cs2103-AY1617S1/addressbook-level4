package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withDescription("Alice Pauline").withDateTime("22/01/2014 12:01")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Benson Meier").withDateTime("21/03/2015 11:00")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Carl Kurz").withDateTime("01/05/2015").build();
            daniel = new TaskBuilder().withDescription("Daniel Meier").withDateTime("01/06/2015").build();
            elle = new TaskBuilder().withDescription("Elle Meyer").withDateTime("30/11/2016").build();
            fiona = new TaskBuilder().withDescription("Fiona Kunz").withDateTime("30/01/2017").build();
            george = new TaskBuilder().withDescription("George Best").withDateTime("30/05/2017").build();

            //Manually added
            hoon = new TaskBuilder().withDescription("Hoon Meier").withDateTime("20/11/2016").build();
            ida = new TaskBuilder().withDescription("Ida Mueller").withDateTime("20/12/2016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEmeraldoWithSampleData(Emeraldo ab) {

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

    public Emeraldo getTypicalEmeraldo(){
        Emeraldo ab = new Emeraldo();
        loadEmeraldoWithSampleData(ab);
        return ab;
    }
}

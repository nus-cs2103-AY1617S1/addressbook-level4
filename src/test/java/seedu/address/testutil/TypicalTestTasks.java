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
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("22/01/2014 12:01")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("21/03/2015 11:00")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withAddress("01/05/2015").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withAddress("01/06/2015").build();
            elle = new PersonBuilder().withName("Elle Meyer").withAddress("30/11/2016").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withAddress("30/01/2017").build();
            george = new PersonBuilder().withName("George Best").withAddress("30/05/2017").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withAddress("20/11/2016").build();
            ida = new PersonBuilder().withName("Ida Mueller").withAddress("20/12/2016").build();
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

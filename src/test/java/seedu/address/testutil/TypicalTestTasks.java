package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, 08-111")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, 02-25")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(Emeraldo ab) {

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

    public TestPerson[] getTypicalTasks() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public Emeraldo getTypicalEmeraldo(){
        Emeraldo ab = new Emeraldo();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withStart("alice@gmail.com").withDate("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withStart("johnd@gmail.com").withDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withDate("95352563").withStart("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withDate("87652533").withStart("cornelia@google.com").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withDate("9482224").withStart("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withDate("9482427").withStart("lydia@gmail.com").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withDate("9482442").withStart("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withDate("8482424").withStart("stefan@mail.com").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withDate("8482131").withStart("hans@google.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addPerson(new Person(alice));
            ab.addPerson(new Person(benson));
            ab.addPerson(new Person(carl));
            ab.addPerson(new Person(daniel));
            ab.addPerson(new Person(elle));
            ab.addPerson(new Person(fiona));
            ab.addPerson(new Person(george));
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

package harmony.testutil;

import harmony.commons.exceptions.IllegalValueException;
import harmony.model.TaskManager;
import harmony.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline")
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {

        try {
            ab.addPerson(new Task(alice));
            ab.addPerson(new Task(benson));
            ab.addPerson(new Task(carl));
            ab.addPerson(new Task(daniel));
            ab.addPerson(new Task(elle));
            ab.addPerson(new Task(fiona));
            ab.addPerson(new Task(george));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalAddressBook(){
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

package seedu.address.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withUniqueID(1)
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withUniqueID(2)
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withUniqueID(3).build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withUniqueID(4).build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withUniqueID(5).build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withUniqueID(6).build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withUniqueID(7).build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").withUniqueID(8).build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").withUniqueID(9).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {

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

    public TaskList getTypicalAddressBook(){
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

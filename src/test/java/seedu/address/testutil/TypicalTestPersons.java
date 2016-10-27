package seedu.address.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.AddressBook;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson CS2103_Project, CS2103_Lecture, MA1101R_Homework, soccerWithFriends, buyGroceries, goSkydiving, watchMovie,
    				learnPython, learnSpanish;

    public TypicalTestPersons() {
        try {
            CS2103_Project =  new PersonBuilder().withName("CS2103 Project").withAddress("6pm")
                    .withEmail("3pm").withPhone("today").build();
            CS2103_Lecture = new PersonBuilder().withName("CS2103 Lecture").withAddress("4pm")
                    .withEmail("2pm").withPhone("next friday").build();
            MA1101R_Homework = new PersonBuilder().withName("MA1101R Homework").withPhone("today").withEmail("3pm").withAddress("5pm").build();
            soccerWithFriends = new PersonBuilder().withName("Soccer with friends").withPhone("tomorrow").withEmail("1pm").withAddress("5pm").build();
            buyGroceries = new PersonBuilder().withName("Buy groceries").withPhone("Sunday").withEmail("10am").withAddress("12pm").build();
            goSkydiving = new PersonBuilder().withName("Go skydiving").withPhone("next month").withEmail("2am").withAddress("10am").build();
            watchMovie = new PersonBuilder().withName("Watch movie").withPhone("tomorrow").withEmail("10pm").withAddress("12am").build();

            //Manually added
            learnPython = new PersonBuilder().withName("Hoon Meier").withPhone("today").withEmail("2pm").withAddress("5pm").build();
            learnSpanish = new PersonBuilder().withName("Ida Mueller").withPhone("tomorrow").withEmail("1am").withAddress("2am").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Task(CS2103_Project));
            ab.addPerson(new Task(CS2103_Lecture));
            ab.addPerson(new Task(MA1101R_Homework));
            ab.addPerson(new Task(soccerWithFriends));
            ab.addPerson(new Task(buyGroceries));
            ab.addPerson(new Task(goSkydiving));
            ab.addPerson(new Task(watchMovie));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{CS2103_Project, CS2103_Lecture, MA1101R_Homework, soccerWithFriends, buyGroceries, goSkydiving, watchMovie};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

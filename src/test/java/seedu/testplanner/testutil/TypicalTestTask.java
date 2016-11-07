package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.AddressBook;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TypicalTestTask {

    public static TestTask CS2103_Project, CS2103_Lecture, MA1101R_Homework, soccerWithFriends, buyGroceries, goSkydiving, watchMovie,
    				learnPython, learnSpanish;

    public TypicalTestTask() {
        try {
            CS2103_Project =  new TaskBuilder().withName("CS2103 Project").withEndDateAndTime("6pm")
                    .withEmail("3pm").withStart("today").build();
            CS2103_Lecture = new TaskBuilder().withName("CS2103 Lecture").withEndDateAndTime("4pm")
                    .withEmail("2pm").withStart("next friday").build();
            MA1101R_Homework = new TaskBuilder().withName("MA1101R Homework").withStart("today").withEmail("3pm").withEndDateAndTime("5pm").build();
            soccerWithFriends = new TaskBuilder().withName("Soccer with friends").withStart("tomorrow").withEmail("1pm").withEndDateAndTime("5pm").build();
            buyGroceries = new TaskBuilder().withName("Buy groceries").withStart("Sunday").withEmail("10am").withEndDateAndTime("12pm").build();
            goSkydiving = new TaskBuilder().withName("Go skydiving").withStart("next month").withEmail("2am").withEndDateAndTime("10am").build();
            watchMovie = new TaskBuilder().withName("Watch movie").withStart("tomorrow").withEmail("10pm").withEndDateAndTime("12am").build();

            //Manually added
            learnPython = new TaskBuilder().withName("Hoon Meier").withStart("today").withEmail("2pm").withEndDateAndTime("5pm").build();
            learnSpanish = new TaskBuilder().withName("Ida Mueller").withStart("tomorrow").withEmail("1am").withEndDateAndTime("2am").build();
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

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{CS2103_Project, CS2103_Lecture, MA1101R_Homework, soccerWithFriends, buyGroceries, goSkydiving, watchMovie};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

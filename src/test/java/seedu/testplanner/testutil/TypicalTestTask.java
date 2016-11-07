package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.AddressBook;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TypicalTestTask {

	public static TestTask CS2103_Lecture, CS2103_Project, MA1101R_Homework, SoccerWithFriends, BuyGroceries,
			WatchMovie, learnPython, learnSpanish, GoSkydiving;

	public TypicalTestTask() {
		try {
			CS2103_Project = new TaskBuilder().withName("CS2103 Project").withStartDateAndTime("yesterday 1am")
					.withEndDateAndTime("yesterday 2am").withCompletion(false).withPin(false).withCategories("Homework")
					.build();
			CS2103_Lecture = new TaskBuilder().withName("CS2103 Lecture").withStartDateAndTime("yesterday 2pm")
					.withEndDateAndTime("yesterday 4pm").withCompletion(true).withPin(false).withCategories("School")
					.build();
			MA1101R_Homework = new TaskBuilder().withName("MA1101R Homework").withStartDate("yesterday 10pm")
					.withEndDateAndTime("yesterday 11pm").withCompletion(false).withPin(false).build();
			SoccerWithFriends = new TaskBuilder().withName("Soccer with friends").withStartDateAndTime("today 10am")
					.withEndDateAndTime("today 12pm").withCompletion(false).withPin(false).withCategories("Sports")
					.build();
			BuyGroceries = new TaskBuilder().withName("Buy groceries").withStartDateAndTime("today 2pm")
					.withEndDateAndTime("today 4pm").withCompletion(false).withPin(false).withCategories("Chores")
					.build();
			WatchMovie = new TaskBuilder().withName("Watch movie").withStartDateAndTime("tomorrow 6pm")
					.withEndDateAndTime("tomorrow 8pm").withCompletion(false).withPin(false).build();
			learnPython = new TaskBuilder().withName("Learn python").withStartDate("next month").withoutEnd()
					.withCompletion(false).withPin(false).withCategories("Selfimprovement").build();
			// Manually added
			learnSpanish = new TaskBuilder().withName("Learn Spanish").withStartDateAndTime("next year").withoutEnd()
					.withCompletion(false).withPin(false).build();
			GoSkydiving = new TaskBuilder().withName("Go skydiving").withoutStart().withoutEnd().withCompletion(false)
					.withPin(false).withCategories("Bucketlist").build();
		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public static void loadAddressBookWithSampleData(AddressBook dp) {

		try {
			dp.addPerson(new Task(CS2103_Project));
			dp.addPerson(new Task(CS2103_Lecture));
			dp.addPerson(new Task(MA1101R_Homework));
			dp.addPerson(new Task(SoccerWithFriends));
			dp.addPerson(new Task(BuyGroceries));
			dp.addPerson(new Task(WatchMovie));
			dp.addPerson(new Task(learnPython));
		} catch (UniqueTaskList.DuplicatePersonException e) {
			assert false : "not possible";
		}
	}

	public TestTask[] getTypicalPersons() {
		return new TestTask[] { CS2103_Project, CS2103_Lecture, MA1101R_Homework, SoccerWithFriends, BuyGroceries,
				WatchMovie, learnPython };
	}

	public AddressBook getTypicalAddressBook() {
		AddressBook ab = new AddressBook();
		loadAddressBookWithSampleData(ab);
		return ab;
	}
}

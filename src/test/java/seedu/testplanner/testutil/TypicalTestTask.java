package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.AddressBook;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class TypicalTestTask {

	public static TestTask CS2103_Project, CS2103_Lecture, MA1101R_Homework, SoccerWithFriends, BuyGroceries,
			GoSkydiving, WatchMovie, learnPython, learnSpanish;

	public TypicalTestTask() {
		try {
			CS2103_Project = new TaskBuilder().withName("CS2103 Project").withStartDateAndTime("today 6pm")
					.withEndDateAndTime("today 10pm").withCompletion(false).withPin(false).withCategories("Homework")
					.build();
			CS2103_Lecture = new TaskBuilder().withName("CS2103 Lecture").withStartDateAndTime("friday 2pm")
					.withEndDateAndTime("friday 4pm").withCompletion(true).withPin(false).withCategories("School")
					.build();
			MA1101R_Homework = new TaskBuilder().withName("MA1101R Homework").withStartDate("tomorrow")
					.withEndDateAndTime("tomorrow 11pm").withCompletion(false).withPin(false).build();
			SoccerWithFriends = new TaskBuilder().withName("Soccer with friends").withStartDateAndTime("tomorrow 6pm")
					.withEndDateAndTime("today 7pm").withCompletion(false).withPin(false).withCategories("Sports")
					.build();
			BuyGroceries = new TaskBuilder().withName("Buy groceries").withStartDateAndTime("next saturday 6am")
					.withEndDateAndTime("next saturday 7am").withCompletion(false).withPin(false)
					.withCategories("Chores").build();
			GoSkydiving = new TaskBuilder().withName("Go skydiving").withoutStart().withoutEnd().withCompletion(false)
					.withPin(false).withCategories("Bucketlist").build();
			WatchMovie = new TaskBuilder().withName("Watch movie").withStartDateAndTime("next friday 6pm")
					.withEndDateAndTime("next friday 8pm").withCompletion(false).withPin(false).build();

			// Manually added
			learnPython = new TaskBuilder().withName("Learn python").withStartDate("next month")
					.withoutEnd().withCompletion(false).withPin(false).withCategories("Selfimprovement").build();
			learnSpanish = new TaskBuilder().withName("Learn Spanish").withStartDateAndTime("next year")
					.withoutEnd().withCompletion(false).withPin(false).build();
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
			dp.addPerson(new Task(GoSkydiving));
			dp.addPerson(new Task(WatchMovie));
		} catch (UniqueTaskList.DuplicatePersonException e) {
			assert false : "not possible";
		}
	}

	public TestTask[] getTypicalPersons() {
		return new TestTask[] { CS2103_Project, CS2103_Lecture, MA1101R_Homework, SoccerWithFriends, BuyGroceries,
				GoSkydiving, WatchMovie };
	}

	public AddressBook getTypicalAddressBook() {
		AddressBook ab = new AddressBook();
		loadAddressBookWithSampleData(ab);
		return ab;
	}
}

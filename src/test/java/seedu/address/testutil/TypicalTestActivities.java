package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.task.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity findAlice, findHoon;
    public static TestTask findBenson, findCarl, findDaniel, findIda; 
    public static TestEvent findElle, findFiona, findGeorge, findJodie;

    public TypicalTestActivities() {
        try {
        	//to test loading saved activity, tasks and events
            findAlice =  new ActivityBuilder().withName("find Alice").withReminder("Sat, Dec 30, 2017 12:00 PM").withTags("bringgift").build();
            findBenson = new TaskBuilder().withName("find Benson").withDueDate("Sat, Dec 30, 2017 12:00 PM").withTags("bringgift").build();
            findCarl = new TaskBuilder().withName("find Carl").withDueDate("Sat, Dec 30, 2017 12:00 PM").build();
            findDaniel = new TaskBuilder().withName("find Daniel").withDueDate("Sat, Dec 30, 2017 12:00 PM").withReminder("Sat, Dec 30, 2017 11:59 AM").withTags("bringgifts").build();
            findElle = new EventBuilder().withName("find Elle").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Sat, Dec 30, 2017 11:59 PM").build();
            findFiona = new EventBuilder().withName("find Fiona").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Fri, Dec 29, 2017 12:00 PM").build();
            findGeorge = new EventBuilder().withName("find George").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withTags("bringgifts").build();

            //To test adding of activity, task and event.
            findHoon = new ActivityBuilder().withName("find Hoon").withReminder("Sat, Dec 30, 2017 12:00 PM").withTags("bringgift").build();
            findIda = new TaskBuilder().withName("find Ida").withDueDate("Sat, Dec 30, 2017 12:00 PM").withReminder("Fri, Dec 29, 2017 12:00 PM").withTags("bringgifts").build();
            findJodie = new EventBuilder().withName("find Jodie").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Fri, Dec 29, 2017 12:00 PM").withTags("bringgifts").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
    	

        try {
            ab.addPerson(new Activity(findAlice));
            ab.addPerson(new Task(findBenson));
            ab.addPerson(new Task(findCarl));
            ab.addPerson(new Task(findDaniel));
            ab.addPerson(new Event(findElle));
            ab.addPerson(new Event(findFiona));
            ab.addPerson(new Event(findGeorge)); 
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
        
    }

    public TestActivity[] getTypicalPersons() {
        return new TestActivity[]{findAlice, findBenson, findCarl, findDaniel, findElle, findFiona, findGeorge};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}

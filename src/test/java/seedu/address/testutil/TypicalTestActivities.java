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
            findBenson = new TaskBuilder().withName("find Benson").withDueDate("31-12-2017 1400").withTags("bringgift").build();
            findCarl = new TaskBuilder().withName("find Carl").withDueDate("31-12-2017 1200").build();
            findDaniel = new TaskBuilder().withName("find Daniel").withDueDate("31-12-2017 1200").withReminder("30-12-2017 1200").withTags("bringgifts").build();
            findElle = new EventBuilder().withName("find Elle").withStartTime("31-12-2017 1200").withReminder("30-12-2017 1200").build();
            findFiona = new EventBuilder().withName("find Fiona").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withReminder("30-12-2017 1200").build();
            findGeorge = new EventBuilder().withName("find George").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withTags("bringgifts").build();

            //To test adding of activity, task and event.
            findHoon = new ActivityBuilder().withName("find Hoon").withReminder("Sat, Dec 30, 2017 12:00 PM").withTags("bringgift").build();
            findIda = new TaskBuilder().withName("find Ida").withDueDate("31-12-2017 1200").withReminder("30-12-2017 1200").withTags("bringgifts").build();
            findJodie = new EventBuilder().withName("find Jodie").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withReminder("30-12-2017 1200").withTags("bringgifts").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
    	
    	//TO KEVIN:: THERE IS SOMETHING WRONG WITH THIS BLOCK OF CODE.
    	/*
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
        }*/
        
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

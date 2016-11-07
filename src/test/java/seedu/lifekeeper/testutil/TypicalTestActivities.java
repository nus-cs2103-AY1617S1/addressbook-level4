package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.task.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity findAlice, findHoon, findLisa;
    public static TestTask findBenson, findCarl, findDaniel, findIda; 
    public static TestEvent findElle, findFiona, findGeorge, findJodie, findKenny, findMoney;

    public TypicalTestActivities() {
        try {
        	//to test loading saved activity, tasks and events
            findAlice =  new ActivityBuilder().withName("find Alice").withReminder("Sat, Dec 30, 2017 12:00 PM").withTags("lunch").build();
            findBenson = new TaskBuilder().withName("find Benson").withDueDate("Sat, Dec 30, 2017 12:00 PM").withPriority("1").withTags("lunch").build();
            findCarl = new TaskBuilder().withName("find Carl").withDueDate("Sat, Dec 30, 2017 12:00 PM").build();
            findDaniel = new TaskBuilder().withName("find Daniel").withDueDate("Sat, Dec 30, 2017 12:00 PM").withPriority("2").withReminder("Sat, Dec 30, 2017 11:59 AM").withTags("lunch").build();
            findElle = new EventBuilder().withName("find Elle").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Sat, Dec 30, 2017 11:59 PM").build();
            findFiona = new EventBuilder().withName("find Fiona").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Fri, Dec 29, 2017 12:00 PM").build();
            findGeorge = new EventBuilder().withName("find George").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withTags("dinner").build();

            //To test adding of activity, task and event.
            findHoon = new ActivityBuilder().withName("find Hoon").withReminder("Sat, Dec 30, 2017 12:00 PM").withTags("bringgift").build();
            findIda = new TaskBuilder().withName("find Ida").withDueDate("Sat, Dec 30, 2017 12:00 PM").withPriority("1").withReminder("Fri, Dec 29, 2017 12:00 PM").withTags("dinner").build();
            findJodie = new EventBuilder().withName("find Jodie").withStartTime("Sat, Dec 30, 2017 12:00 PM").withEndTime("Sun, Dec 31, 2017 12:00 PM").withReminder("Fri, Dec 29, 2017 12:00 PM").withTags("bringgifts").build();
            findKenny = new EventBuilder().withName("find Kenny").withStartTime("Tue, Dec 26, 2017 11:00 AM").withEndTime("Tue, Dec 26, 2017 12:00 PM").withReminder("Mon, Dec 25, 2017 12:00 PM").withTags("bringgifts").build();

            
            //To test activities and events with recurring parameters.
            findLisa = new ActivityBuilder().withName("find Lisa").withReminder("every thu 1300").withTags("bringgift").build();
            findMoney = new EventBuilder().withName("find Money").withStartTime("every fri 1400").withEndTime("every fri 1500").withReminder("Sat, Dec 30, 2017 11:59 PM").withTags("dinner").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(LifeKeeper ab) {
    	

        try {
        	ab.addActivity(new Event(findGeorge));
            ab.addActivity(new Event(findFiona)); 
            ab.addActivity(new Event(findElle));
            ab.addActivity(new Task(findDaniel));
            ab.addActivity(new Task(findCarl));
            ab.addActivity(new Task(findBenson));
            ab.addActivity(new Activity(findAlice));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
        
    }

    public TestActivity[] getTypicalActivities() {
        return new TestActivity[]{ findAlice, findBenson, findCarl, findDaniel, findElle, findFiona, findGeorge };
    }

    public LifeKeeper getTypicalAddressBook(){
        LifeKeeper ab = new LifeKeeper();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
    public TestActivity[] getActivitiesOnly() {
        return new TestActivity[]{ findAlice };
    }
    
    public TestActivity[] getTasksOnly() {
        return new TestActivity[]{ findBenson, findCarl, findDaniel };
    }
    
    public TestActivity[] getEventsOnly() {
        return new TestActivity[]{ findElle, findFiona, findGeorge };
    }
    
    public TestActivity[] getTaggedActivitiesOnly(String tag) {
    	if(tag.equals("lunch")){
    	return new TestActivity[]{ findAlice, findBenson, findDaniel };
    	} else if(tag.equals("dinner")){
    		return new TestActivity[]{findGeorge };
    	} else
    		return null;
    }
}

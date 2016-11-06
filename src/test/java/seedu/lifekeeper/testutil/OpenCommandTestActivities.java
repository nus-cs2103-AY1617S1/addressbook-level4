package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.task.Task;

/**
 * Test activities that match the data in testopen.xml.
 */
public class OpenCommandTestActivities {
    public static TestActivity findAlice2;
    public static TestTask findBenson2, findCarl2, findDaniel2; 
    public static TestEvent findElle2, findFiona2, findGeorge2;

    public OpenCommandTestActivities() {
        try {
            //to test loading saved activity, tasks and events
            findAlice2 =  new ActivityBuilder().withName("find Alice2").withReminder("Sun, Dec 30, 2018 11:00 AM").withTags("lunch", "dinner").build();
            findBenson2 = new TaskBuilder().withName("find Benson2").withDueDate("Wed, Jan 3, 2018 1:00 PM").withPriority("3").withTags("lunch").build();
            findCarl2 = new TaskBuilder().withName("find Carl2").withDueDate("Sat, Mar 3, 2018 11:59 PM").build();
            findDaniel2 = new TaskBuilder().withName("find Daniel2").withDueDate("Sat, May 5, 2018 12:00 PM").withPriority("1").withReminder("Sat, May 5, 2018 11:59 AM").withTags("lunch").build();
            findElle2 = new EventBuilder().withName("find Elle2").withStartTime("Fri, Apr 6, 2018 12:00 PM").withEndTime("Sat, Apr 7, 2018 12:00 PM").withReminder("Fri, Apr 6, 2018 11:59 AM").build();
            findFiona2 = new EventBuilder().withName("find Fiona2").withStartTime("Sun, Dec 30, 2018 12:00 PM").withEndTime("Mon, Dec 31, 2018 12:00 PM").withReminder("Sat, Dec 29, 2018 12:00 PM").build();
            findGeorge2 = new EventBuilder().withName("find George2").withStartTime("Sun, Dec 31, 2017 12:00 PM").withEndTime("Mon, Jan 1, 2018 12:00 PM").withTags("dinner", "supper").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public TestActivity[] getTypicalActivities() {
        return new TestActivity[]{ findAlice2, findBenson2, findCarl2, findDaniel2, findElle2, findFiona2, findGeorge2 };
    }
}

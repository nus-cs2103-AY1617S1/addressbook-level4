package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;
/**
 *
 */
public class TypicalTestEvent {

   
    public static TestEvent  e1, e2, e3, e4, e5, e6, e7, e8;

    public TypicalTestEvent() {
        try {
            e1 = new EventBuilder().withName("e 1").withDate("30-10-2016").withStartTime("0100").withEndTime("0130").build();
            e2 = new EventBuilder().withName("e 2").withDate("26-10-2016").withStartTime("0200").withEndTime("0230").build();
            e3 = new EventBuilder().withName("Eeambuilding 3").withDate("27-10-2016").withStartTime("0300").withEndTime("0330").build();
            e4 = new EventBuilder().withName("Essignment 4").withDate("27-10-2016").withStartTime("0400").withEndTime("0430").build();
            e5 = new EventBuilder().withName("Eroject 5").withDate("28-10-2016").withStartTime("0500").withEndTime("0530").build();
            //Manually added
            e6 = new EventBuilder().withName("Essignment 6").withDate("28-10-2016").withStartTime("0600").withEndTime("0630").build();
            e7 = new EventBuilder().withName("Eomework 7").withDate("29-10-2016").withStartTime("0700").withEndTime("0730").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEventListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Event(e1));
            ab.addTask(new Event(e2));
            ab.addTask(new Event(e3));
            ab.addTask(new Event(e4));
            ab.addTask(new Event(e5));
            ab.addTask(new Event(e6));
            ab.addTask(new Event(e7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }
    }

    public TestEvent[] getTypicalEvent() {
        return new TestEvent[]{e1, e2, e3, e4, e5, e6, e7};
    }

    public TaskList getTypicalEventList(){
        TaskList ab = new TaskList();
        loadEventListWithSampleData(ab);
        return ab;
    }
}

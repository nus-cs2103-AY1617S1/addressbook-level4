package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;
/**
 *
 */
//@@author A0132157M reused
public class TypicalTestEvent {

   
    public static TestEvent  e1, e2, e3, e4, e5, e6, e7, e8;

    public TypicalTestEvent() {
/*        try {
            e1 = new EventBuilder().withName("e 1").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e2 = new EventBuilder().withName("e 2").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e3 = new EventBuilder().withName("Eeambuilding 3").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e4 = new EventBuilder().withName("Essignment 4").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e5 = new EventBuilder().withName("Eroject 5").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            //Manually added
            e6 = new EventBuilder().withName("Essignment 6").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();
            e7 = new EventBuilder().withName("Eomework 7").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }*/
    }

    public static void loadEventListWithSampleData(TaskList ab) {

/*        try {
            ab.addTask(new Event(e1));
            ab.addTask(new Event(e2));
            ab.addTask(new Event(e3));
            ab.addTask(new Event(e4));
            ab.addTask(new Event(e5));
            ab.addTask(new Event(e6));
            ab.addTask(new Event(e7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }*/
    }

    public TestEvent[] getTypicalEvent() throws IllegalValueException {
        return new TestEvent[]{
                new EventBuilder().withName("eventgtt 1").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
                new EventBuilder().withName("eventgtt 2").withStartDate("01-11-2016").withEndDate("02-11-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
                new EventBuilder().withName("eventgtt 3").withStartDate("02-11-2016").withEndDate("03-11-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
                new EventBuilder().withName("eventgtt 4").withStartDate("03-12-2016").withEndDate("04-12-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
                new EventBuilder().withName("eventgtt 5").withStartDate("05-12-2016").withEndDate("06-12-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build()};
    }

    public TaskList getTypicalEventList(){
        TaskList ab = new TaskList();
        loadEventListWithSampleData(ab);
        return ab;
    }
}

package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask floatingTask1, floatingTask2, deadline1, deadline2, event1, event2, event3, event4, manualFloatingTask, manualDeadline;

    public TypicalTestTasks() {
        try {
            floatingTask1 =  new TaskBuilder().withName("adjust meter")
                    .withTags("careful").build();
            floatingTask2 = new TaskBuilder().withName("bring along notes")
                    .withTags("pen", "cs2103").build();
            deadline1 = new TaskBuilder().withName("copy answer").dueOn("11012015 0006").build();
            deadline2 = new TaskBuilder().withName("do some sit-up").dueOn("11042016 0000").build();
            event1 = new TaskBuilder().withName("eat with mom").start("10072016 1250").end("10072016 1500").build();
            event2 = new TaskBuilder().withName("forgive and forget").start("01072016 1250").end("12122016 0600").build();
            event3 = new TaskBuilder().withName("go shopping").start("11052016 1250").end("11052016 1500").build();
            event4 = new TaskBuilder().withName("hopping").start("31062016 1250").end("13072016 1500").build();

            //Manually added
            manualFloatingTask = new TaskBuilder().withName("spa relaxation").build();
            manualDeadline = new TaskBuilder().withName("prepare for interview").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadmalitioWithSampleData(Malitio ab) {

        try {
            ab.addFloatingTask(new FloatingTask(floatingTask1));
            ab.addFloatingTask(new FloatingTask(floatingTask2));
            ab.addDeadline(new Deadline(deadline1));
            ab.addDeadline(new Deadline(deadline2));
            ab.addEvent(new Event(event1));
            ab.addEvent(new Event(event2));
            ab.addEvent(new Event(event3));
        } catch (DuplicateDataException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{floatingTask1, floatingTask2, deadline1, deadline2, event1, event2, event3};
    }

    public Malitio getTypicalMalitio(){
        Malitio ab = new Malitio();
        loadmalitioWithSampleData(ab);
        return ab;
    }
}

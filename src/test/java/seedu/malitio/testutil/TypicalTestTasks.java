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
            deadline1 = new TaskBuilder().withName("copy answer").build();
            deadline2 = new TaskBuilder().withName("do some sit-up").build();
            event1 = new TaskBuilder().withName("eat with mom").build();
            event2 = new TaskBuilder().withName("forgive with forget").build();
            event3 = new TaskBuilder().withName("go shopping").build();
            event4 = new TaskBuilder().withName("hopping").build();

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
            ab.addFloatingTask(new FloatingTask(deadline1));
            ab.addFloatingTask(new FloatingTask(deadline2));
            ab.addFloatingTask(new FloatingTask(event1));
            ab.addFloatingTask(new FloatingTask(event2));
            ab.addFloatingTask(new FloatingTask(event3));
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

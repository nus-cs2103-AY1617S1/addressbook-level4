package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.task.*;

/**
 *
 */
public class TypicalTestFloatingTasks {

    public static TestFloatingTask water, ideas, car, airport, lunch, flight, beach, night, dream;

    public TypicalTestFloatingTasks() {
        try {
            water =  new TaskBuilder().withName("add water").withTags("noturgent").build();
            ideas = new TaskBuilder().withName("brainstorm ideas").withTags("project").build();
            car = new TaskBuilder().withName("catch a car").build();
            airport = new TaskBuilder().withName("drive to airport").build();
            lunch = new TaskBuilder().withName("eat lunch").build();
            flight = new TaskBuilder().withName("fly to spain").build();
            beach = new TaskBuilder().withName("go to the beach").build();

            //Manually added
            night = new TaskBuilder().withName("have a nice night").build();
            dream = new TaskBuilder().withName("into a dream").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new FloatingTask(water));
            ab.addTask(new FloatingTask(ideas));
            ab.addTask(new FloatingTask(car));
            ab.addTask(new FloatingTask(airport));
            ab.addTask(new FloatingTask(lunch));
            ab.addTask(new FloatingTask(flight));
            ab.addTask(new FloatingTask(beach));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestFloatingTask[] getTypicalTasks() {
        return new TestFloatingTask[]{water, ideas, car, airport, lunch, flight, beach};
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}

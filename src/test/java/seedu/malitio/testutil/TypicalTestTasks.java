package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask sleep, eat, read, exercise, lecture, homework, play, relax, prepare, test;

    public TypicalTestTasks() {
        try {
            sleep =  new TaskBuilder().withName("sleep")
                    .withTags("early").build();
            eat = new TaskBuilder().withName("eat lunch")
                    .withTags("11am", "friends").build();
            read = new TaskBuilder().withName("read book").build();
            exercise = new TaskBuilder().withName("exercise in gym").build();
            lecture = new TaskBuilder().withName("cs2103 lecture").build();
            homework = new TaskBuilder().withName("cs2103 homework").build();
            play = new TaskBuilder().withName("play basketball").build();
            test = new TaskBuilder().withName("test").dueOn("11012015 0006").build();

            //Manually added
            relax = new TaskBuilder().withName("spa relaxation").build();
            prepare = new TaskBuilder().withName("prepare for interview").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadmalitioWithSampleData(Malitio ab) {

        try {
            ab.addTask(new FloatingTask(sleep));
            ab.addTask(new FloatingTask(eat));
            ab.addTask(new FloatingTask(read));
            ab.addTask(new FloatingTask(exercise));
            ab.addTask(new FloatingTask(lecture));
            ab.addTask(new FloatingTask(homework));
            ab.addTask(new FloatingTask(play));
        } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{sleep, eat, read, exercise, lecture, homework, play};
    }

    public Malitio getTypicalMalitio(){
        Malitio ab = new Malitio();
        loadmalitioWithSampleData(ab);
        return ab;
    }
}

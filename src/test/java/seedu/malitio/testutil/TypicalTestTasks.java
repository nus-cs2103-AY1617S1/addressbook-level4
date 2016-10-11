package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask sleep, eat, read, exercise, lecture, homework, play, relax, prepare;

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
            ab.addTask(new Task(sleep));
            ab.addTask(new Task(eat));
            ab.addTask(new Task(read));
            ab.addTask(new Task(exercise));
            ab.addTask(new Task(lecture));
            ab.addTask(new Task(homework));
            ab.addTask(new Task(play));
        } catch (UniqueTaskList.DuplicateTaskException e) {
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

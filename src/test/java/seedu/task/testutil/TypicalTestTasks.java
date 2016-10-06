package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask cs1010, cs1020, computing, science, biz, engine, music, arts, socSciences;

    public TypicalTestTasks() {
        try {
            cs1010 =  new TaskBuilder().withName("CS1010 CodeCrunch Practices").withDescription("20 Practices up to Lecture 7 syllabus").build();
            cs1020 = new TaskBuilder().withName("CS1020 CodeCrunch Practices").withDescription("20 Practices up to Lecture 7 syllabus").build();
            computing = new TaskBuilder().withName("Computing Project 1").withDescription("Complete my part before meeting").build();
            science = new TaskBuilder().withName("Science Project 1").withDescription("Complete my part before meeting").build();
            biz = new TaskBuilder().withName("Biz Project 1").withDescription("Complete my part before meeting").build();
            engine = new TaskBuilder().withName("Engineering Project 1").withDescription("Complete my part before meeting").build();
            music = new TaskBuilder().withName("Music Project 1").withDescription("Complete my part before meeting").build();

            //Manually added
            arts = new TaskBuilder().withName("Arts Project 1").withDescription("Complete my part before meeting").build();
            socSciences = new TaskBuilder().withName("Social Sciences Project 1").withDescription("Complete my part before meeting").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTestBookWithSampleData(TaskBook tb) {

        try {
            tb.addTask(new Task(cs1010));
            tb.addTask(new Task(cs1020));
            tb.addTask(new Task(computing));
            tb.addTask(new Task(science));
            tb.addTask(new Task(biz));
            tb.addTask(new Task(engine));
            tb.addTask(new Task(music));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{cs1010, cs1020, computing, science, biz, engine, music};
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook tb = new TaskBook();
        loadTestBookWithSampleData(tb);
        return tb;
    }
}

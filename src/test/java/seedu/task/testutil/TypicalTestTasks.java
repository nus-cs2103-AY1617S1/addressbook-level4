package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.item.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask cs1010, cs1020, computing, science, biz, engine, music, arts, socSciences, slack;

    public TypicalTestTasks() {
        try {
            cs1010 =  new TaskBuilder().withName("CS1010 CodeCrunch Practices").withDescription("20 Practices up to Lecture 7 syllabus").build();
            cs1020 = new TaskBuilder().withName("CS1020 CodeCrunch Practices").withDescription("20 Practices up to Lecture 7 syllabus").build();
            computing = new TaskBuilder().withName("Computing Project 1").withDescription("Complete my part before meeting").build();
            science = new TaskBuilder().withName("Science Project 1").withDescription("Complete my part before meeting").build();
            biz = new TaskBuilder().withName("Biz Project 1").withDescription("Complete my part before meeting").build();
            engine = new TaskBuilder().withName("Engineering Project 1").withDescription("Complete my part before meeting").build();
            music = new TaskBuilder().withName("Music Project 1").withDescription("Complete my part before meeting").build();

            //completed tasks
            slack = new TaskBuilder().withName("slack for one hour").withDescription("do not do any work").withStatus(true).build();
          
            
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
            tb.addTask(new Task(music));
            tb.addTask(new Task(engine));
            tb.addTask(new Task(cs1010));
            tb.addTask(new Task(cs1020));
            tb.addTask(new Task(slack));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{music, engine, cs1010, cs1020};
    }
    
    public TestTask[] getTypicalAllTasks() {
    	return new TestTask[] {music, engine, cs1010, cs1020,slack};
    }
    

    public TaskBook getTypicalTaskBook(){
        TaskBook tb = new TaskBook();
        loadTestBookWithSampleData(tb);
        return tb;
    }
}

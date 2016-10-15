package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask friend, lunch, book, work, movie, meeting, travel, project, workshop;

    public TypicalTestTasks() {
        try {
            friend =  new TaskBuilder().withName("Meet old friends").withDeadline("")
                    .withTags("friends").build();
            lunch = new TaskBuilder().withName("Eat lunch with friends").withDeadline("11.10.2016-12")
                    .withTags("lunch", "friends").build();
            book = new TaskBuilder().withName("Read book").withDeadline("").build();
            work = new TaskBuilder().withName("Work").withDeadline("11.10.2016").build();
            movie = new TaskBuilder().withName("Watch movie").withDeadline("11.10.2016-16").build();
            meeting = new TaskBuilder().withName("Project meeting").withEventDate("11.10.2016-10", "11.10.2016-12").build();
            travel = new TaskBuilder().withName("Travel").withEventDate("11.10.2016", "15.10.2016").build();

            //Manually added
            project = new TaskBuilder().withName("Project due").withDeadline("11.10.2016").build();
            workshop = new TaskBuilder().withName("Attend workshop").withEventDate("11.10.2016-10", "11.10.2016-16").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(friend));
            ab.addTask(new Task(lunch));
            ab.addTask(new Task(book));
            ab.addTask(new Task(work));
            ab.addTask(new Task(movie));
            ab.addTask(new Task(meeting));
            ab.addTask(new Task(travel));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{friend, lunch, book, work, movie, meeting, travel};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

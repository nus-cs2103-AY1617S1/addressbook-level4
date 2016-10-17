package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask trash, book, homework, lecture, meeting, jogging, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            trash =  new TaskBuilder().withName("take trash").withTags("notUrgent").build();
            book = new TaskBuilder().withName("read book").withTags("weekly", "textBook").build();
            homework = new TaskBuilder().withName("do homework").build();
            lecture = new TaskBuilder().withName("read weblecture").build();
            meeting = new TaskBuilder().withName("group meeting").build();
            jogging = new TaskBuilder().withName("jogging").build();
            george = new TaskBuilder().withName("visit George Best").build();

            //Manually added
            hoon = new TaskBuilder().withName("eat with Hoon Meier").build();
            ida = new TaskBuilder().withName("play with Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) throws TimeslotOverlapException {

        try {
            ab.addTask(new Task(trash));
            ab.addTask(new Task(book));
            ab.addTask(new Task(homework));
            ab.addTask(new Task(lecture));
            ab.addTask(new Task(meeting));
            ab.addTask(new Task(jogging));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{trash, book, homework, lecture, meeting, jogging, george};
    }
    
    public TaskDateComponent[] getTypicalTaskComponents() {
        List<TaskDateComponent> components = new ArrayList<TaskDateComponent>();
        TestTask[] tasks = getTypicalTasks();
        for(TestTask t : tasks) {
            components.addAll(t.getTaskDateComponent());
        }
        TaskDateComponent[] taskComponents = new TaskDateComponent[components.size()];
        return components.toArray(taskComponents);
    }

    public TaskList getTypicalTaskList() throws TimeslotOverlapException{
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}

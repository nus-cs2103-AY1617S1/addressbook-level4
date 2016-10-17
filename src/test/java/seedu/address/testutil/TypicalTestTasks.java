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
	
	/** Floating test tasks. */
    public static TestTask trash, book, homework, lecture, meeting, george, hoon, ida;
    
    /** Non-floating test tasks. */
    public static TestTask labDeadline, tutorialSlot, essayDeadline, concert, movie, project, paper;
    
    /** Blocked slots. */
    public static TestTask block1, block2, block3;

    public TypicalTestTasks() {
        try {
        	//Floating tasks
            trash =  new TaskBuilder().withName("take trash").withTags("notUrgent").build();
            book = new TaskBuilder().withName("read book").withTags("weekly", "textBook").build();
            homework = new TaskBuilder().withName("do homework").build();
            lecture = new TaskBuilder().withName("read weblecture").build();
            meeting = new TaskBuilder().withName("group meeting").build();
            george = new TaskBuilder().withName("visit George Best").build();
            //Non-floating tasks
            labDeadline = new TaskBuilder().withName("cs lab").withEndDate("18 oct 5pm").build();
            tutorialSlot = new TaskBuilder().withName("cs tutorial").withStartDate("17 oct 1pm").withEndDate("17 oct 2pm").build();
            essayDeadline = new TaskBuilder().withName("cs essay").withEndDate("19 oct 12am").withTags("urgent").build();
            concert = new TaskBuilder().withName("concert").withStartDate("17 oct 7pm").withEndDate("17 oct 9pm").build();
            //Manually added
            hoon = new TaskBuilder().withName("eat with Hoon Meier").build();
            ida = new TaskBuilder().withName("play with Ida Mueller").build();
            project = new TaskBuilder().withName("project discussion").withStartDate("19 oct 4pm").withEndDate("19 oct 5pm").build();
            paper = new TaskBuilder().withName("cs paper").withEndDate("18 oct 5pm").build();
            block1 = new TaskBuilder().withName("BLOCKED SLOT").withStartDate("20 oct 2pm").withEndDate("20 oct 3pm").build();
            
            //Exceptions
            //Add non-floating overlapped timeslot
            movie = new TaskBuilder().withName("movie").withStartDate("17 oct 8pm").withEndDate("17 oct 11pm").build();
            //Block overlapped timeslot
            block2 = new TaskBuilder().withName("BLOCKED SLOT").withStartDate("17 oct 8pm").withEndDate("17 oct 11pm").build();
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
            ab.addTask(new Task(george));
            ab.addTask(new Task(labDeadline));
            ab.addTask(new Task(tutorialSlot));
            ab.addTask(new Task(essayDeadline));
            ab.addTask(new Task(concert));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{trash, book, homework, lecture, meeting, george, labDeadline, tutorialSlot, essayDeadline,concert};
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

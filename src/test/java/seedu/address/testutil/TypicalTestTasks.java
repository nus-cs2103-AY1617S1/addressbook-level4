package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskMaster;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 *
 */
public class TypicalTestTasks {
	
	/** Floating test tasks. */
    public static TestTask trash, book, homework, lecture, meeting, george, hoon, ida;
    //@@author A0147967J
    /** Non-floating test tasks. */
    public static TestTask labDeadline, tutorialSlot, essayDeadline, concert, movie, project, paper, incoming;
    
    /** Blocked slots. */
    public static TestTask block1, block2, block3;

    /** Recurring Task. */
    public static TestTask daily, weekly, monthly, yearly, none, block;
    
    public TypicalTestTasks() {
        try {
        	//Floating tasks
            trash =  new TaskBuilder().withName("take trash").withTags("notUrgent").build();
            book = new TaskBuilder().withName("read book").withTags("CS2105", "textBook").build();
            homework = new TaskBuilder().withName("do homework").withTags("CS2105").build();
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
            block1 = new TaskBuilder().withName("BLOCKED SLOT").withStartDate("20 oct 2pm").withEndDate("20 oct 3pm").withTags("tag").build();
            incoming = new TaskBuilder().withName("incoming").withEndDate("tomorrow 5pm").build();
            //Exceptions
            //Add non-floating overlapped timeslot
            movie = new TaskBuilder().withName("movie").withStartDate("17 oct 8pm").withEndDate("17 oct 11pm").build();
            //Block overlapped timeslot
            block2 = new TaskBuilder().withName("BLOCKED SLOT").withStartDate("17 oct 8pm").withEndDate("17 oct 11pm").build(); 
            //Recurring Type
            daily = new TaskBuilder().withName("Daily Task").withStartDate("7am").withEndDate("8am")
            		.withRecurringType(RecurringType.DAILY).build();
            weekly = new TaskBuilder().withName("Weekly Task").withStartDate("6am").withEndDate("7am")
            		.withRecurringType(RecurringType.WEEKLY).build();
            monthly = new TaskBuilder().withName("Monthly Task").withStartDate("5am").withEndDate("6am")
            		.withRecurringType(RecurringType.MONTHLY).build();
            yearly = new TaskBuilder().withName("Yearly Task").withStartDate("3am").withEndDate("5am")
            		.withRecurringType(RecurringType.YEARLY).build();
            none = new TaskBuilder().withName("Normal Task").withStartDate("1am").withEndDate("3am")
            		.withRecurringType(RecurringType.NONE).build();
            block = new TaskBuilder().withName("BLOCKED SLOT").withStartDate("8am").withEndDate("9am")
            		.withRecurringType(RecurringType.NONE).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author
    public static void loadTaskListWithSampleData(TaskMaster ab) throws TimeslotOverlapException {

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
    //@@author A0147967J
    public TestTask[] getTypicalTasksWithRecurringOnes(){
    	return new TestTask[]{trash, tutorialSlot, essayDeadline,concert, yearly, monthly, weekly, daily, none};
    }
    //@@author
    public TaskOcurrence[] getTypicalTaskComponents() {
        List<TaskOcurrence> components = new ArrayList<TaskOcurrence>();
        TestTask[] tasks = getTypicalTasks();
        for(TestTask t : tasks) {
            components.addAll(t.getTaskDateComponent());
        }
        TaskOcurrence[] taskComponents = new TaskOcurrence[components.size()];
        return components.toArray(taskComponents);
    }

    public TaskMaster getTypicalTaskList() throws TimeslotOverlapException{
        TaskMaster ab = new TaskMaster();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}

package seedu.ggist.testutil;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask milk, assignment, movie, jog, holiday, dinner, dance;

    public TypicalTestTasks() {
        try {
            milk =  new TaskBuilder().withName("go buy milk")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            		.withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
            		.withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withTags("Misc").build();
            assignment = new TaskBuilder().withName("complete assignment")
            		.withStartDate("tomorrow")
            		.withStartTime("0900")
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
            		.withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withTags("CS2103","CS2101").build();
            movie = new TaskBuilder().withName("watch movie")
            		.withStartDate("20 Oct").withStartTime("2000")
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
            		.withEndTime(Messages.MESSAGE_NO_END_TIME_SET).build();
            jog = new TaskBuilder().withName("go for a jog")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            		.withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate("11 Nov")
            		.withEndTime("2000").build();
            holiday = new TaskBuilder()
            		.withName("plan holiday")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            		.withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate("Dec 12")
            		.withEndTime("2300").build();
            dinner = new TaskBuilder()
            		.withName("dinner appointment")
            		.withStartDate("14 Oct")
            		.withStartTime("2100")
            		.withEndDate("14 Oct")
            		.withEndTime("2200").build();
            
            //Manually added
            dance = new TaskBuilder().withName("dance practice").withStartDate("15 Oct").withStartTime("1830").withEndDate("15 Oct").withEndTime("2130").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(milk));
            ab.addTask(new Task(assignment));
            ab.addTask(new Task(movie));
            ab.addTask(new Task(jog));
            ab.addTask(new Task(holiday));
            ab.addTask(new Task(dinner));
          
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{milk, assignment, movie, jog, holiday, dinner};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

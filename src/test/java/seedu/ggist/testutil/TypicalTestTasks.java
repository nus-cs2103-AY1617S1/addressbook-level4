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
            milk =  new TaskBuilder().withName("buy milk").withTaskDate(Messages.MESSAGE_NO_DATE_SPECIFIED)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
                    .withTags("").build();
            assignment = new TaskBuilder().withName("complete assignment").withTaskDate("Wed, 12 OctT 2016")
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET).withEndTime("2359")
                    .withTags("").build();
            movie = new TaskBuilder().withName("watch movie").withTaskDate("Thu, 13 Oct 2016").withStartTime("2000").withEndTime("2200").build();
            jog = new TaskBuilder().withName("go for a jog").withTaskDate(Messages.MESSAGE_NO_DATE_SPECIFIED).withStartTime(Messages.MESSAGE_NO_START_TIME_SET).withEndTime(Messages.MESSAGE_NO_END_TIME_SET).build();
            holiday = new TaskBuilder().withName("plan holiday").withTaskDate(Messages.MESSAGE_NO_DATE_SPECIFIED).withStartTime(Messages.MESSAGE_NO_START_TIME_SET).withEndTime(Messages.MESSAGE_NO_END_TIME_SET).build();
            dinner = new TaskBuilder().withName("dinner appointment").withTaskDate("Fri, 14 Oct 2016").withStartTime("2100").withEndTime("2200").build();
            
            //Manually added
            dance = new TaskBuilder().withName("dance practice").withTaskDate("Tue, 15 Oct 2016").withStartTime("1830").withEndTime("2130").build();
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

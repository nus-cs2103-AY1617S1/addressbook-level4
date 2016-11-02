package seedu.ggist.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.task.*;

/**
 *
 */

//@@author A0147994J
public class TypicalTestTasks {
    
    public static TestTask floating, deadline, event, dance, soccer, lunch, report, reportWithNoTime, 
                            lunchWithNoDate, lunchWithNoTime, dinnerWithNoStartDate, dinnerWithNoEndDate;

    Date currentDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy");
    String startDateForTask = sdf.format(currentDate);
    //@@author A0138411N
    public TypicalTestTasks() {
        try {
            floating =  new TaskBuilder().withName("go buy milk")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            		.withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
            		.withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withPriority("low").build();
            deadline = new TaskBuilder().withName("complete assignment")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
            		.withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withPriority("low").build();
            event = new TaskBuilder().withName("go watch movie")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED).withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withPriority("med").build();
            
            //Manually added
            dance = new TaskBuilder()
                    .withName("go dance practice")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
                    .withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
                    .withPriority("high").build();
            soccer = new TaskBuilder()
                    .withName("soccer")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
                    .withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
                    .withPriority("high").build();
            lunch =  new TaskBuilder().withName("lunch with friends")
                    .withStartDate(startDateForTask)
                    .withStartTime("1pm")
                    .withEndDate(startDateForTask)
                    .withEndTime("2pm")
                    .withPriority("low").build();
            lunchWithNoDate =  new TaskBuilder().withName("lunch with friends with no date")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime("1pm")
                    .withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
                    .withEndTime("2pm")
                    .withPriority("med").build();
            lunchWithNoTime =  new TaskBuilder().withName("lunch with friends with no time")
                    .withStartDate(startDateForTask)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
                    .withEndDate(startDateForTask)
                    .withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
                    .withPriority("high").build();
            report = new TaskBuilder().withName("submit report")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
                    .withEndDate(startDateForTask)
                    .withEndTime("6pm")
                    .withPriority("med").build();
            reportWithNoTime = new TaskBuilder().withName("submit report with no time deadline")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
                    .withEndDate(startDateForTask)
                    .withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
                    .withPriority("med").build();
            dinnerWithNoStartDate = new TaskBuilder().withName("eat dinner with no start date")
                    .withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED)
                    .withStartTime("8pm")
                    .withEndDate(startDateForTask)
                    .withEndTime("9pm")
                    .withPriority("med").build();
            dinnerWithNoEndDate = new TaskBuilder().withName("eat dinner with no end date")
                    .withStartDate(startDateForTask)
                    .withStartTime("8pm")
                    .withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED)
                    .withEndTime("9pm")
                    .withPriority("med").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
  //@@author A0147994J
    public static void loadTaskManagerWithSampleData(TaskManager ab) throws IllegalValueException {

        try {
            //ab.addTask(new Task(floating));
            ab.addTask(new Task(deadline));
            ab.addTask(new Task(event));
            ab.addTask(new Task(dance));
            //ab.addTask(new Task(soccer));
          
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{deadline,dance,event};
    }

    public TaskManager getTypicalTaskManager() throws IllegalValueException{
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

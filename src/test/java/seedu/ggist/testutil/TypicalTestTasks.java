package seedu.ggist.testutil;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.task.*;

/**
 *
 */

//@@author A0147994J
public class TypicalTestTasks {

    public static TestTask floating, deadline, event, dance, soccer;

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
            		.withPriority("high").build();
            event = new TaskBuilder().withName("go watch movie")
            		.withStartDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED).withStartTime(Messages.MESSAGE_NO_START_TIME_SET)
            		.withEndDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED).withEndTime(Messages.MESSAGE_NO_END_TIME_SET)
            		.withPriority("med").build();
            
            //Manually added
            dance = new TaskBuilder()
                    .withName("dance")
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
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

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
        return new TestTask[]{deadline,event,dance};
    }

    public TaskManager getTypicalTaskManager() throws IllegalValueException{
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

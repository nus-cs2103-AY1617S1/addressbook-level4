package seedu.ggist.testutil;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask floating, deadline, event, dance;

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
            		.withEndDate("Thu, 21 Oct 16").withEndTime("23:59")
            		.withPriority("high").build();
            event = new TaskBuilder().withName("watch movie")
            		.withStartDate("Fri, 22 Oct 16").withStartTime("21:00")
            		.withEndDate("Fri, 22 Oct 16").withEndTime("23:00")
            		.withPriority("med").build();
            
            //Manually added
            dance = new TaskBuilder()
            		.withName("dance practice")
            		.withStartDate("Wed, 12 Oct 16")
            		.withStartTime("12:00")
            		.withEndDate("Wed, 12 Oct 16")
            		.withEndTime("13:00").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(floating));
            ab.addTask(new Task(deadline));
            ab.addTask(new Task(event));
            ab.addTask(new Task(dance));
          
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{floating,deadline,event,dance};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}

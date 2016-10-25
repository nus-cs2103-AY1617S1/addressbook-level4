package seedu.ggist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

/**
 * Adds a task to GGist.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task\n"
            + "Parameters: TASK, [DATE], [TIME]\n"
            + "Example: " + COMMAND_WORD
            + " examination period, friday 1pm to next friday 12pm";
    
    public static final String MESSAGE_SUCCESS = "New %1$s added: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "duplicated tasks found";
    
    private enum TaskType {
        FLOATING("task"), DEADLINE("deadline"), EVENT("event"); 
        
        private final String taskType;
        TaskType(String taskType) {
            this.taskType = taskType;
        }
        
        @Override
        public String toString() {
            return this.taskType;
        }
    }
    
    private final Task toAdd;
    private TaskType taskType;

    /**
     * Constructor for task with start and end times using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String startDate, String startTime, String endDate, String endTime, String priority) throws IllegalValueException {      

        if (startTime.equals("")) {
            startTime = Messages.MESSAGE_NO_START_TIME_SET;
        }
        
        if (endTime.equals("")) {
            endTime = Messages.MESSAGE_NO_END_TIME_SET;
        }
        
        this.toAdd = new EventTask(
                new TaskName(taskName),
                new TaskDate(startDate),
                new TaskTime(startTime),
                new TaskDate(endDate),
                new TaskTime(endTime),
                new Priority(priority)
        );
        taskType = TaskType.EVENT;
    }
    
    /**
     * Convenience constructor for task with deadline using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String date, String endTime, String priority) throws IllegalValueException {      

        if (endTime.equals("")) {
            endTime = Messages.MESSAGE_NO_END_TIME_SET;
        }
        
        this.toAdd = new DeadlineTask(
                new TaskName(taskName),
                new TaskDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED),
                new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                new TaskDate(date),
                new TaskTime(endTime),
                new Priority(priority)
        );
        taskType = TaskType.DEADLINE;
    }
    
    /**
     * Convenience constructor for task without deadlines using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String priority) throws IllegalValueException {      

        this.toAdd = new FloatingTask(
                new TaskName(taskName),
                new TaskDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED),
                new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                new TaskDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED),
                new TaskTime(Messages.MESSAGE_NO_END_TIME_SET),
                new Priority(priority)
        );
        taskType = TaskType.FLOATING;
    }
    
    public String getTaskType() {
        return taskType.toString();
    }

    @Override
    public CommandResult execute() {
       
        assert model != null;
        try {
            model.addTask(toAdd); 
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskType, toAdd.getTaskName()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    @Override
    public  String toString(){
        return COMMAND_WORD;
    }

}

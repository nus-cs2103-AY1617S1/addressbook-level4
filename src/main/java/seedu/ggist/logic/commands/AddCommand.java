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
            + " go run, 12 oct, 1200-1230";
    
    public static final String MESSAGE_SUCCESS = "New %1$s task added: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "duplicated tasks found";
    
    private enum TaskType {
        FLOATING("floating"), DEADLINE("deadline"), EVENT("event"); 
        
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
    final Set<Tag> tagSet = new HashSet<>();

    /**
     * Constructor for task with start and end times using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String date, String startTime, String endTime, Set<String> tags) throws IllegalValueException {      
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new EventTask(
                new TaskName(taskName),
                new TaskDate(date),
                new TaskTime(startTime),
                new TaskTime(endTime),
                new UniqueTagList(tagSet)
        );
        taskType = TaskType.EVENT;
    }
    
    /**
     * Convenience constructor for task with deadline using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String date, String endTime, Set<String> tags) throws IllegalValueException {      
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new DeadlineTask(
                new TaskName(taskName),
                new TaskDate(date),
                new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                new TaskTime(endTime),
                new UniqueTagList(tagSet)
        );
        taskType = TaskType.DEADLINE;
    }
    
    /**
     * Convenience constructor for task without deadlines using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, Set<String> tags) throws IllegalValueException {      
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new FloatingTask(
                new TaskName(taskName),
                new TaskDate(Messages.MESSAGE_NO_DATE_SPECIFIED),
                new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
                new TaskTime(Messages.MESSAGE_NO_END_TIME_SET),
                new UniqueTagList(tagSet)
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
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskType, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    @Override
    public  String toString(){
        return COMMAND_WORD;
    }

}

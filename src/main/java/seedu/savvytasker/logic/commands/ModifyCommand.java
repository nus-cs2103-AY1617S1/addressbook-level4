package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.commons.util.SmartDefaultDates;
import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Modifies a task in savvy tasker.
 */
public class ModifyCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "modify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Madifies a task in Savvy Tasker. "
            + "Parameters: INDEX [t/TASK_NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL]"
            + "[r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD
            + " 1 t/Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones";

    public static final String MESSAGE_SUCCESS = "Task modified: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private Task originalTask;
    private Task replacement;
    private final int index;
    private final String taskName;
    private final InferredDate startDateTime;
    private final InferredDate endDateTime;
    private final String location;
    private final PriorityLevel priority;
    private final RecurrenceType recurringType;
    private final Integer numberOfRecurrence;
    private final String category;
    private final String description;
    
    /**
     * Creates an add command.
     */
    public ModifyCommand(int index, String taskName, InferredDate startDateTime, InferredDate endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, String category, 
            String description) {
        this.index = index;
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.priority = priority;
        this.recurringType = recurringType;
        this.numberOfRecurrence = numberOfRecurrence;
        this.category = category;
        this.description = description;
        this.originalTask = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < index) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        

        SmartDefaultDates sdd = new SmartDefaultDates(null, null);

        ReadOnlyTask taskToModify = lastShownList.get(index - 1);
        replacement = new Task(taskToModify, taskName, sdd.getStart(startDateTime), 
                                    sdd.getEnd(endDateTime), location, priority, 
                                    recurringType, numberOfRecurrence, 
                                    category, description);

        try {
            originalTask = (Task)taskToModify;
            model.modifyTask(taskToModify, replacement);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (InvalidDateException ex) {
            return new CommandResult(Messages.MESSAGE_INVALID_START_END);
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, replacement));
    }
    
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }
    
    /**
     * Undo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {

        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ReadOnlyTask taskToModify = lastShownList.get(index - 1);

        try {
            model.modifyTask(taskToModify, originalTask);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (InvalidDateException ex) {
            //TODO: Verify branch
            //assert false : "The target task cannot be having an invalid start end ";
            //return new CommandResult(Messages.MESSAGE_INVALID_START_END);
        }
       
        return true;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
}

package seedu.taskscheduler.logic.commands;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.commons.util.CollectionUtil;
import seedu.taskscheduler.model.task.Location;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.ReadOnlyTask.TaskType;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.TaskDateTime;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E
/**
 * Adds a task to the Task Scheduler.
 */
public class EditCommand extends Command {


    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the scheduler. "
            + "Parameters: INDEX TASK from START_DATE to END_DATE at LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 Must Do CS2103 Pretut\n"
            + "Example: " + COMMAND_WORD
            + " 2 at NUS COM1-B103\n"
            + "Example: " + COMMAND_WORD
            + " 1 from 11-Oct-2016 8am to 11-Oct-2016 9am\n";

    public static final String MESSAGE_SUCCESS = "Task editted: %1$s";

    private final String args;
    private final int targetIndex;
    private Name name;
    private TaskDateTime startDate;
    private TaskDateTime endDate;
    private Location address;
    
    private Task oldTask;
    private Task newTask;
    /**
     * Convenience constructor using raw values.
     */
    public EditCommand(int targetIndex, String args) {
        this.targetIndex = targetIndex;
        this.args = args;
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            oldTask = (Task)getTaskFromIndexOrLastModified(targetIndex);
            extractParamsFromArgs();
            if (CollectionUtil.isAllNull(name, startDate, endDate, address)) {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            } 
            newTask = assignParamsToTask(oldTask);
            model.replaceTask(oldTask, newTask);
            CommandHistory.addExecutedCommand(this);
            CommandHistory.setModTask(newTask);
            
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException | IllegalValueException ex) {
            return new CommandResult(ex.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldTask));
    }

    @Override
    public CommandResult revert() {
        try {
            model.replaceTask(newTask, oldTask);
            CommandHistory.addRevertedCommand(this);
            CommandHistory.setModTask(oldTask);
        } catch (DuplicateTaskException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_DUPLICATED;
        } catch (TaskNotFoundException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + newTask));
    }

    private void extractParamsFromArgs() throws IllegalValueException {
        String args = this.args;
        args = extractAddressFromArgs(args);
        args = extractDatetimesFromArgs(args);
        extractNameFromArgs(args);
    }
    
    private String extractAddressFromArgs(String args) throws IllegalValueException {
        if (args.contains("at ")) {
            int index = args.lastIndexOf("at ");
            address = new Location(args.substring(index + 3).trim());
            return args.substring(0, index);
        }
        return args;
    }
    
    private String extractDatetimesFromArgs(String args) throws IllegalValueException {
        if (args.contains("from ") && args.contains("to ")) {
            int indexTo = args.lastIndexOf("to ");
            int indexFrom = args.lastIndexOf("from ");
            endDate = new TaskDateTime(args.substring(indexTo));
            startDate = new TaskDateTime(args.substring(indexFrom, indexTo));
            return args.substring(0, indexFrom);
        } else {
            return extractEndDateTimeFromArgs(args);
        }
    }
    
    private String extractEndDateTimeFromArgs(String args) throws IllegalValueException {
        if (args.contains("by ")) {
            int index = args.lastIndexOf("by ");
            endDate = new TaskDateTime(args.substring(index));
            return args.substring(0, index);
        }
        return args;
    }
    private void extractNameFromArgs(String args) throws IllegalValueException {
        if (!args.trim().isEmpty()) {
            name = new Name(args.trim());
        }
    }
    
    private Task assignParamsToTask(Task task) {
        Task newTask = new Task(task);
        if (task.getType() != TaskType.EVENT && (address != null || startDate != null))
            newTask.setType(TaskType.EVENT);
        else if (endDate != null && task.getType() == TaskType.FLOATING) {
            newTask.setType(TaskType.DEADLINE);
        }
        if (startDate != null) {
            newTask.setStartDate(startDate);
        }
        if (endDate != null) {
            newTask.setEndDate(endDate);
        }
        if (address != null) {
            newTask.setLocation(address);
        }
        if (name != null) {
            newTask.setName(name);
        }
        return newTask;
    }
}

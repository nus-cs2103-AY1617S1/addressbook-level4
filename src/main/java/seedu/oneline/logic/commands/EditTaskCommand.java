//@@author A0140156R

package seedu.oneline.logic.commands;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * Edits a task to the task book.
 */
public class EditTaskCommand extends EditCommand {

    public final int targetIndex;
    private final Map<TaskField, String> fields;
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = EditCommand.MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";


    public EditTaskCommand(int targetIndex, Map<TaskField, String> fields) throws IllegalValueException, IllegalCmdArgsException {
        this.targetIndex = targetIndex;
        this.fields = fields;
    }
    
    public static EditTaskCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        Entry<Integer, Map<TaskField, String>> info = Parser.getIndexAndTaskFieldsFromArgs(args);
        int targetIndex = info.getKey();
        Map<TaskField, String> fields = info.getValue();
        return new EditTaskCommand(targetIndex, fields);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask oldTask = lastShownList.get(targetIndex - 1);
        Task newTask = null;
        try {
            newTask = oldTask.update(fields);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
        try {
            model.replaceTask(oldTask, newTask);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
        } catch (UniqueTaskList.TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "The update task should not already exist";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTask.toString()));
    }
    
//    private static String checkTimeFields(Task oldTask, Map<TaskField, String> newFields) throws IllegalCmdArgsException {
//        boolean haveStart = newFields.containsKey(TaskField.START_TIME);
//        boolean haveEnd = newFields.containsKey(TaskField.END_TIME);
//        boolean haveDeadline = newFields.containsKey(TaskField.DEADLINE);
//        if (haveDeadline && (haveStart || haveEnd)) {
//            throw new IllegalCmdArgsException("A deadline cannot appear with a start or end date.");
//        }
//        int taskType = -1;
//        if (oldTask.isFloating()) {
//            taskType = 0;
//        } else if (oldTask.isEvent()) {
//            taskType = 1;
//        } else if (oldTask.hasDeadline()) {
//            taskType = 2;
//        }
//        if (taskType == 0) {
//            if (haveDeadline) {
//                // continue
//                return "Floating task changed to a deadline task.";
//            } else if (haveStart && haveEnd) {
//                 // continue
//                return "Floating task changed to an event.";
//            } else if (!haveDeadline && !haveStart && !haveEnd) {
//                // floating task update
//                return null;
//            } else {
//                throw new IllegalCmdArgsException("Both start and end times need to be stated.");
//            }
//        } else if (taskType == 1) {
//            if ()
//        } else if (taskType == 2) {
//            if (haveDeadline) {
//                // deadline update
//                return null;
//            } else if (haveStart && haveEnd) {
//                 // continue
//                return "Floating task changed to an event.";
//            } else {
//                throw new IllegalCmdArgsException("Both start and end times need to be stated.");
//            }
//        }
//        return null;
//    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}

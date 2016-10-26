package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/** 
 * Edits a task identified using its last displayed index in the task manager.
 * @author Ronald
 * @author A0139430L JingRui
 */


public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String DESCRIPTION_WORD = "des";
    public static final String DATE_WORD = "date";
    public static final String START_WORD = "start";
    public static final String END_WORD = "end";
    public static final String TAG_WORD = "tag";
    public static final String ADD_WORD = "add";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task in Simply. "
            +"Parameters: INDEX <section to delete> <edited information>\n" 
            +"Example: " + COMMAND_WORD + " 1 " + DESCRIPTION_WORD + " beach party\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + DATE_WORD + " 120516\n"
            +"Example: " + COMMAND_WORD + " 1 " + START_WORD + " 1600\t\t\t"
            +"Example: " + COMMAND_WORD + " 1 " + END_WORD + " 2300\n"
    		+"Example: " + COMMAND_WORD + " 1 " + TAG_WORD + " sentosa";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s%2$s       Changes:  %3$s";
    
    public final Integer targetIndex;
    public final String editArgs;
    public final char category;

    public EditCommand(Integer index, String args, char category) {
        // TODO Auto-generated constructor stub
        this.targetIndex = index;
        this.editArgs = args;
        this.category = category;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        if(category == 'E'){
            if (lastShownEventList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask eventToEdit = lastShownEventList.get(targetIndex - 1);
            try {
                model.addToUndoStack();
                model.editTask(eventToEdit, editArgs, category);
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }

            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        else if(category == 'D'){
            if (lastShownDeadlineList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask deadlineToEdit = lastShownDeadlineList.get(targetIndex - 1);

            try {
                model.addToUndoStack();
                model.editTask(deadlineToEdit, editArgs, category);
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }

            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        else if(category == 'T'){
            if (lastShownTodoList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask todoToEdit = lastShownTodoList.get(targetIndex - 1);

            try {
                model.addToUndoStack();
                model.editTask(todoToEdit, editArgs, category);
            } catch (TaskNotFoundException ive){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            catch (IllegalValueException ive) {
                indicateAttemptToExecuteIncorrectCommand();
                Command command = new IncorrectCommand(ive.getMessage());
                return command.execute();
            }
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, category, targetIndex, editArgs));
        }
        return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}

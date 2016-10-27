package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.event.EndTime;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.StartTime;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author A0125680H
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the indexed task from Lifekeeper. \n"
            + "Parameters: INDEX (must be a positive integer) [n/TASK_NAME] [c/CATEGORY] [d/DEADLINE] p/PRIORITY_LEVEL r/REMINDER [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 n/CS2103 T8A2 d/15-10-2016 p/3 r/12-01-2016 t/CS t/project";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task from: %1$s\nto: %2$s";
    public static final String MESSAGE_TASK_EXISTS = "An existing task already contains the specified parameters.";
    public static final String MESSAGE_ACTIVITY_MISMATCH = "Task cannot be changed to event and vice versa.";

    public final int targetIndex;
    
    private final String newParamsType;
    
    public final Activity newParams;

    /**
     * Set parameters to null if they are not provided.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String name, String duedate, String priority, String start, String end, String reminder, Set<String> tags)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        newParamsType = identifyActivityType(duedate, priority, start, end);
        
        if (newParamsType.equalsIgnoreCase("float")) {
            this.newParams = new Activity(
                    new Name(name),
                    new Reminder(reminder),
                    new UniqueTagList(tagSet)
            );
        } else if (newParamsType.equalsIgnoreCase("task")) {
            this.newParams = new Task(
                    new Name(name),
                    new DueDate(duedate),
                    new Priority(priority),
                    new Reminder(reminder),
                    new UniqueTagList(tagSet)
            );
        } else if (newParamsType.equalsIgnoreCase("event")) {
            this.newParams = new Event(
                    new Name(name),
                    new StartTime(start),
                    new EndTime(start, end),
                    new Reminder(reminder),
                    new UniqueTagList(tagSet)
            );
        } else {
            assert false : "Invalid method output: identifyActivityType";
            throw new IllegalValueException(MESSAGE_INVALID_ACTIVITY_TYPE);
        }
        
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<Activity> lastShownList = model.getFilteredTaskListForEditing();

        Activity taskToEdit = lastShownList.get(targetIndex - 1);
        String taskToEditType = taskToEdit.getClass().getSimpleName();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        if (taskToEditType.equalsIgnoreCase("task") && newParamsType.equalsIgnoreCase("event")
                || taskToEditType.equalsIgnoreCase("event") && newParamsType.equalsIgnoreCase("task")) {
            return new CommandResult(MESSAGE_ACTIVITY_MISMATCH);
        }

        try {
            Activity oldTask = new Activity(taskToEdit);
            Activity editedTask = model.editTask(taskToEdit, newParams);

            PreviousCommand editCommand = new PreviousCommand(COMMAND_WORD, oldTask, editedTask);
            PreviousCommandsStack.push(editCommand);

            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, oldTask, editedTask));
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task to be edited cannot be missing";
            return new CommandResult("");
        } catch (DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_TASK_EXISTS);
        }
    }

}

package seedu.menion.logic.commands;

import java.util.Arrays;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139164A
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edit an activity using their type, index, [Parameters to change] and new changes: " + "\n"
            + "Parameters: [Activity_Type] + [Activity_Index] [ 'name', 'note', 'by (Date & Time)', 'from (Date & Time) - to (Date & Time)' ], + [Changes]\n"
            + "Example: " + COMMAND_WORD + " task 1 by 10-10-2016 1900 \n" + "Example: " + COMMAND_WORD
            + " task 1 note write in red ink \n" + "Example: " + COMMAND_WORD + " event 1 name ORD";

    public static final String MESSAGE_EDITTED_ACTIVITY_SUCCESS = "Menion editted your Activity to: %1$s";
    public static final String NAME_PARAM = "name";
    public static final String NOTE_PARAM = "note";
    public static final String TASK_DEADLINE_PARAM = "by";
    public static final String EVENT_FROM_PARAM = "from";
    public static final String EVENT_TO_PARAM = "to";
    public static final String NOT_TO_EDIT = "-";
    public static final String SEPARATOR = "/ ";
    
    public static final String MESSAGE_INVALID_PARAMETER = "Menion detected an invalid parameter for the current type! \n" +
            "Please make sure it is, for: \n" + 
            Activity.FLOATING_TASK_TYPE + ": "  + NAME_PARAM + SEPARATOR + NOTE_PARAM + "\n" +
            Activity.TASK_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + TASK_DEADLINE_PARAM + "\n" +
            Activity.EVENT_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + EVENT_FROM_PARAM + SEPARATOR + EVENT_TO_PARAM;
    
            
    
    public final int targetIndex;
    public final String targetType;
    public final String[] changes;
    public final String paramToChange;
    ReadOnlyActivity activityToEdit;

    public EditCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]) - 1;
        this.paramToChange = splited[3];
        this.changes = Arrays.copyOfRange(splited, 4, splited.length);
    }

    @Override
    public CommandResult execute() {
    	assert model != null;
    	
    	storePreviousState();
    	
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        
        try {
            if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
                lastShownList = model.getFilteredFloatingTaskList();
                floatingTaskEdit(this.targetIndex, this.paramToChange, this.changes);
            } else if (targetType.equals(Activity.TASK_TYPE)) {
                lastShownList = model.getFilteredTaskList();
                taskEdit(this.targetIndex, this.paramToChange, this.changes);
            } else {
                lastShownList = model.getFilteredEventList();
                eventEdit(this.targetIndex, this.paramToChange, this.changes);
            }
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
        
        // Validates valid index is an index of an activity in the correct list
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyActivity activityToEdit = lastShownList.get(targetIndex);
        return new CommandResult(String.format(MESSAGE_EDITTED_ACTIVITY_SUCCESS, activityToEdit));
    }

    private void floatingTaskEdit(int index, String paramToChange, String[] changes) throws IllegalValueException {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {

        case 0:
            String newName = arrayToString(changes);
            model.editFloatingTaskName(index, newName);
            break;
        case 1:
            String newNote = arrayToString(changes);
            model.editFloatingTaskNote(index, newNote);
            break;
        }

    }

    private void taskEdit(int index, String paramToChange, String[] changes) throws IllegalValueException {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);

        switch (indexOfParam) {

        case 0:
            String newName = arrayToString(changes);
            model.editTaskName(index, newName);
            break;
        case 1:
            String newNote = arrayToString(changes);
            model.editTaskNote(index, newNote);
            break;
        case 2:
            String newDate = NOT_TO_EDIT;
            String newTime = NOT_TO_EDIT;
            // User passed in both date and time
            if (changes.length == 2) {
                newDate = changes[0];
                newTime = changes[1];
            }
            // Either date, or time passed in
            else {
                // Must be time, does not contain "-"
                if (!changes[0].contains("-")) {
                    newTime = changes[0];
                }
                // Must be date.
                else {
                    newDate = changes[0];
                }
            }
            model.editTaskDateTime(index, newDate, newTime);
            break;
        }
    }

    private void eventEdit(int index, String paramToChange, String[] changes) throws IllegalValueException {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {

        case 0:
            String newName = arrayToString(changes);
            model.editEventName(index, newName);
            break;
        case 1:
            String newNote = arrayToString(changes);
            model.editEventNote(index, newNote);
            break;
        case 3: // Only change the start Date & Time. We can call the same method as task.
            String newDate = NOT_TO_EDIT;
            String newTime = NOT_TO_EDIT;
            // User passed in both date and time
            if (changes.length == 2) {
                newDate = changes[0];
                newTime = changes[1];
            }
            // Either date, or time passed in
            else {
                // Must be time, does not contain "-"
                if (!changes[0].contains("-")) {
                    newTime = changes[0];
                }
                // Must be date.
                else {
                    newDate = changes[0];
                }
            }
            model.editEventStartDateTime(index, newDate, newTime);
            break;
        case 4:
            String newEndDate = NOT_TO_EDIT;
            String newEndTime = NOT_TO_EDIT;
            // User passed in both date and time
            if (changes.length == 2) {
                newEndDate = changes[0];
                newEndTime = changes[1];
            }
            // Either date, or time passed in
            else {
                // Must be time, does not contain "-"
                if (!changes[0].contains("-")) {
                    newEndTime = changes[0];
                }
                // Must be date.
                else {
                    newEndDate = changes[0];
                }
            }
            model.editEventEndDateTime(index, newEndDate, newEndTime);
            break;
        }
    }

    /**
     * 
     * @param paramToChange
     * @return an integer to match with the param to change, refer below for index:
     *         0 = name (For all) 
     *         1 = note (For all) 
     *         2 = by (For Tasks only) 
     *         3 = from (For Event's Start Date & Time)
     *         4 = to (For Event's End Date & Time)
     */
    private int checkParam(String paramToChange) throws IllegalValueException {

        if (paramToChange.equals(NAME_PARAM)) {
            return 0;
        } else if (paramToChange.equals(NOTE_PARAM)) {
            return 1;
        } else if (paramToChange.equals(TASK_DEADLINE_PARAM) && this.targetType.equals(Activity.TASK_TYPE)) {
            return 2;
        } else if (paramToChange.equals(EVENT_FROM_PARAM) && this.targetType.equals(Activity.EVENT_TYPE)) {
            return 3;
        } else if (paramToChange.equals(EVENT_TO_PARAM) && this.targetType.equals(Activity.EVENT_TYPE) ) {
            return 4;
        }

        throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
    }

    private String arrayToString(String[] changes) {
        StringBuilder sb = new StringBuilder();

        if (changes.length == 1) {
            return changes[0];
        }

        for (int i = 0; i < changes.length; i++) {
            sb.append(changes[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    //@@author A0139515A
    /**
     * Edit command will store previous activity manager to support undo command
     * 
     */
    public void storePreviousState() {
        assert model != null;

        ReadOnlyActivityManager beforeState = new ActivityManager(model.getActivityManager());
    	model.addStateToUndoStack(beforeState);
    }
}

package seedu.menion.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.parser.NattyDateParser;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

//@@author A0139164A
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDITTED_ACTIVITY_SUCCESS = "Menion editted your Activity to: %1$s";
    public static final String NAME_PARAM = "name:";
    public static final String NOTE_PARAM = "n:";
    public static final String TASK_DEADLINE_PARAM = "by:";
    public static final String EVENT_FROM_PARAM = "from:";
    public static final String EVENT_TO_PARAM = "to:";
    public static final String SEPARATOR = "/ ";
    public static final String MESSAGE_INVALID_PARAMETER = "Menion detected an invalid parameter for the current type! \n" +
            "Please make sure it is, for: \n" + 
            Activity.FLOATING_TASK_TYPE + ": "  + NAME_PARAM + SEPARATOR + NOTE_PARAM + "\n" +
            Activity.TASK_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + TASK_DEADLINE_PARAM + "\n" +
            Activity.EVENT_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + EVENT_FROM_PARAM + SEPARATOR + EVENT_TO_PARAM;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edit an activity using their type, index, [Parameters to change] and new changes: " + "\n"
            + "Parameters: [Activity_Type] + [Activity_Index] [ '"+NAME_PARAM+"', '"+NOTE_PARAM+"', '"+TASK_DEADLINE_PARAM+" (Date & Time)', '"+EVENT_FROM_PARAM+" (Date & Time)' '"+EVENT_TO_PARAM+" (Date & Time)' ] + [Changes]\n"
            + "Example: " + COMMAND_WORD + " task 1 by 10-10-2016 1900 \n" + "Example: " + COMMAND_WORD
            + " task 1 note write in red ink \n" + "Example: " + COMMAND_WORD + " event 1 name ORD";

            
    
    public final int targetIndex;
    public final String targetType;
    public final String changes;
    public final String paramToChange;
    private ArrayList<String> fromNatty = new ArrayList<String>();
    ReadOnlyActivity activityToEdit;

    public EditCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]) - 1;
        this.paramToChange = splited[3];
        this.changes = arrayToString(Arrays.copyOfRange(splited, 4, splited.length));
    }

    @Override
    public CommandResult execute() {
    	assert model != null;
    	
    	storePreviousState();
    	
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        
        try {
            if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
                lastShownList = model.getFilteredFloatingTaskList();
                activityToEdit = lastShownList.get(targetIndex);
                floatingTaskEdit(activityToEdit, this.paramToChange);
            } else if (targetType.equals(Activity.TASK_TYPE)) {
                lastShownList = model.getFilteredTaskList();
                activityToEdit = lastShownList.get(targetIndex);
                taskEdit(activityToEdit, this.paramToChange);
            } else {
                lastShownList = model.getFilteredEventList();
                activityToEdit = lastShownList.get(targetIndex);
                eventEdit(activityToEdit, this.paramToChange);
            }
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        } catch (ActivityNotFoundException pnfe) {
            return new CommandResult("The target activity cannot be missing");
        }
        
        // Validates valid index is an index of an activity in the correct list
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        model.updateRecentChangedActivity(activityToEdit);
        
        return new CommandResult(String.format(MESSAGE_EDITTED_ACTIVITY_SUCCESS, activityToEdit));
    }

    private void floatingTaskEdit(ReadOnlyActivity floatingTaskToEdit, String paramToChange) throws IllegalValueException, ActivityNotFoundException{
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {

        case 0:
            String newName = this.changes;
            model.editFloatingTaskName(floatingTaskToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editFloatingTaskNote(floatingTaskToEdit, newNote);
            break;
        }

    }

    private void taskEdit(ReadOnlyActivity taskToEdit, String paramToChange) throws IllegalValueException, ActivityNotFoundException {

        int indexOfParam;
        indexOfParam = checkParam(paramToChange);

        switch (indexOfParam) {

        case 0:
            String newName = this.changes;
            model.editTaskName(taskToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editTaskNote(taskToEdit, newNote);
            break;
        case 2:
            NattyDateParser.parseDate(this.changes, fromNatty);
            model.editTaskDateTime(taskToEdit, fromNatty.get(0), fromNatty.get(1));
            break;
        }
    }

    private void eventEdit(ReadOnlyActivity eventToEdit, String paramToChange) throws IllegalValueException , ActivityNotFoundException{
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {

        case 0:
            String newName = this.changes;
            model.editEventName(eventToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editEventNote(eventToEdit, newNote);
            break;
        case 3: 
            NattyDateParser.parseDate(this.changes, fromNatty);
            model.editEventStartDateTime(eventToEdit, fromNatty.get(0), fromNatty.get(1));
            break;
        case 4:
            NattyDateParser.parseDate(this.changes, fromNatty);
            model.editEventEndDateTime(eventToEdit, fromNatty.get(0), fromNatty.get(1));
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

    private String arrayToString(String[] from) {
        StringBuilder build = new StringBuilder();
        
        for (int i = 0; i < from.length; i++) {
            build.append(from[i]);
            build.append(" ");
        }
        return build.toString();
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

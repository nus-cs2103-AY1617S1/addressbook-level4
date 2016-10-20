package seedu.menion.logic.commands;

import java.util.Arrays;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;

/**
 * 
 * @author Marx A0139164A
 * Edits the variables of an activity given the index and it's activtyType, and the changes.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit an activity using their type, index, [Parameters to change] and new changes: "
            + "\n" + "Parameters: [Activity_Type] + [Activity_Index] [ 'name', 'note', 'by (Date & Time)', 'from (Date & Time) - to (Date & Time)' ], + [Changes]\n" 
            + "Example: " + COMMAND_WORD + " task 1 by 10-10-2016 1900 \n"
            + "Example: " + COMMAND_WORD + " task 1 note write in red ink \n"
            + "Example: " + COMMAND_WORD + " event 1 name ORD";

    public static final String MESSAGE_EDITTED_ACTIVITY_SUCCESS = "Edited Activity to: %1$s";
    public static final String NAME_PARAM = "name";
    public static final String NOTE_PARAM = "note";
    public static final String TASK_DEADLINE_PARAM = "by"; 
    
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
        
        for (int i = 0; i < splited.length; i++) {
            System.out.println("This is i : " + i + ". And this is inside: " + splited[i]);
        }
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;

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

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        
        ReadOnlyActivity activityToEdit= lastShownList.get(targetIndex);
        return new CommandResult(String.format(MESSAGE_EDITTED_ACTIVITY_SUCCESS, activityToEdit));
    }
    
    private void floatingTaskEdit(int index, String paramToChange, String[] changes) {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {
        
        case 0:
            String newName = changes[0];
            model.editFloatingTaskName(index, newName);
            break;
        case 1:
            String newNote = changes[0];
            model.editFloatingTaskNote(index, newNote);
            break;
        }
        
    }
    private void taskEdit(int index, String paramToChange, String[] changes) {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        System.out.println("This is indexOfParam:  " + indexOfParam);
        
        switch (indexOfParam) {
        
        case 0:
            String newName = changes[0];
            model.editTaskName(index, newName);
            break;
        case 1:
            String newNote = changes[0];
            model.editTaskNote(index, newNote);
            break;
        case 2:
            String newDate = changes[0];
            String newTime = changes[1];
            model.editTaskDateTime(index, newDate, newTime);
            break;
        }
    }
    private void eventEdit(int index, String paramToChange, String[] changes) {
        int indexOfParam;
        indexOfParam = checkParam(paramToChange);
        switch (indexOfParam) {
        
        case 0:
            String newName = changes[0];
            model.editEventName(index, newName);
            break;
        case 1:
            String newNote = changes[0];
            model.editEventNote(index, newNote);
            break;
        case 2:
            String newDate = changes[0];
            String newTime = changes[1];
            model.editEventDateTime(newDate, newTime);
            break;
        }
    }
    /**
     * 
     * @param paramToChange
     * @return an integer to match with the param to change, refer below for index
     * 0 = name (For all)
     * 1 = note (For all)
     * 2 = by (For Tasks only)
     * 3 = from: to: (For Events only)
     */
    private int checkParam(String paramToChange) {

        if (paramToChange.equals(NAME_PARAM)) {
            return 0;
        }
        else if (paramToChange.equals(NOTE_PARAM)) {
            return 1;
        }
        else if (paramToChange.equals(TASK_DEADLINE_PARAM)) {
            return 2;
        }
        
        return 100;
    }
    /*
     * Complete command supports undo
     */
    @Override
    public boolean undo() {
        assert model != null;
        return true;
    }

}

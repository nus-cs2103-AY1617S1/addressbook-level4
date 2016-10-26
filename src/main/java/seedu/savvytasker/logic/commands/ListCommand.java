package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.task.ListType;

/**
 * Lists all tasks in the savvy tasker to the user.
 */
public class ListCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the tasks in the task list.\n"
            + "Parameters: [t/LIST_TYPE]\n"
            + "Example: " + COMMAND_WORD + " t/Archived";
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private final ListType listType;

    //@@author A0139915W
    /**
     * Creates the List command to list the specified tasks
     * @author A0139915W
     * @param commandModel Arguments for the List command, must not be null
     */
    public ListCommand(ListType listType) {
        this.listType = listType;
    }

    @Override
    public CommandResult execute() {
        ListType _listType = listType;
        if (listType == null) {
            // use default, sort by due date
            _listType = ListType.DueDate;
        }
        switch (_listType)
        {
        case DueDate:
            model.updateFilteredListToShowActiveSortedByDueDate();
            break;
        case PriorityLevel:
            model.updateFilteredListToShowActiveSortedByPriorityLevel();
            break;
        case Archived:
            model.updateFilteredListToShowArchived();
            break;
        default:
            assert false; // should not reach here
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
    //@@author
    
    //@@author A0097627N
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the list command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return false;
    }

    
    /**
     * Undo the list command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return false;
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
    //@@author
}


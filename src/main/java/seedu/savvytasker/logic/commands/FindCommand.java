package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.task.FindType;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 * @author A0139915W
 */
public class FindCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_FORMAT = "find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    
    private final FindType findType;
    private final String[] keywords;

    //@@author A0139915W
    public FindCommand(FindType findType, String[] keywords) {
        this.findType = findType;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        FindType _findType = findType;
        if (_findType == null) {
            // use default find type, partial.
            _findType = FindType.Partial;
        }
        model.updateFilteredTaskList(_findType, keywords);
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
     * Redo the find command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return false;
    }

    /**
     * Undo the find command
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

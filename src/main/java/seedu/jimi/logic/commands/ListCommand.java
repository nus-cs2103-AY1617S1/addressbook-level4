package seedu.jimi.logic.commands;


/**
 * Lists all tasks in Jimi to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateAllFilteredListsToNormalListing();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    // @@author A0140133B
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    // @@author
}

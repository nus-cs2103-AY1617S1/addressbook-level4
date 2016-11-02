package seedu.whatnow.logic.commands;

import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

public class PinCommand extends Command {
    
    public static final String COMMAND_WORD = "pin";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins task to Pinned Items based on pin criteria\n"
            + "Parameters: tag/type/date and keyword\n" 
            + "Example: " + COMMAND_WORD + " tag highPriority";
    
    public static final String MESSAGE_SUCCESS = "Pinned Item view updated!";
    
    private final String type;
    private final String keyword;
    
    public PinCommand(String type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() throws DuplicateTaskException, TaskNotFoundException {
        model.updatePinnedItemsToShowMatchKeywords(type, keyword);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}

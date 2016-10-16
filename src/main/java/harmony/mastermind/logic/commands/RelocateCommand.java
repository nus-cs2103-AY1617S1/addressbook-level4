package harmony.mastermind.logic.commands;

import java.util.HashSet;
import java.util.Set;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * Adds a task to the task manager.
 */
public class RelocateCommand extends Command{

    public static final String COMMAND_WORD = "relocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes save location in MasterMind. "
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD
            + "Desktop";

    public static final String MESSAGE_SUCCESS = "Relocated save location to %1$s";
    public static final String MESSAGE_INVALID_INPUT = "This file path is not valid.";

    private final String newFilePath;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RelocateCommand(String newFilePath) throws IllegalValueException {
        this.newFilePath = newFilePath;
        
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.relocateSaveLocation(newFilePath);
            
        return new CommandResult(String.format(MESSAGE_SUCCESS, newFilePath));
    }
    
}

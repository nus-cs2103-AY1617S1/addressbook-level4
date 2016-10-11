package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Alias;

/**
 * Adds a task to TaskManager.
 */
public class AddAliasCommand extends TaskCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a one-word alias for any sentence to be used as a command. "
            + "Parameters: DESCRIPTION \n"
            + "Example: " + COMMAND_WORD
            + " am add Meeting";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This alias already exists in TaskManager";
    public static final String MESSAGE_EMPTY_TASK = "There is no alias";

    private final Alias toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddAliasCommand(String shortcut, String sentence)
            throws IllegalValueException {
    	if (shortcut == null || shortcut.isEmpty()) {
    		throw new IllegalValueException("Shortcut to AliasCommand constructor is empty.");
    	}
    	if (sentence == null || sentence.isEmpty()) {
    		throw new IllegalValueException("Sentence to AliasCommand constructor is empty.");
    	}
        this.toAdd = new Alias(shortcut, sentence);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addAlias(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}

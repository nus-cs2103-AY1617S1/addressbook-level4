package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.models.AddCommandModel;
import seedu.address.logic.commands.models.AliasCommandModel;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Command to create aliases
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an alias for a command or parameter. "
            + "Parameters: s/SHORT_KEYWORD\n"
            + "Example: " + COMMAND_WORD
            + " s/pjm";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNREGOGNIZED_ALIAS = "This alias is not in use";

    private final Person toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UnaliasCommand(AliasCommandModel commandModel) {
        this.toAdd = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_UNREGOGNIZED_ALIAS);
        }

    }

    @Override
    protected boolean redo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }

}

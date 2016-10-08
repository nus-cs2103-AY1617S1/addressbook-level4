package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.models.AddCommandModel;
import seedu.address.logic.commands.models.AliasCommandModel;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Command to create aliases
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for a command or parameter. "
            + "Parameters: k/KEYWORD s/SHORT_KEYWORD\n"
            + "Example: " + COMMAND_WORD
            + " k/Project Meeting s/pjm";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias is already in use";

    private final Person toAdd;

    /**
     * Creates an alias command
     */
    public AliasCommand(AliasCommandModel commandModel) {
        // create new alias mapping
        toAdd = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
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

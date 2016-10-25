package seedu.oneline.logic.commands;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * Edits a task to the task book.
 */
public abstract class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task to the task book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

    public static EditCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        if (args.startsWith("#")) {
            return EditTagCommand.createFromArgs(args);
        } else {
            return EditTaskCommand.createFromArgs(args);
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}

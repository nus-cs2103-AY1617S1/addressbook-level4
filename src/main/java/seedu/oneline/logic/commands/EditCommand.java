//@@author A0140156R

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task or category in the task book. \n"
            + " === Edit Task === \n"
            + "Parameters: INDEX (must be a positive integer), [taskName] [.from <start> .to <end>] [.due <deadline>] [.every <period>] [#<cat>] \n"
            + "Example: " + COMMAND_WORD
            + " 1 Acad meeting .from 2pm .to 4pm #acad"
            + " === Edit Category === \n"
            + "Parameters: #cat [#newCatName] \n"
            + "Example: " + COMMAND_WORD
            + " #acad #meeting";
    
    public static EditCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        args = args.trim();
        if (args.startsWith(CommandConstants.TAG_PREFIX)) {
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

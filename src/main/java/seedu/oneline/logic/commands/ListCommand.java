package seedu.oneline.logic.commands;

import java.util.Iterator;
import java.util.Set;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;

/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_INVALID = "Argument given is invalid." +
                                                "Supported formats: list done, today, week, float";

    public String listBy;

    public ListCommand() {
        this.listBy = " ";
    }

    public ListCommand(String args) throws IllegalCmdArgsException {
        args = args.trim().toLowerCase();
        if(args.isEmpty()){
            this.listBy = " ";
        } else {
            Set<String> keywords = Parser.getKeywordsFromArgs(args);
            if (keywords == null) {
                throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_INVALID));
            }
            Iterator<String> iter = keywords.iterator();
            this.listBy = iter.next();
        }
    }

    @Override
    public CommandResult execute() {
        switch (listBy) {
        case " ":
            model.updateFilteredListToShowAllNotDone();
            break;
        case "done":
            model.updateFilteredListToShowAllDone();
            break;
        case "today":
            model.updateFilteredListToShowToday();
            break;
        case "week":
            model.updateFilteredListToShowWeek();
            break;
        case "float":
            model.updateFilteredListToShowFloat();
            break;
        default:
            return new CommandResult(MESSAGE_INVALID);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
    @Override
    public boolean canUndo() {
        return true;
    }
}

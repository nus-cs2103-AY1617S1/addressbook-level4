//@@author A0121657H
package seedu.oneline.logic.commands;

import java.util.Iterator;
import java.util.Set;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.events.ui.ShowHelpRequestEvent;
import seedu.oneline.commons.events.ui.ShowAllViewEvent; 
import seedu.oneline.commons.events.ui.ShowDayViewEvent; 
import seedu.oneline.commons.events.ui.ShowWeekViewEvent; 
import seedu.oneline.commons.events.ui.ShowFloatViewEvent; 
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;

/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_INVALID = "Argument given is invalid. \n" +
                                                "Supported formats: list [done/today/week/float/#<Category>]";

    public String listBy;

    public ListCommand() {
        this.listBy = " ";
    }

    public ListCommand(String listBy) throws IllegalCmdArgsException {
        this.listBy = listBy;
    }

    public static ListCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        String listBy = " ";
        args = args.trim();
        if(!args.isEmpty()){
            if (args.startsWith(CommandConstants.TAG_PREFIX)){
                return ListTagCommand.createFromArgs(args);
            } else {
                args.toLowerCase();
                Set<String> keywords = Parser.getKeywordsFromArgs(args);
                if (keywords == null) {
                    throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_INVALID));
                }
                Iterator<String> iter = keywords.iterator();
                listBy = iter.next();
            }
        }
        return new ListCommand(listBy);
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAllNotDone();
        switch (listBy) {
        case " ":
            EventsCenter.getInstance().post(new ShowAllViewEvent());
            model.updateFilteredListToShowAllNotDone();
            break;
        case "done":
            EventsCenter.getInstance().post(new ShowAllViewEvent());
            model.updateFilteredListToShowAllDone();
            break;
        case "undone":
            model.updateFilteredListToShowAllNotDone();
            break;
        case "today":
            EventsCenter.getInstance().post(new ShowDayViewEvent());
            model.updateFilteredListToShowToday();
            break;
        case "week":
            EventsCenter.getInstance().post(new ShowWeekViewEvent());
            model.updateFilteredListToShowWeek();
            break;
        case "float":
            EventsCenter.getInstance().post(new ShowFloatViewEvent());
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

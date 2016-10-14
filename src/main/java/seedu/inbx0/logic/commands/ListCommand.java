package seedu.inbx0.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;

/**
 * Lists all tasks in the tasks to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks associated with the date and "
            + "displays them as a list with index numbers.\n"
            + "Without any parameters, it will display all tasks.\n"
            + "Parameters: [DATE]\n"
            + "Example: " + COMMAND_WORD + " today";
    
    private final String checkDate;
    private final String preposition;
    private static final Pattern TASKS_DUE_UNTIL_DATE_LIST_FORMAT = Pattern.compile(" due (?<date>[^$]+)");
    
    public ListCommand() {
        this.checkDate = "";
        this.preposition = "";
    }

    public ListCommand(String arguments) throws IllegalValueException {
        final Matcher matcher = TASKS_DUE_UNTIL_DATE_LIST_FORMAT.matcher(arguments);
        if(matcher.matches()) {
            this.preposition = "due";
            Date checkDate = new Date(matcher.group("date"));
            this.checkDate = checkDate.value;
            
        }
        else {            
            Date checkDate = new Date (arguments);
            this.checkDate = checkDate.value;
            this.preposition = "";
        }
    }

    @Override
    public CommandResult execute() {
        if(checkDate == "") {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        else {
                model.updateFilteredTaskList(checkDate, preposition);
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }       
    }
}

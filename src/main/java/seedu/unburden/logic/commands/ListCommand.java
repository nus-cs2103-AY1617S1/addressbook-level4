package seedu.unburden.logic.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Predicate;

import seedu.unburden.model.task.Task;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists out all existings tasks or exising tasks according to a date";
	
	public final Date date;
	
    public ListCommand() {
    	this.date = null;
    }

    public ListCommand(String args) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		this.date = df.parse(args);
	}
    
    public Predicate<Task> getAllDatesBefore(Date date){
    	return t -> t.getDate().before.(date);
    }

	@Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

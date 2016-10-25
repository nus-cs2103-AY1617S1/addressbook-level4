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

	public static final String MESSAGE_USAGE = "Type : \"" + COMMAND_WORD + "\" or type : \"" + COMMAND_WORD + "\" your specified date ";
	
	public final Date date;
	
	public final String mode;
	
    public ListCommand() {
    	this.date = null;
    	this.mode = "all";
    }
    
    public ListCommand(String done){
    	this.date = null;
    	this.mode = "done";
    }

    public ListCommand(String args, String mode) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		this.date = df.parse(args);
		this.mode = "date";
	}
    
    public java.util.function.Predicate<? super Task> getAllDatesBefore(Date date){
    	return t -> {
			try {
				return t.getDate().toDate().before(date) || t.getDate().toDate().equals(date);
			} catch (ParseException e) {
				return false;
			}
		};
    }
    
    public java.util.function.Predicate<? super Task> getAllDone(){
    	return t -> {
    		return t.getDone();
    	};
    }

	@Override
    public CommandResult execute() {
		if(mode.equals("all")){
			 model.updateFilteredListToShowAll();
		}
		else if(mode.equals("done")){
			model.updateFilteredListToShowAllDone(getAllDone());
		}
		else{
			model.updateFilteredListToShowAllDatesBefore(getAllDatesBefore(date));
		}
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package seedu.menion.logic.commands;

import java.text.DateFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LIST_ALL = "all";
    public static final String LIST_MONTH = "month";
    public static final String LIST_DATE = "date";
    private static final Pattern VALID_DATE = Pattern
			.compile("(0?[0-1][0-9]-[0-3][0-9]-[0-2][0-9][0-9][0-9])");
    
    public static final String WRONG_ARGUMENT = "Wrong argument! Please input either list all, list DATE or list MONTH";
    public static final String MESSAGE_SUCCESS = "Listed all activities";

    private String listArgument;
    private String listType;
    private int monthToList;
    private String dateToList;
    
    private static Matcher matcher;
    
    
    public ListCommand(String args){
    	this.listArgument = args;
    }

    
    public String checkListType(String args) throws IllegalValueException{
    	
    	if (args.toLowerCase().equals(LIST_ALL)){
    		return LIST_ALL;
    	}
    	
    	else {
    		
    		int month = isMonth(args);
    		if (month != -1){
    			this.monthToList = month;
    			return LIST_MONTH;
    		}
    		
    		else {
  
    			String date = isDate(args);
    			if (date == null){
    				throw new IllegalValueException(WRONG_ARGUMENT);
    			}
    			else {
    				this.dateToList = date;
    				return LIST_DATE;
    			}
    		}
    	
    	}
    	
    }
    
    private static String isDate (String args){
    	matcher = VALID_DATE.matcher(args);
    	if (matcher.find()){
    		return args;
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * This method checks if the arguments is a month
     * @return month number if its a month. else return -1
     */
    private static int isMonth (String args){
    	
    	String monthString;
    	
    	for (int i = 0 ; i < 12; i ++){
    		monthString = new DateFormatSymbols().getMonths()[i];
    		if (args.toLowerCase().equals(monthString.toLowerCase())){
    			return i;
    		}
    	}
    	
    	return -1;
    }
    
    @Override
    public CommandResult execute() {
    	
    	try{
    		
    		this.listType = checkListType(this.listArgument);
    		
    	} catch (IllegalValueException e){
    		
    		return new CommandResult(WRONG_ARGUMENT);
    		
    	}
    	switch (this.listType){
    	
    	case LIST_ALL:

            model.updateFilteredListToShowAll();
    		break;
    	case LIST_DATE:
    		break;
    	case LIST_MONTH:
    		break;
    	
    	}
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /* 
     * list command does not support undo
     */
	@Override
	public boolean undo() {
		
		return false;
	}
}

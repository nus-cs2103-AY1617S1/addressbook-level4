package seedu.menion.logic.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParser {

	
	public AddParser(){};

	private static final Pattern REGULAR_TASK_REGEX = 
			Pattern.compile("by: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9])");
	private static final Pattern EVENTS_REGEX = 
			Pattern.compile("from: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9]) "
					+ "to: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9])");
	
	private static final String REGULAR_TASK = "task";
	private static final String EVENTS = "event";
	private static final String FLOATING_TASK = "floatingTask";
	
	private static Matcher matcher;
	private static ArrayList<String> parsedArguments;
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be a Task.
	 * @return
	 */
	public static Boolean isTask(String args){
		matcher = REGULAR_TASK_REGEX.matcher(args);
		
		if (matcher.find()){
			return true;
		}
		
		return false;
		
	}
	
	
}

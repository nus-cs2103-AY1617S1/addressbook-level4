package seedu.menion.logic.parser;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.AddCommand;
import seedu.menion.model.activity.Activity;


import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@@author A0139277U
public class AddParser {

	public AddParser() {
	};

	private static final Pattern REGULAR_TASK_REGEX_WITH_NOTES = Pattern
			.compile("(.+)[\\ ]*?by[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern REGULAR_TASK_REGEX_WITHOUT_NOTES = Pattern
			.compile("(.+)[\\ ]*?by[\\ ]*?:[\\ ]*?(.+)[\\ ]*?");
	private static final Pattern EVENTS_FROM_TO_REGEX_WITH_NOTES = Pattern
			.compile("(.+)[\\ ]*?from:[\\ ]*?(.+)[\\ ]*?to[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern EVENTS_FROM_TO_REGEX_WITHOUT_NOTES = Pattern
			.compile("(.+)[\\ ]*?from:[\\ ]*?(.+)[\\ ]*?to[\\ ]*?:[\\ ]*?(.+)[\\ ]*?");
	private static final Pattern EVENTS_TO_FROM_REGEX_WITH_NOTES = Pattern
			.compile("(.+)[\\ ]*?to:[\\ ]*?(.+)[\\ ]*?from[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern EVENTS_TO_FROM_REGEX_WITHOUT_NOTES = Pattern
			.compile("(.+)[\\ ]*?to:[\\ ]*?(.+)[\\ ]*?from[\\ ]*?:[\\ ]*?(.+)[\\ ]*?");
	private static final Pattern FLOATING_TASK_REGEX_WITH_NOTES = Pattern
			.compile("(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern FLOATING_TASK_REGEX_WITHOUT_NOTES = Pattern
			.compile("(.+)");
		
	
	
	private static Matcher matcher;
	private static ArrayList<String> parsedArguments;
	

	/**
	 * This method parses the input command and will check the type of add
	 * command.
	 * 
	 * @param args
	 * @return An array of parsed commands
	 */
	public static ArrayList<String> parseCommand (String args) throws IllegalValueException {

		parsedArguments = new ArrayList<String>();
		checkActivityType(args);

		return parsedArguments;
	}

	/**
	 * This method checks the type of activity based on its arguments and sets
	 * the arguments into an array list.
	 * 
	 * @param args
	 * @throws IllegalValueException 
	 */
	public static void checkActivityType(String args) throws IllegalValueException {

		if (isEvents(args)) {
			parsedArguments.add(Activity.EVENT_TYPE);
			inputEventArguments();
		}

		else if (isTask(args)) {
			parsedArguments.add(Activity.TASK_TYPE);
			inputTaskArguments();
		}

		else if (isFloatingTask(args)){
			parsedArguments.add(Activity.FLOATING_TASK_TYPE);
			inputFloatingTaskArguments();
		}
		else {
			throw new IllegalValueException(AddCommand.MESSAGE_USAGE);
		}

	}
	

	/**
	 * Input the arguments into the parsedArguments ArrayList.
	 * list(1) = Floating Task Name
	 * list(2) = Floating Task Notes
	 */
	private static void inputFloatingTaskArguments(){

		if (matcher.pattern().equals(FLOATING_TASK_REGEX_WITHOUT_NOTES)){
				parsedArguments.add(1, matcher.group(1).trim());
				parsedArguments.add(2, null);
				
		}
		else if (matcher.pattern().equals(FLOATING_TASK_REGEX_WITH_NOTES)){
				parsedArguments.add(1, matcher.group(1).trim());
				parsedArguments.add(2, matcher.group(2).trim());
		}
		
	}
	
	/**
	 * Input the arguments into the parsedArguments ArrayList. 
	 * list(1) = Task Name
	 * list(2) = Task Notes
	 * list(3) = Task Start Date
	 * list(4) = Task Start Time
	 */
	private static void inputTaskArguments() {
		parsedArguments.add(1, matcher.group(1).trim());

		if (matcher.pattern().equals(REGULAR_TASK_REGEX_WITHOUT_NOTES)){
			parsedArguments.add(2, null);			
		}
		else {
			parsedArguments.add(2, matcher.group(3).trim());
		}
		
		ArrayList<String> dateTimeList = new ArrayList<String>();
		NattyDateParser.parseDate(matcher.group(2), dateTimeList);
		parsedArguments.add(3, dateTimeList.get(0));
		parsedArguments.add(4, dateTimeList.get(1));			
	}

	/**
	 * Input the arguments into the parsedArguments ArrayList.
	 * list(1) = Event Name
	 * list(2) = Event Notes
	 * list(3) = Event Start Date
	 * list(4) = Event Start Time
	 * list(5) = Event End Date
	 * list(6) = Event End Time
	 */
	private static void inputEventArguments() {

		parsedArguments.add(1, matcher.group(1).trim());

		if (matcher.pattern().equals(EVENTS_FROM_TO_REGEX_WITH_NOTES) || 
				matcher.pattern().equals(EVENTS_TO_FROM_REGEX_WITH_NOTES)){
			parsedArguments.add(2, matcher.group(4).trim());
		}
		
		else if (matcher.pattern().equals(EVENTS_FROM_TO_REGEX_WITHOUT_NOTES) || 
				matcher.pattern().equals(EVENTS_TO_FROM_REGEX_WITHOUT_NOTES)){
			parsedArguments.add(2, null);
		}
		
		ArrayList<String> dateTimeList = new ArrayList<String>();
		
		if (matcher.pattern().equals(EVENTS_FROM_TO_REGEX_WITHOUT_NOTES) || 
				matcher.pattern().equals(EVENTS_FROM_TO_REGEX_WITH_NOTES)){
			NattyDateParser.parseDate(matcher.group(2), dateTimeList);
			parsedArguments.add(3, dateTimeList.get(0));
			parsedArguments.add(4, dateTimeList.get(1));
			NattyDateParser.parseDate(matcher.group(3), dateTimeList);
			parsedArguments.add(5, dateTimeList.get(0));
			parsedArguments.add(6, dateTimeList.get(1));
		}
		else {
			NattyDateParser.parseDate(matcher.group(3), dateTimeList);
			parsedArguments.add(3, dateTimeList.get(0));
			parsedArguments.add(4, dateTimeList.get(1));
			NattyDateParser.parseDate(matcher.group(2), dateTimeList);
			parsedArguments.add(5, dateTimeList.get(0));
			parsedArguments.add(6, dateTimeList.get(1));
		}
		
	}

	public static Boolean isFloatingTask(String args){
		matcher = FLOATING_TASK_REGEX_WITHOUT_NOTES.matcher(args);
		
		if (matcher.find()){
			
			matcher = FLOATING_TASK_REGEX_WITH_NOTES.matcher(args);
			if (matcher.find() == false){
				matcher = FLOATING_TASK_REGEX_WITHOUT_NOTES.matcher(args);
				matcher.find();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be
	 * a Task.
	 * 
	 * @return
	 */
	public static Boolean isTask(String args) {
		matcher = REGULAR_TASK_REGEX_WITHOUT_NOTES.matcher(args);

		if (matcher.find()) {
			// Check if the user has input notes.
			matcher = REGULAR_TASK_REGEX_WITH_NOTES.matcher(args);
			if (matcher.find() == false){
				matcher = REGULAR_TASK_REGEX_WITHOUT_NOTES.matcher(args);
				matcher.find();
			}
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the input arguments satisfy the requirements to be
	 * a Event.
	 * 
	 * @return
	 */
	public static Boolean isEvents(String args) {
		matcher = EVENTS_FROM_TO_REGEX_WITHOUT_NOTES.matcher(args);

		// User input from: to: arguments.
		if (matcher.find()) {
			matcher = EVENTS_FROM_TO_REGEX_WITH_NOTES.matcher(args);
			if (matcher.find() == false){
				matcher = EVENTS_FROM_TO_REGEX_WITHOUT_NOTES.matcher(args);
				matcher.find();
			}
			return true;
		}
		// User input to: from: arguments
		else {
			matcher = EVENTS_TO_FROM_REGEX_WITHOUT_NOTES.matcher(args);
			if (matcher.find()){
				matcher = EVENTS_TO_FROM_REGEX_WITH_NOTES.matcher(args);
				if (matcher.find() == false){
					matcher = EVENTS_TO_FROM_REGEX_WITHOUT_NOTES.matcher(args);
					matcher.find();
				}
				return true;
			}
		}
		return false;
	}
}

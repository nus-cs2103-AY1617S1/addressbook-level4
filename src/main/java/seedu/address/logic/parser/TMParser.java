package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;


public class TMParser {
	
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
	
	// Different regexes for different permutations of arguments
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern.compile("(?<taskType>event|deadline|someday)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern.compile("(?<addTaskArgs>.*)(?<taskType>event|deadline|someday)");
	
	private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile("(?<taskName>'.+')\\son\\s(?<date>.+)\\sfrom\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)");
	private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile("(?<taskName>'.+')\\sfrom\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)\\son\\s(?<date>.+)");
	private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile("on\\s(?<date>.+)\\sfrom\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)\\s(?<taskName>'.+')");
	private static final Pattern EVENT_ARGS_FORMAT_4 = Pattern.compile("on\\s(?<date>.+)\\s(?<taskName>'.+')\\sfrom\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)");
	private static final Pattern EVENT_ARGS_FORMAT_5 = Pattern.compile("from\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)\\son\\s(?<date>.+)\\s(?<taskName>'.+')");
	private static final Pattern EVENT_ARGS_FORMAT_6 = Pattern.compile("from\\s(?<startTime>.+)\\sto\\s(?<endTime>.+)\\s(?<taskName>'.+')\\son\\s(?<date>.+)");
	
	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern.compile("(?<taskName>'.+')\\sby\\s(?<date>.+)\\sat\\s(?<time>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern.compile("(?<taskName>'.+')\\sat\\s(?<time>.+)\\sby\\s(?<date>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_3 = Pattern.compile("by\\s(?<date>.+)\\sat\\s(?<time>.+)\\s(?<taskName>'.+')");
	private static final Pattern DEADLINE_ARGS_FORMAT_4 = Pattern.compile("by\\s(?<date>.+)\\s(?<taskName>'.+')\\sat\\s(?<time>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_5 = Pattern.compile("at\\s(?<time>.+)\\sby\\s(?<date>.+)\\s(?<taskName>'.+')");
	private static final Pattern DEADLINE_ARGS_FORMAT_6 = Pattern.compile("at\\s(?<time>.+)\\s(?<taskName>'.+')\\sby\\s(?<date>.+)");
	
	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("(?<taskName>'.+')");
	
	
	public static Command parseUserInput(String userInput) {
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments").trim();
        
		System.out.println("command: " + commandWord);
		System.out.println("arguments: " + arguments);

		switch (commandWord) {
		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);

//		case SelectCommand.COMMAND_WORD:
//            return prepareSelect(arguments);
//
//        case DeleteCommand.COMMAND_WORD:
//            return prepareDelete(arguments);
//
//        case ClearCommand.COMMAND_WORD:
//            return new ClearCommand();
//
//        case FindCommand.COMMAND_WORD:
//            return prepareFind(arguments);
//
//        case ListCommand.COMMAND_WORD:
//            return new ListCommand();
//
//        case ExitCommand.COMMAND_WORD:
//            return new ExitCommand();
//
//        case HelpCommand.COMMAND_WORD:
//            return new HelpCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}


	private static Command prepareAdd(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(ADD_COMMAND_FORMAT_1.matcher(arguments.trim()));
		matchers.add(ADD_COMMAND_FORMAT_2.matcher(arguments.trim()));
		
		String taskType = null;
        String addTaskArgs = null;
		
		boolean isAnyMatch = false;
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
	            isAnyMatch = true;
	            taskType = matcher.group("taskType").trim();
	            addTaskArgs = matcher.group("addTaskArgs").trim();
	        }
		}
        
        if (!isAnyMatch) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        System.out.println("task type: " + taskType);
        System.out.println("add args: " + addTaskArgs);
        
        
		switch (taskType) {
		// TODO change hardcoded strings to references to strings in command classes
		case "event":
			return prepareAddEvent(addTaskArgs);
			
		case "deadline":
			return prepareAddDeadline(addTaskArgs);
			
		case "someday":
			return prepareAddSomeday(addTaskArgs);
			
		default: 
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}
	
	private static Command prepareAddEvent(String arguments) {		
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(EVENT_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_2.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_3.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_4.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_5.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_6.matcher(arguments.trim()));
		
		String taskName = null;
	    String date = null;
	    String startTime = null;
	    String endTime = null;
		
		boolean isAnyMatch = false;
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
	            isAnyMatch = true;
	            
	            taskName = matcher.group("taskName").trim();
	            date = matcher.group("date").trim();
	            startTime = matcher.group("startTime").trim();
	            endTime = matcher.group("endTime").trim();
	            
	            break;
	        }
		}
        
        if (!isAnyMatch) {
        	System.out.println("no match");
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        System.out.println("task name: " + taskName);
        System.out.println("date: " + date);
        System.out.println("start time: " + startTime);
        System.out.println("end time: " + endTime);
        
        // TODO return new addeventcommand
		return null;
	}
	
	
	private static Command prepareAddDeadline(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(DEADLINE_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_2.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_3.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_4.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_5.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_6.matcher(arguments.trim()));
		
		String taskName = null;
	    String date = null;
	    String time = null;
		
		boolean isAnyMatch = false;
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
	            isAnyMatch = true;
	            
	            taskName = matcher.group("taskName").trim();
	            date = matcher.group("date").trim();
	            time = matcher.group("time").trim();
	            
	            break;
	        }
		}
        
        if (!isAnyMatch) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        System.out.println("task name: " + taskName);
        System.out.println("date: " + date);
        System.out.println("time: " + time);
        
        // TODO return new adddeadlinecommand
		return null;
	}
	

	private static Command prepareAddSomeday(String arguments) {
		final Matcher matcher = SOMEDAY_ARGS_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String taskName = matcher.group("taskName").trim();
        System.out.println("task name: " + taskName);
        
        // TODO return new addsomedaycommand
		return null;
	}
	
	
	public static void main(String[] args) {
		String userInput = "add event 'Read 50 Shades of Grey' from 05:00 to 06:00 on 25/12/2001";
		parseUserInput(userInput);
	}
}

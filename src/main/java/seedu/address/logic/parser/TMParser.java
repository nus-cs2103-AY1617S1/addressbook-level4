package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
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
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern.compile("(?i)(?<taskType>event|deadline|someday)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern.compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|deadline|someday)");

	private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+on\\s+(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+on\\s+(?<date>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile("(?i)on\\s+(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	private static final Pattern EVENT_ARGS_FORMAT_4 = Pattern.compile("(?i)on\\s+(?<date>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_5 = Pattern.compile("(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+on\\s+(?<date>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	private static final Pattern EVENT_ARGS_FORMAT_6 = Pattern.compile("(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'\\s+on\\s+(?<date>\\S+)");

	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+by\\s+(?<date>\\S+)\\s+(?<time>\\S+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+by\\s+(?<time>\\S+)\\s+(?<date>\\S+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_3 = Pattern.compile("(?i)by\\s+(?<date>\\S+)\\s+(?<time>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	private static final Pattern DEADLINE_ARGS_FORMAT_4 = Pattern.compile("(?i)by\\s+(?<time>\\S+)\\s+(?<date>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");

	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>(\\s*[^\\s+])+)'");
	
	private static final Pattern EDIT_ARGS_FORMAT_1 = Pattern.compile("(?<index>\\d)\\s+(?<newName>.+)");
	
	
	public TMParser() {
	};

	public Command parseUserInput(String userInput) {
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
			
		case ListCommand.COMMAND_WORD:
			if (arguments.equals("")) {
				// TODO return new listcommand()
				return null;
			}
			else {
				return prepareList(arguments);
			}
			
		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);
			
		case "edit":
			return prepareEdit(arguments);
		//
		// case ClearCommand.COMMAND_WORD:
		// return new ClearCommand();
		//
		// case FindCommand.COMMAND_WORD:
		// return prepareFind(arguments); 
		//
		// case ExitCommand.COMMAND_WORD:
		// return new ExitCommand();
		//
		// case HelpCommand.COMMAND_WORD:
		// return new HelpCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}

	private Command prepareAdd(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(ADD_COMMAND_FORMAT_1.matcher(arguments.trim()));
		matchers.add(ADD_COMMAND_FORMAT_2.matcher(arguments.trim()));
		
		// Null values will always be overwritten if the matcher matches.
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

	private Command prepareAddEvent(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(EVENT_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_2.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_3.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_4.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_5.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_6.matcher(arguments.trim()));
		
		// Null values will always be overwritten if the matcher matches.
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

	private Command prepareAddDeadline(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(DEADLINE_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_2.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_3.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_4.matcher(arguments.trim()));
		
		// Null values will always be overwritten if the matcher matches.
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

	private Command prepareAddSomeday(String arguments) {
		final Matcher matcher = SOMEDAY_ARGS_FORMAT.matcher(arguments.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String taskName = matcher.group("taskName").trim();
		System.out.println("task name: " + taskName);

		// TODO return new addsomedaycommand
		return null;
	}
	
	// Only supports task type and done|not-done options.
	private Command prepareList(String arguments) {
		String[] args = arguments.split(" ");
		
		System.out.println(Arrays.toString(args));
		
		String taskType = null;
		String done = null;
		for (int i=0; i<args.length; i++) {
			switch(args[i].trim()) {
			case "event":
			case "deadline":
			case "someday":
				taskType = args[i];
				break;
			case "done":
			case "not-done":
				done = args[i];
				break;
			default:
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
			}
		}
		
		System.out.println("task type: " + taskType);
		System.out.println("done: " + done);
		
		// TODO return new listcommand(taskType, done)
		// Since both taskType and done may be supplied as the only parameter to the listcommand constructor, 
		// the listcommand constructor must make null checks. Alternatively, the parameters can be encapsulated in an object
		// and the constructor overloaded.
		return null;
	}
	
	/**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String arguments) {
        ArrayList<Optional<Integer>> indexOptionals = parseIndices(arguments);
        
        int[] indices = new int[indexOptionals.size()];
        int i=0;
        for (Optional<Integer> index : indexOptionals) {
        	if(!index.isPresent()){
        		return new IncorrectCommand(
        				String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        	}
        	indices[i] = index.get();
        	i++;
        }
        
        System.out.println("indices: " + Arrays.toString(indices));

        //return new TMDeleteCommand(indices);
        return null;
    }
    
    
    private Command prepareEdit(String arguments) {
    	final Matcher matcher = EDIT_ARGS_FORMAT_1.matcher(arguments.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String indices = matcher.group("index").trim();
//		final String args = matcher.group("arguments").trim();
		
		
		
		System.out.println("index: " + indices);
//		System.out.println("non-index args: " + args);
		
    	return null;
    }
    
    /**
     * Returns an ArrayList of the specified indices in the {@code command} IF positive unsigned integers are given.
     * Returns an ArrayList with a single element {@code Optional.empty()} otherwise.
     */
    private ArrayList<Optional<Integer>> parseIndices(String args) {
    	String[] indices = args.split(" ");
    	ArrayList<Optional<Integer>> optionals = new ArrayList<>();
    	
    	for (int i=0; i<indices.length; i++) {
    		if(!StringUtil.isUnsignedInteger(indices[i].trim())){
    			optionals = new ArrayList<>();
    			optionals.add(Optional.empty());
                return optionals;
            }
    		
    		optionals.add(Optional.of(Integer.parseInt(indices[i])));
    	}
    	
    	return optionals;
    }

	public static void main(String[] args) {
		String userInput = "add someday 'g'";
		TMParser p = new TMParser();
		p.parseUserInput(userInput);
	}
}

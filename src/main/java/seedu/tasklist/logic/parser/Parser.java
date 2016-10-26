package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tasklist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.*;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Parses user input.
 */
public class Parser {

	/**
	 * Used for initial separation of command word and args.
	 */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	//private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
	//@@author A0146107M
	private static final String[] ADD_TASK_KEYWORDS = {" at ", " by ", " from ", " to ", " r/", " p/", " t/"};

	private static final String[] UPDATE_TASK_KEYWORDS = {" at ", " by ", " from ", " to ", " r/"," p/", " t/"};
	//@@author
	
	public Parser() {
	}

	/**
	 * Parses user input into command for execution.
	 *
	 * @param userInput
	 *            full user input string
	 * @return the command based on the user input
	 */
	public Command parseCommand(String userInput) {
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String commandWord = matcher.group("commandWord").toLowerCase();
		final String arguments = matcher.group("arguments");
		switch (commandWord) {

		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);

		case SetStorageCommand.COMMAND_WORD:
			return prepareFilePath(arguments);

		case DoneCommand.COMMAND_WORD:
			return prepareDone(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case UpdateCommand.COMMAND_WORD:
			return prepareUpdate(arguments);

		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ShowCommand.COMMAND_WORD:
			return prepareShow(arguments);

		case UndoCommand.COMMAND_WORD:
			return prepareUndo();

		case RedoCommand.COMMAND_WORD:
			return prepareRedo();

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case HelpCommand.COMMAND_WORD:
			return new HelpCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}

	private Command prepareUndo() {
		return new UndoCommand();
	}

	private Command prepareRedo() {
		return new RedoCommand();
	}

	private Command prepareFilePath(String args){
		if(args!=null){
			args= args.trim();
			return new SetStorageCommand(args);
		}
		else
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStorageCommand.MESSAGE_USAGE));
	}
	private Command prepareUpdate(String args) {
		ArgumentParser parser = new ArgumentParser(args.trim(), UPDATE_TASK_KEYWORDS);
		int index = ArgumentParser.extractIndex(parser.getDefault());
		String name = ArgumentParser.cutIndex(parser.getDefault());
		if(name == ""){
			name = null;
		}
		String startTime = parser.getEitherField("at", "from");
		String endTime = parser.getEitherField("by", "to");
        String frequency = parser.getField("r/");
		String priority = parser.getField("p/");
		String tag = parser.getField("t/");

		try {
			UniqueTagList utags = new UniqueTagList();
			return new UpdateCommand(index, name, startTime, endTime, priority, utags, frequency);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}

	private Tag[] getTags(String[] tagList) {
		Tag[] tags = new Tag[tagList.length];
		for(int i=0; i<tagList.length; i++){
			try {
				tags[i] = new Tag(tagList[i]);
			} 
			catch (IllegalValueException e) {
				e.printStackTrace();
			}
		}
		return tags;
	}

	private Command prepareDone(String input) {
		String args = input.trim();
		Integer index = ArgumentParser.extractIndexBlocking(args);
		
		if(index != null){
			return new DoneCommand(index);
		}
		else if(!args.isEmpty()){
			return new DoneCommand("*"+args+"*");
		}
		else{
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
		}
	}

	/**
	 * Parses arguments in the context of the add person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareAdd(String args) {
		ArgumentParser parser = new ArgumentParser(args, ADD_TASK_KEYWORDS);
		String name = parser.getDefault();
		String startTime = parser.getEitherField("at", "from");
		String endTime = parser.getEitherField("by", "to");
        String frequency = parser.getField("r/");
		String priority = parser.getField("p/");
		String tag = parser.getField("t/");
		
		if (frequency == null) frequency = "";
		
		try{
			Set<String> utags = (tag==null)?Collections.emptySet():getTagsFromArgs("/t"+tag);
			return new AddCommand(name, startTime, endTime, priority, utags, frequency);
		}
		catch(IllegalValueException ive){
			return new IncorrectCommand(ive.getMessage());
		}
	}

	/**
	 * Extracts the new person's tags from the add command's tag arguments
	 * string. Merges duplicate tag strings.
	 */
	private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
		// no tags
		if (tagArguments.isEmpty()) {
			return Collections.emptySet();
		}
		// replace first delimiter prefix, then split
		final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
		return new HashSet<>(tagStrings);
	}

	/**
	 * Parses arguments in the context of the delete person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareDelete(String input) {
		String args = input.trim();
		Integer index = ArgumentParser.extractIndexBlocking(args);
		
		if(index != null){
			return new DeleteCommand(index);
		}
		else if(!args.isEmpty()){
			System.out.println("*"+args+"*");
			return new DeleteCommand("*"+args+"*");
		}
		else{
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}
	}

	/**
	 * Parses arguments in the context of the find person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareFind(String args) {
		String input = args.trim();
		if (input.isEmpty()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
		}
		input = '*' + input + '*';
		return new FindCommand(input);
	}

	/**
	 * Parses arguments in the context of the show task command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareShow(String args) {
		String input = args.trim();
		if (input.isEmpty()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
		}
		return new ShowCommand(input);
	}

}
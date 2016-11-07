package seedu.unburden.logic.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import static seedu.unburden.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.unburden.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.unburden.commons.core.Config;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.commons.util.StringUtil;
import seedu.unburden.logic.commands.*;


/**
 * Parses user input.
 */
public class Parser {

	/**
	 * Used for initial separation of command word and args.
	 */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
	
	private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

	private static final Pattern KEYWORDS_NAME_FORMAT = Pattern.compile("(?<keywords>[A-Za-z0-9 \\-,.?!\'\"]+)");

	// @@author A0143095H
	private static final Pattern KEYWORDS_DATE_FORMAT = Pattern
			.compile("(?<dates>([0-9]{2})[-]([0-9]{2})[-]([0-9]{4}))");
	
	
	//@@author A0139678J
	// Event
	private static final Pattern ADD_FORMAT_0 = Pattern.compile(
			"(?<name>[^/]+)" + "i/(?<taskDescriptions>[^/]+)" + "d/(?<date>[^/]+)" + "s/(?<startTimeArguments>[^/]+)"
					+ "e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Event without task description
	private static final Pattern ADD_FORMAT_1 = Pattern.compile("(?<name>[^/]+)" + "d/(?<date>[^/]+)"
			+ "s/(?<startTimeArguments>[^/]+)" + "e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline
	private static final Pattern ADD_FORMAT_2 = Pattern.compile("(?<name>[^/]+)" + "i/(?<taskDescriptions>[^/]+)"
			+ "d/(?<date>[^/]+)" + "e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without task description
	private static final Pattern ADD_FORMAT_3 = Pattern.compile(
			"(?<name>[^/]+)" + "d/(?<date>[^/]+)" + "e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without task description and time
	private static final Pattern ADD_FORMAT_4 = Pattern
			.compile("(?<name>[^/]+)" + "d/(?<date>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without time
	private static final Pattern ADD_FORMAT_5 = Pattern.compile(
			"(?<name>[^/]+)" + "i/(?<taskDescriptions>[^/]+)" + "d/(?<date>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Floating task
	private static final Pattern ADD_FORMAT_6 = Pattern
			.compile("(?<name>[^/]+)" + "i/(?<taskDescriptions>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Floating task without task description
	private static final Pattern ADD_FORMAT_7 = Pattern.compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");
	
	
	//@@author A0139714B
	private static final Pattern EDIT_FORMAT = Pattern
			.compile("(?<index>[^/]+)(?!$)" + "((?<name>[^/]+))?" + "(i/(?<taskDescriptions>[^/]+))?"
					+ "(d/(?<date>[^/]+))?" + "(s/(?<startTimeArguments>[^/]+))?" + "(e/(?<endTimeArguments>[^/]+))?");

	private static final Pattern SET_DIR_FORMAT = Pattern.compile("(?<filename>.+).xml");

	private static final Pattern SET_DIR_FORMAT_RESET = Pattern.compile(SetDirectoryCommand.COMMAND_RESET);
	
	//@@author A0147986H-unused
	private static final Pattern INDEX_PHASE_FORMAT = Pattern.compile("(?<targetIndex>\\d+-\\d+)");
			
	private static final Pattern INDEX_LIST_FORMAT = Pattern.compile("(?<targetIndex>\\d+(\\s+\\d+)*)");

	//@@author A0139678J
	private static final String BYTODAY = "by today";

	private static final String BYTOMORROW = "by tomorrow";

	private static final String BYNEXTWEEK = "by next week";

	private static final String BYNEXTMONTH = "by next month";

	private static final String TODAY = "today";

	private static final String TOMORROW = "tomorrow";

	private static final String NEXTWEEK = "next week";

	private static final String DONE = "done";

	private static final String UNDONE = "undone";

	private static final String OVERDUE = "overdue";

	private static final String ALL = "all";

	private static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");

	public Parser() {
	}

	/**
	 * Parses user input into command for execution.
	 *
	 * @param userInput
	 *            full user input string
	 * @return the command based on the user input
	 * @throws ParseException
	 */

	// @@author A0139678J
	public Command parseCommand(String userInput) throws ParseException {
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String commandWord = matcher.group("commandWord");
		final String arguments = matcher.group("arguments");
		switch (commandWord.toLowerCase()) {

		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);

		case SelectCommand.COMMAND_WORD:
			return prepareSelect(arguments);

		case EditCommand.COMMAND_WORD:
			return prepareEdit(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case SetDirectoryCommand.COMMAND_WORD:
			return prepareSetDir(arguments);

		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		case UndoCommand.COMMAND_WORD:
			return new UndoCommand();

		case RedoCommand.COMMAND_WORD:
			return new RedoCommand();

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			return prepareList(arguments);

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case HelpCommand.COMMAND_WORD:
			return prepareHelp(arguments);

		case DoneCommand.COMMAND_WORD:
			return prepareDone(arguments);

		case UnDoneCommand.COMMAND_WORD:
			return prepareUnDone(arguments);

		/*
		case UnwantedDeleteCommand.COMMAND_WORD:
		    return prepareUnwantedDelete(arguments);
		*/
		default:
			if (AddCommand.COMMAND_WORD.substring(0, 1).contains(commandWord.toLowerCase())) {
				return prepareAdd(arguments);
			} else if (DeleteCommand.COMMAND_WORD.substring(0, 1).contains(commandWord.toLowerCase())) {
				return prepareDelete(arguments);
			} else if (EditCommand.COMMAND_WORD.substring(0, 1).contains(commandWord.toLowerCase())) {
				return prepareEdit(arguments);
			} else if (SelectCommand.COMMAND_WORD.substring(0, 1).contains(commandWord.toLowerCase())) {
				return prepareSelect(arguments);
			} else {
				return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
			}
		}
	}

	/**
	 * Parses arguments in the context of the add person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 *
	 */
	
	//@@author A0139678J
	private Command prepareAdd(String args) {
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> details = new ArrayList<String>(); // Arraylist to
																// store all
																// details of
																// the input
		final Matcher matcher0 = ADD_FORMAT_0.matcher(args.trim());
		final Matcher matcher1 = ADD_FORMAT_1.matcher(args.trim());
		final Matcher matcher2 = ADD_FORMAT_2.matcher(args.trim());
		final Matcher matcher3 = ADD_FORMAT_3.matcher(args.trim());
		final Matcher matcher4 = ADD_FORMAT_4.matcher(args.trim());
		final Matcher matcher5 = ADD_FORMAT_5.matcher(args.trim());
		final Matcher matcher6 = ADD_FORMAT_6.matcher(args.trim());
		final Matcher matcher7 = ADD_FORMAT_7.matcher(args.trim());

		// Validates that the format for the add command and returns an
		// IncorrectCommand if the the format is wrong
		if (!matcher0.matches() & !matcher1.matches() & !matcher2.matches() & !matcher3.matches() & !matcher4.matches()
				& !matcher5.matches() & !matcher6.matches() & !matcher7.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		try {
			if (matcher0.matches()) { // Matcher for event with description,
										// date, start time and end time
				details.add(matcher0.group("name"));
				details.add(matcher0.group("taskDescriptions"));
				details.add(matcher0.group("date"));
				details.add(matcher0.group("startTimeArguments"));
				details.add(matcher0.group("endTimeArguments"));
				return new AddCommand("event with everything", details,
						getTagsFromArgs(matcher0.group("tagArguments")));

			} // Matcher for event with date, start time and end time
			if (matcher1.matches()) {
				details.add(matcher1.group("name"));
				details.add(matcher1.group("date"));
				details.add(matcher1.group("startTimeArguments"));
				details.add(matcher1.group("endTimeArguments"));
				return new AddCommand("event without description", details,
						getTagsFromArgs(matcher1.group("tagArguments")));

			} else if (matcher2.matches()) { // Matcher for deadline with
												// description, date and end
												// time
				details.add(matcher2.group("name"));
				details.add(matcher2.group("taskDescriptions"));
				details.add(matcher2.group("date"));
				details.add(matcher2.group("endTimeArguments"));
				return new AddCommand("deadline", details, getTagsFromArgs(matcher2.group("tagArguments")));

			} else if (matcher3.matches()) { // Matcher for deadline with date
												// and end time
				details.add(matcher3.group("name"));
				details.add(matcher3.group("date"));
				details.add(matcher3.group("endTimeArguments"));
				return new AddCommand("deadline without task description", details,
						getTagsFromArgs(matcher3.group("tagArguments")));

			} else if (matcher4.matches()) {// Matcher for deadline with date
				details.add(matcher4.group("name"));
				details.add(matcher4.group("date"));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher4.group("tagArguments")));

			} else if (matcher5.matches()) { // Matcher for deadline with
												// description and date
				details.add(matcher5.group("name"));
				details.add(matcher5.group("taskDescriptions"));
				details.add(matcher5.group("date"));
				return new AddCommand("deadline without time", details,
						getTagsFromArgs(matcher5.group("tagArguments")));

			} else if (matcher6.matches()) { // Matcher for floating task with
												// description
				details.add(matcher6.group("name"));
				details.add(matcher6.group("taskDescriptions"));
				return new AddCommand("floating task", details, getTagsFromArgs(matcher6.group("tagArguments")));

			} else if (matcher7.group("name").toLowerCase().contains(BYTODAY)) {// Matcher
																				// for
																				// deadline
																				// with
																				// date
																				// as
																				// today
				details.add(matcher7.group("name").replaceAll("(?i)" + Pattern.quote(BYTODAY), ""));
				details.add(DATEFORMATTER.format(calendar.getTime()));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher7.group("tagArguments")));

			} else if (matcher7.group("name").toLowerCase().contains(BYTOMORROW)) { // Matcher
																					// for
																					// deadline
																					// with
																					// date
																					// as
																					// tomorrow
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				details.add(matcher7.group("name").replaceAll("(?i)" + Pattern.quote(BYTOMORROW), ""));
				details.add(DATEFORMATTER.format(calendar.getTime()));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher7.group("tagArguments")));

			} else if (matcher7.group("name").toLowerCase().contains(BYNEXTWEEK)) { // Matcher
																					// for
																					// deadline
																					// with
																					// date
																					// as
																					// next
																					// week
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.WEEK_OF_YEAR, 1);
				details.add(matcher7.group("name").replaceAll("(?i)" + Pattern.quote(BYNEXTWEEK), ""));
				details.add(DATEFORMATTER.format(calendar.getTime()));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher7.group("tagArguments")));

			} else if (matcher7.group("name").toLowerCase().contains(BYNEXTMONTH)) {// Matcher
																					// for
																					// deadline
																					// with
																					// date
																					// as
																					// next
																					// month
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.MONTH, 1);
				details.add(matcher7.group("name").replaceAll("(?i)" + Pattern.quote(BYNEXTMONTH), ""));
				details.add(DATEFORMATTER.format(calendar.getTime()));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher7.group("tagArguments")));

			} else {// Matcher for floating task
				details.add(matcher7.group("name"));
				return new AddCommand("floating task without task description", details,
						getTagsFromArgs(matcher7.group("tagArguments")));
			}

		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
	//@@author

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
	private Command prepareDelete(String args) {

		Optional<Integer> index = parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}

		return new DeleteCommand(index.get());
	}
	
	//@@author A0147986H-unused
	/**
	 * Parses arguments in the context of the delete person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 * This is unused because I did not discuss with my teammates in advanced so they decided 
     * not to include this method
	 */
	/*
	private Command prepareUnwantedDelete(String args) throws ParseException {
		final Matcher matcherList = INDEX_LIST_FORMAT.matcher(args.trim());
		final Matcher matcherPhase = INDEX_PHASE_FORMAT.matcher(args.trim());  

		if(!matcherList.matches()&&!matcherPhase.matches()){
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));		
		}

		if(matcherPhase.matches()){
			String indexes_phase = matcherPhase.group("targetIndex");
			String[] SeperateIndexes_phase = indexes_phase.trim().split("-");
			ArrayList<Integer> sortList = new ArrayList<> ();
			ArrayList<Integer> indexesInt_phase = new ArrayList<> ();
			Optional<Integer> index_list = parseIndex(SeperateIndexes_phase[0]);

			if (!index_list.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
			}			
			
			Optional<Integer> index_list2 = parseIndex(SeperateIndexes_phase[1]);
			if (!index_list2.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
			}

			sortList.add(Integer.parseInt(SeperateIndexes_phase[0]));
			sortList.add(Integer.parseInt(SeperateIndexes_phase[1]));

			Collections.sort(sortList);

			for(int i= sortList.get(0); i<=sortList.get(1); i++){   
				indexesInt_phase.add(i);
			}
			Collections.sort(indexesInt_phase);
			return new UnwantedDeleteCommand(indexesInt_phase);
		}

		else if(matcherList.matches()){
			String indexes_list = matcherList.group("targetIndex");     
			String[] SeperateIndexes_list = indexes_list.split(" ");
			ArrayList<Integer> indexesInt_list = new ArrayList<> ();
			
			for(int i=0; i<(SeperateIndexes_list.length); i++){
				Optional<Integer> index_list = parseIndex(SeperateIndexes_list[i]);
				if (!index_list.isPresent()) {
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));
				}
				indexesInt_list.add(Integer.parseInt(SeperateIndexes_list[i]));
			}	
			indexesInt_list = (ArrayList<Integer>) indexesInt_list.stream().distinct().collect(Collectors.toList());
			Collections.sort(indexesInt_list);
			return new UnwantedDeleteCommand(indexesInt_list); 
		}

		else
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE));	

	}
	*/
	
	//@@author A0139678J
	private Command prepareList(String args) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		final String todayKeyword = DATEFORMATTER.format(calendar.getTime());
		args = args.trim();
		if (args.equals("")) {
			return new ListCommand();
		}
		final Matcher matcherDate = KEYWORDS_DATE_FORMAT.matcher(args);
		final Matcher matcherWord = KEYWORDS_NAME_FORMAT.matcher(args);
		if (!matcherWord.matches() && !matcherDate.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		}
		if (matcherDate.matches()) {
			return new ListCommand(todayKeyword, args, "date");
		}
		switch (args.toLowerCase()) {
		case TOMORROW:
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			final String tomorrowKeyword = DATEFORMATTER.format(calendar.getTime());
			System.out.println(tomorrowKeyword);
			return new ListCommand(todayKeyword, tomorrowKeyword, "date");
		case NEXTWEEK:
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			final String nextWeekKeyword = DATEFORMATTER.format(calendar.getTime());
			return new ListCommand(todayKeyword, nextWeekKeyword, "date");
		case DONE:
			return new ListCommand(DONE);
		case UNDONE:
			return new ListCommand(UNDONE);
		case OVERDUE:
			return new ListCommand(OVERDUE);
		case ALL:
			return new ListCommand(ALL);
		}
		if (args.toLowerCase().contains(" to ")) {
			String[] dates = args.toLowerCase().split("to");
			return new ListCommand(dates[0], dates[1], "date");
		}
		return new IncorrectCommand("Try List, or List followed by \"done\" or \"all\" or a date");
	}

	/**
	 * Parses arguments in the context of the edit task command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 * 
	 */
	//@@author A0139714B
	private Command prepareEdit(String args) {
		String name, taskDescription, date, startTime, endTime;

		final Matcher matcher = EDIT_FORMAT.matcher(args.trim());
		if (!matcher.matches())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

		try {

			String tempArgs = args.trim();

			String[] seperateIndex = tempArgs.split(" ", 2); // if no parameters
																// is
			// entered
			if (seperateIndex.length <= 1)
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

			Optional<Integer> index = parseIndex(seperateIndex[0]);
			if (!index.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
			}

			String[] newArgs = seperateIndex[1].split(" ");

			String[] parameters = getNewArgs(newArgs);
			name = parameters[0];
			taskDescription = (parameters[1].length() == 0) ? null : parameters[1].substring(2);
			date = (parameters[2].length() == 0) ? null : parameters[2].substring(2);
			startTime = (parameters[3].length() == 0) ? null : parameters[3].substring(2);
			endTime = (parameters[4].length() == 0) ? null : parameters[4].substring(2);

			EditCommand.reset();
			return new EditCommand(index.get(), name, taskDescription, date, startTime, endTime);

		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
	//@@author

	/*
	 * private Command prepareClear(String args) { args = args.trim();
	 * if(args.equals("")){ return new ClearCommand(args); } else
	 * if(args.toLowerCase().equals(ALL)){ return new ClearCommand(args); }
	 * else{ return new
	 * IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
	 * ClearCommand.MESSAGE_USAGE)); }
	 * 
	 * }
	 */

	/**
	 * Parses arguments in the context of the set directory command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 * 
	 */
	//@@author A0139714B
	private Command prepareSetDir(String args) {
		final Matcher resetMatcher = SET_DIR_FORMAT_RESET.matcher(args.trim());
		final Matcher pathMatcher = SET_DIR_FORMAT.matcher(args.trim());

		if (!resetMatcher.matches() && !pathMatcher.matches())
			return new IncorrectCommand(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDirectoryCommand.MESSAGE_USAGE));

		if (resetMatcher.matches())
			return new SetDirectoryCommand(Config.ORIGINAL_TASK_PATH);

		return new SetDirectoryCommand(pathMatcher.group("filename") + ".xml");
	}
	//@@author

	/**
	 * Parses arguments in the context of the select person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */

	//@@author generated
	private Command prepareSelect(String args) {

		Optional<Integer> index = parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}

		return new SelectCommand(index.get());
	}

	/**
	 * Returns the specified index in the {@code command} IF a positive unsigned
	 * integer is given as the index. Returns an {@code Optional.empty()}
	 * otherwise.
	 */
	//@@author generated
	private Optional<Integer> parseIndex(String command) {
		final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
		if (!matcher.matches()) {
			return Optional.empty();
		}

		String index = matcher.group("targetIndex");
		if (!StringUtil.isUnsignedInteger(index)) {
			return Optional.empty();
		}
		return Optional.of(Integer.parseInt(index));

	}

	/**
	 * Parses arguments in the context of the find person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 * 
	 */
	//@@author A0139678J
	private Command prepareFind(String args) {
		final Matcher matcherName = KEYWORDS_NAME_FORMAT.matcher(args.trim());
		final Matcher matcherDate = KEYWORDS_DATE_FORMAT.matcher(args.trim());

		// Validates the format for Find command otherwise returns an
		// IncorrectCommand with an error message to the user
		if (!matcherName.matches() && !matcherDate.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
		}

		if (matcherDate.matches()) {
			final String keywords = matcherDate.group("dates");
			return new FindCommand(keywords, "date");
		} else { // keywords delimited by whitespace
			Calendar calendar = Calendar.getInstance();
			switch (matcherName.group("keywords").toLowerCase()) {
			case TODAY:
				final String todayKeyword = DATEFORMATTER.format(calendar.getTime());
				return new FindCommand(todayKeyword, "date");
			case TOMORROW:
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				final String tomorrowKeyword = DATEFORMATTER.format(calendar.getTime());
				return new FindCommand(tomorrowKeyword, "date");
			default:
				final String[] nameKeywords = matcherName.group("keywords").split("\\s+");
				final Set<String> nameKeyword = new HashSet<>(Arrays.asList(nameKeywords));
				return new FindCommand(nameKeyword, "name");
			}
		}
	}

	/**
	 * Sets up done command to be executed
	 * 
	 * @param args
	 *            full command args string
	 * @return prepared doneCommand
	 *
	 */
	//@@author A0143095H
	private Command prepareDone(String args) {
		args = args.trim();
		if (args.toLowerCase().equals(ALL)) {
			return new DoneCommand();
		} else {
			Optional<Integer> index = parseIndex(args);
			if (!index.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
			} else {
				return new DoneCommand(index.get());
			}
		}
	}

	/**
	 * Sets up undone command to be executed
	 * 
	 * @param args
	 *            full command args string
	 * @return prepared undoneCommand
	 * 
	 * 
	 */
	//@@author A0143095H
	private Command prepareUnDone(String args) {
		Optional<Integer> index = parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}

		return new UnDoneCommand(index.get());
	}

	//@@author A0139678J
	private Command prepareHelp(String args) {
		args = args.trim();

		switch (args.toLowerCase()) {
		case AddCommand.COMMAND_WORD:
			return new HelpCommand(AddCommand.COMMAND_WORD);
		case DeleteCommand.COMMAND_WORD:
			return new HelpCommand(DeleteCommand.COMMAND_WORD);
		case FindCommand.COMMAND_WORD:
			return new HelpCommand(FindCommand.COMMAND_WORD);
		case EditCommand.COMMAND_WORD:
			return new HelpCommand(EditCommand.COMMAND_WORD);
		case ClearCommand.COMMAND_WORD:
			return new HelpCommand(ClearCommand.COMMAND_WORD);
		case ListCommand.COMMAND_WORD:
			return new HelpCommand(ListCommand.COMMAND_WORD);
		case DoneCommand.COMMAND_WORD:
			return new HelpCommand(DoneCommand.COMMAND_WORD);
		case UndoCommand.COMMAND_WORD:
			return new HelpCommand(UndoCommand.COMMAND_WORD);
		case RedoCommand.COMMAND_WORD:
			return new HelpCommand(RedoCommand.COMMAND_WORD);
		case UnDoneCommand.COMMAND_WORD:
			return new HelpCommand(UnDoneCommand.COMMAND_WORD);
		case ExitCommand.COMMAND_WORD:
			return new HelpCommand(ExitCommand.COMMAND_WORD);
		case "":
			return new HelpCommand(HelpCommand.COMMAND_WORD);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}
	}

	/*
	 * To retrieve, concatenate and split the arguments to the respective
	 * parameters
	 */
	//@@author A0139714B
	private String[] getNewArgs(String[] tokens) {
		String[] newArgs = new String[5];
		for (int i = 0; i < 5; i++)
			newArgs[i] = "";

		int loopIndex = 0;
		int targetIndex = 0;
		while (loopIndex < tokens.length) {
			if (tokens[loopIndex].length() > 1 && tokens[loopIndex].charAt(1) == '/') {
				switch (tokens[loopIndex].charAt(0)) {
				case ('i'):
					targetIndex = 1;
					break;
				case ('d'):
					targetIndex = 2;
					break;
				case ('s'):
					targetIndex = 3;
					break;
				case ('e'):
					targetIndex = 4;
					break;
				default:
					break;
				}
			}

			if (newArgs[targetIndex] == "") {
				newArgs[targetIndex] = tokens[loopIndex] + " ";
			} else {
				newArgs[targetIndex] = newArgs[targetIndex] + (tokens[loopIndex]) + " ";
			}
			loopIndex = loopIndex + 1;
		}

		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = newArgs[i].trim();
		}

		return newArgs;
	}
}
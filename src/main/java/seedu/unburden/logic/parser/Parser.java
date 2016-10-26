package seedu.unburden.logic.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static seedu.unburden.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.unburden.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final Pattern KEYWORDS_NAME_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
																											// or
																											// more
																											// keyword
																											// separated
																											// by
																											// whitespace

	// private static final Pattern HELP_FORMAT =
	// Pattern.compile("(?<help>\\S+(?:\\s+\\S+)*)");

	/*
	 * private static final Pattern KEYWORDS_DATE_FORMAT =
	 * Pattern.compile("(?<dates>\\S+([0-9]{2}[/][0-9]{2}[/][0-9]{4})*)");
	 */

	private static final Pattern KEYWORDS_DATE_FORMAT = Pattern.compile("(?<dates>[0-9]{2}[-][0-9]{2}[-][0-9]{4}$)");

	// Event
	private static final Pattern ADD_FORMAT_0 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isTaskDescriptionPrivate>p?)i/(?<taskDescriptions>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)"
			+ "(?<isStartTimeArgumentsPrivate>p?)s/(?<startTimeArguments>[^/]+)"
			+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Event without task description
	private static final Pattern ADD_FORMAT_1 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isDatePrivate>p?)d/(?<date>[^/]+)"
			+ "(?<isStartTimeArgumentsPrivate>p?)s/(?<startTimeArguments>[^/]+)"
			+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline
	private static final Pattern ADD_FORMAT_2 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isTaskDescriptionPrivate>p?)i/(?<taskDescriptions>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)"
			+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without task description
	private static final Pattern ADD_FORMAT_3 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isDatePrivate>p?)d/(?<date>[^/]+)" + "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)"
			+ "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without task description and time
	private static final Pattern ADD_FORMAT_4 = Pattern
			.compile("(?<name>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without task description and date
	private static final Pattern ADD_FORMAT_5 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without date
	private static final Pattern ADD_FORMAT_6 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isTaskDescriptionPrivate>p?)i/(?<taskDescriptions>[^/]+)"
			+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Deadline without time
	private static final Pattern ADD_FORMAT_7 = Pattern
			.compile("(?<name>[^/]+)" + "(?<isTaskDescriptionPrivate>p?)i/(?<taskDescriptions>[^/]+)"
					+ "(?<isDatePrivate>p?)d/(?<date>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Floating task
	private static final Pattern ADD_FORMAT_8 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isTaskDescriptionPrivate>p?)i/(?<taskDescriptions>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	// Floating task without task description
	private static final Pattern ADD_FORMAT_9 = Pattern.compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	private static final Pattern EDIT_FORMAT = Pattern.compile("(?<index>[^/]+)(?!$)" + "((?<name>[^/]+))?" + "(i/(?<taskDescriptions>[^/]+))?"
			+ "(d/(?<date>[^/]+))?" + "(s/(?<startTimeArguments>[^/]+))?" + "(e/(?<endTimeArguments>[^/]+))?");
	
	private static final Pattern SET_DIR_FORMAT = Pattern.compile("(?<filename>.+).xml");
	
	private static final Pattern SET_DIR_FORMAT_RESET = Pattern.compile(SetDirectoryCommand.COMMAND_RESET);

	private static final String byToday = "by today";

	private static final String byTomorrow = "by tomorrow";

	private static final String byNextWeek = "by next week";

	private static final String byNextMonth = "by next month";

	private static final String today = "today";

	private static final String tomorrow = "tomorrow";

	private static final String nextWeek = "next week";
	
	private static final String done = "done";
	
	private static final String undone = "undone";

	private static final DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

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

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
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
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> details = new ArrayList<String>();
		final Matcher matcher0 = ADD_FORMAT_0.matcher(args.trim());
		final Matcher matcher1 = ADD_FORMAT_1.matcher(args.trim());
		final Matcher matcher2 = ADD_FORMAT_2.matcher(args.trim());
		final Matcher matcher3 = ADD_FORMAT_3.matcher(args.trim());
		final Matcher matcher4 = ADD_FORMAT_4.matcher(args.trim());
		final Matcher matcher5 = ADD_FORMAT_5.matcher(args.trim());
		final Matcher matcher6 = ADD_FORMAT_6.matcher(args.trim());
		final Matcher matcher7 = ADD_FORMAT_7.matcher(args.trim());
		final Matcher matcher8 = ADD_FORMAT_8.matcher(args.trim());
		final Matcher matcher9 = ADD_FORMAT_9.matcher(args.trim());

		// Validate arg string format
		if (!matcher0.matches() & !matcher1.matches() & !matcher2.matches() & !matcher3.matches() & !matcher4.matches()
				& !matcher5.matches() & !matcher6.matches() & !matcher7.matches() & !matcher8.matches()
				& !matcher9.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		try {
			if (matcher0.matches()) {
				System.out.println("parser");
				details.add(matcher0.group("name"));
				details.add(matcher0.group("taskDescriptions"));
				details.add(matcher0.group("date"));
				details.add(matcher0.group("startTimeArguments"));
				details.add(matcher0.group("endTimeArguments"));
				return new AddCommand("event with everything", details,
						getTagsFromArgs(matcher0.group("tagArguments")));
				
			}

			if (matcher1.matches()) {
				details.add(matcher1.group("name"));
				details.add(matcher1.group("date"));
				details.add(matcher1.group("startTimeArguments"));
				details.add(matcher1.group("endTimeArguments"));
				return new AddCommand("event without description", details,
						getTagsFromArgs(matcher1.group("tagArguments")));
				

			} else if (matcher2.matches()) {
				details.add(matcher2.group("name"));
				details.add(matcher2.group("taskDescriptions"));
				details.add(matcher2.group("date"));
				details.add(matcher2.group("endTimeArguments"));
				return new AddCommand("deadline", details, getTagsFromArgs(matcher2.group("tagArguments")));

			} else if (matcher3.matches()) {
				details.add(matcher3.group("name"));
				details.add(matcher3.group("date"));
				details.add(matcher3.group("endTimeArguments"));
				return new AddCommand("deadline without task description", details,
						getTagsFromArgs(matcher3.group("tagArguments")));
				
			} else if (matcher4.matches()) {
				details.add(matcher4.group("name"));
				details.add(matcher4.group("date"));
				return new AddCommand("deadline without task description and time", details,
						getTagsFromArgs(matcher4.group("tagArguments")));
				
			} else if (matcher5.matches()) {
				details.add(matcher5.group("name"));
				details.add(matcher5.group("endTimeArguments"));
				return new AddCommand("deadline without task descriptions and date", details,
						getTagsFromArgs(matcher5.group("tagArguments")));

			} else if (matcher6.matches()) {
				details.add(matcher6.group("name"));
				details.add(matcher6.group("taskDescriptions"));
				details.add(matcher6.group("endTimeArguments"));
				return new AddCommand("deadline without date", details,
						getTagsFromArgs(matcher6.group("tagArguments")));

			} else if (matcher7.matches()) {
				details.add(matcher7.group("name"));
				details.add(matcher7.group("taskDescriptions"));
				details.add(matcher7.group("date"));
				return new AddCommand("deadline without time", details,
						getTagsFromArgs(matcher7.group("tagArguments")));

			} else if (matcher8.matches()) {
				details.add(matcher8.group("name"));
				details.add(matcher8.group("taskDescriptions"));
				return new AddCommand("floating task", details, getTagsFromArgs(matcher8.group("tagArguments")));

			} else {
				if (matcher9.group("name").toLowerCase().contains(byToday)) {
					details.add(matcher9.group("name").replaceAll("(?i)" + Pattern.quote(byToday), ""));
					details.add(dateFormatter.format(calendar.getTime()));
					return new AddCommand("deadline without task description and time", details,
							getTagsFromArgs(matcher9.group("tagArguments")));

				}

				else if (matcher9.group("name").toLowerCase().contains(byTomorrow)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					details.add(matcher9.group("name").replaceAll("(?i)" + Pattern.quote(byTomorrow), ""));
					details.add(dateFormatter.format(calendar.getTime()));
					return new AddCommand("deadline without task description and time", details,
							getTagsFromArgs(matcher9.group("tagArguments")));
					
				}

				else if (matcher9.group("name").toLowerCase().contains(byNextWeek)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.WEEK_OF_YEAR, 1);
					details.add(matcher9.group("name").replaceAll("(?i)" + Pattern.quote(byNextWeek), ""));
					details.add(dateFormatter.format(calendar.getTime()));
					return new AddCommand("deadline without task description and time", details,
							getTagsFromArgs(matcher9.group("tagArguments")));
					
				}

				else if (matcher9.group("name").toLowerCase().contains(byNextMonth)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.WEEK_OF_MONTH, 4);
					details.add(matcher9.group("name").replaceAll("(?i)" + Pattern.quote(byNextMonth), ""));
					details.add(dateFormatter.format(calendar.getTime()));
					return new AddCommand("deadline without task description and time", details,
							getTagsFromArgs(matcher9.group("tagArguments")));
				

				} else {
					details.add(matcher9.group("name"));
					return new AddCommand("floating task without task description", details,
							getTagsFromArgs(matcher9.group("tagArguments")));
				}
			}

		} catch (IllegalValueException ive) {
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
	private Command prepareDelete(String args) {

		Optional<Integer> index = parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}

		return new DeleteCommand(index.get());
	}

	private Command prepareList(String args) throws ParseException {
		if (args.equals("")) {
			return new ListCommand();
		}
		final Matcher matcherDate = KEYWORDS_DATE_FORMAT.matcher(args.trim());
		final Matcher matcherWord = KEYWORDS_NAME_FORMAT.matcher(args.trim());
		if (!matcherDate.matches() && !matcherWord.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		}
		if (matcherDate.matches()) {
			try {
				return new ListCommand(args, "date");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Calendar calendar = Calendar.getInstance();
			switch (matcherWord.group("keywords").toLowerCase()) {
			case tomorrow:
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				final String tomorrowKeyword = dateFormatter.format(calendar.getTime());
				return new ListCommand(tomorrowKeyword, "date");
			case nextWeek:
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.WEEK_OF_YEAR, 1);
				final String nextWeekKeyword = dateFormatter.format(calendar.getTime());
				return new ListCommand(nextWeekKeyword, "date");
			case done:
				return new ListCommand(done);
			case undone:
				return new ListCommand(undone);
			}
		}
		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
	}
	
	/**
	 * Parses arguments in the context of the edit task command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareEdit(String args) {

		final Matcher matcher = EDIT_FORMAT.matcher(args.trim());
		if (!matcher.matches())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

		String tempArgs = args.trim();

		String[] newArgs = tempArgs.split(" ", 2);
		if (newArgs.length <= 1)
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

		Optional<Integer> index = parseIndex(newArgs[0]);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		return new EditCommand(index.get(), newArgs[1].trim());
	}
	
	/**
	 * Parses arguments in the context of the set directory command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareSetDir(String args) {
		final Matcher resetMatcher = SET_DIR_FORMAT_RESET.matcher(args.trim());
		final Matcher pathMatcher = SET_DIR_FORMAT.matcher(args.trim());
		
		if (!resetMatcher.matches() && !pathMatcher.matches())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDirectoryCommand.MESSAGE_USAGE));
		
		if (resetMatcher.matches())
			return new SetDirectoryCommand(Config.ORIGINAL_TASK_PATH);
		
		return new SetDirectoryCommand(pathMatcher.group("filename") + ".xml");
	}

	/**
	 * Parses arguments in the context of the select person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
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
	 */
	private Command prepareFind(String args) {
		final Matcher matcherName = KEYWORDS_NAME_FORMAT.matcher(args.trim());
		final Matcher matcherDate = KEYWORDS_DATE_FORMAT.matcher(args.trim());
		if (!matcherName.matches() && !matcherDate.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
		}

		if (matcherDate.matches()) {
			final String keywords = matcherDate.group("dates");
			final Set<String> dateKeyword = new HashSet<>(Arrays.asList(keywords));
			return new FindCommand(dateKeyword, "date");
		} else { // keywords delimited by whitespace
			Calendar calendar = Calendar.getInstance();
			switch (matcherName.group("keywords").toLowerCase()) {
			case today:
				final String todayKeyword = dateFormatter.format(calendar.getTime());
				final Set<String> todayKeywords = new HashSet<>(Arrays.asList(todayKeyword));
				return new FindCommand(todayKeywords, "date");
			case tomorrow:
				calendar.setTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				final String tomorrowKeyword = dateFormatter.format(calendar.getTime());
				final Set<String> tomorrowKeywords = new HashSet<>(Arrays.asList(tomorrowKeyword));
				return new FindCommand(tomorrowKeywords, "date");
			}
			final String[] nameKeywords = matcherName.group("keywords").split("\\s+");
			final Set<String> nameKeyword = new HashSet<>(Arrays.asList(nameKeywords));
			return new FindCommand(nameKeyword, "name");
		}
	}

	/**
	 * Sets up done command to be executed
	 * 
	 * @param args
	 *            full command args string
	 * @return prepared doneCommand
	 */
	private Command prepareDone(String args) {
		Optional<Integer> index = parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}

		return new DoneCommand(index.get());
	}

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
		case "":
			return new HelpCommand(HelpCommand.COMMAND_WORD);
		case ExitCommand.COMMAND_WORD:
			return new HelpCommand(ExitCommand.COMMAND_WORD);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}
	}
}
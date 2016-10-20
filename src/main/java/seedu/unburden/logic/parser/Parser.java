package seedu.unburden.logic.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static seedu.unburden.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.unburden.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final Pattern KEYWORDS_NAME_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keyword separated by whitespace

	// private static final Pattern HELP_FORMAT = Pattern.compile("(?<help>\\S+(?:\\s+\\S+)*)"); 

	/*
	 * private static final Pattern KEYWORDS_DATE_FORMAT =
	 * Pattern.compile("(?<dates>\\S+([0-9]{2}[/][0-9]{2}[/][0-9]{4})*)");
	 */

	private static final Pattern KEYWORDS_DATE_FORMAT = Pattern.compile("(?<dates>[0-9]{2}[-][0-9]{2}[-][0-9]{4}$)");

	private static final Pattern ADD_FORMAT_0 = // '/' forward slashes are
												// reserved for delimiter
												// prefixes
			Pattern.compile("(?<name>[^/]+)" + "dd/(?<details>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)"
					+ "(?<isStartTimeArgumentsPrivate>p?)s/(?<startTimeArguments>[^/]+)"
					+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)"
					+ "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of
															// tags

	private static final Pattern ADD_FORMAT_1 = // '/' forward slashes are
												// reserved for delimiter
												// prefixes
			Pattern.compile("(?<name>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)"
					+ "(?<isStartTimeArgumentsPrivate>p?)s/(?<startTimeArguments>[^/]+)"
					+ "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)"
					+ "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of
															// tags

	private static final Pattern ADD_FORMAT_2 = Pattern
			.compile("(?<name>[^/]+)" + "(?<isDatePrivate>p?)d/(?<date>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	private static final Pattern ADD_FORMAT_3 = Pattern.compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

	private static final Pattern ADD_FORMAT_4 = Pattern.compile("(?<name>[^/]+)"
			+ "(?<isDatePrivate>p?)d/(?<date>[^/]+)" + "(?<isEndTimeArgumentsPrivate>p?)e/(?<endTimeArguments>[^/]+)"
			+ "(?<tagArguments>(?: t/[^/]+)*)");

	private static final Pattern EDIT_FORMAT = Pattern.compile("(?<index>[^/]+)(?!$)" + "(d/(?<date>[^/]+))?"
			+ "(s/(?<startTimeArguments>[^/]+))?" + "(e/(?<endTimeArguments>[^/]+))?");

	private static final String byToday = "by today";

	private static final String byTomorrow = "by tomorrow";

	private static final String byNextWeek = "by next week";

	private static final String byNextMonth = "by next month";

	private static final String today = "today";

	private static final String tomorrow = "tomorrow";

	private static final DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

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

		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			return new ListCommand();

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case HelpCommand.COMMAND_WORD:
			return prepareHelp(arguments);

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
		final Matcher matcher0 = ADD_FORMAT_0.matcher(args.trim());
		final Matcher matcher1 = ADD_FORMAT_1.matcher(args.trim());
		final Matcher matcher2 = ADD_FORMAT_2.matcher(args.trim());
		final Matcher matcher3 = ADD_FORMAT_3.matcher(args.trim());
		final Matcher matcher4 = ADD_FORMAT_4.matcher(args.trim());
		// Validate arg string format
		if (!matcher0.matches() & !matcher1.matches() & !matcher2.matches() & !matcher3.matches() & !matcher4.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
		try {
			if (matcher0.matches()) {
				return new AddCommand(matcher0.group("name"), matcher0.group("details"),
						matcher0.group("startTimeArguments"), matcher0.group("endTimeArguments"),
						getTagsFromArgs(matcher0.group("tagArguments")));
			}
			if (matcher1.matches()) {
				return new AddCommand(matcher1.group("name"), matcher1.group("date"),
						matcher1.group("startTimeArguments"), matcher1.group("endTimeArguments"),
						getTagsFromArgs(matcher1.group("tagArguments")));
			} else if (matcher2.matches()) {
				return new AddCommand(matcher2.group("name"), matcher2.group("date"),
						getTagsFromArgs(matcher2.group("tagArguments")));
			} else if (matcher4.matches()) {
				return new AddCommand(matcher4.group("name"), matcher4.group("date"),
						matcher4.group("endTimeArguments"), getTagsFromArgs(matcher4.group("tagArguments")));
			} else {
				if (matcher3.group("name").toLowerCase().contains(byToday)) {
					return new AddCommand(matcher3.group("name").replaceAll("(?i)" + Pattern.quote(byToday), ""),
							dateFormatter.format(calendar.getTime()), getTagsFromArgs(matcher3.group("tagArguments")));
				} else if (matcher3.group("name").toLowerCase().contains(byTomorrow)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					return new AddCommand(matcher3.group("name").replaceAll("(?i)" + Pattern.quote(byTomorrow), ""),
							dateFormatter.format(calendar.getTime()), getTagsFromArgs(matcher3.group("tagArguments")));
				} else if (matcher3.group("name").toLowerCase().contains(byNextWeek)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.WEEK_OF_YEAR, 1);
					return new AddCommand(matcher3.group("name").replaceAll("(?i)" + Pattern.quote(byNextWeek), ""),
							dateFormatter.format(calendar.getTime()), getTagsFromArgs(matcher3.group("tagArguments")));
				} else if (matcher3.group("name").toLowerCase().contains(byNextMonth)) {
					calendar.setTime(calendar.getTime());
					calendar.add(Calendar.WEEK_OF_MONTH, 4);
					return new AddCommand(matcher3.group("name").replaceAll("(?i)" + Pattern.quote(byNextMonth), ""),
							dateFormatter.format(calendar.getTime()), getTagsFromArgs(matcher3.group("tagArguments")));
				} else {
					return new AddCommand(matcher3.group("name"), getTagsFromArgs(matcher3.group("tagArguments")));
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

	private Command prepareEdit(String args) {

		final Matcher matcher = EDIT_FORMAT.matcher(args);
		if (!matcher.matches())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

		String tempArgs = args.trim();

		String[] newArgs = tempArgs.split(" ", 2);

		Optional<Integer> index = parseIndex(newArgs[0]);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		return new EditCommand(index.get(), newArgs[1].trim());
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
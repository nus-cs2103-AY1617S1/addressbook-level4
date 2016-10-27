package seedu.flexitrack.logic.parser;

import seedu.flexitrack.logic.commands.*;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.DateTimeInfoParser;
import seedu.flexitrack.commons.util.StringUtil;
import seedu.flexitrack.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 
    private static final HashMap<String, String> SHORTCUT_MAP = new HashMap<String, String>();                                                                                                       // more
                                                                                                           
    static {
        SHORTCUT_MAP.put(AddCommand.COMMAND_SHORTCUT, AddCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(ClearCommand.COMMAND_SHORTCUT, ClearCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(DeleteCommand.COMMAND_SHORTCUT, DeleteCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(EditCommand.COMMAND_SHORTCUT, EditCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(ExitCommand.COMMAND_SHORTCUT, ExitCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(HelpCommand.COMMAND_SHORTCUT, HelpCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(FindCommand.COMMAND_SHORTCUT, FindCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(ListCommand.COMMAND_SHORTCUT, ListCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(MarkCommand.COMMAND_SHORTCUT, MarkCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(UnmarkCommand.COMMAND_SHORTCUT, UnmarkCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(SelectCommand.COMMAND_SHORTCUT, SelectCommand.COMMAND_WORD);
        SHORTCUT_MAP.put(BlockCommand.COMMAND_SHORTCUT, BlockCommand.COMMAND_WORD);
    }                                                                                                      
    private static final Pattern TASK_EVENT_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "from/(?<startTime>[^/]+)" + "to/(?<endTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern TASK_DEADLINE_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "by/(?<dueDate>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern TASK_FLOATING_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern EDIT_COMMAND_FORMAT = Pattern.compile("(?<index>[0-9]+)(?<arguments>.*)");

    private static final Pattern EDIT_ARGS_NAME = Pattern.compile("n/\\s*(?<name>.+)");
    private static final Pattern EDIT_ARGS_DUEDATE = Pattern.compile("by/\\s*(?<dueDate>[^/]+)");
    private static final Pattern EDIT_ARGS_STARTTIME = Pattern.compile("from/\\s*(?<startTime>[^/]+)");
    private static final Pattern EDIT_ARGS_ENDTIME = Pattern.compile("to/\\s*(?<endTime>[^/]+)");

    public final static String EMPTY_TIME_INFO = "Feb 29 2000 00:00:00";

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
        final String parsedCommandWord = parseCommandWord(commandWord);
        
        final String arguments = matcher.group("arguments");
        switch (parsedCommandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case BlockCommand.COMMAND_WORD:
            return prepareBlock(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return prepareUnmark(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(arguments.trim());

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
        
        private String parseCommandWord(String commandWord) {       
            return SHORTCUT_MAP.getOrDefault(commandWord, commandWord);
        }

        private Command prepareList(String arguments) {
            arguments=arguments.trim();
            try {
                if (isValideListFormat(arguments)) {
                    return new ListCommand(arguments);
                }
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        
        private Command prepareBlock(String args) {
            final Matcher matcherEvent = TASK_EVENT_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());

            // Validate arg string format
            try {
                if (matcherEvent.matches()) {
                    return new BlockCommand("(Blocked) " + matcherEvent.group("name"), EMPTY_TIME_INFO, matcherEvent.group("startTime"),
                            matcherEvent.group("endTime"), getTagsFromArgs(matcherEvent.group("tagArguments")));
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockCommand.MESSAGE_USAGE));
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }

    /**
     * @param arguments
     * @return
     * @throws IllegalValueException 
     */
    private boolean isValideListFormat(String arguments) throws IllegalValueException {
        String dateInfo = (arguments.replace(ListCommand.LIST_FUTURE_COMMAND, "").replace(ListCommand.LIST_PAST_COMMAND, "").
        replace(ListCommand.LIST_UNMARK_COMMAND, "").replace(ListCommand.LIST_MARK_COMMAND, "").
        replace(ListCommand.LIST_LAST_MONTH_COMMAND, "").replace(ListCommand.LIST_LAST_WEEK_COMMAND, "").
        replace(ListCommand.LIST_NEXT_MONTH_COMMAND, "").replace(ListCommand.LIST_NEXT_WEEK_COMMAND, "").
        replace(ListCommand.LIST_BLOCK_COMMAND, "").trim());
        if ( !dateInfo.equals("") ){
            DateTimeInfoParser timeArgs = new DateTimeInfoParser(dateInfo);
        }
        return (arguments.contains(ListCommand.LIST_FUTURE_COMMAND) || arguments.contains(ListCommand.LIST_UNMARK_COMMAND)
                || arguments.contains(ListCommand.LIST_PAST_COMMAND) || arguments.contains(ListCommand.LIST_MARK_COMMAND)
                || arguments.contains(ListCommand.LIST_UNSPECIFIED_COMMAND) || arguments.contains(ListCommand.LIST_LAST_WEEK_COMMAND) 
                || arguments.contains(ListCommand.LIST_LAST_MONTH_COMMAND) || arguments.contains(ListCommand.LIST_NEXT_MONTH_COMMAND) 
                || arguments.contains(ListCommand.LIST_NEXT_WEEK_COMMAND) || arguments.contains(ListCommand.LIST_BLOCK_COMMAND));
    }

    private Command prepareEdit(String arguments) {

        int index;
        String args;
        String[] passing = new String[4];

        final Matcher matcherEdit = EDIT_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcherEdit.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } else {
            index = Integer.parseInt(matcherEdit.group("index"));
            args = matcherEdit.group("arguments");
        }

        final Matcher matcherName = EDIT_ARGS_NAME.matcher(args.trim());
        final Matcher matcherDueDate = EDIT_ARGS_DUEDATE.matcher(args.trim());
        final Matcher matcherStartTime = EDIT_ARGS_STARTTIME.matcher(args.trim());
        final Matcher matcherEndTime = EDIT_ARGS_ENDTIME.matcher(args.trim());

        boolean namePresent = matcherName.find();
        boolean dueDatePresent = matcherDueDate.find();
        boolean startTimePresent = matcherStartTime.find();
        boolean endTimePresent = matcherEndTime.find();

        if (!namePresent && !dueDatePresent && !startTimePresent && !endTimePresent) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        if (namePresent) {
            passing[0] = matcherName.group("name");
        } else {
            passing[0] = null;
        }

        if (dueDatePresent) {
            if (startTimePresent || endTimePresent) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            } else {
                passing[1] = matcherDueDate.group("dueDate");
            }
        } else {
            passing[1] = null;
        }

        if (startTimePresent) {
            passing[2] = matcherStartTime.group("startTime");
        } else {
            passing[2] = null;
        }

        if (endTimePresent) {
            passing[3] = matcherEndTime.group("endTime");
        } else {
            passing[3] = null;
        }

        return new EditCommand(index, passing);
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareUnmark(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        return new UnmarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcherEvent = TASK_EVENT_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherDeadline = TASK_DEADLINE_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherFloating = TASK_FLOATING_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        try {
            if (matcherEvent.matches()) {
                return addEventTask(matcherEvent);
            } else if (matcherDeadline.matches()) {
                return addDeadlineTask(matcherDeadline);
            } else if (matcherFloating.matches()) {
                return addFloatingTask(matcherFloating);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private AddCommand addFloatingTask(Matcher matcher) throws IllegalValueException {
        return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, EMPTY_TIME_INFO, EMPTY_TIME_INFO,
                getTagsFromArgs(matcher.group("tagArguments")));
    }

    private AddCommand addDeadlineTask(Matcher matcher) throws IllegalValueException {
        return new AddCommand(matcher.group("name"), matcher.group("dueDate"), EMPTY_TIME_INFO, EMPTY_TIME_INFO,
                getTagsFromArgs(matcher.group("tagArguments")));
    }

    private AddCommand addEventTask(Matcher matcher) throws IllegalValueException {
        return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, matcher.group("startTime"),
                matcher.group("endTime"), getTagsFromArgs(matcher.group("tagArguments")));
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
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
     * Parses arguments in the context of the delete task command.
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

    /**
     * Parses arguments in the context of the select task command.
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
     * Parses arguments in the context of the find task command.
     *
     * @param args (full command args string)
     * @return the prepared command
     */
    public static Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (matcher.group("keywords").contains("f/")) {
            return new FindCommand(matcher.group("keywords").trim());
        }

        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        return new FindCommand(keywordSet);
    }

}
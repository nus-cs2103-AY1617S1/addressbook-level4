package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.logic.commands.BlockCommand;
import seedu.address.logic.commands.ChangeDirectoryCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.TaskDate;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern FIND_ARGS_WITHOUT_DATE_FORMAT = Pattern
            .compile("(?<keywords>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern FIND_ARGS_WITH_DATE_FORMAT = Pattern
            .compile("(?<keywords>[^/]+)" + "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
                    + "(?<deadline>(?: by [^/]+)))" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern FIND_ARGS_WITHOUT_KEYWORD_FORMAT = Pattern
            .compile("((?<startTime>(?:from [^/]+)(?<endTime>(?: to [^/]+)))|" + "(?<deadline>(?:by [^/]+)))"
                    + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern FIND_ARGS_WITH_TAG_FORMAT = Pattern.compile("(?<tagArguments>(?:t/[^/]+)*)");

    private static final Pattern EDIT_ARGS_WITHOUT_DATE_FORMAT = Pattern
            .compile("(?<targetIndex>[\\d]+)" + "(?<name> [^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern EDIT_ARGS_WITH_DATE_FORMAT = Pattern.compile(
            "(?<targetIndex>[\\d]+)" + "(?<name> [^/]+)" + "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
                    + "(?<deadline>(?: by [^/]+)))" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern EDIT_ARGS_WITHOUT_NAME_FORMAT = Pattern
            .compile("(?<targetIndex>[\\d]+)" + "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
                    + "(?<deadline>(?: by [^/]+)))" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern EDIT_ARGS_WITH_TAG_FORMAT = Pattern
            .compile("(?<targetIndex>[\\d]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = // '/' forward
                                                                  // slashes are
                                                                  // reserved
                                                                  // for
                                                                  // delimiter
                                                                  // prefixes
            Pattern.compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable
                                                                                  // number
                                                                                  // of
                                                                                  // tags

    private static final Pattern NON_FLOATING_TASK_DATA_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]+)" + "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
                    + "(?<deadline>(?: by [^/]+)))" + "(?<tagArguments>(?: t/[^ ]+)*)"); // variable
                                                                                         // number
                                                                                         // of
                                                                                         // tags

    private static final Pattern RECURRING_TASK_DATA_ARGS_FORMAT = Pattern
            .compile("(?<recurring>\\b(?i)daily|weekly|monthly|yearly|none(?i)\\b)");

    private static final Pattern BLOCK_DATA_ARGS_FORMAT = // '/' forward slashes
                                                          // are reserved for
                                                          // delimiter prefixes
            Pattern.compile("(?<startTime>(?:from [^/]+)(?<endTime>(?: to [^/]+)))" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable
                                                                                                                         // number
                                                                                                                         // of
                                                                                                                         // tags

    private static final int DEADLINE_INDEX = 0;

    private static final int START_TIME_INDEX = 0;

    private static final int END_TIME_INDEX = 1;

    private static final int ONLY_DEADLINE = 1;

    private static final int TIME_PERIOD = 2;

    // For find/edit matcher
    private static Date startTime;

    private static Date endTime;

    private static Date deadline;

    private static String taskName;

    private static Set<String> keywordSet;

    private static Set<String> tagSet;

    private static RecurringType recurringType;

    private static int targetIndex;

    private static final com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();

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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case BlockCommand.COMMAND_WORD:
            return prepareBlock(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case ChangeDirectoryCommand.COMMAND_WORD:
            return new ChangeDirectoryCommand(arguments.trim());

        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);

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
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ViewCommand.COMMAND_WORD:
            return prepareView(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcherNonFloating = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcherNonFloating.matches()) {
            return prepareAddFloating(args);
        }
        return prepareAddNonFloating(args);
    }

    /**
     * Parses arguments in the context of adding a floating task
     * 
     * @param args
     *            full command args string
     * @return the prepared add floating command
     */
    private Command prepareAddFloating(String args) {
        final Matcher matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFloatingCommand.MESSAGE_USAGE));
        }
        try {
            return new AddFloatingCommand(matcher.group("name"), getTagsFromArgs(matcher.group("tagArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of adding a non floating task
     * 
     * @param args
     *            full command args string
     * @return the prepared add non floating command
     */
    private Command prepareAddNonFloating(String args) {
        final Matcher matcher = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        matcher.matches();

        if (matcher.group("deadline") != null) {
            return prepareAddNonFloatingByDate(matcher);
        } else {
            return prepareAddNonFloatingFromDateToDate(matcher);
        }

    }

    private RecurringType checkForRecurringTask(String args) throws IllegalArgumentException {
        final Matcher matcher = RECURRING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        RecurringType recurringType = RecurringType.IGNORED;
        if (!matcher.find()) {
            return recurringType;
        } else {

            recurringType = extractRecurringInfo(matcher.group("recurring"));

        }
        return recurringType;
    }

    /**
     * Prepares arguments in the context of adding a non floating task by date
     * only
     * 
     * @param matcher
     *            Contains the information we need
     * @param recurringType
     * @return the prepared add non floating command
     * @throws IllegalValueException
     *             Signals for incorrect command
     */
    private Command prepareAddNonFloatingByDate(Matcher matcher) {
        String endInput = matcher.group("deadline");
        RecurringType recurringType = checkForRecurringTask(endInput);
        if (recurringType == RecurringType.IGNORED) {
            recurringType = RecurringType.NONE;
        }
        try {
            return new AddNonFloatingCommand(matcher.group("name"), getTagsFromArgs(matcher.group("tagArguments")),
                    new TaskDate(TaskDate.DATE_NOT_PRESENT), new TaskDate(getDateFromString(endInput).getTime()),
                    recurringType);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(iae.getMessage());
        }
    }

    /**
     * Prepares arguments in the context of adding a non floating task from date
     * to date
     * 
     * @param matcher
     *            Contains the information we need
     * @param recurringType
     * @return the prepared add non floating command
     * @throws IllegalValueException
     *             Signals for incorrect command
     */
    private Command prepareAddNonFloatingFromDateToDate(Matcher matcher) {
        String startInput = matcher.group("startTime");
        String endInput = matcher.group("endTime");
        RecurringType recurringType = checkForRecurringTask(endInput);
        if (recurringType == RecurringType.IGNORED) {
            recurringType = RecurringType.NONE;
        }
        try {
            return new AddNonFloatingCommand(matcher.group("name"), getTagsFromArgs(matcher.group("tagArguments")),
                    new TaskDate(getDateFromString(startInput).getTime()),
                    new TaskDate(getDateFromString(endInput).getTime()), recurringType);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(iae.getMessage());
        }
    }

    // @@author A0147967J
    private Command prepareBlock(String args) {
        Matcher matcher = BLOCK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockCommand.MESSAGE_USAGE));
        }
        try {

            String startInput = matcher.group("startTime");
            String endInput = matcher.group("endTime");

            return new BlockCommand(getTagsFromArgs(matcher.group("tagArguments")),
                    new TaskDate(getDateFromString(startInput).getTime()),
                    new TaskDate(getDateFromString(endInput).getTime()));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    // @@author

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

    // @@author A0147967J
    /**
     * Parses arguments in the context of the complete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new CompleteCommand(index.get());
    }
    // @@author

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

    // @@author A0147995H
    /**
     * Initialize all the global variables to its initial value
     */
    private void initializeFindElements() {
        keywordSet = new HashSet<String>();
        startTime = null;
        endTime = null;
        deadline = null;
        tagSet = new HashSet<String>();
    }

    /**
     * Returns correct resulting command if the command is in the format of Find
     * TASK_NAME from DATE to DATE | by DATE t/TAG
     * 
     * @param m
     * @return the corresponding find command
     */
    private Command matchFindDateMatcher(Matcher m) {
        String[] keywords = m.group("keywords").split("\\s+");
        keywordSet = new HashSet<>(Arrays.asList(keywords));
        try {
            ArrayList<Date> dateSet = extractDateInfo(m);
            if (dateSet.size() == ONLY_DEADLINE) {
                deadline = dateSet.get(DEADLINE_INDEX);
            } else if (dateSet.size() == TIME_PERIOD) {
                startTime = dateSet.get(START_TIME_INDEX);
                endTime = dateSet.get(END_TIME_INDEX);
            }
            tagSet = getTagsFromArgs(m.group("tagArguments"));
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(iae.getMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new FindCommand(keywordSet, startTime, endTime, deadline, tagSet);
    }

    /**
     * Returns correct resulting command if the command is in the format of Find
     * from DATE to DATE | by DATE t/TAG
     * 
     * @param m
     * @return the corresponding find command
     */
    private Command matchFindNoKeywordMatcher(Matcher m) {
        try {
            ArrayList<Date> dateSet = extractDateInfo(m);
            if (dateSet.size() == ONLY_DEADLINE) {
                deadline = dateSet.get(DEADLINE_INDEX);
            } else if (dateSet.size() == TIME_PERIOD) {
                startTime = dateSet.get(START_TIME_INDEX);
                endTime = dateSet.get(END_TIME_INDEX);
            }
            tagSet = getTagsFromArgs(m.group("tagArguments"));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new FindCommand(keywordSet, startTime, endTime, deadline, tagSet);
    }

    /**
     * Returns correct resulting command if the command is in the format of Find
     * t/TAG
     * 
     * @param m
     * @return the corresponding find command
     */
    private Command matchFindTagMatcher(Matcher m) {
        final Collection<String> tagStrings = Arrays
                .asList(m.group("tagArguments").replaceFirst("t/", "").split(" t/"));
        tagSet = new HashSet<>(tagStrings);
        return new FindCommand(keywordSet, startTime, endTime, deadline, tagSet);
    }

    /**
     * Returns correct resulting command if the command is in the format of Find
     * TASK_NAME t/TAG
     * 
     * @param m
     * @return the corresponding find command
     */
    private Command matchFindNoDateMatcher(Matcher m) {
        String[] keywords = m.group("keywords").split("\\s+");
        keywordSet = new HashSet<>(Arrays.asList(keywords));
        try {
            tagSet = getTagsFromArgs(m.group("tagArguments"));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new FindCommand(keywordSet, startTime, endTime, deadline, tagSet);
    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        if (args == null || args.length() == 0)
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        final Matcher noDateMatcher = FIND_ARGS_WITHOUT_DATE_FORMAT.matcher(args.trim());
        final Matcher dateMatcher = FIND_ARGS_WITH_DATE_FORMAT.matcher(args.trim());
        final Matcher tagMatcher = FIND_ARGS_WITH_TAG_FORMAT.matcher(args.trim());
        final Matcher noKeywordMatcher = FIND_ARGS_WITHOUT_KEYWORD_FORMAT.matcher(args.trim());

        initializeFindElements();

        boolean dateMatcherMatches = dateMatcher.matches();
        boolean noDateMatcherMatches = noDateMatcher.matches();
        boolean tagMatcherMatches = tagMatcher.matches();
        boolean noKeywordMatcherMathces = noKeywordMatcher.matches();

        if (dateMatcherMatches) {
            return matchFindDateMatcher(dateMatcher);
        } else if (noKeywordMatcherMathces) {
            return matchFindNoKeywordMatcher(noKeywordMatcher);
        } else if (tagMatcherMatches) {
            return matchFindTagMatcher(tagMatcher);
        } else if (noDateMatcherMatches) {
            return matchFindNoDateMatcher(noDateMatcher);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Initialize all the global variables to its initial value
     */
    private void initializeEditElements() {
        taskName = "";
        startTime = null;
        endTime = null;
        tagSet = new HashSet<String>();
        recurringType = RecurringType.NONE;
        targetIndex = -1;
    }

    /**
     * Returns correct resulting command if the command is in the format of Edit
     * TARGET_INDEX TASK_NAME from DATE to DATE | by DATE RECURRING_TYPE t/TAG
     * 
     * @param m
     * @return corresponding edit command
     */
    private Command matchEditDateMatcher(Matcher m) {
        targetIndex = Integer.parseInt(m.group("targetIndex"));
        taskName = m.group("name").replaceFirst("\\s", "");

        try {
            ArrayList<Date> dateSet = extractDateInfo(m);
            if (dateSet.size() == ONLY_DEADLINE) {
                endTime = dateSet.get(DEADLINE_INDEX);
                recurringType = checkForRecurringTask(m.group("deadline"));
            } else if (dateSet.size() == TIME_PERIOD) {
                startTime = dateSet.get(START_TIME_INDEX);
                endTime = dateSet.get(END_TIME_INDEX);
                recurringType = checkForRecurringTask(m.group("startTime"));
            }

            tagSet = getTagsFromArgs(m.group("tagArguments"));
            return new EditCommand(targetIndex, taskName, tagSet, startTime, endTime, recurringType);
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(iae.getMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Returns correct resulting command if the command is in the format of Edit
     * TARGET_INDEX from DATE to DATE | by DATE RECURRING_TYPE t/TAG
     * 
     * @param m
     * @return corresponding edit command
     */
    private Command matchEditNoNameMatcher(Matcher m) {
        targetIndex = Integer.parseInt(m.group("targetIndex"));

        try {
            ArrayList<Date> dateSet = extractDateInfo(m);
            if (dateSet.size() == ONLY_DEADLINE) {
                endTime = dateSet.get(DEADLINE_INDEX);
                recurringType = checkForRecurringTask(m.group("deadline"));
            } else if (dateSet.size() == TIME_PERIOD) {
                startTime = dateSet.get(START_TIME_INDEX);
                endTime = dateSet.get(END_TIME_INDEX);
                recurringType = checkForRecurringTask(m.group("startTime"));
            }

            tagSet = getTagsFromArgs(m.group("tagArguments"));
            return new EditCommand(targetIndex, taskName, tagSet, startTime, endTime, recurringType);
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(iae.getMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Returns correct resulting command if the command is in the format of Edit
     * TARGET_INDEX from DATE to DATE | by DATE RECURRING_TYPE t/TAG
     * 
     * @param m
     * @return corresponding edit command
     */
    private Command matchEditTagMatcher(Matcher m) {
        targetIndex = Integer.parseInt(m.group("targetIndex"));
        try {
            tagSet = getTagsFromArgs(m.group("tagArguments"));
            return new EditCommand(targetIndex, taskName, tagSet, startTime, endTime, recurringType);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Returns correct resulting command if the command is in the format of Edit
     * TARGET_INDEX t/TAG
     * 
     * @param m
     * @return corresponding edit command
     */
    private Command matchEditNoDateMatcher(Matcher m) {
        targetIndex = Integer.parseInt(m.group("targetIndex"));
        taskName = m.group("name").replaceFirst("\\s", "");
        // -------Parts for detecting recurring information-----------------
        String[] words = taskName.split(" ");
        String lastWord = words[words.length - 1];
        recurringType = checkForRecurringTask(lastWord);
        if (recurringType != RecurringType.IGNORED) {
            taskName = (words.length == 1) ? "" : taskName.substring(0, taskName.length() - lastWord.length());
        }
        // -----------------------------------------------------------------
        try {
            tagSet = getTagsFromArgs(m.group("tagArguments"));
            return new EditCommand(targetIndex, taskName, tagSet, startTime, endTime, recurringType);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Returns different edit command according to the regex.
     * 
     * @param args
     * @return corresponding edit command
     */
    private Command prepareEdit(String args) {
        if (args == null || args.length() == 0)
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        final Matcher noDateMatcher = EDIT_ARGS_WITHOUT_DATE_FORMAT.matcher(args.trim());
        final Matcher dateMatcher = EDIT_ARGS_WITH_DATE_FORMAT.matcher(args.trim());
        final Matcher tagMatcher = EDIT_ARGS_WITH_TAG_FORMAT.matcher(args.trim());
        final Matcher noNameMatcher = EDIT_ARGS_WITHOUT_NAME_FORMAT.matcher(args.trim());

        initializeEditElements();

        boolean dateMatcherMatches = dateMatcher.matches();
        boolean noDateMatcherMatches = noDateMatcher.matches();
        boolean tagMatcherMatches = tagMatcher.matches();
        boolean noNameMatcherMathces = noNameMatcher.matches();

        if (dateMatcherMatches) {
            return matchEditDateMatcher(dateMatcher);
        } else if (noNameMatcherMathces) {
            return matchEditNoNameMatcher(noNameMatcher);
        } else if (tagMatcherMatches) {
            return matchEditTagMatcher(tagMatcher);
        } else if (noDateMatcherMatches) {
            return matchEditNoDateMatcher(noDateMatcher);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }
    // @@author

    // @@author A0147967J
    /**
     * Returns the view command with input date parsed
     * 
     * @param arguments
     *            passed by user
     * @return prepared view command
     */
    private Command prepareView(String arguments) {
        // TODO Auto-generated method stub
        Date date;
        try {
            date = getDateFromString(arguments);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
        return new ViewCommand(new TaskDate(date));
    }
    // @@author

    // @@author A0147995H
    public static ArrayList<Date> extractDateInfo(Matcher m) throws IllegalValueException{
        ArrayList<Date> resultSet = new ArrayList<Date>();
        try {
            String[] time = m.group("startTime").replace(" from ", "").split(" to ");
            resultSet.clear();
            try {
                resultSet.add(getDateFromString(time[START_TIME_INDEX]));
                resultSet.add(getDateFromString(time[END_TIME_INDEX]));
            } catch (IllegalValueException e) {              
                throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
            }
        } catch (Exception ise) {
            resultSet.clear();
            try {
                resultSet.add(getDateFromString(m.group("deadline").replace(" by ", "")));
            } catch (Exception e) {
                throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
            }
        }
        return resultSet;
    }
    // @@author

    /**
     * Parses through the dateInput and provides the Date from that input
     * 
     * @param dateInput
     *            The date that we want to convert from string to Date
     * @return A single Date from the string
     * @throws IllegalValueException 
     */
    public static Date getDateFromString(String dateInput) throws IllegalValueException {
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        try {
            return dateGroups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
        }
    }

    private static RecurringType extractRecurringInfo(String recurringInfo) throws IllegalArgumentException {
        recurringInfo = recurringInfo.toUpperCase().trim();
        RecurringDateParser recurringParser = RecurringDateParser.getInstance();
        return recurringParser.getRecurringType(recurringInfo);
    }

}

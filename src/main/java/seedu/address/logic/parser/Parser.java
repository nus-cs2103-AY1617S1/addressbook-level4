package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ArgumentTokenizer.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                           // or
                                                                                                           // more
                                                                                                           // keywords
                                                                                                           // separated
                                                                                                           // by
                                                                                                           // whitespace

    private static final Pattern TASK_NAME_ARGS_FORMAT = Pattern.compile("[\\p{Alnum} ]+");

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes
                                                         // are reserved for
                                                         // delimiter prefixes
            Pattern.compile("(?<name>[^/]+)" + "(?<deadline>(?: d/[^/]+)?)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable
                                                                                                                 // number
                                                                                                                 // of
                                                                                                                 // tags

    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes
                                                          // are reserved for
                                                          // delimiter prefixes
            Pattern.compile("(?<name>[^/]+)" + "s/(?<startDate>[^/]+)" + "e/(?<endDate>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of
                                                         // tags

    public static final Prefix deadlinePrefix = new Prefix("d/");
    public static final Prefix tagPrefix = new Prefix("t/");
    public static final Prefix startDatePrefix = new Prefix("s/");
    public static final Prefix endDatePrefix = new Prefix("e/");
    public static final Prefix namePrefix = new Prefix("n/");
    public static final Prefix recurringPrefix = new Prefix("r/");

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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            // return new ListCommand();
            // System.out.println(arguments);
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DoneCommand.COMMAND_WORD:
            return prepareMarkAsDone(arguments);
            
        case RefreshCommand.COMMAND_WORD:
            return new RefreshCommand();

        case ChangeCommand.COMMAND_WORD:
            return prepareChange(arguments);

        case FilterCommand.COMMAND_WORD:
            return prepareFilter(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case UndoChangeCommand.COMMAND_WORD:
            return new UndoChangeCommand(arguments);
            
        case RedoChangeCommand.COMMAND_WORD:
            return new RedoChangeCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command prepareMarkAsDone(String args) {
        Optional<Integer> index = parseIndex(args);
        String name = args;
        if (!index.isPresent()) {
            if (name == null || name.equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new DoneCommand(keywordSet);
        }
        return new DoneCommand(index.get());
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, namePrefix, tagPrefix, startDatePrefix,
                endDatePrefix, recurringPrefix);
        argsTokenizer.tokenize(args);
        try {
            if (argsTokenizer.getTokenizedArguments().containsKey(namePrefix)) {
                
                   if (!argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)
                            && !argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
                        // non-recurring task
                        return new AddCommand(argsTokenizer.getValue(namePrefix).get(), "",
                                toSet(argsTokenizer.getAllValues(tagPrefix)), "");}
                // check if task is recurring floating task
                if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)
                        && argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix)) {
                    return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                            argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                            argsTokenizer.getValue(recurringPrefix).get());
                    // non-recurring floating task
                } else if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
                    return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                            argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                            "");
                } 
                 else if (argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)
                        && argsTokenizer.getTokenizedArguments().containsKey(endDatePrefix)) {
                    if (!argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix))
                        // non-recurring event
                        return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                                argsTokenizer.getValue(startDatePrefix).get(),
                                argsTokenizer.getValue(endDatePrefix).get(),
                                toSet(argsTokenizer.getAllValues(tagPrefix)), "");
                    else// recurring event
                        return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                                argsTokenizer.getValue(startDatePrefix).get(),
                                argsTokenizer.getValue(endDatePrefix).get(),
                                toSet(argsTokenizer.getAllValues(tagPrefix)),
                                argsTokenizer.getValue(recurringPrefix).get());
                }
            }

            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    /**
     * Extracts the new task's deadline from the add command's deadline argument
     * string. Merges duplicate tag strings.
     */
    private static String getDeadlineFromArg(String deadlineArgument) throws IllegalValueException {
        // no deadline
        if (deadlineArgument.isEmpty()) {
            return "";
        }
        return deadlineArgument.replace(" d/", "");
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

    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
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
        String name = args;
        if (!index.isPresent()) {
            if (name == null || name.equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            return new DeleteCommand(args, KEYWORDS_ARGS_FORMAT);
        }
        return new DeleteCommand(index.get());

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
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
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
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<String> parseString(String command) {
        final Matcher matcher = TASK_NAME_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String name = matcher.group("targetName");
        return Optional.of(name);

    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Group keywords by AND operator
        final String[] keywords = matcher.group("keywords").split("AND");
        // keywords delimited by whitespace
        final Set<Set<String>> keywordsGroup = new HashSet<Set<String>>();
        for (String keyword: keywords) {
            keywordsGroup.add(new HashSet<>(Arrays.asList(keyword.trim().split("\\s+"))));
        }
        return new FindCommand(keywordsGroup, matcher.group("keywords").contains("exact!"));
    }

    private Command prepareList(String args) {
        if (args.equals(""))
            return new ListCommand();
        return new ListCommand(args);
    }

    /**
     * Parses arguments in the context of the change storage location command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareChange(String arguments) {
        final String[] args = arguments.trim().split("\\s+");
        if (args.length >= 0) {
            String filePath = args[0];
            if (args.length == 1) {
                return new ChangeCommand(filePath);
            } else if (args.length == 2) {
                String clear = args[1];
                return new ChangeCommand(filePath, clear);
            }
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments in the context of the filter attributes command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFilter(String arguments) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, startDatePrefix, endDatePrefix,
                recurringPrefix, tagPrefix);
        argsTokenizer.tokenize(arguments);
        Optional<String> deadline = argsTokenizer.getValue(deadlinePrefix);
        Optional<String> startDate = argsTokenizer.getValue(startDatePrefix);
        Optional<String> endDate = argsTokenizer.getValue(endDatePrefix);
        Optional<String> recurring = argsTokenizer.getValue(recurringPrefix);
        Optional<List<String>> tags = argsTokenizer.getAllValues(tagPrefix);
        if (deadline.isPresent() || startDate.isPresent() || endDate.isPresent() 
                || recurring.isPresent() || tags.isPresent()) {
           return new FilterCommand(deadline, startDate, endDate, recurring, toSet(tags));
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    private Command prepareEdit(String args) {
        final Matcher taskMatcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format if it is a valid add task command
        try {
            if (taskMatcher.matches()) {
                return new AddCommand(taskMatcher.group("name"), getDeadlineFromArg(taskMatcher.group("deadline")),
                        getTagsFromArgs(taskMatcher.group("tagArguments")), "");
            }
            final Matcher eventMatcher = EVENT_DATA_ARGS_FORMAT.matcher(args.trim());
            // Validate arg string format if it is a valid add event command
            if (eventMatcher.matches()) {
                return new AddCommand(eventMatcher.group("name"), eventMatcher.group("startDate"),
                        eventMatcher.group("endDate"), getTagsFromArgs(eventMatcher.group("tagArguments")), "");
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

}
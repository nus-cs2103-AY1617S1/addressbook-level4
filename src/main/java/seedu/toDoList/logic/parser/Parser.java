package seedu.toDoList.logic.parser;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.exceptions.MissingRecurringDateException;
import seedu.toDoList.commons.util.StringUtil;
import seedu.toDoList.logic.commands.*;
import seedu.toDoList.logic.parser.ArgumentTokenizer.*;
import seedu.toDoList.model.task.Recurring;

import static seedu.toDoList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.toDoList.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                           // or
                                                                                                           // more
                                                                                                           // keywords
                                                                                                           // separated
                                                                                                           // by
                                                                                                           // whitespace

    // @@author A0138717X
    private static final Pattern EDIT_FORMAT = Pattern
            .compile("(?<name>[^/]+)" + "(?<edit>(?: [dsenrp]/[^/]+)?)" + "((i/(?<index>([0-9])+)*)?)");

    private static final String MESSAGE_INVALID_DATE = "Date format entered is invalid";

    public static final String EDIT_TYPE_NAME = "name";
    public static final String EDIT_TYPE_PRIORITY = "priority";
    public static final String EDIT_TYPE_RECURRING = "recurring";
    public static final String EDIT_TYPE_START_DATE = "startDate";
    public static final String EDIT_TYPE_END_DATE = "endDate";
    public static final String EDIT_TYPE_DEADLINE = "deadline";

    public static final Prefix priorityPrefix = new Prefix("p/");

    // @@author A0142325R
    public static final Prefix deadlinePrefix = new Prefix("d/");
    public static final Prefix tagPrefix = new Prefix("t/");
    public static final Prefix startDatePrefix = new Prefix("s/");
    public static final Prefix endDatePrefix = new Prefix("e/");
    public static final Prefix namePrefix = new Prefix("n/");
    public static final Prefix recurringPrefix = new Prefix("r/");
    // @@author

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

        case JumpToDeadlineCommand.COMMAND_WORD:
            return new JumpToDeadlineCommand();

        case JumpToStartDateCommand.COMMAND_WORD:
            return new JumpToStartDateCommand();

        case JumpToEndDateCommand.COMMAND_WORD:
            return new JumpToEndDateCommand();

        case JumpToRecurringCommand.COMMAND_WORD:
            return new JumpToRecurringCommand();

        case JumpToPriorityCommand.COMMAND_WORD:
            return new JumpToPriorityCommand();

        case JumpToTagCommand.COMMAND_WORD:
            return new JumpToTagCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    // @@author A0142325R

    /**
     * prepare to mark a task as done
     *
     * @param args
     * @return
     */

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
        } else {
            return new DoneCommand(index.get());
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
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, namePrefix, tagPrefix, startDatePrefix,
                endDatePrefix, recurringPrefix, priorityPrefix);
        argsTokenizer.tokenize(args);
        if (!argsTokenizer.isPresent(namePrefix)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            if (argsTokenizer.isFloatingTask(startDatePrefix, endDatePrefix, deadlinePrefix)) {
                return prepareAddFloatingTaskCommand(argsTokenizer);
            } else if (argsTokenizer.isDeadlineTask(startDatePrefix, endDatePrefix, deadlinePrefix)) {
                return prepareAddDeadlineTaskCommand(argsTokenizer);
            } else if (argsTokenizer.isEvent(startDatePrefix, endDatePrefix, deadlinePrefix)) {
                return prepareAddEventCommand(argsTokenizer);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (MissingRecurringDateException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * prepare to add a deadline task
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddDeadlineTaskCommand(ArgumentTokenizer argsTokenizer) throws Exception {
        if (argsTokenizer.isPresent(recurringPrefix)) {
            return prepareAddRecurringDeadlineTask(argsTokenizer);
        } else {
            return prepareAddNonRecurringDeadlineTask(argsTokenizer);
        }
    }

    /**
     * prepare to add a floating task
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddFloatingTaskCommand(ArgumentTokenizer argsTokenizer) throws Exception {
        if (argsTokenizer.isPresent(recurringPrefix)) {
            throw new MissingRecurringDateException(Recurring.RECURRING_MISSING_DATE);
        }
        if (!argsTokenizer.isPresent(priorityPrefix)) {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(), "",
                    toSet(argsTokenizer.getAllValues(tagPrefix)), "", 0);
        } else {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(), "",
                    toSet(argsTokenizer.getAllValues(tagPrefix)), "",
                    Integer.parseInt(argsTokenizer.getValue(priorityPrefix).get()));
        }
    }

    /**
     * prepare to add an event
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddEventCommand(ArgumentTokenizer argsTokenizer) throws Exception {
        if (!argsTokenizer.isPresent(recurringPrefix)) {
            return prepareAddRecurringEvent(argsTokenizer);
        } else {
            return prepareAddNonRecurringEvent(argsTokenizer);
        }
    }

    /**
     * prepare to add a recurring deadline task
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddRecurringDeadlineTask(ArgumentTokenizer argsTokenizer) throws Exception {
        if (!argsTokenizer.isPresent(priorityPrefix)) {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                    argsTokenizer.getValue(recurringPrefix).get(), 0);
        } else {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                    argsTokenizer.getValue(recurringPrefix).get(),
                    Integer.parseInt(argsTokenizer.getValue(priorityPrefix).get()));
        }
    }

    /**
     * prepare to add a recurring event
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddRecurringEvent(ArgumentTokenizer argsTokenizer) throws Exception {
        if (!argsTokenizer.isPresent(priorityPrefix)) {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(startDatePrefix).get(), argsTokenizer.getValue(endDatePrefix).get(),
                    toSet(argsTokenizer.getAllValues(tagPrefix)), "", 0);
        } else {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(startDatePrefix).get(), argsTokenizer.getValue(endDatePrefix).get(),
                    toSet(argsTokenizer.getAllValues(tagPrefix)), "",
                    Integer.parseInt(argsTokenizer.getValue(priorityPrefix).get()));
        }
    }

    /**
     * prepare to add a non recurring deadline task
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddNonRecurringDeadlineTask(ArgumentTokenizer argsTokenizer) throws Exception {
        if (!argsTokenizer.getTokenizedArguments().containsKey(priorityPrefix)) {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)), "", 0);
        } else {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)), "",
                    Integer.parseInt(argsTokenizer.getValue(priorityPrefix).get()));
        }
    }

    /**
     * prepare to add a non-recurring event
     *
     * @param argsTokenizer
     * @return the addCommand
     * @throws Exception
     */

    private Command prepareAddNonRecurringEvent(ArgumentTokenizer argsTokenizer) throws Exception {
        if (!argsTokenizer.isPresent(priorityPrefix)) {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(startDatePrefix).get(), argsTokenizer.getValue(endDatePrefix).get(),
                    toSet(argsTokenizer.getAllValues(tagPrefix)), argsTokenizer.getValue(recurringPrefix).get(), 0);
        } else {
            return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                    argsTokenizer.getValue(startDatePrefix).get(), argsTokenizer.getValue(endDatePrefix).get(),
                    toSet(argsTokenizer.getAllValues(tagPrefix)), argsTokenizer.getValue(recurringPrefix).get(),
                    Integer.parseInt(argsTokenizer.getValue(priorityPrefix).get()));
        }
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
            if (name == null || name.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(args);
        } else {
            return new DeleteCommand(index.get());
        }

    }
    // @@author

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
    // @@author A0146123R
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Group keywords by AND operator
        final String[] keywords = matcher.group("keywords").split("AND");
        final Set<Set<String>> keywordsGroup = new HashSet<Set<String>>();
        for (String keyword : keywords) {
            // keywords delimited by whitespace
            keywordsGroup.add(new HashSet<>(Arrays.asList(keyword.trim().split("\\s+"))));
        }
        return new FindCommand(keywordsGroup, matcher.group("keywords").contains("exact!"));
    }

    // @@author A0142325R

    /**
     * prepare to create list command
     *
     * @param args
     * @return
     */

    private Command prepareList(String args) {
        if (args.isEmpty()) {
            return new ListCommand();
        } else {
            return new ListCommand(args);
        }
    }

    // @@author A0146123R
    /**
     * Parses arguments in the context of the change storage location command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareChange(String arguments) {
        final String[] args = arguments.trim().split("\\s+");
        final int defaultLength = 1;
        final int clearLength = 2;
        final int filePath = 0;
        final int clear = 1;
        if ((args.length != defaultLength && args.length != clearLength) || args[filePath].isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        }
        if (args.length == defaultLength) {
            return new ChangeCommand(args[filePath]);
        } else {
            return new ChangeCommand(args[filePath], args[clear]);
        }
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
                recurringPrefix, tagPrefix, priorityPrefix);
        argsTokenizer.tokenize(arguments);
        Optional<String> deadline = argsTokenizer.getValue(deadlinePrefix);
        Optional<String> startDate = argsTokenizer.getValue(startDatePrefix);
        Optional<String> endDate = argsTokenizer.getValue(endDatePrefix);
        Optional<String> recurring = argsTokenizer.getValue(recurringPrefix);
        Optional<List<String>> tags = argsTokenizer.getAllValues(tagPrefix);
        Optional<String> priority = argsTokenizer.getValue(priorityPrefix);
        if (deadline.isPresent() || startDate.isPresent() || endDate.isPresent() || recurring.isPresent()
                || tags.isPresent() || priority.isPresent()) {
            return new FilterCommand(deadline, startDate, endDate, recurring, toSet(tags), priority);
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    // @@author A0138717X
    /**
     * Parses arguments in the context of the edit attributes command.
     *
     * @param args
     *            full command args string
     * @return the editCommand
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = EDIT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        if (!matcher.group("name").isEmpty() && !matcher.group("edit").isEmpty()) {
            String name = matcher.group("name");
            String type = matcher.group("edit");
            String index = matcher.group("index");

            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, namePrefix, tagPrefix,
                    startDatePrefix, endDatePrefix, recurringPrefix, priorityPrefix);
            argsTokenizer.tokenize(type);

            String detailsType = getEditCommandDetailsType(argsTokenizer);
            String details = getEditCommandDetails(argsTokenizer);
            if (detailsType == null || details == null) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            if (index == null) {
                return new EditCommand(name, detailsType, details);
            }
            return new EditCommand(name, detailsType, details, Integer.parseInt(index));
        } else
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    /**
     * prepare to get the details type(field that is to be edited) of the edit
     * command
     *
     * @param argsTokenizer
     * @return String
     */
    private String getEditCommandDetailsType(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getTokenizedArguments().containsKey(namePrefix)) {
            return EDIT_TYPE_NAME;
        } else if (argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix)) {
            return EDIT_TYPE_RECURRING;
        } else if (argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)) {
            return EDIT_TYPE_START_DATE;
        } else if (argsTokenizer.getTokenizedArguments().containsKey(endDatePrefix)) {
            return EDIT_TYPE_END_DATE;
        } else if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
            return EDIT_TYPE_DEADLINE;
        } else if (argsTokenizer.getTokenizedArguments().containsKey(priorityPrefix)) {
            return EDIT_TYPE_PRIORITY;
        } else {
            return null;
        }
    }

    /**
     * prepare to get the values of the field that is to be edited
     *
     * @param argsTokenizer
     * @return String
     */
    private String getEditCommandDetails(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getTokenizedArguments().containsKey(namePrefix)) {
            return argsTokenizer.getValue(namePrefix).get();
        } else if (argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix)) {
            return argsTokenizer.getValue(recurringPrefix).get();
        } else if (argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)) {
            return argsTokenizer.getValue(startDatePrefix).get();
        } else if (argsTokenizer.getTokenizedArguments().containsKey(endDatePrefix)) {
            return argsTokenizer.getValue(endDatePrefix).get();
        } else if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
            return argsTokenizer.getValue(deadlinePrefix).get();
        } else if (argsTokenizer.getTokenizedArguments().containsKey(priorityPrefix)) {
            return argsTokenizer.getValue(priorityPrefix).get();
        } else {
            return null;
        }
    }

}
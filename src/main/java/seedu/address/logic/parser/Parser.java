package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

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
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)", Pattern.CASE_INSENSITIVE);

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_FLOAT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern RELATIVE_PATH_FORMAT =
            Pattern.compile("^(?!-)[a-z0-9-]+(?<!-)(/(?!-)[a-z0-9-]+(?<!-))*$");

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
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
            return prepareAdd(commandWord + arguments); //for adding floating tasks

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(commandWord + arguments);

        case ShowCommand.COMMAND_WORD:
            return prepareShow(commandWord + arguments);

        case HideCommand.COMMAND_WORD:
            return prepareHide(commandWord + arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);

        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);

        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);

        case RelocateCommand.COMMAND_WORD:
            return prepareRelocate(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final KeywordParser parser = new KeywordParser("add", "by", "from", "to", "repeattime", "tag");
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(args);
        String name = parsed.get("add");
        String by = parsed.get("by");
        String startTime = parsed.get("from");
        String endTime = parsed.get("to");
        String recurrence = parsed.get("repeattime");
        String tags = parsed.get("tag");

        if(name == null){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if(tags == null){
            tags = "";
        }
        try {
            return new AddCommand(
                    name,
                    by,
                    startTime,
                    endTime,
                    recurrence,
                    getTagsFromArgs(tags)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses a raw String of task type into correct task type.
     * Example: UNCOMPLETEDDD many tasks i have -> uncompleted task
     * @param typeString Raw task type string
     * @return correct task type string
     */
    private String parseTaskTypeString(String typeString) {
        String[] typeWords = {"uncompleted", "completed", "task", "event",
                            "floating", "normal", "timeslot", "free time"};
        typeString = typeString.toLowerCase();
        StringBuffer strBuf = new StringBuffer();
        for (String word : typeWords) {
            if (typeString.contains(word)) {
                typeString = typeString.replaceFirst(word, "");
                strBuf.append(word);
                strBuf.append(" ");
            }
        }
        return strBuf.toString();
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args){
        // No parameter, use defaults
        if (args.trim().equals("list")) {
            try {
                return new ListCommand();
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }

        final KeywordParser parser = new KeywordParser("list", "by", "from", "to", "tag", "sort");
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(args);
        String type = parsed.get("list");
        String deadline = parsed.get("by");
        String startTime = parsed.get("from");
        String endTime = parsed.get("to");
        String tags = parsed.get("tag");
        String sortingOrder = parsed.get("sort");

        type = parseTaskTypeString(type);

        if (type != null && type.equals(""))
            type = null;
        if (deadline != null && deadline.equals(""))
            deadline = null;
        if (startTime != null && startTime.equals(""))
            startTime = null;
        if (endTime != null && endTime.equals(""))
            endTime = null;
        if (sortingOrder != null && sortingOrder.equals(""))
            sortingOrder = null;
        if(tags == null){
            tags = "";
        }
        try {
            return new ListCommand(
                    type,
                    deadline,
                    startTime,
                    endTime,
                    getTagsFromArgs(tags),
                    sortingOrder
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the show command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareShow(String args){
        if (args.trim().equals("show")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        final KeywordParser parser = new KeywordParser("show", "on", "by", "from", "to", "tag");
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(args);
        String type = parsed.get("show");
        String date = parsed.get("on");
        String deadline = parsed.get("by");
        String startTime = parsed.get("from");
        String endTime = parsed.get("to");
        String tags = parsed.get("tag");

        type = parseTaskTypeString(type);

        if (type != null && type.equals(""))
            type = null;
        if (date != null && date.equals(""))
            date = null;
        if (deadline != null && deadline.equals(""))
            deadline = null;
        if (startTime != null && startTime.equals(""))
            startTime = null;
        if (endTime != null && endTime.equals(""))
            endTime = null;
        if(tags == null){
            tags = "";
        }
        try {
            return new ShowCommand(
                    type,
                    date,
                    deadline,
                    startTime,
                    endTime,
                    getTagsFromArgs(tags)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the hide command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareHide(String args){
        if (args.trim().equals("hide")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
        }

        final KeywordParser parser = new KeywordParser("hide", "on", "by", "from", "to", "tag");
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(args);
        String type = parsed.get("hide");
        String date = parsed.get("on");
        String deadline = parsed.get("by");
        String startTime = parsed.get("from");
        String endTime = parsed.get("to");
        String tags = parsed.get("tag");

        type = parseTaskTypeString(type);

        if (type != null && type.equals(""))
            type = null;
        if (date != null && date.equals(""))
            date = null;
        if (deadline != null && deadline.equals(""))
            deadline = null;
        if (startTime != null && startTime.equals(""))
            startTime = null;
        if (endTime != null && endTime.equals(""))
            endTime = null;
        if(tags == null){
            tags = "";
        }
        try {
            return new HideCommand(
                    type,
                    date,
                    deadline,
                    startTime,
                    endTime,
                    getTagsFromArgs(tags)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the relocate task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareRelocate(String args) {
        if (args.equals("")) {
            return new RelocateCommand();
        }
        final Matcher matcher = RELATIVE_PATH_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RelocateCommand.MESSAGE_USAGE));
        }

        return new RelocateCommand(args.trim());
    }

    /**
     * Extracts the new task's tags from the add/list command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.split(" "));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    private Command prepareUndo(String args) {
        if (args.equals("")) {
            return new UndoCommand(1);
        }
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        return new UndoCommand(index.get());
    }

    /**
     * Parses arguments in the context of the complete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new CompleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the update task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUpdate(String args) {
        final KeywordParser parser = new KeywordParser(UpdateCommand.VALID_KEYWORDS);
        HashMap<String, String> parsed = parser
                .parseKeywordsWithoutFixedOrder(UpdateCommand.COMMAND_WORD + args);

        Optional<Integer> targetIndex = parseIndex(parsed.get(UpdateCommand.COMMAND_WORD));
        if (!targetIndex.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        String name = parsed.get(UpdateCommand.KEYWORD_NAME);
        String by = parsed.get(UpdateCommand.KEYWORD_DEADLINE);
        String startTime = parsed.get(UpdateCommand.KEYWORD_PERIOD_START_TIME);
        String endTime = parsed.get(UpdateCommand.KEYWORD_PERIOD_END_TIME);
        String deadlineRecurrence = parsed.get(UpdateCommand.KEYWORD_DEADLINE_RECURRENCE);
        String periodRecurrence = parsed.get(UpdateCommand.KEYWORD_PERIOD_RECURRENCE);
        String addTagsArgs = parsed.get(UpdateCommand.KEYWORD_TAG);

        boolean removeDeadline = (parsed.get(UpdateCommand.KEYWORD_REMOVE_DEADLINE) != null);
        boolean removePeriod = (parsed.get(UpdateCommand.KEYWORD_REMOVE_START_TIME) != null
                || parsed.get(UpdateCommand.KEYWORD_REMOVE_END_TIME) != null);
        boolean removeDeadlineRecurrence = (parsed
                .get(UpdateCommand.KEYWORD_REMOVE_DEADLINE_RECURRENCE) != null);
        boolean removePeriodRecurrence = (parsed.get(UpdateCommand.KEYWORD_REMOVE_PERIOD_RECURRENCE) != null);
        String removeTagsArgs = parsed.get(UpdateCommand.KEYWORD_REMOVE_TAG);

        Set<String> tagsToAdd = null;
        try {
            if (addTagsArgs != null) {
                tagsToAdd = getTagsFromArgs(addTagsArgs);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        Set<String> tagsToRemove = null;
        try {
            if (removeTagsArgs != null) {
                tagsToRemove = getTagsFromArgs(removeTagsArgs);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new UpdateCommand(targetIndex.get(), name, by, startTime, endTime, deadlineRecurrence,
                periodRecurrence, tagsToAdd, removeDeadline, removePeriod, removeDeadlineRecurrence,
                removePeriodRecurrence, tagsToRemove);
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        if (command == null) {
            return Optional.empty();
        }

        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
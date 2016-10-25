package teamfour.tasc.logic.parser;

import teamfour.tasc.logic.commands.*;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.logic.commands.AddCommand;
import teamfour.tasc.logic.commands.ClearCommand;
import teamfour.tasc.logic.commands.Command;
import teamfour.tasc.logic.commands.CompleteCommand;
import teamfour.tasc.logic.commands.DeleteCommand;
import teamfour.tasc.logic.commands.ExitCommand;
import teamfour.tasc.logic.commands.FindCommand;
import teamfour.tasc.logic.commands.HelpCommand;
import teamfour.tasc.logic.commands.HideCommand;
import teamfour.tasc.logic.commands.IncorrectCommand;
import teamfour.tasc.logic.commands.ListCommand;
import teamfour.tasc.logic.commands.RelocateCommand;
import teamfour.tasc.logic.commands.SelectCommand;
import teamfour.tasc.logic.commands.ShowCommand;
import teamfour.tasc.logic.commands.UndoCommand;
import teamfour.tasc.logic.commands.UpdateCommand;
import teamfour.tasc.logic.commands.CollapseCommand;

import static teamfour.tasc.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static teamfour.tasc.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)", Pattern.CASE_INSENSITIVE);

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_FLOAT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern RELATIVE_PATH_FORMAT =
            Pattern.compile("^((?!-)[a-zA-Z0-9-]+(?<!-)|(..))(/((?!-)[a-zA-Z0-9-]+(?<!-)|(..)))*$");

    private static final Pattern FILE_NAME_ONLY_FORMAT = Pattern.compile("^[\\w,\\s-]+$");
    
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
            return prepareList(arguments);

        case ShowCommand.COMMAND_WORD:
            return prepareShow(arguments);

        case HideCommand.COMMAND_WORD:
            return prepareHide(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);
            
        case RedoCommand.COMMAND_WORD:
            return prepareRedo(arguments);

        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);

        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);

        case RelocateCommand.COMMAND_WORD:
            return prepareRelocate(arguments);
            
        case SwitchlistCommand.COMMAND_WORD:
            return prepareSwitchlist(arguments);

        case CalendarCommand.COMMAND_WORD:
            return prepareCalendar(arguments);
            
        case CollapseCommand.COMMAND_WORD:
            return new CollapseCommand();

        case ExpandCommand.COMMAND_WORD:
            return new ExpandCommand();

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
        final KeywordParser parser = new KeywordParser("add", "by", "from", "to", "repeat", "tag");
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(args);
        String name = parsed.get("add");
        String by = parsed.get("by");
        String startTime = parsed.get("from");
        String endTime = parsed.get("to");
        String recurrence = parsed.get("repeat");
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
     * Parses arguments in the context of the switch list command.
     *
     * @param args the file name of list that user wish to switch to. 
     * @return the prepared command
     */
    public Command prepareSwitchlist(String args) {
        final Matcher matcher = FILE_NAME_ONLY_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchlistCommand.MESSAGE_USAGE));
        }
        return new SwitchlistCommand(args.trim());
    }

    /**
     * Takes in a string and return null if it is empty,
     * or otherwise returns the string itself.
     */
    private String setToNullIfIsEmptyString(String string) {
        if (string == null || string.equals(""))
            return null;
        return string;
    }

    /**
     * Precondition: argument is not null.
     * Takes in a string and remove all occurrences of full stops and commas.
     */
    private String removeFullStopsAndCommas(String string) {
        assert string != null;
        string = string.replace(",", "");
        string = string.replace(".", "");
        return string;
    }

    /**
     * Precondition: argument string is not null.
     * Parses the command string in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args){
        assert args != null;

        // No arguments, use default 'list' command
        if (args.trim().equals("")) {
            try {
                return new ListCommand();
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }

        final KeywordParser parser = new KeywordParser(ListCommand.VALID_KEYWORDS);
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(ListCommand.COMMAND_WORD + args);
        String type = setToNullIfIsEmptyString(parsed.get(ListCommand.COMMAND_WORD));
        String deadline = setToNullIfIsEmptyString(parsed.get(ListCommand.KEYWORD_DEADLINE));
        String startTime = setToNullIfIsEmptyString(parsed.get(ListCommand.KEYWORD_PERIOD_START_TIME));
        String endTime = setToNullIfIsEmptyString(parsed.get(ListCommand.KEYWORD_PERIOD_END_TIME));
        String tags = setToNullIfIsEmptyString(parsed.get(ListCommand.KEYWORD_TAG));
        String sortingOrder = setToNullIfIsEmptyString(parsed.get(ListCommand.KEYWORD_SORT));

        if(tags == null){
            tags = "";
        } else {
            tags = removeFullStopsAndCommas(tags);
        }

        if (sortingOrder != null) {
            sortingOrder = removeFullStopsAndCommas(sortingOrder);
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
     * Precondition: argument string is not null.
     * Parses the command string in the context of the show command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareShow(String args){
        assert args != null;

        if (args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        final KeywordParser parser = new KeywordParser(ShowCommand.VALID_KEYWORDS);
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(ShowCommand.COMMAND_WORD + args);
        String type = setToNullIfIsEmptyString(parsed.get(ShowCommand.COMMAND_WORD));
        String date = setToNullIfIsEmptyString(parsed.get(ShowCommand.KEYWORD_DATE));
        String deadline = setToNullIfIsEmptyString(parsed.get(ShowCommand.KEYWORD_DEADLINE));
        String startTime = setToNullIfIsEmptyString(parsed.get(ShowCommand.KEYWORD_PERIOD_START_TIME));
        String endTime = setToNullIfIsEmptyString(parsed.get(ShowCommand.KEYWORD_PERIOD_END_TIME));
        String tags = setToNullIfIsEmptyString(parsed.get(ShowCommand.KEYWORD_TAG));

        if(tags == null){
            tags = "";
        } else {
            tags = removeFullStopsAndCommas(tags);
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
     * Precondition: argument string is not null.
     * Parses the command string in the context of the hide command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareHide(String args){
        assert args != null;

        if (args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
        }

        final KeywordParser parser = new KeywordParser(HideCommand.VALID_KEYWORDS);
        HashMap<String, String> parsed = parser.parseKeywordsWithoutFixedOrder(HideCommand.COMMAND_WORD + args);
        String type = setToNullIfIsEmptyString(parsed.get(HideCommand.COMMAND_WORD));
        String date = setToNullIfIsEmptyString(parsed.get(HideCommand.KEYWORD_DATE));
        String deadline = setToNullIfIsEmptyString(parsed.get(HideCommand.KEYWORD_DEADLINE));
        String startTime = setToNullIfIsEmptyString(parsed.get(HideCommand.KEYWORD_PERIOD_START_TIME));
        String endTime = setToNullIfIsEmptyString(parsed.get(HideCommand.KEYWORD_PERIOD_END_TIME));
        String tags = setToNullIfIsEmptyString(parsed.get(HideCommand.KEYWORD_TAG));

        if(tags == null){
            tags = "";
        } else {
            tags = removeFullStopsAndCommas(tags);
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
     * Parses arguments in the context of the change calendar view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareCalendar(String args) {
        if (args.equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
        }
        try {
            return new CalendarCommand(args.trim());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
        }
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
     * Special case: if arg provided is "last", index is set to -1
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        if(args.trim().toLowerCase().equals("last")){
            return new SelectCommand(-1);
        }
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Parses arguments in the context of the undo command.
     * Special case: if no arg is provided, undoes 1 command.
     * @param args full command args string
     * @return the prepared command
     */
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
     * Parses arguments in the context of the redo command.
     * Special case: if no arg is provided, redoes 1 command.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareRedo(String args) {
        if (args.equals("")) {
            return new RedoCommand(1);
        }
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }

        return new RedoCommand(index.get());
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
        String recurrence = parsed.get(UpdateCommand.KEYWORD_RECURRENCE);
        String addTagsArgs = parsed.get(UpdateCommand.KEYWORD_TAG);

        boolean removeDeadline = (parsed.get(UpdateCommand.KEYWORD_REMOVE_DEADLINE) != null);
        boolean removePeriod = (parsed.get(UpdateCommand.KEYWORD_REMOVE_START_TIME) != null
                || parsed.get(UpdateCommand.KEYWORD_REMOVE_END_TIME) != null);
        boolean removeRecurrence = (parsed
                .get(UpdateCommand.KEYWORD_REMOVE_RECURRENCE) != null);
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

        return new UpdateCommand(targetIndex.get(), name, by, startTime, endTime, recurrence,
                tagsToAdd, removeDeadline, removePeriod, removeRecurrence, tagsToRemove);
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
package seedu.flexitrack.logic.parser;

import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flexitrack.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.util.StringUtil;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.logic.commands.BlockCommand;
import seedu.flexitrack.logic.commands.ClearCommand;
import seedu.flexitrack.logic.commands.Command;
import seedu.flexitrack.logic.commands.DeleteCommand;
import seedu.flexitrack.logic.commands.EditCommand;
import seedu.flexitrack.logic.commands.ExitCommand;
import seedu.flexitrack.logic.commands.FindCommand;
import seedu.flexitrack.logic.commands.GapCommand;
import seedu.flexitrack.logic.commands.HelpCommand;
import seedu.flexitrack.logic.commands.IncorrectCommand;
import seedu.flexitrack.logic.commands.ListCommand;
import seedu.flexitrack.logic.commands.MarkCommand;
import seedu.flexitrack.logic.commands.SelectCommand;
import seedu.flexitrack.logic.commands.UndoCommand;
import seedu.flexitrack.logic.commands.UnmarkCommand;
import seedu.flexitrack.model.task.DateTimeInfoParser;
import seedu.flexitrack.model.Model;


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
  //@@author A0127855W 
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

    //@@author A0127686R
    private static final Pattern TASK_EVENT_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "from/(?<startTime>[^/]+)" + "to/(?<endTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern TASK_DEADLINE_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "by/(?<dueDate>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern TASK_FLOATING_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    //@@author A0147092E
    private static final Pattern TASK_RECURRING_EVENT_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>.+)" + "fr/(?<numOfOccurrence>[^/dd]+)" + "ty/(?<occurrenceType>[^/].+)" + "from/(?<startTime>[^/]+)" + "to/(?<endTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    //@@author 
    private static final Pattern EDIT_COMMAND_FORMAT = Pattern.compile("(?<index>[0-9]+)(?<arguments>.*)");
    private static final Pattern EDIT_ARGS_NAME = Pattern.compile("n/\\s*(?<name>.+)");
    private static final Pattern EDIT_ARGS_DUEDATE = Pattern.compile("by/\\s*(?<dueDate>[^/]+)");
    private static final Pattern EDIT_ARGS_STARTTIME = Pattern.compile("from/\\s*(?<startTime>[^/]+)");
    private static final Pattern EDIT_ARGS_ENDTIME = Pattern.compile("to/\\s*(?<endTime>[^/]+)");
    
    private Model model;
   
    public final static String EMPTY_TIME_INFO = "Feb 29 2000 00:00:00";
    
    
    public Parser() {
    }
    
    public Parser(Model model) {
        this.model = model;
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
            
        case GapCommand.COMMAND_WORD:
            return prepareGap(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(arguments.trim());

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command prepareGap(String arguments) {
        arguments.toLowerCase(); 
        if( isGapArgumentValid(arguments)){
            int keyword = extractKeywordFromArgs(arguments);
            int length = extractLength(arguments);
            System.out.println("does it find any of this :6");
            return new GapCommand(keyword, length);
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GapCommand.MESSAGE_USAGE));
    }

    private int extractLength(String arguments) {
        String length = arguments.replace(GapCommand.DAY_WORD+"s", "").
        replace(GapCommand.HOUR_WORD + "s", "").replace(GapCommand.MINUTE_WORD+"s", "").
        replace(GapCommand.DAY_WORD, ""). replace(GapCommand.HOUR_WORD, "").
        replace(GapCommand.MINUTE_WORD, "");
        if (length.trim().equals("")){
            System.out.println("does it find any of this :1");
            return 1; 
        } else {
            System.out.println("does it find any of this :2");
            return Integer.parseInt(length.trim());
        }
    }

    private int extractKeywordFromArgs(String arguments) {
        if (arguments.contains(GapCommand.DAY_WORD)||arguments.contains(GapCommand.DAY_WORD + "s")){
            return 2;
        }
        if (arguments.contains(GapCommand.HOUR_WORD)||arguments.contains(GapCommand.HOUR_WORD + "s")){
            return 1; 
        }
//        if (arguments.contains(GapCommand.MINUTE_WORD)||arguments.contains(GapCommand.MINUTE_WORD + "s")){
        else {
            return 0; 
        }
    }

    private boolean isGapArgumentValid(String arguments) {
        int numberOfMatch = 0; 
        if (arguments.contains(GapCommand.DAY_WORD)||arguments.contains(GapCommand.DAY_WORD + "s")){
            numberOfMatch = numberOfMatch + 1; 
            System.out.println("does it find any of this :3");
        }
        if (arguments.contains(GapCommand.HOUR_WORD)||arguments.contains(GapCommand.HOUR_WORD + "s")){
            numberOfMatch = numberOfMatch + 1; 
            System.out.println("does it find any of this :4");
        }
        if (arguments.contains(GapCommand.MINUTE_WORD)||arguments.contains(GapCommand.MINUTE_WORD + "s")){
            numberOfMatch = numberOfMatch + 1; 
            System.out.println("does it find any of this :5");
        }
        return numberOfMatch == 1;
    }

    //@@author A0127855W
    /**
     * parseCommandWord
     * -------------------------------------------
     * Parses the given command word string, converting shortcut commands into their full versions
     * @param commandWord
     * @return String: Full command word
     */
    private String parseCommandWord(String commandWord) {     
        assert commandWord != null;
        return SHORTCUT_MAP.getOrDefault(commandWord, commandWord);
    }

    //@@author A0127686R
    /**
     * Check if the arguments are valid for list Command
     * @param arguments
     * @return new List Command containing arguments
     */
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

  //@@author A0138455Y
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

    //@@author A0127686R
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

  //@@author A0127855W
    /**
     * prepareEdit
     * ------------------------------------------
     * Parses the edit command arguments and outputs the correct EditCommand object for execution
     * @param arguments
     * @return Command: The correct EditCommand object
     */
    private Command prepareEdit(String arguments) {
        assert arguments != null;
        
        int index;
        String editParameters;
        String[] passing = new String[EditCommand.EDIT_PARAMETER_PASSING_MASK.size()];

        final Matcher matcherEdit = EDIT_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcherEdit.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } else {
            index = Integer.parseInt(matcherEdit.group("index"));
            editParameters = matcherEdit.group("arguments").trim();
        }

        final Matcher matcherName = EDIT_ARGS_NAME.matcher(editParameters);
        final Matcher matcherDueDate = EDIT_ARGS_DUEDATE.matcher(editParameters);
        final Matcher matcherStartTime = EDIT_ARGS_STARTTIME.matcher(editParameters);
        final Matcher matcherEndTime = EDIT_ARGS_ENDTIME.matcher(editParameters);

        boolean namePresent = matcherName.find();
        boolean dueDatePresent = matcherDueDate.find();
        boolean startTimePresent = matcherStartTime.find();
        boolean endTimePresent = matcherEndTime.find();
        
        //Check that at least one edit parameter exists
        if (!namePresent && !dueDatePresent && !startTimePresent && !endTimePresent) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        prepareEditParameter(passing, matcherName, namePresent, "name");
        prepareEditParameter(passing, matcherDueDate, dueDatePresent, "dueDate");
        prepareEditParameter(passing, matcherStartTime, startTimePresent, "startTime");
        prepareEditParameter(passing, matcherEndTime, endTimePresent, "endTime");

        return new EditCommand(index, passing);
    }

    /**
     * prepareEditParameter
     * ---------------------------------------------------------
     * prepares the passing array for each parameter to be passed into the EditCommand constructor
     * @param passing
     * @param matcherType
     * @param typePresent
     * @param typeGroupID
     */
    private void prepareEditParameter(String[] passing, final Matcher matcherType, boolean typePresent, String typeGroupID) {
        if (typePresent) {
            passing[EditCommand.EDIT_PARAMETER_PASSING_MASK.get(typeGroupID)] = matcherType.group(typeGroupID);
        } else {
            passing[EditCommand.EDIT_PARAMETER_PASSING_MASK.get(typeGroupID)] = null;
        }
    }

  //@@author A0138455Y
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
    
    //@@author A0127686R
    /**
     * Parses arguments in the context of the add task command.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcherEvent = TASK_EVENT_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherDeadline = TASK_DEADLINE_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcherFloating = TASK_FLOATING_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        //@@author A0147092E
        final Matcher matcherRecurring = TASK_RECURRING_EVENT_TYPE_DATA_ARGS_FORMAT.matcher(args.trim());
        //@@author A0127686R
        // Validate arg string format
        try {
            if (matcherRecurring.matches()) {
                return addRecurringEvent(matcherRecurring);
            }else if (matcherEvent.matches()) {
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
        return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, EMPTY_TIME_INFO, EMPTY_TIME_INFO);
    }

    private AddCommand addDeadlineTask(Matcher matcher) throws IllegalValueException {
        return new AddCommand(matcher.group("name"), matcher.group("dueDate"), EMPTY_TIME_INFO, EMPTY_TIME_INFO);
    }
    
    
    private AddCommand addEventTask(Matcher matcher) throws IllegalValueException {
        return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, matcher.group("startTime"),
                matcher.group("endTime"));
    }
    
    //@@author A0147092E
    private Command addRecurringEvent(Matcher matcher) throws IllegalValueException{
        String formattedStartTime;
        String formattedEndTime;
        int numOfOccurrrence = Integer.parseInt(matcher.group("numOfOccurrence").trim());
        String occurrenceType = matcher.group("occurrenceType").trim().toLowerCase();
        Date initialStartTime = new DateTimeInfoParser(matcher.group("startTime")).getParsedDateTime();
        Date initialEndTime = new DateTimeInfoParser(matcher.group("endTime")).getParsedDateTime();
       
        if (occurrenceType.equalsIgnoreCase("day")) {

            for(int index=1; index < numOfOccurrrence; index++){
                Calendar calendar = Calendar.getInstance();
                //increment startTime
                calendar.setTime(initialStartTime);
                calendar.add(Calendar.DATE, index);
                Date newStartTime = calendar.getTime();
                //increment endTime
                calendar.add(Calendar.DATE, index);
                calendar.setTime(initialEndTime);
                Date newEndTime = calendar.getTime();
                
                // Format the new startTime and endTime for adding
                formattedStartTime = new SimpleDateFormat("MM-dd-yyyy hhmmss").format(newStartTime);
                formattedEndTime = new SimpleDateFormat("MM-dd-yyyy hhmmss").format(newEndTime);
                
                Command command =  new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, formattedStartTime, formattedEndTime);
                command.setData(model);
                command.execute();
            }
            
            return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, matcher.group("startTime"), matcher.group("endTime"));
        }     
        else{

            for(int index=1; index < numOfOccurrrence; index++){

                Calendar calendar = Calendar.getInstance();
                // increment startTime
                calendar.setTime(initialStartTime);
                calendar.add(Calendar.MONTH, index);
                Date newStartTime = calendar.getTime();
                // increment endTime
                calendar.setTime(initialEndTime);
                calendar.add(Calendar.MONTH, index);
                Date newEndTime = calendar.getTime();
                
                // format the new startTime and endTime for adding
                formattedStartTime = new SimpleDateFormat("MM-dd-yyyy hhmm").format(newStartTime);
                formattedEndTime = new SimpleDateFormat("MM-dd-yyyy hhmm").format(newEndTime);
                
                System.out.println("Formatted start: " + formattedStartTime);
                System.out.println("Formatted end: " + formattedEndTime);
                
                Command command =  new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, formattedStartTime, formattedEndTime);
                command.setData(model);
                command.execute();
            }
            
            return new AddCommand(matcher.group("name"), EMPTY_TIME_INFO, matcher.group("startTime"), matcher.group("endTime"));
        }
    }

    //@@author 
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
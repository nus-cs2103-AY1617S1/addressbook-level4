package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT;


/**
 * Parses user input.
 */
public class CommandParser {
    
    private final Logger logger = LogsCenter.getLogger(CommandParser.class);
    
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String RESET_KEYWORD = "-reset";
    
    private static final String MAP_NAME = "taskName";
    private static final String MAP_START_DATE = "startDate";
    private static final String MAP_END_DATE = "endDate";
    private static final String MAP_RECURRENCE_RATE = "rate";
    private static final String MAP_RECURRENCE_TIME_PERIOD = "timePeriod";
    private static final String MAP_PRIORITY = "priority";
    
    private static final String RESET_START = "start";
    private static final String RESET_END = "end";
    private static final String RESET_RECURRENCE = "repeat";
    private static final String RESET_PRIORITY = "priority";

    public CommandParser() {}

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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case StoreCommand.COMMAND_WORD:
            return prepareStore(arguments);
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return prepareAdd(commandWord + arguments);
        }
    }


    //@@author A0139655U
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        assert args != null;
        
        logger.finer("Entering CommandParser, prepareAdd()");
        String argsTrimmed = args.trim();
        if(argsTrimmed.isEmpty()) {
            logger.finer("Trimmed argument is empty");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {  
            HashMap<String, Optional<String>> extractedValues = retrieveAddFieldsFromArgs(argsTrimmed);
            logger.finer("Exiting CommandParser, prepareAdd()");
            return new AddCommand(extractedValues);
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareAdd()");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    AddCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }
    }

    //@@author
    /**
     * Extracts the new person's tags from the add command's tag arguments string.
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
    
    //@@author A0139552B
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        
        int index = ZERO;     
        String resetField = null;

        args = args.trim();
        String[] indexSplit = args.split(" ");
        String indexNum = indexSplit[ZERO];

        if(indexSplit.length == ONE){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
            
        try{
            index = Integer.parseInt(indexNum);
        } catch (NumberFormatException e){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
           
        String[] resetSplit = args.substring(TWO).split("-reset");

        String argsTrimmed = " " + resetSplit[ZERO];        

        logger.finer("Entering CommandParser, prepareEdit()");
                       
        try {
            HashMap<String, Optional<String>> extractedValues = new CommandParserHelper().prepareEdit(argsTrimmed);
            
            logger.finer("Exiting CommandParser, prepareEdit()");
            
            if(resetSplit.length == TWO){
                resetField = resetSplit[ONE];
            }
            
            return new EditCommand(index, extractedValues.get("taskName"), extractedValues.get("startDate"), 
                    extractedValues.get("endDate"), extractedValues.get("rate"), 
                    extractedValues.get("timePeriod"), extractedValues.get("priority"),resetField);
            
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareEdit()");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    EditCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }            
    }
    //@@author A0139498J
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<List<Integer>> indexes = parseIndexes(args);
        if(!indexes.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(indexes.get());
    }
    
    //@@author A0139498J
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {

        Optional<List<Integer>> indexes = parseIndexes(args);
        if(!indexes.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(indexes.get());
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
    
    //@@author A0139498J
    /**
     * Parses arguments in the context of the list tasks command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            // no arguments
            
            return new ListCommand(new HashSet<String>());
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ListCommand(keywordSet);
    }
    
    /**
     * Parses arguments in the context of the help command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    /*
    private Command prepareHelp(String args) {
        
        if (args != null) {
            return new HelpCommand(args.trim());
        }
        
        return new HelpCommand(HelpCommand.COMMAND_WORD);
    }
    */

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    
    //@@author A0139498J
    /**
     * Returns the specified indexes in the {@code command} IF any positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<List<Integer>> parseIndexes(String command) {
        
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command);
        
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String indexes = matcher.group("targetIndex");
        String[] indexesArray = indexes.split(" ");
        List<Integer> indexesToHandle = new ArrayList<Integer>();
        for (String index: indexesArray) {
            if (StringUtil.isUnsignedInteger(index)) {
                indexesToHandle.add(Integer.parseInt(index));
            }
        }
        
        return (indexesToHandle.isEmpty())? Optional.empty(): Optional.of(indexesToHandle);

    }

    /**
     * Parses arguments in the context of the change storage location command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareStore(String args) {  
        return new StoreCommand(args.trim());
    }
    
    //@@author
    /**
     * Parses arguments in the context of the find person command.
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
    
    /**
     * Parses an incomplete user input to determine the most appropriate tooltip for the user to see.
     * The tooltip depends on the command that the user is trying to execute (which this parser tries to 
     * determine).
     * 
     * @param userInput user input string
     * @return a list of Strings for tooltips
     */
    public String parseForTooltip(String userInput, boolean isViewingDoneList) {
        assert userInput != null;

        String trimmedUserInput = userInput.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedUserInput);       
        boolean invalidCommandFormat = !matcher.matches();
        
        if (invalidCommandFormat) {
            return MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT;
        }    
        
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        boolean interpretAsNoArgs = commandWord.equals(userInput);
         
        return getTooltip(arguments, commandWord, interpretAsNoArgs, isViewingDoneList);
    }

    /**
     * Returns the list of tooltips that matches the current user's input.
     * 
     * @param toolTips list of tooltips
     * @param commandWord the user input command word
     * @param inputHasNoArgs boolean representing whether user input has no arguments
     */
    private String getTooltip(final String arguments, final String commandWord, boolean inputHasNoArgs, boolean isViewingDoneList) { 
        if (isViewingDoneList) {
            return getTooltipForDoneList(commandWord, inputHasNoArgs);          
        }
        
        return getTooltipForUndoneList(arguments, commandWord, inputHasNoArgs);
    }
    
    private String getTooltipForUndoneList(final String arguments, final String commandWord, boolean inputHasNoArgs) {
        // tooltip depends on whether the input command has arguments
        if (inputHasNoArgs) {
            return getTooltipForCmdWithNoArgsUndoneList(commandWord);
        } else {
            return getTooltipForCmdWithArgsUndoneList(arguments, commandWord);
        }
    }


    private String getTooltipForDoneList(final String commandWord, boolean inputHasNoArgs) {
     // tooltip depends on whether the input command has arguments
        if (inputHasNoArgs) {
            return getTooltipForCmdWithNoArgsDoneList(commandWord);
        } else {
            return getTooltipForCmdWithArgsDoneList(commandWord);
        }
    }

    private String getTooltipForCmdWithNoArgsDoneList(String commandWord) {
        List<String> tooltips = new ArrayList<String>();

        if (StringUtil.isSubstringFromStart(ClearCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(DeleteCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DeleteCommand.TOOL_TIP);
        }   
        if (StringUtil.isSubstringFromStart(ExitCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(FindCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(HelpCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ListCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(RedoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(RedoCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(SelectCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(StoreCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(StoreCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(UndoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(UndoCommand.TOOL_TIP);
        }
        

        boolean hasNoTooltipMatches = tooltips.isEmpty();
        if (hasNoTooltipMatches) {
            return Messages.MESSAGE_DONE_LIST_RESTRICTED_COMMANDS;
        }

        String combinedTooltip = StringUtil.combineStrings(tooltips);
        return combinedTooltip;
    }
    
    private String getTooltipForCmdWithArgsDoneList(String commandWord) {
        
        if (commandWord.equals(ClearCommand.COMMAND_WORD)) {
            return ClearCommand.TOOL_TIP;
            
        } else if (commandWord.equals(DeleteCommand.COMMAND_WORD)) {
            return DeleteCommand.TOOL_TIP;
            
        } else if (commandWord.equals(ExitCommand.COMMAND_WORD)) {
            return ExitCommand.TOOL_TIP;
            
        } else if (commandWord.equals(FindCommand.COMMAND_WORD)) {
            return FindCommand.TOOL_TIP;
            
        } else if (commandWord.equals(HelpCommand.COMMAND_WORD)) {
            return HelpCommand.TOOL_TIP;
            
        } else if (commandWord.equals(ListCommand.COMMAND_WORD)) {
            return ListCommand.TOOL_TIP;
            
        } else if (commandWord.equals(RedoCommand.COMMAND_WORD)) {
            return RedoCommand.TOOL_TIP;
            
        } else if (commandWord.equals(SelectCommand.COMMAND_WORD)) {
            return SelectCommand.TOOL_TIP;
            
        } else if (commandWord.equals(StoreCommand.COMMAND_WORD)) {
            return StoreCommand.TOOL_TIP;
            
        } else if (commandWord.equals(UndoCommand.COMMAND_WORD)) {
            return UndoCommand.TOOL_TIP;
            
        } else {
            return Messages.MESSAGE_DONE_LIST_RESTRICTED_COMMANDS;
            
        }
    }

    
    /**
     * Get the tooltip string appropriate for the current user input that has no arguments.
     * 
     * @param commandWord the command part of the user input
     * @return the tooltip
     */
    private String getTooltipForCmdWithNoArgsUndoneList(final String commandWord) {
        List<String> tooltips = new ArrayList<String>();
        
        if (StringUtil.isSubstringFromStart(AddCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(AddCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ClearCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(DeleteCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DeleteCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(DoneCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DoneCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(EditCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(EditCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ExitCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(FindCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(HelpCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ListCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(RedoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(RedoCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(SelectCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(StoreCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(StoreCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(UndoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(UndoCommand.TOOL_TIP);
        }
        
        boolean hasNoTooltipMatches = tooltips.isEmpty();       
        if (hasNoTooltipMatches) {
            tooltips.add(AddCommand.TOOL_TIP);           
        } 
        
        String combinedTooltip = StringUtil.combineStrings(tooltips);
        return combinedTooltip;
    }
    
    /**
     * Get the tooltip string appropriate for the current user input that has arguments.
     * 
     * @param commandWord the command part of the user input
     * @return the tooltip
     */
    private String getTooltipForCmdWithArgsUndoneList(final String arguments, final String commandWord) {        
        if (commandWord.equals(AddCommand.COMMAND_WORD)) {
            return prepareAddDetailedTooltip(arguments);
            
        } else if (commandWord.equals(ClearCommand.COMMAND_WORD)) {
            return ClearCommand.TOOL_TIP;
            
        } else if (commandWord.equals(DeleteCommand.COMMAND_WORD)) {
            return DeleteCommand.TOOL_TIP;
            
        } else if (commandWord.equals(DoneCommand.COMMAND_WORD)) {
            return DoneCommand.TOOL_TIP;
            
        } else if (commandWord.equals(EditCommand.COMMAND_WORD)) {
            return prepareEditDetailedTooltip(arguments);
            
        } else if (commandWord.equals(ExitCommand.COMMAND_WORD)) {
            return ExitCommand.TOOL_TIP;
            
        } else if (commandWord.equals(FindCommand.COMMAND_WORD)) {
            return FindCommand.TOOL_TIP;
            
        } else if (commandWord.equals(HelpCommand.COMMAND_WORD)) {
            return HelpCommand.TOOL_TIP;
            
        } else if (commandWord.equals(ListCommand.COMMAND_WORD)) {
            return ListCommand.TOOL_TIP;
            
        } else if (commandWord.equals(RedoCommand.COMMAND_WORD)) {
            return RedoCommand.TOOL_TIP;
            
        } else if (commandWord.equals(SelectCommand.COMMAND_WORD)) {
            return SelectCommand.TOOL_TIP;
            
        } else if (commandWord.equals(StoreCommand.COMMAND_WORD)) {
            return StoreCommand.TOOL_TIP;
            
        } else if (commandWord.equals(UndoCommand.COMMAND_WORD)) {
            return UndoCommand.TOOL_TIP;
            
        } else {
            // default command is an add command
            return prepareAddDetailedTooltip(commandWord + arguments);
            
        }

    }

    /**
     * @param trimmedArgs
     * @return
     * @throws IllegalValueException
     */
    private String generateEditDetailedTooltip(String trimmedArgs) throws IllegalValueException {
        String indexToEdit = trimmedArgs.substring(ZERO, ONE);
        String argumentsWithoutIndex = trimmedArgs.substring(ONE);
        String resetField = null;
        String[] resetSplit = argumentsWithoutIndex.split(RESET_KEYWORD);
        
        boolean resetStartDate = false, resetEndDate = false, resetRecurrence = false, 
                resetPriority = false;
                    
        HashMap<String, Optional<String>> fieldMap = retrieveEditFieldsFromArgs(resetSplit);
        
        Optional<String> name = fieldMap.get(MAP_NAME);
        Optional<String> startDate = fieldMap.get(MAP_START_DATE);
        Optional<String> endDate = fieldMap.get(MAP_END_DATE);
        Optional<String> rate = fieldMap.get(MAP_RECURRENCE_RATE);
        Optional<String> timePeriod = fieldMap.get(MAP_RECURRENCE_TIME_PERIOD);
        Optional<String> priority = fieldMap.get(MAP_PRIORITY);

        if(resetSplit.length == TWO){
            resetField = resetSplit[ONE];
        }
        
        if (resetField != null) {
            String[] resetFieldNames = resetField.split("\\s+");
            
            for (String resetFieldStr : resetFieldNames) {
                if (resetFieldStr.equals(RESET_START)) {
                    resetStartDate = true;
                } else if (resetFieldStr.equals(RESET_END)) {
                    resetEndDate = true;
                } else if (resetFieldStr.equals(RESET_RECURRENCE)) {
                    resetRecurrence = true;
                } else if (resetFieldStr.equals(RESET_PRIORITY)) {
                    resetPriority = true;
                }
            }
        }

                    
        StringBuilder sb = new StringBuilder();
        sb.append(EditCommand.TOOL_TIP);
        sb.append("\n\tEditing task at INDEX " + indexToEdit + ": ");

        if (name.isPresent() && trimmedArgs.length()>1 && !name.get().isEmpty()) {
            sb.append("\n\tName:\t" + name.get());
        } else {
            sb.append("\n\tName:\tNo Change");
        }
        
        if (resetStartDate) {
            sb.append("\n\tStart Date:\tRESET");
        } else if (startDate.isPresent()) {
            sb.append("\n\tStart Date:\t" + startDate.get());
        } else {
            sb.append("\n\tStart Date:\tNo Change");
        }
        
        if (resetEndDate) {
            sb.append("\n\tEnd Date:\t\tRESET");
        } else if (endDate.isPresent()) {
            sb.append("\n\tEnd Date:\t\t" + endDate.get());
        } else {
            sb.append("\n\tEnd Date:\t\tNo Change");
        }
        
        if (resetRecurrence) {
            sb.append("\n\tRecurrence Rate:\tRESET");
        } else if (timePeriod.isPresent()) {
            if (rate.isPresent()) {
                String recurRate = rate.get();
                sb.append("\n\tRecurrence Rate:\t" + "every " + recurRate + " " + timePeriod.get());
            } else {
                sb.append("\n\tRecurrence Rate:\t" + "every 1 " + timePeriod.get());
            }
        } else {
            sb.append("\n\tRecurrence Rate:\tNo Change");
        }
        
        if (resetPriority) {
            sb.append("\n\tPriority:\tRESET");
        } else if (!priority.get().equals("null")) {
            sb.append("\n\tPriority:\t" + priority.get());
        } else {
            sb.append("\n\tPriority:\tNo Change");
        }
        
        return sb.toString();
    }

    /**
     * @param resetSplit
     * @return
     * @throws IllegalValueException
     */
    private HashMap<String, Optional<String>> retrieveEditFieldsFromArgs(String[] resetSplit)
            throws IllegalValueException {
        return new CommandParserHelper().prepareEdit(" " + resetSplit[ZERO]);
    }

    private String prepareAddDetailedTooltip(final String arguments) {
        try {
            
            // should not use exceptions for this
            if (arguments.isEmpty()) {
                throw new IllegalValueException("No arguments found");
            }
            
            String trimmedArgs = arguments.trim();
            
            return generateAddDetailedTooltip(trimmedArgs);
            
        } catch (IllegalValueException e) {
            return AddCommand.TOOL_TIP;
        }

    }
    
    private String prepareEditDetailedTooltip(final String arguments) {
        try {
            
            // should not use exceptions for this
            if (arguments.isEmpty()) {
                throw new IllegalValueException("No arguments found");
            }
            
            String trimmedArgs = arguments.trim();

            return generateEditDetailedTooltip(trimmedArgs);
            
        } catch (IllegalValueException e) {
            return EditCommand.TOOL_TIP;
        }
    }

    /**
     * @param trimmedArgs
     * @return
     * @throws IllegalValueException
     */
    private String generateAddDetailedTooltip(String trimmedArgs) throws IllegalValueException {
        HashMap<String, Optional<String>> fieldMap = retrieveAddFieldsFromArgs(trimmedArgs);
        
        Optional<String> name = fieldMap.get(MAP_NAME);
        Optional<String> startDate = fieldMap.get(MAP_START_DATE);
        Optional<String> endDate = fieldMap.get(MAP_END_DATE);
        Optional<String> rate = fieldMap.get(MAP_RECURRENCE_RATE);
        Optional<String> timePeriod = fieldMap.get(MAP_RECURRENCE_TIME_PERIOD);
        Optional<String> priority = fieldMap.get(MAP_PRIORITY);
        
        StringBuilder sb = generateAddDetailedTooltipHeader();

        generateAddDetailedTooltipName(name, sb);
        generateAddDetailedTooltipStartDate(startDate, sb);
        generateAddDetailedTooltipEndDate(endDate, sb);
        generateAddDetailedTooltipRecurrence(rate, timePeriod, sb);
        generateAddDetailedTooltipPriority(priority, sb);
        
        return sb.toString();
    }

    /**
     * @param priority
     * @param sb
     */
    private void generateAddDetailedTooltipPriority(Optional<String> priority, StringBuilder sb) {
        if (priority.isPresent()) {
            sb.append("\n\tPriority:\t" + priority.get());
        }
    }

    /**
     * @param rate
     * @param timePeriod
     * @param sb
     */
    private void generateAddDetailedTooltipRecurrence(Optional<String> rate, Optional<String> timePeriod,
            StringBuilder sb) {
        
        if (!timePeriod.isPresent()) {
            return;
        }
        
        String timePeriodStr = timePeriod.get();
        if (rate.isPresent()) {
            String recurRate = rate.get();
            sb.append("\n\tRecurrence Rate:\t" + "every " + recurRate + " " + timePeriodStr);
        } else {
            sb.append("\n\tRecurrence Rate:\t" + "every 1 " + timePeriodStr);
        }
    }

    /**
     * @param endDate
     * @param sb
     */
    private void generateAddDetailedTooltipEndDate(Optional<String> endDate, StringBuilder sb) {
        if (endDate.isPresent()) {
            sb.append("\n\tEnd Date:\t\t" + endDate.get());
        }
    }

    /**
     * @param startDate
     * @param sb
     */
    private void generateAddDetailedTooltipStartDate(Optional<String> startDate, StringBuilder sb) {
        if (startDate.isPresent()) {
            sb.append("\n\tStart Date:\t" +startDate.get());
        }
    }

    /**
     * @param name
     * @param sb
     */
    private void generateAddDetailedTooltipName(Optional<String> name, StringBuilder sb) {
        if (name.isPresent()) {
            sb.append("\n\tName:\t" + name.get());
        }
    }

    /**
     * @return
     */
    private StringBuilder generateAddDetailedTooltipHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.TOOL_TIP);
        sb.append("\n\tAdding task: ");
        return sb;
    }

    /**
     * @param trimmedArgs
     * @return
     * @throws IllegalValueException
     */
    private HashMap<String, Optional<String>> retrieveAddFieldsFromArgs(String trimmedArgs) throws IllegalValueException {
        return new CommandParserHelper().prepareAdd(trimmedArgs);
    }
}
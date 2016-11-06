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

import org.apache.commons.lang.StringUtils;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT;


/**
 * Parses user input.
 */
public class CommandParser {
    
    private static final String EMPTY_SPACE_FOR_NAME_NO_CHANGE = " ";
    private static final String STRING_REGEX_ONE_OR_MORE_WHITESPACE = "\\s+";
    private static final String STRING_ONE_SPACE = " ";
    
    private static final String DETAILED_TOOLTIP_RECURRENCE_SPECIAL_PREFIX = "\n\tRecurrence Rate:\t";
    private static final String DETAILED_TOOLTIP_RESET = "RESET";
    private static final String DETAILED_TOOLTIP_NO_CHANGE = "No Change";
    private static final String DETAILED_TOOLTIP_PRIORITY_PREFIX = "\n\tPriority:\t";
    private static final String DETAILED_TOOLTIP_RECURRENCE_PREFIX = "\n\tRecurrence Rate:\tevery ";
    private static final String DETAILED_TOOLTIP_END_DATE_PREFIX = "\n\tEnd Date:\t\t";
    private static final String DETAILED_TOOLTIP_START_DATE_PREFIX = "\n\tStart Date:\t";
    private static final String DETAILED_TOOLTIP_NAME_PREFIX = "\n\tName:\t";
    private static final String ADD_DETAILED_TOOLTIP_HEADER = "\n\tAdding task: ";

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
    
    private static final String RESET_START_KEYWORD = "start";
    private static final String RESET_END_KEYWORD = "end";
    private static final String RESET_RECURRENCE_KEYWORD = "repeat";
    private static final String RESET_PRIORITY_KEYWORD = "priority";
    
    private static final String NEWLINE_STRING = "\n";

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
            logger.finer("Trimmed argument is empty, returning IncorrectCommand");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.getMessageUsage()));
        }
        
        try {  
            HashMap<String, Optional<String>> extractedValues = retrieveAddFieldsFromArgs(argsTrimmed);
            logger.finer("Exiting CommandParser, prepareAdd()");
            return new AddCommand(extractedValues);
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareAdd(), returning IncorrectCommand");
            //TODO ive auto new line?
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ive.getMessage() + "\n" + 
                    AddCommand.getMessageUsage()));
        }
    }

    //@@author A0139552B
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        assert args != null;

        int index = ZERO;     
        String resetField = null;
        String argsTrimmed = args.trim();
        String[] indexSplit = argsTrimmed.split(STRING_ONE_SPACE);
        String indexNum = indexSplit[ZERO];

        if(indexSplit.length == ONE){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
            
        try{
            index = Integer.parseInt(indexNum);
        } catch (NumberFormatException e){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
           
        String[] resetSplit = argsTrimmed.substring(TWO).split("-reset");
        String argumentsWithoutIndexAndReset = STRING_ONE_SPACE + resetSplit[ZERO];        
        logger.finer("Entering CommandParser, prepareEdit()");
                       
        try {
            HashMap<String, Optional<String>> extractedValues = new CommandParserHelper().prepareEdit(argumentsWithoutIndexAndReset);
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
        if (!indexes.isPresent()) {
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
        if (!indexes.isPresent()) {
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
        final String[] keywords = matcher.group("keywords").split(STRING_REGEX_ONE_OR_MORE_WHITESPACE);
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ListCommand(keywordSet);
    }
    
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
        String[] indexesArray = indexes.split(STRING_ONE_SPACE);
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
        final String[] keywords = matcher.group("keywords").split(STRING_REGEX_ONE_OR_MORE_WHITESPACE);
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    
    //@@author A0093960X
    /**
     * Parses an incomplete user input to determine the most appropriate tooltip for the user to see.
     * The tooltip depends on the command that the user is trying to execute (which this parser tries to 
     * determine).
     * 
     * @param userInput user input string
     * @param isViewingDoneList boolean representing if the user's current view is the done task list
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
        boolean shouldInterpretAsNoArgs = commandWord.equals(userInput);
         
        return getTooltip(arguments, commandWord, shouldInterpretAsNoArgs, isViewingDoneList);
    }

    /**
     * Returns the list of tooltips that matches the current user's input.
     * 
     * @param toolTips list of tooltips
     * @param commandWord the user input command word
     * @param shouldInterpretAsNoArgs boolean representing whether user input has no arguments
     */
    private String getTooltip(final String arguments, final String commandWord, boolean shouldInterpretAsNoArgs, boolean isViewingDoneList) { 
        if (isViewingDoneList) {
            return getTooltipForDoneList(commandWord, shouldInterpretAsNoArgs);          
        }
        else {
            return getTooltipForUndoneList(arguments, commandWord, shouldInterpretAsNoArgs);
        }
    }
    
    private String getTooltipForUndoneList(final String arguments, final String commandWord, boolean inputHasNoArgs) {
        // tooltip depends on whether the input command has arguments
        if (inputHasNoArgs) {
            return getTooltipForCmdWithNoArgsUndoneList(arguments, commandWord);
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

        if (StringUtils.startsWith(ClearCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(DeleteCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DeleteCommand.TOOL_TIP);
        }   
        if (StringUtils.startsWith(ExitCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(FindCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(HelpCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(ListCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(RedoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(RedoCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(SelectCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(StoreCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(StoreCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(UndoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(UndoCommand.TOOL_TIP);
        }
        

        boolean hasNoTooltipMatches = tooltips.isEmpty();
        if (hasNoTooltipMatches) {
            return Messages.MESSAGE_DONE_LIST_RESTRICTED_COMMANDS;
        }

        String combinedTooltip = String.join(NEWLINE_STRING, tooltips);
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
    private String getTooltipForCmdWithNoArgsUndoneList(final String arguments, final String commandWord) {
        List<String> tooltips = new ArrayList<String>();
        
        if (StringUtils.startsWith(AddCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(AddCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(ClearCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(DeleteCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DeleteCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(DoneCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(DoneCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(EditCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(EditCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(ExitCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(FindCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(HelpCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(ListCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(RedoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(RedoCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(SelectCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(StoreCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(StoreCommand.TOOL_TIP);
        }
        if (StringUtils.startsWith(UndoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(UndoCommand.TOOL_TIP);
        }
          
        if (tooltips.isEmpty()) {
            return prepareAddDetailedTooltip(commandWord + arguments);      
        } 
        
        return String.join(NEWLINE_STRING, tooltips);
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
        assert trimmedArgs != null;
        
        String[] splitIndexFromOtherArgs = trimmedArgs.split(STRING_REGEX_ONE_OR_MORE_WHITESPACE, 2);
        String indexToEdit = splitIndexFromOtherArgs[ZERO];
        
        try {
            Integer.parseInt(indexToEdit);
        } catch (NumberFormatException e) {
            return EditCommand.TOOL_TIP + "\n" + "Please enter a number for the index.\n";
        }
        

        String argumentsWithoutIndex;
        if (splitIndexFromOtherArgs.length == 1) {
            argumentsWithoutIndex = splitIndexFromOtherArgs[ZERO];
        } else {
            argumentsWithoutIndex = splitIndexFromOtherArgs[ONE];

        }
        
        logger.info(argumentsWithoutIndex);
        
        String resetField = null;
        String[] resetSplit = argumentsWithoutIndex.split(RESET_KEYWORD);
        
        logger.info(Arrays.asList(resetSplit).toString());
 
        
        boolean isResettingStartDate = false, isResettingEndDate = false, isResettingRecurrence = false, 
                isResettingPriority = false;
                    
        
        String beforeResetSplit = "";
        if (resetSplit.length != 0) {
            beforeResetSplit = resetSplit[0];
        }
        
        HashMap<String, Optional<String>> fieldMap = retrieveEditFieldsFromArgs(beforeResetSplit);
        
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
            String[] resetFieldNames = resetField.split(STRING_REGEX_ONE_OR_MORE_WHITESPACE);
            
            for (String resetFieldStr : resetFieldNames) {
                if (resetFieldStr.equals(RESET_START_KEYWORD)) {
                    isResettingStartDate = true;
                } else if (resetFieldStr.equals(RESET_END_KEYWORD)) {
                    isResettingEndDate = true;
                } else if (resetFieldStr.equals(RESET_RECURRENCE_KEYWORD)) {
                    isResettingRecurrence = true;
                } else if (resetFieldStr.equals(RESET_PRIORITY_KEYWORD)) {
                    isResettingPriority = true;
                }
            }
        }

                    
        StringBuilder sb = new StringBuilder();
        sb.append(EditCommand.TOOL_TIP);
        sb.append("\n\tEditing task at INDEX " + indexToEdit + ": ");

        if (name.isPresent() && trimmedArgs.length()>1 && !name.get().isEmpty()) {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX + name.get());
        } else {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX +  DETAILED_TOOLTIP_NO_CHANGE);
        }
        
        if (isResettingStartDate) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (startDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + startDate.get());
        } else {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
        
        if (isResettingEndDate) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (endDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + endDate.get());
        } else {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
        
        if (isResettingRecurrence) {
            sb.append(DETAILED_TOOLTIP_RECURRENCE_SPECIAL_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (timePeriod.isPresent()) {
            if (rate.isPresent()) {
                String recurRate = rate.get();
                sb.append(DETAILED_TOOLTIP_RECURRENCE_PREFIX + recurRate + STRING_ONE_SPACE + timePeriod.get());
            } else {
                sb.append(DETAILED_TOOLTIP_RECURRENCE_PREFIX + timePeriod.get());
            }
        } else {
            sb.append(DETAILED_TOOLTIP_RECURRENCE_SPECIAL_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
        
        if (isResettingPriority) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (!priority.get().equals("null")) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + priority.get());
        } else {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
        
        return sb.toString();
        
    }

    /**
     * @param resetSplit
     * @return
     * @throws IllegalValueException
     */
    private HashMap<String, Optional<String>> retrieveEditFieldsFromArgs(String beforeResetSplit)
            throws IllegalValueException {
        return new CommandParserHelper().prepareEdit(EMPTY_SPACE_FOR_NAME_NO_CHANGE + beforeResetSplit);
    }

    private String prepareAddDetailedTooltip(final String arguments) {
        try {
            if (arguments.isEmpty()) {
                return AddCommand.TOOL_TIP;
            }
            
            String trimmedArgs = arguments.trim();
            return generateAddDetailedTooltip(trimmedArgs);
        } catch (IllegalValueException e) {
            logger.info("Illegal add arguments passed for detailed tooltip, showing regular add tooltip instead");
            return AddCommand.TOOL_TIP;
        }

    }
    
    private String prepareEditDetailedTooltip(final String arguments) {
        try {
            
            // should not use exceptions for this
            if (arguments.isEmpty()) {
                return EditCommand.TOOL_TIP;
            }
            
            String trimmedArgs = arguments.trim();
            return generateEditDetailedTooltip(trimmedArgs);       
        } catch (IllegalValueException e) {
            logger.info("Illegal edit arguments passed for detailed tooltip, showing regular edit tooltip instead");
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
     * Generate the add detailed tooltip header and return it as a StringBuilder.
     * 
     * @return the StringBuilder object containing the add detailed tooltip header
     */
    private StringBuilder generateAddDetailedTooltipHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.TOOL_TIP);
        sb.append(ADD_DETAILED_TOOLTIP_HEADER);
        return sb;
    }

    /**
     * @param name
     * @param sb
     */
    private void generateAddDetailedTooltipName(Optional<String> name, StringBuilder sb) {
        if (name.isPresent()) {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX + name.get());
        }
    }

    /**
     * @param startDate
     * @param sb
     */
    private void generateAddDetailedTooltipStartDate(Optional<String> startDate, StringBuilder sb) {
        if (startDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + startDate.get());
        }
    }

    /**
     * @param endDate
     * @param sb
     */
    private void generateAddDetailedTooltipEndDate(Optional<String> endDate, StringBuilder sb) {
        if (endDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + endDate.get());
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
            sb.append(DETAILED_TOOLTIP_RECURRENCE_PREFIX + recurRate + STRING_ONE_SPACE + timePeriodStr);
        } else {
            sb.append(DETAILED_TOOLTIP_RECURRENCE_PREFIX + timePeriodStr);
        }
    }

    /**
     * @param priority
     * @param sb
     */
    private void generateAddDetailedTooltipPriority(Optional<String> priority, StringBuilder sb) {
        if (priority.isPresent()) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + priority.get());
        }
    }

    /**
     * @param trimmedArgs
     * @return
     * @throws IllegalValueException
     */
    private HashMap<String, Optional<String>> retrieveAddFieldsFromArgs(String trimmedArgs)
            throws IllegalValueException {
        CommandParserHelper cmdParserHelper = new CommandParserHelper();
        return cmdParserHelper.prepareAdd(trimmedArgs);
    }
}
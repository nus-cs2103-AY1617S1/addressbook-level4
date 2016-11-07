package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.logging.Level;
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
    private static final String TARGET_INDEX_KEYWORD = "targetIndex";
    
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
     * @param args  Full command args string.
     * @return      The prepared command.
     */
    private Command prepareAdd(String args){
        assert args != null;
        
        logger.finer("Entering CommandParser, prepareAdd()");
        String argsTrimmed = args.trim();
        if(argsTrimmed.isEmpty()) {
            logger.log(Level.FINE, "Trimmed argument is empty, returning IncorrectCommand");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.getMessageUsage()));
        }
        
        try {  
            HashMap<String, Optional<String>> extractedValues = retrieveAddFieldsFromArgs(argsTrimmed);
            logger.log(Level.FINE, "Exiting CommandParser, prepareAdd()");
            return new AddCommand(extractedValues);
        } catch (IllegalValueException ive) {
            logger.log(Level.FINE, "IllegalValueException caught in CommandParser, prepareAdd(), returning IncorrectCommand");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ive.getMessage() + NEWLINE_STRING + 
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
    //@@author
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
            return new ListCommand(new String());
        }

        final String keyword = matcher.group("keywords");
        return new ListCommand(keyword);
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

        String index = matcher.group(TARGET_INDEX_KEYWORD);
        if (!StringUtil.isUnsignedInteger(index)) {
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

        String indexes = matcher.group(TARGET_INDEX_KEYWORD);
        String[] indexesArray = indexes.split(STRING_ONE_SPACE);
        List<Integer> indexesToHandle = new ArrayList<Integer>();
        for (String index : indexesArray) {
            if (StringUtil.isUnsignedInteger(index)) {
                indexesToHandle.add(Integer.parseInt(index));
            }
        }
        
        return (indexesToHandle.isEmpty()) ? Optional.empty() : Optional.of(indexesToHandle);

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
    
    /**
     * Parses a user input to determine the appropriate tooltip to display to
     * the user.<br>
     * Asserts that the specified userInput is not null.
     * 
     * @param userInput The user input String
     * @param isViewingDoneList The boolean representing if the user's current
     *            view is the done task list
     * @return The tooltip String
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
     * Returns the tooltip taking into consideration the arguments String,
     * commandWord String, if the input should be interpreted as no arguments
     * and if the current view is the done list view.
     * 
     * @param arguments The parsed arguments String of the user input
     * @param commandWord The parsed command word String of the user input
     * @param shouldInterpretAsNoArgs A boolean representing if the current
     *            input should be interpreted as not having any arguments
     * @param isViewingDoneList A boolean representing if the current view is
     *            the done list view.
     * @return The tooltip String that is appropriate for the specified
     *         parameters
     */
    private String getTooltip(final String arguments, final String commandWord,
            boolean shouldInterpretAsNoArgs, boolean isViewingDoneList) {

        if (isViewingDoneList) {
            return getTooltipForDoneList(commandWord, shouldInterpretAsNoArgs);
        } else {
            return getTooltipForUndoneList(arguments, commandWord, shouldInterpretAsNoArgs);
        }

    }

    /**
     * Returns the tooltip for the undone list view, taking into consideration
     * the arguments String, commandWord String, if the input should be
     * interpreted as no arguments.
     * 
     * @param arguments The parsed arguments String of the user input
     * @param commandWord The parsed command word String of the user input
     * @param shouldInterpretAsNoArgs A boolean representing if the current
     *            input should be interpreted as not having any arguments
     * @return The tooltip String that is appropriate for the specified
     *         parameters
     */
    private String getTooltipForUndoneList(final String arguments, final String commandWord,
            boolean shouldInterpretAsNoArgs) {

        if (shouldInterpretAsNoArgs) {
            return getTooltipForCmdWithNoArgsUndoneList(arguments, commandWord);
        } else {
            return getTooltipForCmdWithArgsUndoneList(arguments, commandWord);
        }
    }

    /**
     * Returns the tooltip for the done list view, taking into consideration the
     * arguments String, commandWord String, if the input should be interpreted
     * as no arguments.
     * 
     * @param commandWord The parsed command word String of the user input
     * @param shouldInterpretAsNoArgs A boolean representing if the current
     *            input should be interpreted as not having any arguments
     * @return The tooltip String that is appropriate for the specified
     *         parameters
     */
    private String getTooltipForDoneList(final String commandWord, boolean shouldInterpretAsNoArgs) {

        if (shouldInterpretAsNoArgs) {
            return getTooltipForCmdWithNoArgsDoneList(commandWord);
        } else {
            return getTooltipForCmdWithArgsDoneList(commandWord);
        }
    }

    /**
     * Returns the tooltip for the done list view and assuming command has no
     * arguments, appropriate for the specified commandWord.
     * 
     * @param commandWord The parsed command word String of the user input
     * @return The tooltip String that is appropriate for the specified
     *         parameters
     */
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
    
    /**
     * Returns the tooltip for the done list view and assuming command has
     * arguments, appropriate for the specified commandWord.
     * 
     * @param commandWord The parsed command word String of the user input
     * @return The tooltip String that is appropriate for the specified
     *         parameters
     */
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
     * Returns the tooltip for the undone list view and assuming command has no
     * arguments, appropriate for the specified commandWord and arguments.
     * 
     * @param arguments The parsed arguments String of the user input
     * @param commandWord The parsed command word String of the user input
     * @return The tooltip String that is appropriate for the specified
     *         parameters
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
     * Returns the tooltip for the undone list view and assuming command has
     * arguments, appropriate for the specified commandWord and arguments.
     * 
     * @param arguments The parsed arguments String of the user input
     * @param commandWord The parsed command word String of the user input
     * @return The tooltip String that is appropriate for the specified
     *         parameters
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
     * Generate the detailed tooltip for the edit command using the specified userInput String.
     * 
     * @param userInput The user input String to generate the detailed edit command tooltip with
     * @return The detailed edit tooltip for the given userInput String
     * @throws IllegalValueException If the specified userInput cannot generate a valid detailed edit tooltip
     */
    private String generateEditDetailedTooltip(String userInput) throws IllegalValueException {
        assert userInput != null;
        
        userInput = userInput.trim();
        String[] splitIndexFromOtherArgs = userInput.split(STRING_REGEX_ONE_OR_MORE_WHITESPACE, 2);
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
        
        
        String resetField = null;
        String[] resetSplit = argumentsWithoutIndex.split(RESET_KEYWORD);
 
        
        String beforeResetSplitString = "";
        if (resetSplit.length != 0) {
            beforeResetSplitString = resetSplit[0];
        }
        
        
        if(resetSplit.length == TWO){
            resetField = resetSplit[ONE];
        }
        
        HashMap<String, Boolean> resetMap = generateResetMapFromInput(resetField);

        return generateEditDetailedTooltipFromMap(userInput, indexToEdit, beforeResetSplitString, resetMap);
        
    }

    /**
     * Generates the reset map from the given resetField String
     * 
     * @param resetField The reset field portion of the input String
     * @return The reset map
     */
    private HashMap<String, Boolean> generateResetMapFromInput(String resetField) {
        HashMap<String, Boolean> resetMap = initializeResetMap();
        
        if (resetField != null) {
            String[] resetFieldNames = resetField.split(STRING_REGEX_ONE_OR_MORE_WHITESPACE);
            
            for (String resetFieldStr : resetFieldNames) {
                if (resetFieldStr.equals(RESET_START_KEYWORD)) {
                    resetMap.put(MAP_START_DATE, true);
                } else if (resetFieldStr.equals(RESET_END_KEYWORD)) {
                    resetMap.put(MAP_END_DATE, true);
                } else if (resetFieldStr.equals(RESET_RECURRENCE_KEYWORD)) {
                    resetMap.put(MAP_RECURRENCE_RATE, true);
                } else if (resetFieldStr.equals(RESET_PRIORITY_KEYWORD)) {
                    resetMap.put(MAP_PRIORITY, true);
                }
            }
        }
        return resetMap;
    }

    /**
     * Initializes and returns a reset map.
     * @return The initialized reset map
     */
    private HashMap<String, Boolean> initializeResetMap() {
        HashMap<String, Boolean> resetMap = new HashMap<String, Boolean>();

        resetMap.put(MAP_START_DATE, false);
        resetMap.put(MAP_END_DATE, false);
        resetMap.put(MAP_RECURRENCE_RATE, false);
        resetMap.put(MAP_PRIORITY, false);
        return resetMap;
    }
    
    /**
     * Generates the edit detailed tooltip from the specified field map and reset map.
     * 
     * @param userInput The full user input
     * @param indexToEdit The edit index
     * @param beforeResetSplitString The arguments of the edit command before the reset keyword
     * @param resetMap The map that contains the mapping if a field is being reset
     * @return The detailed edit tooltip
     * @throws IllegalValueException If the specified userInput cannot generate a valid detailed edit tooltip
     */
    private String generateEditDetailedTooltipFromMap(String userInput, String indexToEdit,
            String beforeResetSplitString, HashMap<String, Boolean> resetMap) throws IllegalValueException {
        HashMap<String, Optional<String>> fieldMap = retrieveEditFieldsFromArgs(beforeResetSplitString);

        StringBuilder sb = generateEditDetailedTooltipHeader(indexToEdit);

        generateEditDetailedTooltipName(userInput, fieldMap, sb);
        generateEditDetailedTooltipStartDate(resetMap, fieldMap, sb);
        generateEditDetailedTooltipEndDate(resetMap, fieldMap, sb);
        generateEditDetailedTooltipRecurrence(resetMap, fieldMap, sb);
        generateEditDetailedTooltipPriority(resetMap, fieldMap, sb);

        return sb.toString();
    }

    /**
     * Generate the priority field in the edit detailed tooltip.
     * 
     * @param isResettingPriority A boolean representing if the current edit input wants to reset the priority field
     * @param priority The parsed priority String from the user input
     * @param sb The StringBuilder to build on
     */
    private void generateEditDetailedTooltipPriority(Map<String, Boolean> resetMap, Map<String, Optional<String>> fieldMap,
            StringBuilder sb) {
        Optional<String> priority = fieldMap.get(MAP_PRIORITY);
        boolean isResettingPriority = resetMap.get(MAP_PRIORITY);

        if (isResettingPriority) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (!priority.get().equals("null")) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + priority.get());
        } else {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
    }

    /**
     * Generate the recurrence field in the edit detailed tooltip.
     * 
     * @param isResettingRecurrence A boolean representing if the current edit
     *            input wants to reset the recurrence field
     * @param rate The parsed rate String from the user input
     * @param timePeriod The parsed timePeriod String from the user input
     * @param sb The StringBuilder to build on
     */
    private void generateEditDetailedTooltipRecurrence(Map<String, Boolean> resetMap,
            Map<String, Optional<String>> fieldMap, StringBuilder sb) {
        Optional<String> rate = fieldMap.get(MAP_RECURRENCE_RATE);
        Optional<String> timePeriod = fieldMap.get(MAP_RECURRENCE_TIME_PERIOD);
        boolean isResettingRecurrence = resetMap.get(MAP_RECURRENCE_RATE);

        
        if (isResettingRecurrence) {
            sb.append(DETAILED_TOOLTIP_RECURRENCE_SPECIAL_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (timePeriod.isPresent()) {
            if (rate.isPresent()) {
                String recurRate = rate.get();
                sb.append(
                        DETAILED_TOOLTIP_RECURRENCE_PREFIX + recurRate + STRING_ONE_SPACE + timePeriod.get());
            } else {
                sb.append(DETAILED_TOOLTIP_RECURRENCE_PREFIX + timePeriod.get());
            }
        } else {
            sb.append(DETAILED_TOOLTIP_RECURRENCE_SPECIAL_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
    }

    /**
     * Generate the end date field in the edit detailed tooltip.
     * 
     * @param isResettingEndDate A boolean representing if the current edit input wants to reset the end date field
     * @param endDate The parsed end date String from the user input
     * @param sb The StringBuilder to build on
     */
    private void generateEditDetailedTooltipEndDate(Map<String, Boolean> resetMap, Map<String, Optional<String>> fieldMap,
            StringBuilder sb) {
        Optional<String> endDate = fieldMap.get(MAP_END_DATE);
        boolean isResettingEndDate = resetMap.get(MAP_END_DATE);
        
        if (isResettingEndDate) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (endDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + endDate.get());
        } else {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
    }

    /**
     * Generate the start date field in the edit detailed tooltip.
     * 
     * @param isResettingStartDate A boolean representing if the current edit
     *            input wants to reset the start date field
     * @param startDate The parsed start date String from the user input
     * @param sb The StringBuilder to build on
     */
    private void generateEditDetailedTooltipStartDate(Map<String, Boolean> resetMap,
            Map<String, Optional<String>> fieldMap, StringBuilder sb) {
        Optional<String> startDate = fieldMap.get(MAP_START_DATE);
        boolean isResettingStartDate = resetMap.get(MAP_START_DATE);
        
        if (isResettingStartDate) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + DETAILED_TOOLTIP_RESET);
        } else if (startDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + startDate.get());
        } else {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
    }

    /**
     * Generate the name field in the edit detailed tooltip.
     * 
     * @param trimmedArgs The trimmed user input arguments
     * @param name The parsed name String from the user input
     * @param sb The StringBuilder to build on
     */
    private void generateEditDetailedTooltipName(String trimmedArgs, Map<String, Optional<String>> fieldMap,
            StringBuilder sb) {
        Optional<String> name = fieldMap.get(MAP_NAME);

        if (name.isPresent() && trimmedArgs.length() > 1 && !name.get().isEmpty()) {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX + name.get());
        } else {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX + DETAILED_TOOLTIP_NO_CHANGE);
        }
    }

    /**
     * Generate the header of the edit detailed tooltip, with the specified indexToEdit.
     * 
     * @param indexToEdit The parsed index the input is trying to edit
     * @return The StringBuilder to build on for the rest of the edit detailed tooltip
     */
    private StringBuilder generateEditDetailedTooltipHeader(String indexToEdit) {
        StringBuilder sb = new StringBuilder();
        sb.append(EditCommand.TOOL_TIP);
        sb.append("\n\tEditing task at INDEX " + indexToEdit + ": ");
        return sb;
    }

    /**
     * Retrieve the edit fields from the user input.
     * 
     * @param beforeResetSplit the user input String arguments before the reset keyword
     * @return the HashMap the maps the edit field to the parsed String matching that field, retrieved from the input
     * @throws IllegalValueException If the specified userInput cannot generate a valid detailed edit tooltip
     */
    private HashMap<String, Optional<String>> retrieveEditFieldsFromArgs(String beforeResetSplit)
            throws IllegalValueException {
        return new CommandParserHelper().prepareEdit(EMPTY_SPACE_FOR_NAME_NO_CHANGE + beforeResetSplit);
    }

    /**
     * Get the add detailed tooltip for this arguments String.
     * 
     * @param arguments The arguments of the add command String
     * @return The add tooltip appropriate for the given arguments
     */
    private String prepareAddDetailedTooltip(final String arguments) {
        try {
            if (arguments.isEmpty()) {
                return AddCommand.TOOL_TIP;
            }
            
            return generateAddDetailedTooltip(arguments);
        } catch (IllegalValueException e) {
            logger.info("Illegal add arguments passed for detailed tooltip, showing regular add tooltip with warning instead");
            return String.join(NEWLINE_STRING, AddCommand.TOOL_TIP, "Instant parser is unable to determine your current input as it does not match a valid command input.");
        }

    }
    
    /**
     * Get the edit detailed tooltip for this arguments String.
     * 
     * @param arguments The arguments of the edit command String
     * @return The edit tooltip appropriate for the given arguments
     */
    private String prepareEditDetailedTooltip(final String arguments) {
        try {
            
            // should not use exceptions for this
            if (arguments.isEmpty()) {
                return EditCommand.TOOL_TIP;
            }
            
            return generateEditDetailedTooltip(arguments);       
        } catch (IllegalValueException e) {
            logger.info("Illegal edit arguments passed for detailed tooltip, showing regular edit tooltip with warning instead");
            return String.join(NEWLINE_STRING, EditCommand.TOOL_TIP, "Instant parser is unable to determine your current input as it does not match a valid command input.");
        }
    }

    /**
     * Generates the add detailed tooltip from the given input String.
     * 
     * @param inputToGenerateTooltip The input to generate the tooltip with
     * @return The add detailed tooltip generated from the input String
     * @throws IllegalValueException If the input String cannot generate a valid
     *             detailed tooltip
     */
    private String generateAddDetailedTooltip(String inputToGenerateTooltip) throws IllegalValueException {

        HashMap<String, Optional<String>> fieldMap = retrieveAddFieldsFromArgs(inputToGenerateTooltip);
        return generateAddDetailedTooltipFromMap(fieldMap);
    }

    /**
     * Generates the add detailed tooltip from the specified field map.
     * 
     * @param fieldMap The map containing the mapping from the fields to the user input
     * @return The add detailed tooltip
     */
    private String generateAddDetailedTooltipFromMap(HashMap<String, Optional<String>> fieldMap) {
        StringBuilder sb = generateAddDetailedTooltipHeader();
        generateAddDetailedTooltipName(fieldMap, sb);
        generateAddDetailedTooltipStartDate(fieldMap, sb);
        generateAddDetailedTooltipEndDate(fieldMap, sb);
        generateAddDetailedTooltipRecurrence(fieldMap, sb);
        generateAddDetailedTooltipPriority(fieldMap, sb);
        return sb.toString();
    }

    /**
     * Generate the add detailed tooltip header and return it as a
     * StringBuilder.
     * 
     * @return The StringBuilder object containing the add detailed tooltip
     *         header
     */
    private StringBuilder generateAddDetailedTooltipHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(AddCommand.TOOL_TIP);
        sb.append(ADD_DETAILED_TOOLTIP_HEADER);
        return sb;
    }

    /**
     * Generates the add detailed tooltip name field from the given Optional
     * name String into the specified StringBuilder.
     * 
     * @param fieldMap the Map containing the mapping of the fields to the user input
     * @param sb The StringBuilder to build upon
     */
    private void generateAddDetailedTooltipName(Map<String, Optional<String>> fieldMap, StringBuilder sb) {
        Optional<String> name = fieldMap.get(MAP_NAME);
        if (name.isPresent()) {
            sb.append(DETAILED_TOOLTIP_NAME_PREFIX + name.get());
        }
    }

    /**
     * Generates the add detailed tooltip start date field from the given
     * Optional start date String into the specified StringBuilder.
     * 
     * @param fieldMap the Map containing the mapping of the fields to the user input
     * @param sb The StringBuilder to build upon
     */
    private void generateAddDetailedTooltipStartDate(Map<String, Optional<String>> fieldMap, StringBuilder sb) {
        Optional<String> startDate = fieldMap.get(MAP_START_DATE);
        if (startDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_START_DATE_PREFIX + startDate.get());
        }
    }

    /**
     * Generates the add detailed tooltip end date field from the given Optional
     * end date String into the specified StringBuilder.
     * 
     * @param fieldMap the Map containing the mapping of the fields to the user input
     * @param sb The StringBuilder to build upon
     */
    private void generateAddDetailedTooltipEndDate(Map<String, Optional<String>> fieldMap, StringBuilder sb) {
        Optional<String> endDate = fieldMap.get(MAP_END_DATE);
        if (endDate.isPresent()) {
            sb.append(DETAILED_TOOLTIP_END_DATE_PREFIX + endDate.get());
        }
    }

    /**
     * Generates the add detailed tooltip recurrence field from the given
     * Optional recurrence Strings into the specified StringBuilder.
     * 
     * @param fieldMap the Map containing the mapping of the fields to the user input
     * @param sb The StringBuilder to build upon
     */
    private void generateAddDetailedTooltipRecurrence(Map<String, Optional<String>> fieldMap, StringBuilder sb) {

        Optional<String> rate = fieldMap.get(MAP_RECURRENCE_RATE);
        Optional<String> timePeriod = fieldMap.get(MAP_RECURRENCE_TIME_PERIOD);

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
     * Generates the add detailed tooltip priority field from the given Optional
     * priority String into the specified StringBuilder.
     * 
     * @param fieldMap the Map containing the mapping of the fields to the user input
     * @param sb The StringBuilder to build upon
     */
    private void generateAddDetailedTooltipPriority(Map<String, Optional<String>> fieldMap,
            StringBuilder sb) {
        Optional<String> priority = fieldMap.get(MAP_PRIORITY);
        
        if (priority.isPresent()) {
            sb.append(DETAILED_TOOLTIP_PRIORITY_PREFIX + priority.get());
        }
    }

    /**
     * Retrieve the add command input fields from the given arguments string
     * 
     * @param argsToGenerateTooltip The arguments String to generate the tooltip
     *            with
     * @return The HashMap that maps the add command fields to the String that
     *         matches that input field
     * @throws IllegalValueException If the specified userInput cannot generate
     *             a valid detailed edit tooltip
     */
    private HashMap<String, Optional<String>> retrieveAddFieldsFromArgs(String argsToGenerateTooltip)
            throws IllegalValueException {
        argsToGenerateTooltip = argsToGenerateTooltip.trim();
        CommandParserHelper cmdParserHelper = new CommandParserHelper();
        return cmdParserHelper.prepareAdd(argsToGenerateTooltip);
    }

    // @@author
    public static String getNewLineString() {
        return NEWLINE_STRING;
    }
}
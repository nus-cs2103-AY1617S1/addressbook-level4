package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.model.item.DateTime;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.logging.Level;
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

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return prepareHelp(arguments);

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
            HashMap<String, Optional<String>> extractedValues = new CommandParserHelper().prepareAdd(argsTrimmed);
            
            logger.finer("Exiting CommandParser, prepareAdd()");
            /*logger.log(Level.FINEST, "taskName, startDate, endDate, rate, timePeriod and "
                    + "priority have these values respectively:", 
                    new Object[] {taskName, startDate, endDate, rate, timePeriod, priority});*/
            return new AddCommand(extractedValues.get("taskName"), extractedValues.get("startDate"), 
                    extractedValues.get("endDate"), extractedValues.get("rate"), 
                    extractedValues.get("timePeriod"), extractedValues.get("priority"));
            
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareAdd()");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    AddCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }
    }

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
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
		
    	int index = ZERO;	 
   	 	
    	args = args.trim();
   	 	//System.out.println(args);   	 
   	 	String[] parts = args.split(" ");
   	 	String indexNum = parts[ZERO];

   	 	if(parts.length == ONE){
   	 		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
   	 	}
   	 
   	 	index = Integer.parseInt(indexNum);
   	 	String[] split = args.substring(TWO).split("-reset");

   	 	String argsTrimmed = " " + split[ZERO];        
        String resetField = null;

        logger.finer("Entering CommandParser, prepareEdit()");
                       
        try {  
            HashMap<String, Optional<String>> extractedValues = new CommandParserHelper().prepareEdit(argsTrimmed);
            
            logger.finer("Exiting CommandParser, prepareEdit()");
            
            if(split.length == TWO){
               	resetField = split[ONE];
            }
            
            //System.out.println(extractedValues.get("taskName"));

            return new EditCommand(index, extractedValues.get("taskName"), extractedValues.get("startDate"), 
                    extractedValues.get("endDate"), extractedValues.get("rate"), 
                    extractedValues.get("timePeriod"), extractedValues.get("priority"),resetField);
            
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareEdit()");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    EditCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }            
    }

    /**
     * Parses arguments in the context of the delete person command.
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
    
    /**
     * Parses arguments in the context of the done person command.
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
     * Parses arguments in the context of the select person command.
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
    
    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args) {
        Boolean isListDoneCommand = false;
        
        if (args != null && args.trim().toLowerCase().equals("done")) {
            isListDoneCommand = true;
        }

        return new ListCommand(isListDoneCommand);
    }
    
    /**
     * Parses arguments in the context of the help command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareHelp(String args) {
        if (args != null) {
            return new HelpCommand(args.trim());
        }
        
        return new HelpCommand(HelpCommand.COMMAND_WORD);
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
    
    /**
     * Returns the specified indexes in the {@code command} IF any positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<List<Integer>> parseIndexes(String command) {
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
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
        if (indexesToHandle.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(indexesToHandle);

    }

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
    public String parseForTooltip(String userInput) {
        assert userInput != null;

        String trimmedUserInput = userInput.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedUserInput);       
        boolean invalidCommandFormat = !matcher.matches();
        
        if (invalidCommandFormat) {
            return MESSAGE_TOOLTIP_INVALID_COMMAND_FORMAT;
        }    
        
        final String commandWord = matcher.group("commandWord");
        boolean interpretAsNoArgs = commandWord.equals(userInput);
         
        return getTooltip(commandWord, interpretAsNoArgs);
    }

    /**
     * Returns the list of tooltips that matches the current user's input.
     * 
     * @param toolTips list of tooltips
     * @param commandWord the user input command word
     * @param inputHasNoArgs boolean representing whether user input has no arguments
     */
    private String getTooltip(final String commandWord, boolean inputHasNoArgs) {               
        // tooltip depends on whether the input command has arguments
        if (inputHasNoArgs) {
            return getTooltipForCmdWithNoArgs(commandWord);
        }
        else {
            return getTooltipForCmdWithArgs(commandWord);
        }
    }

    /**
     * Get the tooltip string appropriate for the current user input that has no arguments.
     * 
     * @param commandWord the command part of the user input
     * @return the tooltip
     */
    private String getTooltipForCmdWithNoArgs(final String commandWord) {
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
        if (StringUtil.isSubstringFromStart(UndoCommand.COMMAND_WORD, commandWord)) {
            tooltips.add(UndoCommand.TOOL_TIP);
        }
        
        boolean hasNoTooltipMatches = tooltips.isEmpty();       
        if (hasNoTooltipMatches) {
            return AddCommand.TOOL_TIP;
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
    private String getTooltipForCmdWithArgs(final String commandWord) {        
        if (commandWord.equals(AddCommand.COMMAND_WORD)) {
            return AddCommand.TOOL_TIP;
        }
        else if (commandWord.equals(ClearCommand.COMMAND_WORD)) {
            return ClearCommand.TOOL_TIP;
        }
        else if (commandWord.equals(DeleteCommand.COMMAND_WORD)) {
            return DeleteCommand.TOOL_TIP;
        }
        else if (commandWord.equals(DoneCommand.COMMAND_WORD)) {
            return DoneCommand.TOOL_TIP;
        }
        else if (commandWord.equals(EditCommand.COMMAND_WORD)) {
            return EditCommand.TOOL_TIP;
        }
        else if (commandWord.equals(ExitCommand.COMMAND_WORD)) {
            return ExitCommand.TOOL_TIP;
        }
        else if (commandWord.equals(FindCommand.COMMAND_WORD)) {
            return FindCommand.TOOL_TIP;
        }
        else if (commandWord.equals(HelpCommand.COMMAND_WORD)) {
            return HelpCommand.TOOL_TIP;
        }
        else if (commandWord.equals(ListCommand.COMMAND_WORD)) {
            return ListCommand.TOOL_TIP;
        }
        else if (commandWord.equals(RedoCommand.COMMAND_WORD)) {
            return RedoCommand.TOOL_TIP;
        }
        else if (commandWord.equals(SelectCommand.COMMAND_WORD)) {
            return SelectCommand.TOOL_TIP;
        }
        else if (commandWord.equals(UndoCommand.COMMAND_WORD)) {
            return UndoCommand.TOOL_TIP;
        }
        else {
            // default command is an add command
            return AddCommand.TOOL_TIP;
        }

    }
}
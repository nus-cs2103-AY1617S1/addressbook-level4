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
		
    	int index = 0;
	 
   	 	args = args.trim();
   	 	System.out.println(args);
   	 
   	 	String[] parts = args.split(" ");
   	 	String indexNum = parts[0];

   	 	if(parts.length == 1){
   	 		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
   	 	}
   	 
   	 	index = Integer.parseInt(indexNum);
   	 	String[] split = args.substring(2).split("-reset");

   	 	String argsTrimmed = " " + split[0];        
        String resetField = null;

        logger.finer("Entering CommandParser, prepareEdit()");
                
        if(argsTrimmed.isEmpty()) {
            logger.finer("Trimmed argument is empty");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        try {  
            HashMap<String, Optional<String>> extractedValues = new CommandParserHelper().prepareEdit(argsTrimmed);
            
            logger.finer("Exiting CommandParser, prepareEdit()");
            
            if(split.length == 2){
               	resetField = split[1];
            }
            
            System.out.println(extractedValues.get("taskName"));

            return new EditCommand(index, extractedValues.get("taskName"), extractedValues.get("startDate"), 
                    extractedValues.get("endDate"), extractedValues.get("rate"), 
                    extractedValues.get("timePeriod"), extractedValues.get("priority"),resetField);
            
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareEdit()");
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    EditCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }
            
        //return new EditCommand(index, taskName, startDate, endDate, rate, timePeriod, priority, resetField);
            
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
     * The tooltip depends on the command that the user is trying to execute (which this parser tries to determine).
     * 
     * @param userInput user input string
     * @return a list of Strings for tooltips
     */
    public List<String> parseIncompleteCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        ArrayList<String> toolTips = new ArrayList<String>();
        if (!matcher.matches()) {
            //TODO: make this thing make sense
            toolTips.add(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            return toolTips;
        }

        final String commandWord = matcher.group("commandWord");
        // reserve this maybe can use next time to match more precisely
        // final String arguments = matcher.group("arguments");
        updateMatchedCommands(toolTips, commandWord);
        
        // if no command matches, by default it is an add command so add the add command tooltip
        if (toolTips.isEmpty()){
            toolTips.add(AddCommand.TOOL_TIP);
        }
        return toolTips;      
    }

    /**
     * Updates the list of toolTips by checking if the user's input command word is a substring of the actual command word.
     * @param toolTips list of tooltips
     * @param commandWord the user input command word
     */
    private void updateMatchedCommands(List<String> toolTips, final String commandWord) {
        
        
        // checks all command words to see if there is a match/*
        if (StringUtil.isSubstringFromStart(AddCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(AddCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ClearCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(ClearCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(DeleteCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(DeleteCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(DoneCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(DoneCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(EditCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(EditCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ExitCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(ExitCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(FindCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(FindCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(HelpCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(HelpCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(ListCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(ListCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(RedoCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(RedoCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(SelectCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(SelectCommand.TOOL_TIP);
        }
        if (StringUtil.isSubstringFromStart(UndoCommand.COMMAND_WORD, commandWord)) {
            toolTips.add(UndoCommand.TOOL_TIP);
        }
        
    }
}
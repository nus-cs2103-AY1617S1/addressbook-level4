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
 * @@author A0138993L
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern EVENT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("\\[(?<name>[^;]+)"
                    + "(; (?<date>[^;]+))?"
                    + "(; (?<start>[^;]+))?"
                    + "(; (?<end>[^#]+))?"
                    + "\\]"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    private static final Pattern DEADLINE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^;]+)"
                    + "; (?<date>[^;#]+)"
                    + "(; (?<end>[^#]+))?"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    
    private static final Pattern TODO_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^#]+)"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags
    
    private static final Pattern EDIT_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
    		Pattern.compile("[E|D|T]\\d+ "
    				+ "(des|date|start|end|tag) "
    				+ ".+");
    
    private static final Pattern ADD_TAGS_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("[E|D|T]\\d+"
                    + " #[^#]+"
                    + "(?<tagArguments>(?: #[^#]+)*)");// variable number of tags
    
    private static final Pattern DELETE_ARGS_FORMAT = 
    		Pattern.compile("(([E|D|T]\\d+, )*([E|D|T]\\d+))|"
    				+ "([E|D|T]\\d+-[E|D|T]\\d+)");
    
    private static final Pattern COMPLETE_ARGS_FORMAT =
            Pattern.compile("(([E|D|T]\\d+, )*([E|D|T]\\d+))|"
                    + "([E|D|T]\\d+-[E|D|T]\\d+)");
    
    private static final Pattern SELECT_ARGS_FORMAT = 
    		Pattern.compile("[E|D|T]\\d{1}");
    
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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD: {
        	if (EVENT_DATA_ARGS_FORMAT.matcher(userInput).find())
        		return prepareEvent(arguments);
        	else if (DEADLINE_DATA_ARGS_FORMAT.matcher(userInput).find())
        		return prepareDeadline(arguments);
        	else if (ADD_TAGS_ARGS_FORMAT.matcher(userInput).find())
                return prepareAddTags(arguments);
        	else if (TODO_DATA_ARGS_FORMAT.matcher(userInput).find())
        		return prepareToDo(arguments);
        }
            
        
        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
        
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case DoneCommand.COMMAND_WORD:
            return prepareComplete(arguments);
                    
        case SpecifyStorageCommand.COMMAND_WORD:
            return prepareSpecifyStorage(arguments);
            
        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);
            
        case RedoCommand.COMMAND_WORD:
            return prepareRedo(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /**
     * Parses arguments in the context of the add tag command.
     *
     * @param args full command args string
     * @return the prepared command
     */
  //@@author A0139430L JingRui
    private Command prepareAddTags(String args) {
        final Matcher matcher = ADD_TAGS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        args = args.trim();

        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(Character.toString(args.charAt(1)));
        args = args.substring(args.indexOf(' ') + 1);

        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Integer pass = index.get();
        args = "add ".concat(args);
        
        //uses Edit command to add tags
        return new EditCommand(pass, args, category);
    }

    /* @author Ronald
     * @param number of times to undo, args
     * @return the prepared command
     */
    
    //@@author A0147890U
    private Command prepareUndo(String args) {
        int numTimes = Integer.parseInt(args.trim());
        return new UndoCommand(numTimes);
    }
    
    /**
     * @author Ronald
     * @param number of times to redo, args
     * @return the prepared command
     */
    
    //@@author A0147890U
    private Command prepareRedo(String args) {
        int numTimes = Integer.parseInt(args.trim());
        return new RedoCommand(numTimes);
    }
    
    /**
     * @Ronald
     * @param String data storage file path args
     * @return the prepared SpecifyStorageCommand
     */
    
    //@@author A0147890U
    private Command prepareSpecifyStorage(String args) {
        args = args.trim().replace("\\", "/") + "/addressbook.xml";
        return new SpecifyStorageCommand(args);
    }

    /**
     * Parses arguments in the context of the add todo command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareToDo(String args) {
    	final Matcher matcher = TODO_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
	}

	/**
     * Parses arguments in the context of the add task command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDeadline(String args){
        final Matcher matcher = DEADLINE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    matcher.group("date"),
                    matcher.group("end"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    
	/**
     * Parses arguments in the context of the add task command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEvent(String args){
        final Matcher matcher = EVENT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    matcher.group("date"),
                    matcher.group("start"),
                    matcher.group("end"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    /**
     * @param tagArguments
     * @return
     * @throws IllegalValueException
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" #", "").split(" #"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author A0139430L JingRui
    private Command prepareDelete(String args){
        final Matcher matcher = DELETE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }
        
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(","))); //might need to change split regex to ; instead of ,
        
        if(args.contains("-")){        
            char cat = args.charAt(1);
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            //check format of start and end
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            //making format of String: T(start), T2, T3.....T(end)
            String newArgs = Character.toString(cat).concat(Integer.toString(start));
            for(int i = start+1; i<= end; i++){
                newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
                newArgs = newArgs.concat(Integer.toString(i));
            }
            indexes = new ArrayList<String> (Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")));
        }

        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDelete = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDelete);      
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }          
        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDelete = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDelete);
            // System.out.println(index.isPresent() + args + indexes.size());
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));             
            }           
        }
        try {
            return new DeleteCommand(indexes);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author A0139430L JingRui
    private Command prepareEdit(String args) {
    	final Matcher matcher = EDIT_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }
        /*final Collection<String> indexes = Arrays.asList(args.trim().replaceAll(" ",  ""));
        Iterator<String> itr = indexes.iterator();
        ArrayList<Integer> pass = new ArrayList<Integer>(); //by right arraylist is redundant cause 1 value only, leave here first in case next time want use
        
        Optional<Integer> index = parseIndex(itr.next()); */
        
        args = args.trim();
        
        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(Character.toString(args.charAt(1)));
        args = args.substring(args.indexOf(' ') + 1);
        
        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        //pass.add(index.get());
        
        Integer pass = index.get();
        System.out.println(pass+" "+ args +" "+ category);
        return new EditCommand(pass, args, category);
    }
    
    //@@author A0135722L Zhiyuan
    private Command prepareComplete(String args) {
        final Matcher matcher = COMPLETE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoneCommand.MESSAGE_USAGE));
        }

        char cat = args.charAt(1);
        Collection<String> indexes = Arrays.asList(args.trim().replaceAll(" ", "").split(",")); //might need to change split regex to ; instead of ,
              
        if(args.contains("-")){          
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
            String newArgs = Character.toString(cat).concat(Integer.toString(start));
            for(int i = start+1; i<= end; i++){
                newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
                newArgs = newArgs.concat(Integer.toString(i));
            }
            indexes = Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")); //might need to change split regex to ; instead of ,
        }

        Iterator<String> itr = indexes.iterator();
        ArrayList<String> pass = new ArrayList<String>();
        pass.addAll(indexes);
        Optional<Integer> index = parseIndex(Character.toString(itr.next().charAt(1)));
        //System.out.println(index.isPresent() + args);
        
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }     
        
        while(itr.hasNext()){
            index = parseIndex(Character.toString(itr.next().charAt(1)));
            // System.out.println(index.isPresent() + args + indexes.size());
            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));             
            }           
        }
       
        System.out.println(pass);
        return new DoneCommand(pass);
        
    }

    /**
     * Parses arguments in the context of the select task command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
    	final Matcher matcher = SELECT_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
        
        args = args.trim();
        
        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(Character.toString(args.charAt(1)));
        args = args.substring(args.indexOf(' ') + 1);
        
        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
        
        Integer pass = index.get();
        return new SelectCommand(pass, args, category);

    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }
        
       // System.out.println(matcher.matches());

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
    //@@author A0139430L JingRui
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        
        // keywords delimited by whitespace
      //  final String[] keywords = matcher.group("keywords").split("\\s+");
        final String[] keywords = {args.trim()};
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
package seedu.simply.logic.parser;

import static seedu.simply.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.simply.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.commons.util.StringUtil;
import seedu.simply.logic.commands.AddCommand;
import seedu.simply.logic.commands.ClearCommand;
import seedu.simply.logic.commands.Command;
import seedu.simply.logic.commands.DeleteCommand;
import seedu.simply.logic.commands.DoneCommand;
import seedu.simply.logic.commands.EditCommand;
import seedu.simply.logic.commands.ExitCommand;
import seedu.simply.logic.commands.FindCommand;
import seedu.simply.logic.commands.HelpCommand;
import seedu.simply.logic.commands.IncorrectCommand;
import seedu.simply.logic.commands.ListCommand;
import seedu.simply.logic.commands.RedoCommand;
import seedu.simply.logic.commands.SelectCommand;
import seedu.simply.logic.commands.SpecifyStorageCommand;
import seedu.simply.logic.commands.UndoCommand;

/**
 * Parses user input.
 * @@author A0138993L
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ARGS_FORMAT_TASK_INDEX = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern ARGS_FORMAT_KEYWORDS =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern ARGS_FORMAT_EVENT_DATA = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("\\[(?<name>[^;]+)"
                    + "(; (?<date>[^;]+))?"
                    + "(; (?<start>[^;]+))?"
                    + "(; (?<end>[^#]+))?"
                    + "\\]"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    private static final Pattern ARGS_FORMAT_DEADLINE_DATA = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^;]+)"
                    + "; (?<date>[^;#]+)"
                    + "(; (?<end>[^#]+))?"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags


    private static final Pattern ARGS_FORMAT_TODO_DATA = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^#]+)"
                    + "(?<tagArguments>(?: #[^#]+)*)"); // variable number of tags

    private static final Pattern ARGS_FORMAT_EDIT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("[E|D|T]\\d+ "
                    + "(des|date|start|end|tag) "
                    + ".+");

    private static final Pattern ARGS_FORMAT_ADD_TAGS = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("[E|D|T]\\d+"
                    + " #[^#]+"
                    + "(?<tagArguments>(?: #[^#]+)*)");// variable number of tags

    private static final Pattern ARGS_FORMAT_DELETE = 
            Pattern.compile("(([E|D|T]\\d+, )*([E|D|T]\\d+))|"
                    + "([E|D|T]\\d+-[E|D|T]\\d+)");

    private static final Pattern ARGS_FORMAT_COMPLETE =
            Pattern.compile("(([E|D|T]\\d+, )*([E|D|T]\\d+))|"
                    + "([E|D|T]\\d+-[E|D|T]\\d+)");

    private static final Pattern ARGS_FORMAT_SELECT = 
            Pattern.compile("[E|D|T]\\d+");

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
            return prepareAdd(userInput, arguments);
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
     * @@author A0138993L
     * Chooses which kind of task to create and prepare
     * @param userInput takes in the user input from the command line interface
     * @param arguments full commands argument string
     * @return the prepared command
     */
    private Command prepareAdd(String userInput, final String arguments) {
        if (ARGS_FORMAT_EVENT_DATA.matcher(userInput).find())
            return prepareEvent(arguments);
        else if (ARGS_FORMAT_DEADLINE_DATA.matcher(userInput).find())
            return prepareDeadline(arguments);
        else if (ARGS_FORMAT_ADD_TAGS.matcher(userInput).find())
            return prepareAddTags(arguments);
        else if (ARGS_FORMAT_TODO_DATA.matcher(userInput).find())
            return prepareToDo(arguments);
        else
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    //@@author A0139430L
    /**
     * Parses arguments in the context of the add tag command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddTags(String args) {
        final Matcher matcher = ARGS_FORMAT_ADD_TAGS.matcher(args.trim());
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
        int numTimes;
        if (args.trim().equals("")) {
            numTimes = 1;
            return new UndoCommand(numTimes);
        }
        try {
            numTimes = Integer.parseInt(args.trim());
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand("The undo command only accepts numeric values.");
        }
        return new UndoCommand(numTimes);
    }

    /**
     * @author Ronald
     * @param number of times to redo, args
     * @return the prepared command
     */

    //@@author A0147890U
    private Command prepareRedo(String args) {
        int numTimes;
        if (args.trim().equals("")) {
            numTimes = 1;
            return new RedoCommand(numTimes);
        }
        try {
            numTimes = Integer.parseInt(args.trim());
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand("The redo command only accepts numeric values.");
        }
        return new RedoCommand(numTimes);
    }

    /**
     * @Ronald
     * @param String data storage file path args
     * @return the prepared SpecifyStorageCommand
     */

    //@@author A0147890U
    private Command prepareSpecifyStorage(String args) {
        args = args.trim().replace("\\", "/");
        try {
            Paths.get(args);
        } catch (InvalidPathException ipe) {
            return new IncorrectCommand("Please enter a valid file path");
        }
        if (new File(args.trim()).exists() == false) {
            return new IncorrectCommand("Please enter a valid file path");
        }
        args = args + "/addressbook.xml";

        return new SpecifyStorageCommand(args);
    }

    /**
     * Parses arguments in the context of the add todo command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareToDo(String args) {
        final Matcher matcher = ARGS_FORMAT_TODO_DATA.matcher(args.trim());
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
        final Matcher matcher = ARGS_FORMAT_DEADLINE_DATA.matcher(args.trim());
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
        final Matcher matcher = ARGS_FORMAT_EVENT_DATA.matcher(args.trim());
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
    
    //@@author A0139430L
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args){
        final Matcher matcher = ARGS_FORMAT_DELETE.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }       
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(",")));       
        if(args.contains("-")){        
            char cat = args.charAt(1);
            String[] temp = args.replaceAll(" ", "").replaceAll(Character.toString(cat),"").split("-");
            int start;
            int end;
            //check format of start and end if it is integer
            try{ 
                start = Integer.parseInt(temp[0]);
                end = Integer.parseInt(temp[temp.length-1]);
            }catch(NumberFormatException nfe){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            indexes = rangeToMultiple(start, end , cat);
        }
        //check if index is valid 1st number check
        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDelete = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDelete);      
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }          
        //the rest of the number check
        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDelete = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDelete);
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
    //@@author A0139430L
    private ArrayList<String> rangeToMultiple(int start, int end, char cat){
        //making format of String: T(start), T2, T3.....T(end)
        String newArgs = Character.toString(cat).concat(Integer.toString(start));
        for(int i = start+1; i<= end; i++){
            newArgs = newArgs.concat(",".concat(Character.toString(cat)));        
            newArgs = newArgs.concat(Integer.toString(i));
        }
        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(newArgs.trim().replaceAll(" ", "").split(",")));
        return indexes;
    }
    
    //@@author A0139430L
    /**
     * Parses arguments in the context of the Edit command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = ARGS_FORMAT_EDIT.matcher(args.trim());
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
        Optional<Integer> index = parseIndex(args.substring(1, args.indexOf(" ")));
        args = args.substring(args.indexOf(' ') + 1);

        if(!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Integer pass = index.get();
        return new EditCommand(pass, args, category);
    }

    //@@author A0135722L Zhiyuan
    private Command prepareComplete(String args) {
        final Matcher matcher = ARGS_FORMAT_COMPLETE.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoneCommand.MESSAGE_USAGE));
        }

        ArrayList<String> indexes = new ArrayList<String> (Arrays.asList(args.trim().replaceAll(" ", "").split(",")));

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
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }
            indexes = rangeToMultiple(start, end, cat);
        }

        Iterator<String> itr = indexes.iterator();
        String tempIndex = itr.next();
        String indexToDone = tempIndex.substring(1, tempIndex.length());
        Optional<Integer> index = parseIndex(indexToDone); 


        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }     

        while(itr.hasNext()){
            tempIndex = itr.next();
            indexToDone = tempIndex.substring(1, tempIndex.length());
            index = parseIndex(indexToDone);

            if(!index.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));             
            }           
        }


        try {
            return new DoneCommand(indexes);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the select task command.
     * @@author A0138993L
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        final Matcher matcher = ARGS_FORMAT_SELECT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        args = args.trim();

        char category = args.charAt(0);
        Optional<Integer> index = parseIndex(args.substring(1));
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
        final Matcher matcher = ARGS_FORMAT_TASK_INDEX.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    
    //@@author A0139430L
    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = ARGS_FORMAT_KEYWORDS.matcher(args.trim());
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
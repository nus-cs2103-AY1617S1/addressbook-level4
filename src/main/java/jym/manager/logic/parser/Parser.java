package jym.manager.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.StringUtil;
import jym.manager.logic.commands.*;
import static jym.manager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static jym.manager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<description>[^/]+)"
            		 + " (by)?\\s(?<deadline>[^/]+)"
            		// + "by(?:\\s ?<deadline>[^/]+)?"
//                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
//                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
//                    + " (?<isAddressPrivate>p?)a/(?<location>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern PERSON_DATA_ARGS_FORMAT_UPDATE = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<index>[\\d+]\\s)"
            		 + "(?<description>[^/]+)"
            		 + " (by)?\\s(?<deadline>[^/]+)"
//            		 + "(?: by\\s (?<deadline>( [^/]+)))?"
//                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
//                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
//                    + " (?<isAddressPrivate>p?)a/(?<location>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    
    public Parser() {}

    public static LocalDateTime parseDate(String date){
    	if(date == null) return null;
    	
		 LocalDateTime ldt = null;
	     if(date != null){
	     	date.replaceAll("\\n", "");
	     	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(Locale.ENGLISH);
	     	ldt = LocalDateTime.parse(date, formatter);
	     }
	     return ldt;
    }
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

        case EditCommand.COMMAND_WORD:
        	return prepareEdit(arguments);
            
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        matcher.matches();
        // Validate arg string format
//        if (!matcher.matches()) {
//            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        }
        System.out.println(matcher.groupCount());
        
        String[] sections = args.split("\\sby\\s|\\sat\\s");
        String date = null;
        String description = sections[0];
        String location = null;
        List<String> list = Arrays.asList(sections);
        list.forEach(n-> System.out.println(n));
        
        if(sections.length > 1){
        	Pattern dateRegex = Pattern.compile("(\\d\\d[- /.]\\d\\d[- /.]\\d\\d\\d\\d)(.*)");
        	try {
        		Matcher m = dateRegex.matcher(sections[1]);
        		if(m.matches()){
        			date = sections[1];
        			System.out.println(date);
        			if(sections.length > 2) location = sections[2];
        		} else {
        			location = sections[1];
        			System.out.println(location);
        			if(sections.length > 2) date = sections[2];
        		}
        	} catch (Exception e){
        		e.printStackTrace();
        	}
        	
        //	if(sections[1])
        //	date = sections[1];
        }
     
    
        LocalDateTime ldt = parseDate(date);
        try {
            return new AddCommand(
                    description,
                    ldt,
                    location
 //                   getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
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
     * Parses arguments in the context of the delete person command.
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

    private Command prepareEdit(String args){
    	final Matcher matcher = PERSON_DATA_ARGS_FORMAT_UPDATE.matcher(args.trim());
    	 if (!matcher.matches()) {
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
         }
         LocalDateTime ldt = null;
         if(matcher.group("deadline") != null){
         	String date = matcher.group("deadline").trim();
         	date.replaceAll("\\n", "");
         	System.out.println(date);
         	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(Locale.ENGLISH);
         	ldt = LocalDateTime.parse(date, formatter);
         }
         Optional<Integer> index = parseIndex(matcher.group("index"));
         if(!index.isPresent()){
             return new IncorrectCommand(
                     String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
         }
         try {
             return new EditCommand(
                     index.get(),
                     matcher.group("description"),
                     ldt
             );
         } catch (IllegalValueException ive) {
             return new IncorrectCommand(ive.getMessage());
         }
 
    	
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
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
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

}
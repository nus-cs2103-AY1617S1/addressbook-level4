package jym.manager.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.StringUtil;
import jym.manager.logic.commands.*;
import static jym.manager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static jym.manager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import com.joestelmach.natty.*;
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
  //          		 + " (by)?\\s(?<deadline>[^/]+)"
//            		 + "(?: by\\s (?<deadline>( [^/]+)))?"
//                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
//                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
//                    + " (?<isAddressPrivate>p?)a/(?<location>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    
    public Parser() {}

    /**
     * helper function for parsing date string. Deprecated.
     * @param date
     * @return
     */
    public static LocalDateTime parseDate(String date){
    	
    	if(date == null || date.equals("no deadline")) return null;
    
    	com.joestelmach.natty.Parser p = new com.joestelmach.natty.Parser();
    	List<DateGroup> dg = p.parse(date);
    	
    	LocalDateTime ldt = null;
    	if(!dg.isEmpty() && dg.get(0) != null){
    		ldt = LocalDateTime.ofInstant(dg.get(0).getDates().get(0).toInstant(), ZoneId.systemDefault());
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

        case CompleteCommand.COMMAND_WORD:
        	return prepareComplete(arguments);
        	
        default:
            return prepareAdd(commandWord.concat(arguments));
        }
    }


    /**
     * Helper function to extract dates from argument
     * @param args
     * @return
     */
  //@@author A0153440R
    private List<LocalDateTime> getDates(DateGroup dg){
    	List<LocalDateTime> dates = new ArrayList<>();
    	if(dg.getDates().size() > 1){
    		dg.getDates().forEach(
    				d -> dates.add(LocalDateTime.ofInstant(d.toInstant(), 
    						ZoneId.systemDefault())));
    	} else {
    		dates.add(LocalDateTime.ofInstant(
    					dg.getDates().get(0).toInstant(), ZoneId.systemDefault()));
    	}
    	return dates;
    }
	/**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
  //@@author A0153440R
    private Command prepareAdd(String args){
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        matcher.matches();
        // Validate arg string format
//        if (!matcher.matches()) {
//            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//        }
//        System.out.println(matcher.groupCount());
        
        com.joestelmach.natty.Parser p = new com.joestelmach.natty.Parser();
    	List<DateGroup> dg = p.parse(args);
        String[] sections; //split around the time
        
        
	    System.out.println("Section dates: ");
	    dg.forEach(n-> System.out.println(n.getText() + " " + n.getDates()));
	    System.out.println(LocalDateTime.now().toString());
        
        String priority = null;
        String date = null;
        String description = null;
        String location = null;
        LocalDateTime ldt = null;
        List<LocalDateTime> dates = null;
        
    	if(!dg.isEmpty() && dg.get(0) != null){
    		 dates = getDates(dg.get(0));

    		sections = args.split(dg.get(0).getText());
    		if(sections.length > 1){
            	location = sections[1];
            	description = sections[0];
            } else {
            	String[] furtherSects = sections[0].split("\\sat\\s");//for location
            	location = (furtherSects.length > 1)? furtherSects[1] : null;
            	description = furtherSects[0];
            }
    		
    		if(location != null && location.contains("at")){
        		location = location.substring(4);
        	}
        	if(description.endsWith("by ")){
        		description = description.split("\\sby\\s")[0];
        	}
    	} else {
    		sections = args.split("\\sat\\s");
    		description = sections[0];
    		if(sections.length > 1){
    			location = sections[1];
    		}
    	}
        if(args.contains("priority")){
        	String[] ps = args.split("\\spriority\\s");
        	priority = ps[1];
        }

        
//        
//        List<String> list = Arrays.asList(sections);
//        System.out.println("Section groups: ");
//        list.forEach(n-> System.out.println(n));
//        System.out.println(LocalDateTime.now().toString());
//        
        
        try {
            return new AddCommand(
                    description,
                    dates,
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
    /**
     * Parse arguments in the context of the complete person command
     */
  //@@author A0153440R
    private Command prepareComplete(String args) {
    	Optional<Integer> index = parseIndex(args);
    	if(!index.isPresent()){
    		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    		
    	}
    	
    	return new CompleteCommand(index.get());
	}
  //@@author A0153440R
    private Command prepareEdit(String args){
    	final Matcher matcher = PERSON_DATA_ARGS_FORMAT_UPDATE.matcher(args.trim());
    	 if (!matcher.matches()) {
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
         }
    	 args = args.substring(args.indexOf(" ", 1));
    	 System.out.println(args);
         LocalDateTime ldt = null;
         com.joestelmach.natty.Parser p = new com.joestelmach.natty.Parser();
     	List<DateGroup> dg = p.parse(args);
         String[] sections; //split around the time
         
         String priority = null;
         String date = null;
         String description = null;
         String location = null;
         List<LocalDateTime> dates = null;
     	if(!dg.isEmpty() && dg.get(0) != null){
     		dates = getDates(dg.get(0));
     		
     		sections = args.split(dg.get(0).getText());
     		if(sections.length > 1){
             	location = sections[1];
             	description = sections[0];
             } else {
             	String[] furtherSects = sections[0].split("\\sat\\s");//for location
             	location = (furtherSects.length > 1)? furtherSects[1] : null;
             	description = furtherSects[0];
             }
     		
     		if(location != null && location.contains("at")){
         		location = location.substring(4);
         	}
         	if(description.endsWith("by ")){
         		description = description.split("\\sby\\s")[0];
         	}
     	} else {
     		sections = args.split("\\sat\\s");
     		description = sections[0];
     		if(sections.length > 1){
     			location = sections[1];
     		}
     	}
         if(args.contains("priority")){
         	String[] ps = args.split("\\spriority\\s");
         	priority = ps[1];
         }

         
         System.out.println("desc:" + description + " " + location + " " + ldt);
         
         
         Optional<Integer> index = parseIndex(matcher.group("index"));
         if(!index.isPresent()){
             return new IncorrectCommand(
                     String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
         }
         try {
             return new EditCommand(
                     index.get(),
                     description,
                     dates,
                     location
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
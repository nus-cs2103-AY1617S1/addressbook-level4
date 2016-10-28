package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ArgumentTokenizer.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                           // or
                                                                                                           // more
                                                                                                           // keywords
                                                                                                           // separated
                                                                                                           // by
                                                                                                           // whitespace

    private static final Pattern TASK_NAME_ARGS_FORMAT = Pattern.compile("[\\p{Alnum} ]+");

    //    private static final Pattern EDIT_FORMAT = Pattern.compile("(?<name>[^.*?(?=[dsenr][/])])" + "(?<edit>[^/]+)" + "?[i][/](?<index>([^/]+)*)");
    private static final Pattern EDIT_FORMAT = Pattern.compile("(?<name>[^/]+)"
			+ "(?<edit>(?: [dsenr]/[^/]+)?)"
			+ "((i/(?<index>([0-9])+)*)?)" );

    private static final String MESSAGE_INVALID_DATE = "Date format entered is invalid";
//@@author A0142325R
    public static final Prefix deadlinePrefix = new Prefix("d/");
    public static final Prefix tagPrefix = new Prefix("t/");
    public static final Prefix startDatePrefix = new Prefix("s/");
    public static final Prefix endDatePrefix = new Prefix("e/");
    public static final Prefix namePrefix = new Prefix("n/");
    public static final Prefix recurringPrefix = new Prefix("r/");
  //@@author
    public Parser() {
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
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            // return new ListCommand();
            // System.out.println(arguments);
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case DoneCommand.COMMAND_WORD:
            return prepareMarkAsDone(arguments);

        case RefreshCommand.COMMAND_WORD:
            return new RefreshCommand();

        case ChangeCommand.COMMAND_WORD:
            return prepareChange(arguments);

        case FilterCommand.COMMAND_WORD:
            return prepareFilter(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case UndoChangeCommand.COMMAND_WORD:
            return new UndoChangeCommand(arguments);
            
        case RedoChangeCommand.COMMAND_WORD:
            return new RedoChangeCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
//@@author A0142325R
    private Command prepareMarkAsDone(String args) {
        Optional<Integer> index = parseIndex(args);
        String name = args;
        if (!index.isPresent()) {
            if (name == null || name.equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new DoneCommand(keywordSet);
        }
        return new DoneCommand(index.get());
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, namePrefix, tagPrefix, startDatePrefix,
                endDatePrefix, recurringPrefix);
        argsTokenizer.tokenize(args);
        try {
            if (argsTokenizer.getTokenizedArguments().containsKey(namePrefix)) {
                if (!argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)
                        && !argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
                    // non-recurring task
                    return new AddCommand(argsTokenizer.getValue(namePrefix).get(), "",
                            toSet(argsTokenizer.getAllValues(tagPrefix)), "");
                }
                // check if task is recurring floating task
                if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)
                        && argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix)) {
                    return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                            argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                            argsTokenizer.getValue(recurringPrefix).get());
                    // non-recurring floating task
                } else if (argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
                    return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                            argsTokenizer.getValue(deadlinePrefix).get(), toSet(argsTokenizer.getAllValues(tagPrefix)),
                            "");
                } else if (argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)
                        && argsTokenizer.getTokenizedArguments().containsKey(endDatePrefix)) {
                    if (!argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix))
                        // non-recurring event
                        return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                                argsTokenizer.getValue(startDatePrefix).get(),
                                argsTokenizer.getValue(endDatePrefix).get(),
                                toSet(argsTokenizer.getAllValues(tagPrefix)), "");
                    else// recurring event
                        return new AddCommand(argsTokenizer.getValue(namePrefix).get(),
                                argsTokenizer.getValue(startDatePrefix).get(),
                                argsTokenizer.getValue(endDatePrefix).get(),
                                toSet(argsTokenizer.getAllValues(tagPrefix)),
                                argsTokenizer.getValue(recurringPrefix).get());
                }
            }

            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (Exception e) {
            return new IncorrectCommand(MESSAGE_INVALID_DATE);
        }
    }

    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        Optional<Integer> index = parseIndex(args);
        String name = args;
        if (!index.isPresent()) {
            if (name == null || name.equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            return new DeleteCommand(args, KEYWORDS_ARGS_FORMAT);
        }
        return new DeleteCommand(index.get());

    }
    //@@author

    /**
     * Parses arguments in the context of the select person command.
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
     * Parses arguments in the context of the find person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    //@@author A0146123R
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Group keywords by AND operator
        final String[] keywords = matcher.group("keywords").split("AND");
        // keywords delimited by whitespace
        final Set<Set<String>> keywordsGroup = new HashSet<Set<String>>();
        for (String keyword: keywords) {
            keywordsGroup.add(new HashSet<>(Arrays.asList(keyword.trim().split("\\s+"))));
        }
        return new FindCommand(keywordsGroup, matcher.group("keywords").contains("exact!"));
    }
    
    //@@author A0142325R
    private Command prepareList(String args) {
        if (args.equals(""))
            return new ListCommand();
        return new ListCommand(args);
    }
    
    //@@author A0146123R
    /**
     * Parses arguments in the context of the change storage location command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareChange(String arguments) {
        final String[] args = arguments.trim().split("\\s+");
        if (args.length >= 0) {
            String filePath = args[0];
            if (args.length == 1) {
                return new ChangeCommand(filePath);
            } else if (args.length == 2) {
                String clear = args[1];
                return new ChangeCommand(filePath, clear);
            }
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments in the context of the filter attributes command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFilter(String arguments) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, startDatePrefix, endDatePrefix,
                recurringPrefix, tagPrefix);
        argsTokenizer.tokenize(arguments);
        Optional<String> deadline = argsTokenizer.getValue(deadlinePrefix);
        Optional<String> startDate = argsTokenizer.getValue(startDatePrefix);
        Optional<String> endDate = argsTokenizer.getValue(endDatePrefix);
        Optional<String> recurring = argsTokenizer.getValue(recurringPrefix);
        Optional<List<String>> tags = argsTokenizer.getAllValues(tagPrefix);
        if (deadline.isPresent() || startDate.isPresent() || endDate.isPresent() 
                || recurring.isPresent() || tags.isPresent()) {
           return new FilterCommand(deadline, startDate, endDate, recurring, toSet(tags));
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
    
    //@@author
    private Command prepareEdit(String args) {
//    	Optional<Integer> index = parseIndex(args);

//    	final String[] edit = args.split("\\s+");
//    	       String detailType = keywords[1];
//    	       String newDetail = keywords[2];
//    	       int targetIndex = Integer.parseInt(keywords[0]);
//
//    	       return new EditCommand(targetIndex, detailType, newDetail);
//    	String name = edit[0];
//        if (name == null || name.equals("")) {
//            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
//        }
//        String type = edit[1];
//        String details = edit[2];
    	final Matcher matcher = EDIT_FORMAT.matcher(args.trim());
    	 if (!matcher.matches()) {
             return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    	 if(!matcher.group("name").isEmpty() && !matcher.group("edit").isEmpty())
    	 {
    	    	String name = matcher.group("name");
    	    	String type = matcher.group("edit");
    	    	String index = matcher.group("index");
    	        String detailsType = null;
    	        String details;
    	        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(deadlinePrefix, namePrefix, tagPrefix, startDatePrefix,
    	                endDatePrefix, recurringPrefix);
    	        argsTokenizer.tokenize(type);
    	        if (argsTokenizer.getTokenizedArguments().containsKey(namePrefix)) {
    	        	detailsType = "name";
    	        	details = argsTokenizer.getValue(namePrefix).get();
    	        }
//    	        else if(argsTokenizer.getTokenizedArguments().containsKey(tagPrefix)) {
//    	        	detailsType = "tag";
//    	        	toSet(argsTokenizer.getAllValues(tagPrefix))
//    	        }
    	        else if(argsTokenizer.getTokenizedArguments().containsKey(recurringPrefix)) {
    	        	detailsType = "recurring";
    	        	details = argsTokenizer.getValue(recurringPrefix).get();
    	        }
    	        else if(argsTokenizer.getTokenizedArguments().containsKey(startDatePrefix)) {
    	        	detailsType = "startDate";
    	        	details = argsTokenizer.getValue(startDatePrefix).get();
    	        }
    	        else if(argsTokenizer.getTokenizedArguments().containsKey(endDatePrefix)) {
    	        	detailsType = "endDate";
    	        	details = argsTokenizer.getValue(endDatePrefix).get();
    	        }
    	        else if(argsTokenizer.getTokenizedArguments().containsKey(deadlinePrefix)) {
    	        	detailsType = "deadline";
    	        	details = argsTokenizer.getValue(deadlinePrefix).get();
    	        }
    	        else
    	        {
    	            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    	        }

    	    	if (index == null) {
    	            return new EditCommand(name, detailsType, details);
    	        }
    	        return new EditCommand(name, detailsType, details, Integer.parseInt(index));
    	 }
    	 else
    		 return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

}
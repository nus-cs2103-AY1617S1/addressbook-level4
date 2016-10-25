package seedu.jimi.logic.parser;

import static seedu.jimi.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jimi.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.jimi.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.exceptions.DateNotParsableException;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.logic.commands.AddCommand;
import seedu.jimi.logic.commands.ClearCommand;
import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.commands.CompleteCommand;
import seedu.jimi.logic.commands.DeleteCommand;
import seedu.jimi.logic.commands.EditCommand;
import seedu.jimi.logic.commands.ExitCommand;
import seedu.jimi.logic.commands.FindCommand;
import seedu.jimi.logic.commands.HelpCommand;
import seedu.jimi.logic.commands.IncorrectCommand;
import seedu.jimi.logic.commands.ListCommand;
import seedu.jimi.logic.commands.SaveAsCommand;
import seedu.jimi.logic.commands.SelectCommand;
import seedu.jimi.logic.commands.ShowCommand;

/**
 * Parses user input.
 */
public class JimiParser {
    
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("[te](?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    private static final Pattern TAGGABLE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<ArgsDetails>[^/]+)(?<tagArguments>(?: t/[^/]+)?)"); // zero or one tag only
    
    private static final Pattern EDIT_DATA_ARGS_FORMAT = // accepts index at beginning, follows task/event patterns after
            Pattern.compile("(?<targetIndex>[^\\s]+) (?<editDetails>.+)");
    
    // acccepts in the format of a deadline task or event
    private static final Pattern EDIT_DETAILS_FORMAT = Pattern.compile(
            "(\"(?<taskDetails>.+)\"\\s?)?(((due (?<deadline>.+))?)|((on (?<startDateTime>((?!to ).)*))?(to (?<endDateTime>.+))?))");
    
    private static final Pattern ADD_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(\"(?<taskDetails>.+)\")( due (?<dateTime>.+))?");
    
    private static final Pattern ADD_EVENT_DATA_ARGS_FORMAT =
            Pattern.compile("(\"(?<taskDetails>.+)\") on (?<startDateTime>((?! to ).)*)( to (?<endDateTime>.+))?");
    
    private static final Pattern SHOW_COMMAND_ARGS_FORMAT = Pattern.compile("(?<sectionToShow>.+)");
    
    private static final Pattern SAVE_DIRECTORY_ARGS_FORMAT = Pattern.compile("(?<filePath>.+).xml");
    
    private static final Pattern SAVE_RESET_DIRECTORY_ARGS_FORMAT = Pattern.compile(SaveAsCommand.COMMAND_WORD_RESET);
    
    private static final List<Command> COMMAND_STUB_LIST =
            Arrays.asList(
                    new AddCommand(), 
                    new EditCommand(), 
                    new CompleteCommand(), 
                    new SelectCommand(), 
                    new DeleteCommand(),
                    new ClearCommand(), 
                    new FindCommand(), 
                    new ListCommand(),
                    new ShowCommand(),
                    new ExitCommand(), 
                    new HelpCommand(), 
                    new SaveAsCommand()
            );

    private static final String XML_FILE_EXTENSION = ".xml";
    
    public JimiParser() {}

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
        final String arguments = matcher.group("arguments").trim();
        
        return prepareCommand(commandWord, arguments);
    }

    /**
     * Identifies which command to prepare according to raw command word.
     * 
     * @param commandWord command word from raw input
     * @param arguments arguments from raw input
     * @return correct Command corresponding to the command word if valid, else returns incorrect command.
     */
    private Command prepareCommand(String commandWord, String arguments) {
        for (Command command : COMMAND_STUB_LIST) {
            // if validation checks implemented by the respective commands are passed
            if (command.isValidCommandWord(commandWord)) {
                // identify which command this is
                if (command instanceof AddCommand) {
                    return prepareAdd(arguments);
                } else if (command instanceof EditCommand) {
                    return prepareEdit(arguments);
                } else if (command instanceof CompleteCommand) {
                    return prepareComplete(arguments);
                } else if (command instanceof SelectCommand) {
                    return prepareSelect(arguments);
                } else if (command instanceof DeleteCommand) {
                    return prepareDelete(arguments);
                } else if (command instanceof FindCommand) {
                    return prepareFind(arguments);
                } else if (command instanceof ShowCommand) {
                    return prepareShow(arguments);
                } else if (command instanceof SaveAsCommand) {
                    return prepareSaveAs(arguments);
                } else { // commands that do not require arguments e.g. exit
                    return command;
                }
            }
        }
        
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher detailsAndTagsMatcher = TAGGABLE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate entire args string format
        if (!detailsAndTagsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        final Matcher taskDetailsMatcher =
                ADD_TASK_DATA_ARGS_FORMAT.matcher(detailsAndTagsMatcher.group("ArgsDetails").trim());
        final Matcher eventDetailsMatcher =
                ADD_EVENT_DATA_ARGS_FORMAT.matcher(detailsAndTagsMatcher.group("ArgsDetails").trim());
        
        if (taskDetailsMatcher.matches()) { // if user trying to add task 
            return generateAddCommandForTask(detailsAndTagsMatcher, taskDetailsMatcher);
        } else if (eventDetailsMatcher.matches()) { // if user trying to add event
            return generateAddCommandForEvent(detailsAndTagsMatcher, eventDetailsMatcher);
        }
        
        /* default return IncorrectCommand */
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    
    /**
     * Creates an AddCommand in the context of adding an event.
     * 
     * @return an AddCommand if raw args is valid, else IncorrectCommand
     */
    private Command generateAddCommandForEvent(final Matcher detailsAndTagsMatcher, final Matcher eventDetailsMatcher) {
        try {
            List<Date> startDates = parseStringToDate(eventDetailsMatcher.group("startDateTime"));
            List<Date> endDates = parseStringToDate(eventDetailsMatcher.group("endDateTime"));
            return new AddCommand(
                    eventDetailsMatcher.group("taskDetails"),
                    startDates,
                    endDates,
                    getTagsFromArgs(detailsAndTagsMatcher.group("tagArguments"))
            );
        } catch (DateNotParsableException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Creates an AddCommand in the context of adding an task.
     * 
     * @return an AddCommand if raw args is valid, else IncorrectCommand
     */
    private Command generateAddCommandForTask(final Matcher detailsAndTagsMatcher, final Matcher taskDetailsMatcher) {
        try {
            List<Date> dates = parseStringToDate(taskDetailsMatcher.group("dateTime"));
            return new AddCommand(
                    taskDetailsMatcher.group("taskDetails"),
                    dates,
                    getTagsFromArgs(detailsAndTagsMatcher.group("tagArguments"))
            );
        } catch (DateNotParsableException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private static List<Date> parseStringToDate(final String str) throws DateNotParsableException {
        if (str == null) {
            return new ArrayList<Date>();
        }
        
        final Parser dateParser = new Parser();
        final List<DateGroup> groups = dateParser.parse(str);
        
        if (!groups.isEmpty()) {
            return groups.get(0).getDates();
        } else {
            throw new DateNotParsableException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses arguments in context of the edit task command.
     * 
     * @param args Full user command input args
     * @return the prepared edit command
     */
    private Command prepareEdit(String args) {
        final Matcher detailsAndTagsMatcher = TAGGABLE_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate full raw args string format
        if (!detailsAndTagsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // Validate args in terms of <idx><details>
        final Matcher editArgsMatcher =
                EDIT_DATA_ARGS_FORMAT.matcher(detailsAndTagsMatcher.group("ArgsDetails").trim());
        if (!editArgsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        // User wishes to remove dates
        if (editArgsMatcher.group("editDetails").trim().equals(EditCommand.COMMAND_REMOVE_DATES)) {
            return new EditCommand(editArgsMatcher.group("targetIndex"));
        }
        
        // Validate details in terms of <name><due X> or <name><on X><to X>
        final Matcher editDetailsMatcher =
                EDIT_DETAILS_FORMAT.matcher(editArgsMatcher.group("editDetails").trim());
        if (!editDetailsMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        try {
            return generateEditCommand(detailsAndTagsMatcher, editArgsMatcher, editDetailsMatcher);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /** Generates an edit command */
    private Command generateEditCommand(Matcher detailsAndTagsMatcher, Matcher editArgsMatcher,
            Matcher editDetailsMatcher) throws IllegalValueException {
        
        List<Date> deadline = parseStringToDate(editDetailsMatcher.group("deadline"));
        List<Date> eventStart = parseStringToDate(editDetailsMatcher.group("startDateTime"));
        List<Date> eventEnd = parseStringToDate(editDetailsMatcher.group("endDateTime"));
        
        /* validating integer index */
        Optional<Integer> index = parseIndex(editArgsMatcher.group("targetIndex"));
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        return new EditCommand(
                editDetailsMatcher.group("taskDetails"),
                getTagsFromArgs(detailsAndTagsMatcher.group("tagArguments")),
                deadline,
                eventStart,
                eventEnd,
                editArgsMatcher.group("targetIndex")
        );
    }
    
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
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
     * Parses arguments in the context of the complete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String args) {
        Optional<Integer> index = parseIndex(args); 
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        
        return new CompleteCommand(args.trim());
    }
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        Optional<Integer> index = parseIndex(args); 
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        
        return new DeleteCommand(args.trim());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
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
     * Parses arguments to filter section of task panel to be displayed to user.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareShow(String args) {
        final Matcher matcher = SHOW_COMMAND_ARGS_FORMAT.matcher(args.trim());
        boolean keywordFound = false;
        
        //goes through list of keywords to check if user input is valid
        for (String validKey : ShowCommand.VALID_KEYWORDS) {
            if (validKey.contains(args)) {
                keywordFound = true;
                break;
            }
        }
        
        if (!keywordFound || !matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowCommand.MESSAGE_USAGE));
        }
        
        // keywords delimited by whitespace
        final String sectionToShow = matcher.group("sectionToShow");
        
        return new ShowCommand(sectionToShow);
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
        
        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
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
     * Parses arguments in the context of the save as command.
     * 
     * @param full command args string
     * @return the prepared command
     */
    private Command prepareSaveAs(String args) {
        final Matcher resetMatcher = SAVE_RESET_DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        if (resetMatcher.matches()) {
            return new SaveAsCommand(Config.DEFAULT_XML_FILE_PATH);
        }
        
        final Matcher matcher = SAVE_DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveAsCommand.MESSAGE_USAGE));
        }
        
        return new SaveAsCommand(matcher.group("filePath") + XML_FILE_EXTENSION);
    }

}
package harmony.mastermind.logic.parser;

import static harmony.mastermind.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static harmony.mastermind.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;


import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import com.google.common.base.Strings;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.commons.util.StringUtil;
import harmony.mastermind.logic.commands.*;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.ModelManager;
import harmony.mastermind.model.tag.Tag;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<keyword>\\S+)(?<arguments>.*)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 
    
    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern TASK_ARCHIVE_ARGS_FORMAT = Pattern.compile("(?<type>[^/]+)");
    
    private static final String TAB_ARCHIVES = "Archives";
    
    public static Memory mem;

    public Parser() {
        Memory memory = initializeMemory();
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput, String currentTab) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String keyword = matcher.group("keyword");
        final String arguments = matcher.group("arguments");

        switch (keyword) {

            case AddCommand.COMMAND_KEYWORD_ADD: // main command
            case AddCommand.COMMAND_KEYWORD_DO: // alias (fall through)
                return prepareAdd(arguments);

            case DeleteCommand.COMMAND_WORD:
                return prepareDelete(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return prepareFind(arguments);

            case FindTagCommand.COMMAND_WORD:
                return prepareFindTag(arguments);

            case ListCommand.COMMAND_WORD:
                return prepareList(arguments);
                
            case UpcomingCommand.COMMAND_WORD:
                return prepareUpcoming(arguments);

            case MarkCommand.COMMAND_WORD:
                return prepareMark(arguments, currentTab);

            case EditCommand.COMMAND_KEYWORD_EDIT:
            case EditCommand.COMMAND_KEYWORD_UPDATE:
            case EditCommand.COMMAND_KEYWORD_CHANGE:
                return prepareEdit(arguments);

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();
                
            case RelocateCommand.COMMAND_WORD:
                return new RelocateCommand(arguments);
                
            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();
                
            case UnmarkCommand.COMMAND_WORD:
                return prepareUnmark(arguments, currentTab);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();
                
            case ImportCommand.COMMAND_WORD:
                return new ImportCommand(arguments);
                
            case ImportIcsCommand.COMMAND_KEYWORD_IMPORTICS:
                return prepareImportIcs(arguments);
            default:
                return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND+": "+userInput);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    // @@author A0138862W
    private Command prepareAdd(String args) {
        try {

            final Matcher matcher = AddCommand.COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());

            // Validate user command input
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_EXAMPLES));
            }
            
            // mandatory field                       
            final String name = matcher.group("name");
            
            // at this point name variable should never be null because the regex only capture full match of mandatory components
            // check for bug in regex expression if the following throws assertion error
            assert name != null;

            // optionals
            final Optional<String> dates = Optional.ofNullable(matcher.group("dates"));
            final Optional<String> tags = Optional.ofNullable(matcher.group("tags"));
            final Optional<String> recur = Optional.ofNullable(matcher.group("recur"));
            
            // return internal value if present. else, return empty string
            final Set<String> tagSet = getTagsFromArgs(tags.map(val -> val).orElse(""));
            
            // after init every capturing groups, we start to build the command 
            AddCommand addCommand = buildAddCommand(name, dates, recur, tagSet);
            
            return addCommand;
        } catch (IllegalValueException | InvalidEventDateException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

    //@@author A0138862W
    /**
     * Build the AddCommand
     * 
     * @param name is mandatory field
     * @param dates contain user input date string that has to be parsed by natural language processing library. Optional
     * @param recur contain recur input contain the keyword such as daily, weekly, monthly. Optional
     * @param tagSet unique set of tag string associated to the task
     * 
     * @throws IllegalValueException if tags contain non-alphanumeric value
     * @throws InvalidEventDateException if event start date is after end date
     */
    private AddCommand buildAddCommand(final String name, final Optional<String> dates, final Optional<String> recur, final Set<String> tagSet) throws IllegalValueException, InvalidEventDateException {
        AddCommandBuilder addCommandBuilder = new AddCommandBuilder(name);
        addCommandBuilder.withTags(tagSet);
        recur.ifPresent(recurVal -> addCommandBuilder.asRecurring(recurVal));
        
        if(dates.isPresent()){
            PrettyTimeParser ptp = new PrettyTimeParser();
            List<DateGroup> parsedDates = ptp.parseSyntax(dates.get());
            
            if(!parsedDates.isEmpty()){
                List<Date> startEndDates = parsedDates.get(0).getDates();
                
                /*
                 * We assume two conditions after parsing nlp dates:
                 * 1. Found only 1 date, then we assume it is a deadline
                 * 2. Found 2 dates, then we assume it is an event
                 */
                if(shouldParseAsDeadline(startEndDates)){
                    addCommandBuilder.asDeadline(startEndDates.get(0));
                }else if(shouldParseAsEvent(startEndDates)){
                    addCommandBuilder.asEvent(startEndDates.get(0), startEndDates.get(1));
                }
            }
        };
        return addCommandBuilder.build();
    }

    //@@author A0138862W
    /*
     * Determine the date should be parse as deadline task
     */
    private boolean shouldParseAsDeadline(List<Date> dates){
        return dates.size() == 1;
    }
    
    //@@author A0138862W
    /*
     * Determine the date should be parse as event task
     */
    private boolean shouldParseAsEvent(List<Date> dates){
        return dates.size() == 2;
    }
    
    //@@author A0138862W
    /*
     * Extract the source destination string and prepare the Import ICS command. 
     * 
     */
    private Command prepareImportIcs(String args){
        final Matcher matcher = ImportIcsCommand.COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());
        
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportIcsCommand.MESSAGE_EXAMPLE));
        }
        
        final String source = matcher.group("source");
        
        assert source != null;
        
        return new ImportIcsCommand(source);
    }
    
    /**
     * Parses arguments in the context of the edit task command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    // @@author A0138862W
    private Command prepareEdit(String args) {
        final Matcher matcher = EditCommand.COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());
        
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        try {
            
            // mandatory
            // regex accept only numbers in index field, encountering NumberFormatException is impossible
            final int index = Integer.parseInt(matcher.group("index"));
            
            //optional
            final Optional<String> recur = Optional.ofNullable(matcher.group("recur"));
            final Optional<String> name = Optional.ofNullable(matcher.group("name"));
            final Optional<String> startDate = Optional.ofNullable(matcher.group("startDate"));
            final Optional<String> endDate = Optional.ofNullable(matcher.group("endDate"));
            final Optional<String> tags = Optional.ofNullable(matcher.group("tags"));
            
            Optional<Set<String>> tagSet = Optional.empty();
            if(tags.isPresent()){
                tagSet = Optional.ofNullable(getTagsFromArgs(tags.get()));
            };
            
            return new EditCommand(index, name, startDate, endDate, tagSet, recur);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException pe) {
            return new IncorrectCommand(pe.getMessage());

        }

    }
    // @@author

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (Strings.isNullOrEmpty(tagArguments)) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.split(","));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        Command result = new DeleteCommand(index.get());

        return result;
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    //@@author A0124797R
    private Command prepareMark(String args, String currentTab) {
        if (currentTab.equals(TAB_ARCHIVES)) {
            return new IncorrectCommand(MarkCommand.MESSAGE_TASK_MARKED);
        }

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
        return new MarkCommand(index.get(), currentTab);
    }
    
    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    //@@author A0124797R
    private Command prepareList(String args) {
        Optional<String> type = parseType(args);
        if (!type.isPresent()) {
            return new ListCommand();
        }else {
            if (type.get().equals(ModelManager.TAB_TASKS.toLowerCase()) ||
                    type.get().equals(ModelManager.TAB_EVENTS.toLowerCase()) || 
                    type.get().equals(ModelManager.TAB_DEADLINES.toLowerCase()) ||
                    type.get().equals(ModelManager.TAB_ARCHIVES.toLowerCase())) {
                return new ListCommand(type);
            }else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Parses arguments in the context of the unmark task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    //@@author A0124797R
    private Command prepareUnmark(String args, String currentTab) {
        if (!currentTab.equals(TAB_ARCHIVES)) {
            return new IncorrectCommand(UnmarkCommand.MESSAGE_UNMARK_TASK_FAILURE);
        }

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
        return new UnmarkCommand(index.get());
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
     * checks if have the type to list archive
     */
    private Optional<String> parseType(String command) {
        final Matcher matcher = TASK_ARCHIVE_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String type = matcher.group("type").toLowerCase();

        return Optional.of(type);

    }
    
    private Optional<String> parseUpcoming(String command) {
        if (command.isEmpty()) {
            return Optional.of("empty");
        }
        
        final Matcher matcher = UpcomingCommand.COMMAND_ARGUMENTS_PATTERN.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String type = matcher.group("taskType").toLowerCase();

        return Optional.of(type);

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    //@@author A0124797R
    private Command prepareUpcoming(String args) {

        Optional<String> taskType = parseUpcoming(args);
        
        if (taskType.isPresent()) {
            return new UpcomingCommand(taskType.get());
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingCommand.MESSAGE_USAGE));
        }
        
    }

    /**
     * Parses arguments in the context of the find tag command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFindTag(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");

        final Set<Tag> tagSet = new HashSet<>();

        try {

            for (String tagName : keywords) {
                tagSet.add(new Tag(tagName));
            }

            return new FindTagCommand(tagSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author A0143378Y
    private Memory initializeMemory() {
        Memory memory = new Memory();
        mem = memory;
        memory.loadFromFile(memory);
        return memory;
    }
}

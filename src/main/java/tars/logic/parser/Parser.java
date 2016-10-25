package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.ExtractorUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.AddCommand;
import tars.logic.commands.CdCommand;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;
import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.EditCommand;
import tars.logic.commands.ExitCommand;
import tars.logic.commands.FindCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.ListCommand;
import tars.logic.commands.MarkCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.RsvCommand;
import tars.logic.commands.TagCommand;
import tars.logic.commands.UndoCommand;
import tars.model.task.TaskQuery;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern FILEPATH_ARGS_FORMAT = Pattern.compile("(?<filepath>\\S+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more whitespace

    private static final Pattern TAG_EDIT_COMMAND_FORMAT = Pattern.compile("\\d+ \\w+$");
    
    private static final Prefix namePrefix = new Prefix("/n");
    private static final Prefix tagPrefix = new Prefix("/t");
    private static final Prefix priorityPrefix = new Prefix("/p");
    private static final Prefix dateTimePrefix = new Prefix("/dt");
    private static final Prefix recurringPrefix = new Prefix("/r");
    private static final Prefix deletePrefix = new Prefix("/del");
    private static final Prefix addTagPrefix = new Prefix("/ta");
    private static final Prefix removeTagPrefix = new Prefix("/tr");
    private static final Prefix donePrefix = new Prefix("/do");
    private static final Prefix undonePrefix = new Prefix("/ud");
    private static final Prefix listPrefix = new Prefix("/ls");
    private static final Prefix editPrefix = new Prefix("/e");
    
    private static final String EMPTY_STRING = "";
    private static final String EMPTY_SPACE_ONE = " ";

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

        case RsvCommand.COMMAND_WORD:
            return prepareRsv(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ConfirmCommand.COMMAND_WORD:
            return prepareConfirm(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            if (arguments != null && !arguments.isEmpty()) {
                return prepareList(arguments);
            } else {
                return new ListCommand();
            }

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case CdCommand.COMMAND_WORD:
            return prepareCd(arguments);

        case TagCommand.COMMAND_WORD:
            return prepareTag(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @@author A0139924W
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(tagPrefix, priorityPrefix, dateTimePrefix, recurringPrefix);
        argsTokenizer.tokenize(args);

        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    DateTimeUtil.getDateTimeFromArgs(argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING)),
                    argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING),
                    argsTokenizer.getMultipleValues(tagPrefix).orElse(new HashSet<String>()),
                    ExtractorUtil.getRecurringFromArgs(argsTokenizer.getValue(recurringPrefix).orElse(EMPTY_STRING), recurringPrefix));           
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        } catch (NoSuchElementException nse) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareRsv(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(dateTimePrefix, deletePrefix);
        argsTokenizer.tokenize(args);
        
        if(argsTokenizer.getValue(deletePrefix).isPresent()) {
            return prepareRsvDel(argsTokenizer);
        } else {
            return prepareRsvAdd(argsTokenizer);
        }
    }

    // Parses arguments for adding a reserved task
    private Command prepareRsvAdd(ArgumentTokenizer argsTokenizer) {
        if(!argsTokenizer.getValue(dateTimePrefix).isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_DATETIME_NOTFOUND));
        }
        
        Set<String[]> dateTimeStringSet = new HashSet<>();
        
        try {
            for (String dateTimeString : argsTokenizer.getMultipleValues(dateTimePrefix).get()) {
                dateTimeStringSet.add(DateTimeUtil.getDateTimeFromArgs(dateTimeString));
            }
            
            return new RsvCommand(argsTokenizer.getPreamble().get(), dateTimeStringSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        } catch(NoSuchElementException nse) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        }
    }

    // Parses arguments for deleting one or more reserved tasks
    private Command prepareRsvDel(ArgumentTokenizer argsTokenizer) {
        try {
            if (argsTokenizer.getPreamble().isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RsvCommand.MESSAGE_USAGE_DEL));
            }

            String rangeIndex = StringUtil.indexString(argsTokenizer.getValue(deletePrefix).get());
            return new RsvCommand(rangeIndex);
        } catch (InvalidRangeException | IllegalValueException ie) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE_DEL));
        }
    }

    /**
     * Parses arguments in the context of the edit task command.
     * 
     * @@author A0121533W
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        args = args.trim();
        int targetIndex = 0;
        if (args.indexOf(EMPTY_SPACE_ONE) != -1) {
            targetIndex = args.indexOf(EMPTY_SPACE_ONE);
        }

        Optional<Integer> index = parseIndex(args.substring(0, targetIndex));

        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix, priorityPrefix,
                dateTimePrefix, addTagPrefix, removeTagPrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.numPrefixFound() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(index.get(), argsTokenizer);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        args = args.trim();
        
        if (EMPTY_STRING.equals(args)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        
        try {
            String rangeIndex = StringUtil.indexString(args);
            args = rangeIndex;
        } catch (InvalidRangeException ire) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        
        return new DeleteCommand(args);
    }

    private Command prepareConfirm(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(priorityPrefix, tagPrefix);
        argsTokenizer.tokenize(args);

        int taskIndex;
        int dateTimeIndex;
        
        try {
            String indexArgs = argsTokenizer.getPreamble().get();
            String[] indexStringArray = StringUtil.indexString(indexArgs).split(EMPTY_SPACE_ONE);
            if (indexStringArray.length > 2) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ConfirmCommand.MESSAGE_USAGE));
            } else {
                taskIndex = Integer.parseInt(indexStringArray[0]);
                dateTimeIndex = Integer.parseInt(indexStringArray[1]);
            }
        } catch (IllegalValueException | NoSuchElementException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        } catch (InvalidRangeException ire) {
            return new IncorrectCommand(ire.getMessage());
        }
        
        try {
            return new ConfirmCommand(taskIndex, dateTimeIndex,
                    argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING),
                    argsTokenizer.getMultipleValues(tagPrefix).orElse(new HashSet<>()));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @@author A0121533W
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(donePrefix, undonePrefix);
        argsTokenizer.tokenize(args);

        String markDone = argsTokenizer.getValue(donePrefix).orElse(EMPTY_STRING);
        String markUndone = argsTokenizer.getValue(undonePrefix).orElse(EMPTY_STRING);

        try {
            String indexesToMarkDone = StringUtil.indexString(markDone);
            String indexesToMarkUndone = StringUtil.indexString(markUndone);
            markDone = indexesToMarkDone;
            markUndone = indexesToMarkUndone;
        } catch (InvalidRangeException | IllegalValueException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(markDone, markUndone);
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
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix, priorityPrefix, dateTimePrefix, donePrefix, undonePrefix, tagPrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.numPrefixFound() == 0) {
            return new FindCommand(generateKeywordSetFromArgs(args.trim()));
        }

        TaskQuery taskQuery;
        try {
            taskQuery = createTaskQuery(argsTokenizer);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }

        return new FindCommand(taskQuery);
    }

    private TaskQuery createTaskQuery(ArgumentTokenizer argsTokenizer)
            throws DateTimeException, IllegalValueException {
        TaskQuery taskQuery = new TaskQuery();
        Boolean statusDone = true;
        Boolean statusUndone = false;

        taskQuery.createNameQuery(argsTokenizer.getValue(namePrefix).orElse(EMPTY_STRING).replaceAll("( )+", EMPTY_SPACE_ONE));
        taskQuery.createDateTimeQuery(
                DateTimeUtil.getDateTimeFromArgs(argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING)));
        taskQuery.createPriorityQuery(argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING));
        if (!argsTokenizer.getValue(donePrefix).orElse(EMPTY_STRING).isEmpty() && !argsTokenizer.getValue(undonePrefix).orElse(EMPTY_STRING).isEmpty()) {
            throw new IllegalValueException(TaskQuery.MESSAGE_BOTH_STATUS_SEARCHED_ERROR);
        } else {
            if (!argsTokenizer.getValue(donePrefix).orElse(EMPTY_STRING).isEmpty()) {
                taskQuery.createStatusQuery(statusDone);
            }
            if (!argsTokenizer.getValue(undonePrefix).orElse(EMPTY_STRING).isEmpty()) {
                taskQuery.createStatusQuery(statusUndone);
            }
        }
        taskQuery.createTagsQuery(argsTokenizer.getMultipleRawValues(tagPrefix).orElse(EMPTY_STRING).replaceAll("( )+", EMPTY_SPACE_ONE));

        return taskQuery;
    }

    private ArrayList<String> generateKeywordSetFromArgs(String keywordsArgs) {
        String[] keywordsArray = keywordsArgs.split("\\s+");
        return new ArrayList<String>(Arrays.asList(keywordsArray));
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @@author @A0140022H
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareList(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ListCommand(keywordSet);
    }

    /**
     * Parses arguments in the context of the change storage file directory (cd)
     * command.
     * 
     * @@author A0124333U
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareCd(String args) {
        final Matcher matcher = FILEPATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(CdCommand.MESSAGE_INVALID_FILEPATH));
        }

        if (!isFileTypeValid(args.trim())) {
            return new IncorrectCommand(String.format(CdCommand.MESSAGE_INVALID_FILEPATH));
        }

        return new CdCommand(args.trim());
    }

    /**
     * Parses arguments in the context of the tag command.
     * 
     * @@author A0139924W
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareTag(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(listPrefix, editPrefix, deletePrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.getValue(listPrefix).isPresent()) {
            return new TagCommand(listPrefix);
        }

        if (argsTokenizer.getValue(editPrefix).isPresent()) {
            String editArgs = argsTokenizer.getValue(editPrefix).get();
            final Matcher matcher = TAG_EDIT_COMMAND_FORMAT.matcher(editArgs);
            if (matcher.matches()) {
                return new TagCommand(editPrefix, editArgs.split(EMPTY_SPACE_ONE));
            }
        }

        if (argsTokenizer.getValue(deletePrefix).isPresent()) {
            String index = argsTokenizer.getValue(deletePrefix).get();
            return new TagCommand(deletePrefix, index);
        }

        return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    /**
     * Checks if new file type is a valid file type
     * 
     * @@author A0124333U
     * @param args
     * @return Boolean variable of whether the file type is valid
     **/

    private Boolean isFileTypeValid(String args) {
        String filePath = args.trim();
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if (extension.equals(CdCommand.getXmlFileExt())) {
            return true;
        }
        return false;
    }

}

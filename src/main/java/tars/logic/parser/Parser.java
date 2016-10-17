package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.ExtractorUtil;
import tars.commons.util.StringUtil;
import tars.logic.commands.AddCommand;
import tars.logic.commands.CdCommand;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.EditCommand;
import tars.logic.commands.ExitCommand;
import tars.logic.commands.FindCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.ListCommand;
import tars.logic.commands.MarkCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.SelectCommand;
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

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                           // or
                                                                                                           // more
    // whitespace

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

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

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
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String name = "";

        Flag priorityFlag = new Flag(Flag.PRIORITY, false);
        Flag dateTimeFlag = new Flag(Flag.DATETIME, false);
        Flag tagFlag = new Flag(Flag.TAG, true);

        Flag[] flags = { priorityFlag, dateTimeFlag, tagFlag };

        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, flags);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args, flags, flagsPosMap);

        if (flagsPosMap.size() == 0) {
            name = args;
        } else if (flagsPosMap.firstKey() == 0) {
            // there are arguments but name should be the first argument
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            name = args.substring(0, flagsPosMap.firstKey()).trim();
        }

        try {
            return new AddCommand(name,
                    DateTimeUtil.getDateTimeFromArgs(argumentMap.get(dateTimeFlag).replace(Flag.DATETIME + " ", "")),
                    argumentMap.get(priorityFlag).replace(Flag.PRIORITY + " ", ""),
                    ExtractorUtil.getTagsFromArgs(argumentMap.get(tagFlag), tagFlag));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses arguments in the context of the edit task command.
     * 
     * @@author A0121533W
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        args = args.trim();
        int targetIndex = 0;
        if (args.indexOf(" ") != -1) {
            targetIndex = args.indexOf(" ");
        }

        Optional<Integer> index = parseIndex(args.substring(0, targetIndex));

        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Flag[] flags = generateFlagArrayForEditCommand();

        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, flags);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args, flags, flagsPosMap);

        if (flagsPosMap.size() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(index.get(), argumentMap);
    }

    private Flag[] generateFlagArrayForEditCommand() {
        Flag nameFlag = new Flag(Flag.NAME, false);
        Flag priorityFlag = new Flag(Flag.PRIORITY, false);
        Flag dateTimeFlag = new Flag(Flag.DATETIME, false);
        Flag addTagFlag = new Flag(Flag.ADDTAG, true);
        Flag removeTagFlag = new Flag(Flag.REMOVETAG, true);

        Flag[] flags = { nameFlag, priorityFlag, dateTimeFlag, addTagFlag, removeTagFlag };

        return flags;
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

        return new DeleteCommand(index.get());
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

        Flag doneFlag = new Flag(Flag.DONE, false);
        Flag undoneFlag = new Flag(Flag.UNDONE, false);

        Flag[] flags = { doneFlag, undoneFlag };

        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, flags);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args, flags, flagsPosMap);

        if (flagsPosMap.size() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        String markDone = argumentMap.get(doneFlag).replace(Flag.DONE + " ", "");
        String markUndone = argumentMap.get(undoneFlag).replace(Flag.UNDONE + " ", "");

        return new MarkCommand(markDone, markUndone);
    }

    /**
     * Parses arguments in the context of the select task command.
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

        Flag[] flags = generateFlagArrayForFindCommand();

        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args.trim(), flags);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args.trim(), flags, flagsPosMap);

        if (flagsPosMap.size() == 0) {
            return new FindCommand(generateKeywordSetFromArgs(args.trim()));
        }

        TaskQuery taskQuery;
        try {
            taskQuery = createTaskQuery(argumentMap, flags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }

        return new FindCommand(taskQuery);
    }

    private TaskQuery createTaskQuery(HashMap<Flag, String> argumentMap, Flag[] flags)
            throws DateTimeException, IllegalValueException {
        TaskQuery taskQuery = new TaskQuery();
        Boolean statusDone = true;
        Boolean statusUndone = false;

        taskQuery.createNameQuery(argumentMap.get(flags[0]).replace(Flag.NAME, "").trim().replaceAll("( )+", " "));
        taskQuery.createDateTimeQuery(
                DateTimeUtil.getDateTimeFromArgs(argumentMap.get(flags[1]).replace(Flag.DATETIME, "").trim()));
        taskQuery.createPriorityQuery(argumentMap.get(flags[2]).replace(Flag.PRIORITY, "").trim());
        if (!argumentMap.get(flags[3]).isEmpty() && !argumentMap.get(flags[4]).isEmpty()) {
            throw new IllegalValueException(TaskQuery.BOTH_STATUS_SEARCHED_ERROR);
        } else {
            if (!argumentMap.get(flags[3]).isEmpty()) {
                taskQuery.createStatusQuery(statusDone);
            }
            if (!argumentMap.get(flags[4]).isEmpty()) {
                taskQuery.createStatusQuery(statusUndone);
            }
        }
        taskQuery.createTagsQuery(argumentMap.get(flags[5]).replace(Flag.TAG, "").trim().replaceAll("( )+", " "));

        return taskQuery;
    }

    private ArrayList<String> generateKeywordSetFromArgs(String keywordsArgs) {
        String[] keywordsArray = keywordsArgs.split("\\s+");
        return new ArrayList<String>(Arrays.asList(keywordsArray));
    }

    private Flag[] generateFlagArrayForFindCommand() {
        Flag nameFlag = new Flag(Flag.NAME, true);
        Flag priorityFlag = new Flag(Flag.PRIORITY, false);
        Flag dateTimeFlag = new Flag(Flag.DATETIME, false);
        Flag doneFlag = new Flag(Flag.DONE, false);
        Flag undoneFlag = new Flag(Flag.UNDONE, false);
        Flag tagFlag = new Flag(Flag.TAG, true);

        Flag[] flags = { nameFlag, dateTimeFlag, priorityFlag, doneFlag, undoneFlag, tagFlag };

        return flags;
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @@author @A0140022H
     * @param args
     *            full command args string
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

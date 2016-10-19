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
import tars.commons.exceptions.InvalidRangeException;
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

        case RsvCommand.COMMAND_WORD:
            return prepareRsv(arguments);

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
        Flag recurringFlag = new Flag(Flag.RECURRING, false);

        Flag[] flags = { priorityFlag, dateTimeFlag, tagFlag, recurringFlag };

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
                    ExtractorUtil.getTagsFromArgs(argumentMap.get(tagFlag), tagFlag),
                    ExtractorUtil.getRecurringFromArgs(argumentMap.get(recurringFlag), recurringFlag));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }
    }

    private Command prepareRsv(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        }

        Flag priorityFlag = new Flag(Flag.PRIORITY, false);
        Flag dateTimeFlag = new Flag(Flag.DATETIME, true);
        Flag tagFlag = new Flag(Flag.TAG, true);
        Flag rsvDelFlag = new Flag(Flag.DELETE_RSVTASK, false);

        Flag[] flags = { priorityFlag, dateTimeFlag, tagFlag, rsvDelFlag };

        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, flags);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args, flags, flagsPosMap);

        if (flagsPosMap.containsValue(rsvDelFlag)) {
            return prepareRsvDel(flagsPosMap, argumentMap, rsvDelFlag);
        } else {
            return prepareRsvAdd(args, flagsPosMap, argumentMap, priorityFlag, dateTimeFlag, tagFlag);
        }

    }

    // Parses arguments for adding a reserved task
    private Command prepareRsvAdd(String args, TreeMap<Integer, Flag> flagsPosMap, HashMap<Flag, String> argumentMap,
            Flag priorityFlag, Flag dateTimeFlag, Flag tagFlag) {
        String name = "";
        if (!flagsPosMap.containsValue(dateTimeFlag)) {
            // there are arguments but arguments must contain at least one
            // DateTime
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_DATETIME_NOTFOUND));
        } else if (flagsPosMap.size() == 0) {
            name = args;
        } else if (flagsPosMap.firstKey() == 0) {
            // there are arguments but name should be the first argument
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE));
        } else {
            name = args.substring(0, flagsPosMap.firstKey()).trim();
        }

        Set<String[]> dateTimeStringSet = new HashSet<>();
        try {
            for (String dateTimeString : ExtractorUtil.getDateTimeStringSetFromArgs(argumentMap.get(dateTimeFlag),
                    dateTimeFlag)) {
                dateTimeStringSet.add(DateTimeUtil.getDateTimeFromArgs(dateTimeString));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        try {
            return new RsvCommand(name, dateTimeStringSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }
    }

    // Parses arguments for deleting one or more reserved tasks
    private Command prepareRsvDel(TreeMap<Integer, Flag> flagsPosMap, HashMap<Flag, String> argumentMap,
            Flag rsvDelFlag) {

        if (flagsPosMap.size() > 1 || flagsPosMap.firstKey() != 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE_DEL));
        } else {
            String rangeIndex;
            try {
                rangeIndex = StringUtil
                        .indexString(argumentMap.get(rsvDelFlag).replace(Flag.DELETE_RSVTASK, "").trim());
                return new RsvCommand(rangeIndex);
            } catch (InvalidRangeException | IllegalValueException ie) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RsvCommand.MESSAGE_USAGE_DEL));
            }

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
        args = args.trim();
        if (args.equals("")) {
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

        String markDone = argumentMap.get(doneFlag).replace(Flag.DONE + " ", "").trim();
        String markUndone = argumentMap.get(undoneFlag).replace(Flag.UNDONE + " ", "").trim();

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
            throw new IllegalValueException(TaskQuery.MESSAGE_BOTH_STATUS_SEARCHED_ERROR);
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
        Flag nameFlag = new Flag(Flag.NAME, false);
        Flag priorityFlag = new Flag(Flag.PRIORITY, false);
        Flag dateTimeFlag = new Flag(Flag.DATETIME, false);
        Flag doneFlag = new Flag(Flag.DONE, false);
        Flag undoneFlag = new Flag(Flag.UNDONE, false);
        Flag tagFlag = new Flag(Flag.TAG, false);

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
     * Parses arguments in the context of the tag command.
     * 
     * @@author A0139924W
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareTag(String args) {
        if (args.trim().equals(Flag.LIST)) {
            return new TagCommand(new Flag(Flag.LIST, false));
        } else if (args.trim().indexOf(Flag.EDIT) == 0) {
            args = args.replace(Flag.EDIT, "").trim();

            final Matcher matcher = TAG_EDIT_COMMAND_FORMAT.matcher(args);
            if (matcher.matches()) {
                return new TagCommand(new Flag(Flag.EDIT, false), args.split(" "));
            }
        }

        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
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

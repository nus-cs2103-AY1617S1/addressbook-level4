package tars.logic.parser;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.commons.util.StringUtil;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.ExtractorUtil;
import tars.logic.commands.AddCommand;
import tars.logic.commands.ClearCommand;
import tars.logic.commands.Command;
import tars.logic.commands.DeleteCommand;
import tars.logic.commands.EditCommand;
import tars.logic.commands.ExitCommand;
import tars.logic.commands.FindCommand;
import tars.logic.commands.HelpCommand;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.ListCommand;
import tars.logic.commands.SelectCommand;
import tars.logic.commands.UndoCommand;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.DateTimeException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
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
            return new ListCommand();
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

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
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        // there is no arguments
        if (args.trim().length() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }             
        
        String name = "";
        Flag priorityOpt = new Flag(Flag.PRIORITY, false);
        Flag dateTimeOpt = new Flag(Flag.DATETIME, false);
        Flag tagOpt = new Flag(Flag.TAG, true);
        
        Flag[] prefixes = {
                priorityOpt, 
                dateTimeOpt, 
                tagOpt
        };
        
        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, prefixes);
        HashMap<Flag, String> optionFlagNArgMap = ExtractorUtil.getArguments(args, prefixes, flagsPosMap);
        
        if (flagsPosMap.size() == 0) {
            name = args;
        } else if (flagsPosMap.firstKey() == 0) {
            // there are arguments but name should be the first argument
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            name = args.substring(0, flagsPosMap.firstKey()).trim();
        }
        
        try {
            return new AddCommand(
                    name,
                    DateTimeUtil.getDateTimeFromArgs(optionFlagNArgMap.get(dateTimeOpt).replace(Flag.DATETIME + " ", "")),
                    optionFlagNArgMap.get(priorityOpt).replace(Flag.PRIORITY + " ", ""),
                    ExtractorUtil.getTagsFromArgs(optionFlagNArgMap.get(tagOpt), tagOpt));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }
    }
    
    

    /**
     * Parses arguments in the context of the edit task command.
     *
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
        
        Flag nameOpt = new Flag(Flag.NAME, false);
        Flag priorityOpt = new Flag(Flag.PRIORITY, false);
        Flag dateTimeOpt = new Flag(Flag.DATETIME, false);
        Flag addTagOpt = new Flag(Flag.ADDTAG, true);
        Flag removeTagOpt = new Flag(Flag.REMOVETAG, true);
        
        Flag[] options = {
                nameOpt,
                priorityOpt, 
                dateTimeOpt, 
                addTagOpt,
                removeTagOpt
        };
        
        TreeMap<Integer, Flag> flagsPosMap = ExtractorUtil.getFlagPositon(args, options);
        HashMap<Flag, String> argumentMap = ExtractorUtil.getArguments(args, options, flagsPosMap);
        
        if (flagsPosMap.size() == 0) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        return new EditCommand(index.get(), argumentMap);   
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

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
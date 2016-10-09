package tars.logic.parser;

import tars.commons.core.Messages;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.prefixes.Prefixes;
import tars.commons.util.StringUtil;
import tars.commons.util.DateTimeUtil;
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

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
                                                                                                           // or
                                                                                                           // more
                                                                                                           // keywords
                                                                                                           // separated
                                                                                                           // by
                                                                                                           // whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = Pattern.compile(
            "(?<name>[^/]+) (?<datetime>(-dt (0?[1-9]|[12][0-9]|3[01])[//](0?[1-9]|1[012])[//]\\d{4} ([01]\\d|2[0-3])?[0-5]\\d)"
                    + "|(-dt (0?[1-9]|[12][0-9]|3[01])[//](0?[1-9]|1[012])[//]\\d{4} ([01]\\d|2[0-3])?[0-5]\\d "
                    + "to (0?[1-9]|[12][0-9]|3[01])[//](0?[1-9]|1[012])[//]\\d{4} ([01]\\d|2[0-3])?[0-5]\\d)) "
                    + "(?<priority>-p [hml])" + "(?<tagArguments>(?: -t [^/]+)*)"); // variable
                                                                                    // number
                                                                                    // of
                                                                                    // tags

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
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(matcher.group("name"),
                    DateTimeUtil.getDateTimeFromArgs(matcher.group("datetime").replace("-dt ", "")),
                    matcher.group("priority").replace("-p ", ""), getTagsFromArgs(matcher.group("tagArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_DATE);
        }
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
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" -t ", "").split(" -t "));
        return new HashSet<>(tagStrings);
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

        if (!index.isPresent() || !isDataExtractableToEdit(args)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        String[] argsToEdit = parseArgsToEdit(args);
        return new EditCommand(index.get(), argsToEdit);
    }

    /**
     * Checks whether edit data (index, name, dateTime, priority, tag to add,
     * tag to remove) can be extracted from the argument string. Format is INDEX
     * -n [name] -dt [dateTime] -p [priority] -ta [tag to add] -tr [tag to
     * remove], name, dateTime, priority, tag to add and tag to remove positions
     * can be swapped and are optional.
     *
     * @param args
     *            full command args string from the user
     * @return whether format of edit command arguments allows parsing into
     *         individual arguments
     */
    private boolean isDataExtractableToEdit(String args) {
        final String matchAnyEditDataPrefix = Prefixes.NAME + '|' + Prefixes.DATETIME + '|' + Prefixes.PRIORITY + '|'
                + Prefixes.ADDTAG + '|' + Prefixes.REMOVETAG;
        final String[] splitArgs = args.trim().split(matchAnyEditDataPrefix);
        return splitArgs.length > 1 && !splitArgs[0].isEmpty() && !splitArgs[1].isEmpty();
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

    /**
     * Parses the given arguments string as string array of arguments to edit
     *
     * @param args
     *            arguments string
     * @return string array of arguments to edit
     */
    private String[] parseArgsToEdit(String args) {
        ArrayList<String> temp = new ArrayList<String>();

        // Find indexes of all the data prefixes
        ArrayList<Integer> prefixIndexes = new ArrayList<Integer>();
        int j = 0;
        while (j + 3 < args.length()) {
            String s1 = args.substring(j, j + Prefixes.LENGTH_TWO);
            String s2 = args.substring(j, j + Prefixes.LENGTH_THREE);
            if (Prefixes.LENGTH_TWO_PREFIXES.contains(s1)) {
                prefixIndexes.add(j);
                j += Prefixes.LENGTH_TWO - 1;
            } else if (Prefixes.LENGTH_THREE_PREFIXES.contains(s2)) {
                prefixIndexes.add(j);
                j += Prefixes.LENGTH_THREE - 1;
            }
            j += 1;
        }

        // Add index of task
        temp.add(args.substring(0, prefixIndexes.get(0)).trim());

        // Add remaining arguments up to second last argument
        for (int i = 0; i < prefixIndexes.size() - 1; i++) {
            int start = prefixIndexes.get(i);
            int stop = prefixIndexes.get(i + 1);
            String arg = args.substring(start, stop).trim();
            temp.add(arg);
        }

        // Add last argument
        int start = prefixIndexes.get(prefixIndexes.size() - 1);
        int stop = args.length();
        String arg = args.substring(start, stop).trim();
        temp.add(arg);

        String[] editableArgs = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            editableArgs[i] = temp.get(i);
        }

        return editableArgs;
    }

}

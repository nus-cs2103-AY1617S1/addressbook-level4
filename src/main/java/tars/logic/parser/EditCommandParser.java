package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.EditCommand;
import tars.logic.commands.IncorrectCommand;

public class EditCommandParser extends CommandParser {

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Parses arguments in the context of the edit task command.
     * 
     * @@author A0121533W
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
        args = args.trim();
        int targetIndex = 0;
        if (args.indexOf(EMPTY_SPACE_ONE) != -1) {
            targetIndex = args.indexOf(EMPTY_SPACE_ONE);
        }

        Optional<Integer> index = parseIndex(args.substring(0, targetIndex));

        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
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
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as
     * the index. Returns an {@code Optional.empty()} otherwise.
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

}

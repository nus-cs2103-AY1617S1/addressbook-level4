package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.EditCommand;
import tars.logic.commands.IncorrectCommand;

/**
 * Edit command parser
 * 
 * @@author A0121533W
 *
 */
public class EditCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the edit task command.
     * 
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

        String index;
        try {
            index = StringUtil.indexString((args.substring(0, targetIndex)));
        } catch (Exception e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        if (index.isEmpty()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix,
                priorityPrefix, dateTimePrefix, addTagPrefix, removeTagPrefix);
        argsTokenizer.tokenize(args);

        if (argsTokenizer.numPrefixFound() == 0) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        return new EditCommand(Integer.parseInt(index), argsTokenizer);
    }

}

package tars.logic.parser;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.util.StringUtil;
import tars.logic.commands.Command;
import tars.logic.commands.IncorrectCommand;
import tars.logic.commands.MarkCommand;

public class MarkCommandParser extends CommandParser {

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @@author A0121533W
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepareCommand(String args) {
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
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(markDone, markUndone);
    }

}

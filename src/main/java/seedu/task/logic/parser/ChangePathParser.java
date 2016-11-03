package seedu.task.logic.parser;

import seedu.task.logic.commands.ChangePathCommand;
import seedu.task.logic.commands.Command;

public class ChangePathParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        return new ChangePathCommand(arguments);
    }
}

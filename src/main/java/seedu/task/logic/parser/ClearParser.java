package seedu.task.logic.parser;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;

public class ClearParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        return new ClearCommand();
    }
}

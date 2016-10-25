package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.UndoCommand;

public class UndoParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        return new UndoCommand();
    }
}

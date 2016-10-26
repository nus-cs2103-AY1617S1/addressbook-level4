//@@author A0141052Y
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.HelpCommand;

public class HelpParser extends BaseParser {
    @Override
    public Command parse(String command, String arguments) {
        return new HelpCommand();
    }
}
//@@author A0141052Y
package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.SearchCommand;

public class SearchParser extends BaseParser {

    @Override
    public Command parse(String command, String arguments) {
        return new SearchCommand();
    }

}

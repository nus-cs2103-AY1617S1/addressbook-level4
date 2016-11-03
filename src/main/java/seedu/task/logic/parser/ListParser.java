//@@author A0141052Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.model.Model.FilterType;

public class ListParser extends BaseParser {
    
    private final String FLAG_LIST_TYPE = "";
    
    private final String[] KEYWORD_ARGS_REQUIRED = new String[]{FLAG_LIST_TYPE};
    private final String[] KEYWORD_ARGS_OPTIONAL = new String[]{};
    
    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        if (!this.checkForRequiredArguments(KEYWORD_ARGS_REQUIRED, KEYWORD_ARGS_OPTIONAL, true)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        
        FilterType filter = FilterType.ALL;
        
        switch (getSingleKeywordArgValue(FLAG_LIST_TYPE)) {
        case "all":
            break;
        case "pinned":
            filter = FilterType.PIN;
            break;
        case "pending":
            filter = FilterType.PENDING;
            break;
        case "completed":
            filter = FilterType.COMPLETED;
            break;
        case "overdue":
            filter = FilterType.OVERDUE;
            break;
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        
        return new ListCommand(filter);
    }
}

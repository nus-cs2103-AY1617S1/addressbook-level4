package seedu.task.logic.commands;

import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    
    public static final String MESSAGE_PRIORITY_USAGE = "A priority level should be specified\n"
            + "Example: high, normal or low";

    private String operand;
    private final Set<String> keywords;

    public FindCommand(String operand, Set<String> keywords) {
        this.operand = operand;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(){
            try {
                model.updateFilteredTaskList(operand, keywords);
            } catch (IllegalValueException e) {
                return new CommandResult(e.getMessage());
            }
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
        }
        
}


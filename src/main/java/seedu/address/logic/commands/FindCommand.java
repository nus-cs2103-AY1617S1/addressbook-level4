package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private String operand;
    private final Set<String> keywords;

    public FindCommand(String operand, Set<String> keywords) {
        this.operand = operand;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(){
        
        switch (operand) {
        case "t":
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : keywords) {
                try {
                    tagSet.add(new Tag(tagName));
                } catch (IllegalValueException e) {
                    return new CommandResult(e.getMessage());
                }
            }
            model.updateFilteredTaskList(new UniqueTagList(tagSet)); 
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
        default:
            model.updateFilteredTaskList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
        }
        
    }

}

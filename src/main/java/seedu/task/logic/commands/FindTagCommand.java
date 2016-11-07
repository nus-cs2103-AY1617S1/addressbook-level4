//@@author A0141052Y
package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "find-tags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks that contains all of the specified tags (case-sensitive).\n"
            + "Parameters: TAG_NAME [MORE_TAG_NAMES]...\n"
            + "Example: " + COMMAND_WORD + " homework cs1101s";

    private final Set<Tag> tags;

    /**
     * Constructs a find-tags command
     * @param tagNames a list of tag names to search for
     * @throws IllegalValueException thrown if the tag names are not valid
     */
    public FindTagCommand(String[] tagNames) throws IllegalValueException {
        this.tags = new HashSet<Tag>();
        for (String tagName : tagNames) {
            this.tags.add(new Tag(tagName));
        }
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListByTags(tags);
        return new CommandResult(true, getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}

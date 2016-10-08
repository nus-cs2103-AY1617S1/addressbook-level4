package seedu.jimi.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;

/**
 * 
 * @author zexuan
 *
 * Edits an existing task/event in Jimi.
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task/event in Jimi. "
            + "Example: " + COMMAND_WORD
            + "2 by 10th July at 12 pm";

    public static final String MESSAGE_SUCCESS = "Updated task details: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";

    private final FloatingTask toEdit;

    /**
     * Convenience constructor using raw values and assigns toEdit to the FloatingTask to be edited.
     * //TODO: change to support FloatingTask, Task and Events types as well
     * 
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String name, Set<String> tags, FloatingTask toEdit) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEdit = toEdit;
    }
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}

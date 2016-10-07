package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task {

    private TaskName taskName;
 
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, UniqueTagList tags) {
        this.taskName = taskName;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

}

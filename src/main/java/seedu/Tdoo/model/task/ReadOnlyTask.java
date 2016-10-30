package seedu.Tdoo.model.task;

import seedu.Tdoo.model.task.attributes.Name;

/**
 * A read-only immutable interface for a task in the TodoList .
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    public Name getName();

}

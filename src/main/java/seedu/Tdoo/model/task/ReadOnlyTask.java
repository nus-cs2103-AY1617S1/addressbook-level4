package seedu.Tdoo.model.task;

import seedu.Tdoo.model.task.attributes.Name;
import seedu.Tdoo.model.task.attributes.StartDate;

/**
 * A read-only immutable interface for a task in the TodoList .
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    public Name getName();
    public StartDate getStartDate();
}

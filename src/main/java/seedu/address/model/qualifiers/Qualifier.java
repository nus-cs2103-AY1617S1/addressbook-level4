package seedu.address.model.qualifiers;

import seedu.address.model.task.ReadOnlyTask;

public interface Qualifier {
    boolean run(ReadOnlyTask person);
    String toString();
}

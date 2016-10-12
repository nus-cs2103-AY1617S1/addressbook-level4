package seedu.todo.model.qualifiers;

import seedu.todo.model.task.ReadOnlyTask;

public interface Qualifier {
    boolean run(ReadOnlyTask person);
    String toString();
}

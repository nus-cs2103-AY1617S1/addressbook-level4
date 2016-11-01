package seedu.todo.model.expressions;

import seedu.todo.model.task.ReadOnlyTask;

public interface Expression {
    boolean satisfies(ReadOnlyTask task);
    String toString();
}

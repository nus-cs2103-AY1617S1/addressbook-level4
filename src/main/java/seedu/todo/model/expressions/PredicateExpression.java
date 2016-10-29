package seedu.todo.model.expressions;

import seedu.todo.model.qualifiers.Qualifier;
import seedu.todo.model.task.ReadOnlyTask;

public class PredicateExpression implements Expression {
    private final Qualifier qualifier;

    public PredicateExpression(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public boolean satisfies(ReadOnlyTask task) {
        return qualifier.run(task);
    }

    @Override
    public String toString() {
        return qualifier.toString();
    }
}

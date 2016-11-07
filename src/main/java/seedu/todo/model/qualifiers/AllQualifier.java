package seedu.todo.model.qualifiers;

import seedu.todo.model.task.ReadOnlyTask;
//@@author A0093896H
/**
 * A qualifier that returns all tasks
 */
public class AllQualifier implements Qualifier {
    
    
    @Override
    public boolean run(ReadOnlyTask task) {
        return true;
    }

    @Override
    public String toString() {
        return "all";
    }
}

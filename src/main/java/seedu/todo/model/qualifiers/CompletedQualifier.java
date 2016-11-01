//@@author A0093896H
package seedu.todo.model.qualifiers;

import seedu.todo.model.task.ReadOnlyTask;

/**
 * A qualifier that filter tasks depending on whether the task
 * is completed or not.
 */
public class CompletedQualifier implements Qualifier {
    
    private boolean wantsDone;
    
    public CompletedQualifier(boolean wantsDone){
        this.wantsDone = wantsDone;
    }
    
    @Override
    public boolean run(ReadOnlyTask task) {
        return this.wantsDone ? task.getCompletion().isCompleted() : !task.getCompletion().isCompleted();
    }

    @Override
    public String toString() {
        return this.wantsDone ? "done" : "not done";
    }
}

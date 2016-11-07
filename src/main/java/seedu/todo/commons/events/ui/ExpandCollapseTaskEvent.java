package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.task.ImmutableTask;

//@@author A0135805H
/**
 * An event to tell the Ui to collapse or expand a given task in the to-do list.
 */
public class ExpandCollapseTaskEvent extends BaseEvent{

    public final ImmutableTask task;
    
    public Boolean toCollapse; 

    /**
     * Constructs an event that tells the Ui to collapse or expend a given task in the to-do list.
     * @param task a single index of the task that is matching to the Ui (index 1 to num of tasks, inclusive)
     */
    public ExpandCollapseTaskEvent(ImmutableTask task) {
        this(task, null);
    }
    
    public ExpandCollapseTaskEvent(ImmutableTask task, Boolean toCollapse) {
        this.task = task;
        this.toCollapse = toCollapse;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

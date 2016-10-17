package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

//@@author A0135805H
/**
 * An event to tell the Ui to collapse or expand a given task in the to-do list.
 */
public class ExpandCollapseTaskEvent extends BaseEvent{

    public final int taskIndex;

    /**
     * Construct an event that tells the Ui to collapse or expend a given task in the to-do list.
     * @param taskIndex a single index of the task that is matching to the Ui (index 1 to num of tasks, inclusive)
     */
    public ExpandCollapseTaskEvent(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

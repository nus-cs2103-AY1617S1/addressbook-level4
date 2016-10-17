package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

import java.util.List;

//@@author A0135805H
/**
 * An event to tell the Ui to collapse or expand a given list of tasks in the to-do list.
 */
public class ExpandCollapseTasksEvent extends BaseEvent{

    public final List<Integer> tasksIndexList;

    /**
     * Construct an event that tells the Ui to collapse or expend a given list of tasks in the to-do list.
     * @param tasksIndexList a list of index of the task that is matching to the Ui (index 1 to num of tasks, inclusive)
     */
    public ExpandCollapseTasksEvent(List<Integer> tasksIndexList) {
        this.tasksIndexList = tasksIndexList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

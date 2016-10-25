//@@author A0140011L
package teamfour.tasc.commons.events.ui;

import java.util.List;

import teamfour.tasc.commons.events.BaseEvent;
import teamfour.tasc.model.ReadOnlyTaskList;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Represents a content change in the Task List panel.
 */
public class TaskPanelListChangedEvent extends BaseEvent {

    private final List<ReadOnlyTask> newTaskList;

    /**
     * Constructor for event
     * @param newTaskList contents
     */
    public TaskPanelListChangedEvent(List<ReadOnlyTask> newTaskList){
        this.newTaskList = newTaskList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public List<ReadOnlyTask> getNewTaskList() {
        return newTaskList;
    }
}

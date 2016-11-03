package seedu.taskscheduler.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.taskscheduler.commons.events.BaseEvent;
import seedu.taskscheduler.model.task.ReadOnlyTask;

/**
 * Represent items change in the task List Panel
 */
public class TaskPanelItemChangedEvent extends BaseEvent {


    private final ObservableList<ReadOnlyTask> newList;

    public TaskPanelItemChangedEvent(ObservableList<ReadOnlyTask> newList){
        this.newList = newList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyTask> getNewList() {
        return newList;
    }
}
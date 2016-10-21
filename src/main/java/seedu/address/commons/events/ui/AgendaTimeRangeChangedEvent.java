package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;

/**
 * Represents a selection change in the Task List Panel
 */
public class AgendaTimeRangeChangedEvent extends BaseEvent {


    private final TaskDate inputDate;
    private final ObservableList<TaskComponent> taskComponentList;

    public AgendaTimeRangeChangedEvent(TaskDate inputDate, ObservableList<TaskComponent> taskComponentList){
        this.inputDate = inputDate;
        this.taskComponentList = taskComponentList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskDate getInputDate() {
        return inputDate;
    }
    
    public ObservableList<TaskComponent> getData() {
        return taskComponentList;
    }
}

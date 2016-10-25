package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;

//@@author A0147967J
/**
 * Indicates a change in the displayed time range of the agenda.
 */
public class AgendaTimeRangeChangedEvent extends BaseEvent {


    private final TaskDate inputDate;
    private final List<TaskComponent> taskComponentList;

    public AgendaTimeRangeChangedEvent(TaskDate inputDate, List<TaskComponent> list){
        this.inputDate = inputDate;
        this.taskComponentList = list;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskDate getInputDate() {
        return inputDate;
    }
    
    public List<TaskComponent> getData() {
        return taskComponentList;
    }
}

package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.model.task.TaskDate;

//@@author A0147967J
/**
 * Indicates a change in the displayed time range of the agenda.
 */
public class AgendaTimeRangeChangedEvent extends BaseEvent {


    private final TaskDate inputDate;
    private final List<TaskOcurrence> taskComponentList;

    public AgendaTimeRangeChangedEvent(TaskDate inputDate, List<TaskOcurrence> list){
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
    
    public List<TaskOcurrence> getData() {
        return taskComponentList;
    }
}

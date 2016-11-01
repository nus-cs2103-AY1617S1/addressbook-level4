package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;

//@@author A0147967J
/**
 * Indicates a change in the displayed time range of the agenda.
 */
public class AgendaTimeRangeChangedEvent extends BaseEvent {

    private final TaskDate inputDate;
    private final List<TaskOccurrence> taskComponentList;

    public AgendaTimeRangeChangedEvent(TaskDate inputDate, List<TaskOccurrence> list) {
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
    
    public List<TaskOccurrence> getData() {
        return taskComponentList;
    }
}

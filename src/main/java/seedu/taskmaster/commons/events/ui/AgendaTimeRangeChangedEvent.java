package seedu.taskmaster.commons.events.ui;

import java.util.List;

import seedu.taskmaster.commons.events.BaseEvent;
import seedu.taskmaster.model.task.TaskDate;
import seedu.taskmaster.model.task.TaskOccurrence;

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

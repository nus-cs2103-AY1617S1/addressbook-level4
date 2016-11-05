package seedu.address.model.task.stub;

import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;

//@@author A0135782Y
public class TaskOccurrenceStub extends TaskOccurrence {

    public TaskOccurrenceStub(TaskStub taskReference, TaskDateStub startDate, TaskDateStub endDate) {
        super(taskReference, startDate, endDate);
    }
    public TaskOccurrenceStub(Task taskReference, TaskDateStub startDate, TaskDateStub endDate) {
        super(taskReference, startDate, endDate);
    }
}
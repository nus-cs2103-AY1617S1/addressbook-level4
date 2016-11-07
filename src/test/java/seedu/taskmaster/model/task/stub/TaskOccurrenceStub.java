package seedu.taskmaster.model.task.stub;

import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.TaskOccurrence;

//@@author A0135782Y
public class TaskOccurrenceStub extends TaskOccurrence {

    public TaskOccurrenceStub(TaskStub taskReference, TaskDateStub startDate, TaskDateStub endDate) {
        super(taskReference, startDate, endDate);
    }
    public TaskOccurrenceStub(Task taskReference, TaskDateStub startDate, TaskDateStub endDate) {
        super(taskReference, startDate, endDate);
    }
}
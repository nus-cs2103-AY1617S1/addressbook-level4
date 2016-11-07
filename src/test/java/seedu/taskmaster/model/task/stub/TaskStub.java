package seedu.taskmaster.model.task.stub;

import java.util.ArrayList;
import java.util.List;

import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.Name;
import seedu.taskmaster.model.task.RecurringType;
import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.TaskDate;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.model.task.TaskType;

//@@author A0135782Y
public class TaskStub extends Task {
    private NameStub name;
    public TaskStub(Name name, UniqueTagList tags) throws IllegalValueException {   
        super(name, tags);
    }
    public TaskStub() throws IllegalValueException {
        name = new NameStub();
        new ArrayList<TaskOccurrenceStub>();
    }
    @Override
    public Name getName() {
        return name;
    }

    public void setTaskType(TaskType type) {
    }
    
    public void setRecurringType(RecurringType type) {
    }
    public void setRecurringDates(List<TaskOccurrence> newComponentList){
    }
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }
    
    @Override
    public TaskType getTaskType() {
        return TaskType.NON_FLOATING;
    }
    @Override
    public RecurringType getRecurringType() {
        return RecurringType.NONE;
    }

    @Override
    public String toString() {
        return "";
    }
    @Override
    public void completeTaskWhenAllOccurrencesArchived() {
    }
    @Override
    public void updateTask(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate, 
                           RecurringType recurringType, int index) {
    }
    
    @Override
    public TaskOccurrenceStub getLastAppendedComponent() {
        return new TaskOccurrenceStub(this,new TaskDateStub(), new TaskDateStub());
    }
    
    @Override
    public List<TaskOccurrence> getTaskDateComponent() {
        List<TaskOccurrence> occurrences = new ArrayList<TaskOccurrence>();
        occurrences.add(getLastAppendedComponent());
        return occurrences;
    }

    @Override
    public void appendRecurringDate(TaskOccurrence componentToBeAdded) {
    }
}
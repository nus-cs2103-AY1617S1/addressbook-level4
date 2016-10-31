package seedu.address.model.task.stub;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskType;

public class TaskStub extends Task {
    private NameStub name;
    private RecurringType recurringType;
    
    private List<TaskOccurrenceStub> recurringDates;
    
    public TaskStub() throws IllegalValueException {    
        name = new NameStub("dummy");
        recurringDates = new ArrayList<TaskOccurrenceStub>();
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
    public void completeTaskWhenAllComponentArchived() {
    }
    @Override
    public void updateTask(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate, RecurringType recurringType) {
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
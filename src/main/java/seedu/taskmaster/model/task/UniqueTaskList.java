package seedu.taskmaster.model.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmaster.commons.exceptions.DuplicateDataException;
import seedu.taskmaster.commons.util.CollectionUtil;
import seedu.taskmaster.model.tag.UniqueTagList;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final List<Task> internalList = new ArrayList<Task>();
    private final ObservableList<TaskOccurrence> internalOccurrenceList = FXCollections.observableArrayList();

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    // @@author A0147967J
    /**
     * Signals that an operation adding/blocking a time slot in the list would
     * fail because the timeslot is already occupied.
     */

    public static class TimeslotOverlapException extends DuplicateDataException {

        public TimeslotOverlapException() {
            super("Operation cannot be done due to overlapping with blocked slots.");
        }
    }
    // @@author

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {
    }

    /**
     * Returns true if the list contains an equivalent task as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return !toCheck.getLastAppendedComponent().isBlockedSlot() // Ignore blocked case
                && internalList.contains(toCheck);
    }

    // @@author A0147967J
    /**
     * Returns true if the given task requests to use an occupied time slot.
     */
    public boolean overlaps(ReadOnlyTask toCheck) {
        assert toCheck != null;
        
        TaskOccurrence taskOccurrence = toCheck.getLastAppendedComponent();
        // ignore floating and deadline tasks
        if (!taskOccurrence.isSlot()){
            return false;
        }
        // Only compare tasks with blocked time slots.
        // Or if it is block command, check with existing tasks
        return isOverlappingWithBlock(taskOccurrence) || isBlockOverlappingWithTask(taskOccurrence);

    }
    
    /**
     * Returns true if the edited task requests to use a blocked time slot.
     */
    public boolean overlapsForEdit(TaskOccurrence original, TaskOccurrence toCheck){
        if (!toCheck.isSlot()){
            return false;
        }
        for(TaskOccurrence t: internalOccurrenceList){
            if(!t.equals(original) && t.isBlockedSlot() && t.isOverlappedWith(toCheck)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if the given blocked slot requests to 
     * use a time slot that is occupied by other tasks.
     */
    public boolean isBlockOverlappingWithTask(TaskOccurrence toCheck) {
        if (!toCheck.isBlockedSlot()) {
            return false;
        }
        for (TaskOccurrence t : internalOccurrenceList) {
            if (!t.isArchived() && t.isSlot() && toCheck.isOverlappedWith(t)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Returns true if the given task requests to use a blocked time slot.
     */
    public boolean isOverlappingWithBlock(TaskOccurrence toCheck) {
        for (TaskOccurrence t : internalOccurrenceList) {
            if (t.isBlockedSlot() && toCheck.isOverlappedWith(t)) {
                return true;
            }
        }
        return false;
    }

    
    // @@author

    // @@author A0135782Y
    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException 
     *                 If the task is a non recurring task is a duplicate of an existing task in the list.
     * @throws TimeslotOverlapException 
     *                 If the task is cutting into an overlapped slot.
     */
    public void add(Task toAdd) throws DuplicateTaskException, TimeslotOverlapException {
        assert toAdd != null;
        if (contains(toAdd)) {
            if (!toAdd.getRecurringType().equals(RecurringType.NONE)) {
                appendDuplicateRecurringDatesToTask(toAdd);
                return;
            }
            throw new DuplicateTaskException();
        }
        if (overlaps(toAdd)) {
            throw new TimeslotOverlapException();
        }
        internalList.add(toAdd);
        internalOccurrenceList.addAll(toAdd.getTaskDateComponent());
    }

    /**
     * Append duplicate recurring task together into a single unique task
     * 
     * @param toAdd Task to be appended into a single unique task.
     */
    private void appendDuplicateRecurringDatesToTask(Task toAdd) {
        int idx = internalList.indexOf(toAdd);
        Task toBeAppendedOn = internalList.get(idx);
        internalOccurrenceList.add(toAdd.getLastAppendedComponent());
        toBeAppendedOn.appendRecurringDate(toAdd.getLastAppendedComponent());
    }
    // @@author

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        internalOccurrenceList.removeAll(toRemove.getTaskDateComponent());
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public List<Task> getInternalTaskList() {
        return internalList;
    }

    public ObservableList<TaskOccurrence> getInternalOccurrenceList() {
        return internalOccurrenceList;
    }

    public void appendTaskOccurrence(TaskOccurrence component) {
        internalOccurrenceList.add(component);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    // @@author A0147967J
    /**
     * Returns true if the specified task component is successfully archived.
     */
    public boolean archive(TaskOccurrence target) {
        assert target != null;
        if (target.isArchived()) {
            return false;
        }
        boolean taskFoundAndArchived = false;
        for (TaskOccurrence t : internalOccurrenceList) {
            if (t.equals(target)) {
                t.archive();
                taskFoundAndArchived = true;
                t.getTaskReference().completeTaskWhenAllOccurrencesArchived();
            }
        }
        return taskFoundAndArchived;
    }
    // @@author
    
    // @@author A0147995H
    public boolean updateTask(TaskOccurrence target, Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate,
            RecurringType recurringType) throws TimeslotOverlapException {
        assert target != null;

        boolean taskFoundAndUpdated = false;
        for (TaskOccurrence t : internalOccurrenceList) {
            if (t.equals(target)) {
                TaskDate realStartDate = startDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : startDate;
                TaskDate realEndDate = endDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : endDate;
                Task checkTask = new Task(target.getTaskReference().getName(), target.getTaskReference().getTags(), realStartDate, realEndDate,
                        recurringType, target.getTaskReference().getRecurringPeriod());
                if (overlapsForEdit(t, checkTask.getLastAppendedComponent()))
                    throw new TimeslotOverlapException();
                int index = t.getTaskReference().getTaskDateComponent().indexOf(t);
                t.getTaskReference().updateTask(name, tags, startDate, endDate, recurringType, index);
                internalOccurrenceList.clear();
                for (Task h : internalList) {
                    internalOccurrenceList.addAll(h.getTaskDateComponent());
                }
                taskFoundAndUpdated = true;
                break;
            }
        }
        return taskFoundAndUpdated;
    }
}

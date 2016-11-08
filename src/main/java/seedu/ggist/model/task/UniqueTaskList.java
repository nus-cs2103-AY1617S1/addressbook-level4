package seedu.ggist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.DuplicateDataException;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.logic.parser.DateTimeParser;

import java.util.*;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate task");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    	public TaskNotFoundException() {
    		super("Target task is not found");
    	}
    }

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
  //@@author A0138411N
    public void edit(ReadOnlyTask toEdit, String field, String value) throws IllegalValueException {
        assert toEdit != null;
        switch (field) {
        case "task":
                toEdit.getTaskName().editTaskName(value);
            break;
        case "start date":
                Task.checkTimeClash(Task.formatMissingDateTime(new TaskDate(value),toEdit.getStartTime()),(toEdit.getEndDateTime()));
                toEdit.getStartDate().editDate(value);
                toEdit.constructStartDateTime(toEdit.getStartDate(), toEdit.getStartTime());
                toEdit.checkTimeOverdue();
            break;
        case "start time":
            if (!toEdit.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) 
                 && toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)){
                Task.checkTimeClash(Task.formatMissingDateTime(toEdit.getEndDate(), new TaskTime(value)), toEdit.getEndDateTime());
                toEdit.getStartTime().editTime(value);   
            } else {
                Task.checkTimeClash(Task.formatMissingDateTime(toEdit.getEndDate(), new TaskTime(value)), toEdit.getEndDateTime());
                toEdit.getStartTime().editTime(value);  
            }
                //if there is no start time saved and there is an end date
                //use the end date as the start date
                if (toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) 
                    && !toEdit.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) ) {
                    toEdit.constructStartDateTime(toEdit.getEndDate(), toEdit.getStartTime());
                //if there is no start and end date
                //use the current date as start date
                } else if (toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
                    toEdit.constructStartDateTime(new TaskDate(new DateTimeParser(new Date().toString()).getDate()), toEdit.getStartTime());
                //if there is a start date and an end date
                } else {
                    toEdit.constructStartDateTime(toEdit.getStartDate(), toEdit.getStartTime());   
                }
                toEdit.checkTimeOverdue();
            break;
        case "end date":
                
                //if it is a deadline task, construct the start using the end date and times
                if (toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) && toEdit.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) { 
                    toEdit.getEndDate().editDate(value);
                    toEdit.constructStartDateTime(toEdit.getEndDate(), toEdit.getEndTime());
                } else {
                    Task.checkTimeClash(toEdit.getStartDateTime(), Task.formatMissingDateTime(new TaskDate(value),toEdit.getEndTime()));
                    toEdit.getEndDate().editDate(value);
                }
                toEdit.constructEndDateTime(toEdit.getEndDate(), toEdit.getEndTime());
                toEdit.checkTimeOverdue();
            break;
        case "end time":  
              //if it is a deadline task, construct the start using the end date and times
                if (toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) && toEdit.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) { 
                    toEdit.getEndTime().editTime(value); 
                    toEdit.constructStartDateTime(toEdit.getEndDate(), toEdit.getEndTime());
                } else if (!toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) 
                            && toEdit.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)){
                    Task.checkTimeClash(toEdit.getStartDateTime(), Task.formatMissingDateTime(toEdit.getStartDate(), new TaskTime(value)));
                    toEdit.getEndTime().editTime(value);   
                } else {
                    Task.checkTimeClash(toEdit.getStartDateTime(), Task.formatMissingDateTime(toEdit.getEndDate(), new TaskTime(value)));
                    toEdit.getEndTime().editTime(value);  
                }
                //if there is no end date but has a start date
                //use the start date as the end date
                if (toEdit.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED) 
                    && !toEdit.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED) ) {
                    toEdit.constructEndDateTime(toEdit.getStartDate(), toEdit.getEndTime());
                //if there is no start and end dates
                //use current date as end date
                } else if (toEdit.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
                    toEdit.constructEndDateTime(new TaskDate(new DateTimeParser(new Date().toString()).getDate()), toEdit.getEndTime());
                }                
                toEdit.constructEndDateTime(toEdit.getEndDate(), toEdit.getEndTime());
                toEdit.checkTimeOverdue();
            break;
        case "priority":
                toEdit.getPriority().editPriority(value);
            break;
        }    
    }
  //@@author
    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

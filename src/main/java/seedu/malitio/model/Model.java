package seedu.malitio.model;

import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.history.InputHistory;
import seedu.malitio.model.task.*;
import seedu.malitio.model.task.UniqueEventList.*;
import seedu.malitio.model.task.UniqueFloatingTaskList.*;
import seedu.malitio.model.task.UniqueDeadlineList.*;

import java.util.Set;
import java.util.Stack;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyMalitio newData);

    /** Returns Malitio */
    ReadOnlyMalitio getMalitio();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyFloatingTask target) throws UniqueFloatingTaskList.FloatingTaskNotFoundException;

    /** Deletes the given deadline. */
    void deleteTask(ReadOnlyDeadline target) throws UniqueDeadlineList.DeadlineNotFoundException;
    
    /** Deletes the given event. */
    void deleteTask(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;
    
    /** Adds the given floating task */
    void addFloatingTask(FloatingTask task) throws UniqueFloatingTaskList.DuplicateFloatingTaskException;
    
    /** Adds the given floating task at a specific index */
    void addFloatingTaskAtSpecificPlace(FloatingTask task, int index) throws DuplicateFloatingTaskException;
    
    /** Adds the given deadline*/
    void addDeadline(Deadline deadline) throws UniqueDeadlineList.DuplicateDeadlineException;
    
    /** Adds the given event*/
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();
    
    /** Returns the filtered deadline list as an {@code UnmodifiableObservableList<ReadOnlyDeadline>} */
    UnmodifiableObservableList<ReadOnlyDeadline> getFilteredDeadlineList();
    
    /** Returns the filtered deadline list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();
    
    /** Returns the History of the Model so far */
    Stack<InputHistory> getHistory();
    
    /** Returns the Future of the Model so far which are commands that are undo-ed */
    Stack<InputHistory> getFuture();

    /** Updates the filter of the filtered floating task list to show all tasks */
    void updateFilteredTaskListToShowAll();
    
    /** Updates the filter of the filtered deadlines to show all deadlines */
    void updateFilteredDeadlineListToShowAll();
    
    /** Updates the filter of the filtered events to show all events */
    void updateFilteredEventListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered deadlines to filter by the given keywords*/
    void updateFilteredDeadlineList(Set<String> keywords);
   
    /** Updates the filter of the filtered deadlines to filter by the given time*/
    void updateFilteredDeadlineList(DateTime keyword);
    
    /** Updates the filter of the filtered events to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
    
    /** Updates the filter of the filtered deadlines to filter by the given time*/
    void updateFilteredEventList(DateTime keyword);
    
    /** Replaces the floating task with the intended edit.*/
    void editFloatingTask(FloatingTask editedTask, ReadOnlyFloatingTask taskToEdit) throws FloatingTaskNotFoundException, DuplicateFloatingTaskException;
    
    /** Replaces the deadline with the intended edit.*/
    void editDeadline(Deadline editedDeadline, ReadOnlyDeadline deadlineToEdit) throws DuplicateDeadlineException, DeadlineNotFoundException;

    /** Replaces the event with the intended edit.*/
    void editEvent(Event editedTask, ReadOnlyEvent eventToEdit) throws DuplicateEventException, EventNotFoundException;
    
    /** Complete the floating task.*/
	void completeFloatingTask(ReadOnlyFloatingTask taskToComplete) throws FloatingTaskNotFoundException, FloatingTaskCompletedException;
	
	 /** Complete the deadline.*/
	void completeDeadline(ReadOnlyDeadline deadlineToComplete) throws DeadlineCompletedException, DeadlineNotFoundException;
	
	/** Uncomplete the floating task.*/
	void uncompleteFloatingTask(ReadOnlyFloatingTask taskToUncomplete) throws FloatingTaskNotFoundException, FloatingTaskUncompletedException;
	
	 /** Uncomplete the deadline.*/
	void uncompleteDeadline(ReadOnlyDeadline deadlineToUncomplete) throws DeadlineUncompletedException, DeadlineNotFoundException;
	
	/** Marks the floating task as a prority.*/
	void markFloatingTask(ReadOnlyFloatingTask taskToMark, boolean marked)
	        throws FloatingTaskNotFoundException, FloatingTaskMarkedException, FloatingTaskUnmarkedException;
    
	/** Marks the deadline as a prority.*/
    void markDeadline(ReadOnlyDeadline deadlineToMark, boolean marked)
            throws DeadlineNotFoundException, DeadlineMarkedException, DeadlineUnmarkedException;
    
    /** Marks the event as a priority.*/
    void markEvent(ReadOnlyEvent eventToMark, boolean marked)
            throws EventNotFoundException, EventMarkedException, EventUnmarkedException;
    
    /** Indicate the directory of data file has changed. Save data into new directory*/
    void dataFilePathChanged(); 
}

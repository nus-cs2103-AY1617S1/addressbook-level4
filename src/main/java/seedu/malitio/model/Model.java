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
    void deleteTask(Object target) throws FloatingTaskNotFoundException, DeadlineNotFoundException, EventNotFoundException;
    
    /** Adds the given task */
    void addTask(Object task) throws DuplicateFloatingTaskException, DuplicateDeadlineException, DuplicateEventException;
    
    /** Adds the given floating task at a specific index */
    void addFloatingTaskAtSpecificPlace(Object task, int index) throws DuplicateFloatingTaskException;
    
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
    void editTask(Object editedTask, Object taskToEdit)
            throws FloatingTaskNotFoundException, DuplicateFloatingTaskException, DuplicateDeadlineException,
            DeadlineNotFoundException, DuplicateEventException, EventNotFoundException;
    
    /** Complete the task.*/
    void completeTask(Object taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException, DeadlineCompletedException, DeadlineNotFoundException;
    
    /** Complete the task.*/
    void uncompleteTask(Object taskToUncomplete) throws FloatingTaskUncompletedException, FloatingTaskNotFoundException, DeadlineUncompletedException, DeadlineNotFoundException;
    
    /** Complete the floating task.*/
	void completeFloatingTask(ReadOnlyFloatingTask taskToComplete) throws FloatingTaskNotFoundException, FloatingTaskCompletedException;
	
	 /** Complete the deadline.*/
	void completeDeadline(ReadOnlyDeadline deadlineToComplete) throws DeadlineCompletedException, DeadlineNotFoundException;
	
	/** Uncomplete the floating task.*/
	void uncompleteFloatingTask(ReadOnlyFloatingTask taskToUncomplete) throws FloatingTaskNotFoundException, FloatingTaskUncompletedException;
	
	 /** Uncomplete the deadline.*/
	void uncompleteDeadline(ReadOnlyDeadline deadlineToUncomplete) throws DeadlineUncompletedException, DeadlineNotFoundException;
	
	/** Marks the task as a priority */
    void markTask(Object taskToMark) throws FloatingTaskNotFoundException, FloatingTaskMarkedException,
    DeadlineNotFoundException, DeadlineMarkedException, EventNotFoundException, EventMarkedException;

    /** Unmarks the task as a priority */
    void unmarkTask(Object taskToUnmark) throws FloatingTaskNotFoundException, FloatingTaskUnmarkedException,
    DeadlineNotFoundException, DeadlineUnmarkedException, EventNotFoundException, EventUnmarkedException;
	
    /** Indicate the directory of data file has changed. Save data into new directory*/
    void dataFilePathChanged();
    
    /** Show all task from beginning of time*/
	void ShowAllTask();

}

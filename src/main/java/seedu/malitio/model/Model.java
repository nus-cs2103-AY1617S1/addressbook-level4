package seedu.malitio.model;

import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueFloatingTaskList;

import java.util.Set;

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

    /** Adds the given floating task */
    void addFloatingTask(FloatingTask task) throws UniqueFloatingTaskList.DuplicateFloatingTaskException;
    
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
    
    /** Updates the filter of the filtered events to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
    
}

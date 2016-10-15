package seedu.malitio.model;

import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlySchedule;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.Schedule;
import seedu.malitio.model.task.UniqueScheduleList;
import seedu.malitio.model.task.UniqueTaskList;

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
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(FloatingTask task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given Schedule task */
    void addSchedule(Schedule schedule) throws UniqueScheduleList.DuplicateScheduleException;

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();
    
    /** Returns the filtered events and deadlines as an {@code UnmodifiableObservableList<ReadOnlySchedule>} */
    UnmodifiableObservableList<ReadOnlySchedule> getFilteredEventsAndDeadlines();

    /** Updates the filter of the filtered floating task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered events and deadlines to show all tasks */
    void updateFilteredScheduleToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered events and deadlines to filter by the given keywords*/
    void updateFilteredSchedule(Set<String> keywords);

}

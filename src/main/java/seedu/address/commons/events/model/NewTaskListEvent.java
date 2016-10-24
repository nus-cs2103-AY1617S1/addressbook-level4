package seedu.address.commons.events.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.Task;

/** 
 * Indicates the Task List have been replaced (due to undo/redo commands) 
 */
//@@author A0139817U
public class NewTaskListEvent extends BaseEvent {

	public final UniqueItemCollection<Task> newTasks;
    public final FilteredList<Task> filteredTasks;

    public NewTaskListEvent(UniqueItemCollection<Task> newTasks, FilteredList<Task> filteredTasks){
        this.newTasks = newTasks;
        this.filteredTasks = filteredTasks;
    }

    @Override
    public String toString() {
        return "number of tasks " + newTasks.getInternalList().size();
    }
}

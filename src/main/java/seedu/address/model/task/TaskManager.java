package seedu.address.model.task;

import java.util.Set;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UniquePersonList;

/*
 * Manages a list of tasks 1and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements InMemoryTaskList {
	private UniqueItemCollection<Task> tasks;

	public TaskManager() {
		// TODO: make use of loaded data
		this.tasks = new UniqueItemCollection<Task>();
	}
	
	public TaskManager(UniqueItemCollection<Task> tasks, UserPrefs userPrefs) {
		this.tasks = tasks;
	}
	
	@Override
	public synchronized void addTask(Task toAdd) throws DuplicateItemException {
		tasks.add(toAdd);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void deleteTask(Task toRemove) throws ItemNotFoundException {
		tasks.remove(toRemove);
		 indicateTaskManagerChanged();
	}
	
    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(tasks));
    }

	@Override
	public void filterTasks(Set<String> keywords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearTasksFilter() throws ItemNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UniqueItemCollection<Task> getCurrentFilteredTasks() {
		// TODO Auto-generated method stub
		return tasks;
	}
	
}

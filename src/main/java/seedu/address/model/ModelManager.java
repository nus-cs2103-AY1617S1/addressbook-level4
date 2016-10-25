package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.sun.xml.internal.ws.util.CompletedFuture;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	private final TaskManager addressBook;
	private final FilteredList<Task> filteredPersons;

	/**
	 * Initializes a ModelManager with the given AddressBook AddressBook and its
	 * variables should not be null
	 */
	public ModelManager(TaskManager src, UserPrefs userPrefs) {
		super();
		assert src != null;
		assert userPrefs != null;

		logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

		addressBook = new TaskManager(src);
		filteredPersons = new FilteredList<>(addressBook.getTasks());
	}

	public ModelManager() {
		this(new TaskManager(), new UserPrefs());
	}

	public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
		addressBook = new TaskManager(initialData);
		filteredPersons = new FilteredList<>(addressBook.getTasks());
	}

	@Override
	public void resetData(ReadOnlyTaskManager newData) {
		addressBook.resetData(newData);
		indicateAddressBookChanged();
	}

	@Override
	public ReadOnlyTaskManager getAddressBook() {
		return addressBook;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateAddressBookChanged() {
		raise(new AddressBookChangedEvent(addressBook));
	}

	@Override
	public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
		addressBook.removeTask(target);
		indicateAddressBookChanged();
	}

	@Override
	public synchronized void addPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
		addressBook.addTask(person);
		updateFilteredListToShowAll();
		indicateAddressBookChanged();
	}

	// =========== Filtered Person List Accessors
	// ===============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList() {
		return new UnmodifiableObservableList<>(filteredPersons);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredPersons.setPredicate(null);
	}

	@Override
	public void updateFilteredPersonList(Set<String> keywords) {
		updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
	}

	private void updateFilteredPersonList(Expression expression) {
		filteredPersons.setPredicate(expression::satisfies);
	}

	public void updateFilteredListToShowClashing() throws DuplicateTaskException {

		TaskManager taskmanager = new TaskManager();
		for (int i = 0; i < filteredPersons.size() - 1; i++) {
			boolean isClashing = false;
			Task task = filteredPersons.get(i);
			Deadline deadline = task.getDeadline();
			for (int j = i + 1; j < filteredPersons.size(); j++) {
				Task task2 = filteredPersons.get(j);
				Deadline deadline2 = task2.getDeadline();
				if (deadline != null && deadline2 != null && deadline.equals(deadline2)) {
					if (!taskmanager.contains(task2))
						taskmanager.addTask(task2);
					isClashing = true;
				}
			}
			if (isClashing) {
				if (!taskmanager.contains(task))
					taskmanager.addTask(task);
			}
		}
		FilteredList<Task> clashingTasks = new FilteredList<Task>(taskmanager.getTasks());
		updateFilteredPersonList(new PredicateExpression(new ClashQualifier(clashingTasks)));
	}
	
	public void updateFilteredListToShowIncompleteTask() throws DuplicateTaskException {
		TaskManager taskmanager = new TaskManager();
		
		for (int i = 0; i < filteredPersons.size() - 1; i++){
			boolean isIncompleted = false;
			Task task = filteredPersons.get(i);
			if (!task.getName().toString().contains(" is completed")) isIncompleted = true;
			if (isIncompleted) {
				if (!taskmanager.contains(task))
					taskmanager.addTask(task);
			}
		}
		
		FilteredList<Task> clashingTasks = new FilteredList<Task>(taskmanager.getTasks());
		updateFilteredPersonList(new PredicateExpression(new ClashQualifier(clashingTasks)));
	}

	// ========== Inner classes/interfaces used for filtering
	// ==================================================

	interface Expression {
		boolean satisfies(ReadOnlyTask person);

		String toString();
	}

	private class PredicateExpression implements Expression {

		private final Qualifier qualifier;

		PredicateExpression(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		@Override
		public boolean satisfies(ReadOnlyTask task) {
			return qualifier.run(task);
		}

		@Override
		public String toString() {
			return qualifier.toString();
		}
	}

	interface Qualifier {
		boolean run(ReadOnlyTask task);

		String toString();
	}

	private class NameQualifier implements Qualifier {
		private Set<String> nameKeyWords;

		NameQualifier(Set<String> nameKeyWords) {
			this.nameKeyWords = nameKeyWords;
		}

		@Override
		public boolean run(ReadOnlyTask person) {
			return nameKeyWords.stream()
					.filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword)).findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", nameKeyWords);
		}
	}

	private class ClashQualifier implements Qualifier {
		private FilteredList<Task> tasks;

		ClashQualifier(FilteredList<Task> tasks) {
			this.tasks = tasks;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			for (Task temp : tasks) {
				if (temp.equals(task)) {
					return true;
				}
			}
			return false;
		}
	}

	private Set<String> getDeadlinesFromArgs(String deadlineArguments) {
		// no tags
		if (deadlineArguments.isEmpty()) {
			return Collections.emptySet();
		}
		// replace first delimiter prefix, then split
		final Collection<String> deadlineStrings = Arrays
				.asList(deadlineArguments.replaceFirst(" d/", "").split(" t/"));
		return new HashSet<>(deadlineStrings);
	}

}

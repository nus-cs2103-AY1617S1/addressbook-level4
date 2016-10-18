package seedu.address.model.task;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AliasChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.Alias;
import seedu.address.model.UserPrefs;

/*
 * Manages a list of tasks 1and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements InMemoryTaskList {
	private UniqueItemCollection<Task> tasks;
	private UniqueItemCollection<Alias> alias;
	private final FilteredList<Task> filteredTasks;



	public TaskManager() {
		this(new UniqueItemCollection<Task>(), new UniqueItemCollection<Alias>(), null);		
	}
	
	public TaskManager(UniqueItemCollection<Task> tasks, UniqueItemCollection<Alias> alias, UserPrefs userPrefs) {
		this.tasks = tasks;
		this.alias = alias;
		filteredTasks = new FilteredList<>(this.tasks.getInternalList());
		filterUncompletedTasks();
	}
	
	@Override
	public synchronized void addTask(Task toAdd) throws DuplicateItemException {
		tasks.add(toAdd);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void updateTask(Task toUpdate, Task newTask) throws ItemNotFoundException {
		assert tasks.contains(toUpdate);
		
		tasks.replace(toUpdate, newTask);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void deleteTask(Task toRemove) throws ItemNotFoundException {
	    tasks.remove(toRemove);
	    indicateTaskManagerChanged();
	}
	
	@Override
	public void favoriteTask(Task toFavorite) {
		assert tasks.contains(toFavorite);
		
		toFavorite.setAsFavorite();
		indicateTaskManagerChanged();
	}

	@Override
	public void unfavoriteTask(Task toUnfavorite) {
		assert tasks.contains(toUnfavorite);
		
		toUnfavorite.setAsNotFavorite();
		indicateTaskManagerChanged();
	}
	
	@Override
	public void completeTask(Task toComplete) {
		assert tasks.contains(toComplete);
		toComplete.setAsComplete();
		indicateTaskManagerChanged();
		
	}
	
	@Override
	public void uncompleteTask(Task toUncomplete) {
		assert tasks.contains(toUncomplete);
		toUncomplete.setAsUncomplete();
		indicateTaskManagerChanged();
		
	}
	
    /** Keeps the internal ObservableList sorted.
     * Raises an event to indicate the model has changed.
     */
    private void indicateTaskManagerChanged() {
    	FXCollections.sort(tasks.getInternalList());
        raise(new TaskManagerChangedEvent(tasks));
    }

	@Override
	public void filterTasks(Set<String> keywords) {
	    filterTasks(new PredicateExpression(new NameQualifier(keywords)));
	}
	
	
	public void filterTasks(Expression expression) {
	    filteredTasks.setPredicate(expression::satisfies);
	}
	
	@Override
	public void filterUncompletedTasks() {
		filteredTasks.setPredicate(p -> !p.isComplete());
	}
	
	@Override
	public void clearTasksFilter() {
	    filteredTasks.setPredicate(p -> !p.isComplete());
	}
	
	@Override
	public void refreshTasksFilter() {
		Predicate<? super Task> currentPredicate = filteredTasks.getPredicate();
		filteredTasks.setPredicate(null);
		filteredTasks.setPredicate(currentPredicate);
	}
	
	@Override
	public void filterCompletedTasks(){
		filteredTasks.setPredicate(p -> p.isComplete());
	}

	@Override
	public UnmodifiableObservableList<Task> getCurrentFilteredTasks() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}
	
	@Override
	public void addAlias(Alias toAdd) throws UniqueItemCollection.DuplicateItemException{
		alias.add(toAdd);
	    indicateAliasChanged();
	}
	
	@Override
	public synchronized void deleteAlias(Alias toRemove) throws ItemNotFoundException {
	    alias.remove(toRemove);
	    indicateAliasChanged();
	}
	
	/** Raises an event to indicate the model has changed */
    private void indicateAliasChanged() {
        raise(new AliasChangedEvent(alias));
    }
    
    @Override
	public UnmodifiableObservableList<Alias> getAlias() {
		return new UnmodifiableObservableList<>(alias.getInternalList());
	}
    
   
	interface Expression {
        boolean satisfies(Task task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Task task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(Task task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(Task task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().getContent(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
	
}
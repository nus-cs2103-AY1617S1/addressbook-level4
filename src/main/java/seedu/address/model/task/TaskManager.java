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
import seedu.address.commons.events.model.NewTaskListEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.Alias;
import seedu.address.model.ModelHistory;
import seedu.address.model.UserPrefs;

/**
 * Manages a list of tasks & aliases and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements InMemoryTaskList {
	private UniqueItemCollection<Task> tasks;
	private UniqueItemCollection<Alias> aliases;
	private FilteredList<Task> filteredTasks;
	private final ModelHistory modelHistory; // Stores tasks & aliases to support undo & redo commands

	public TaskManager() {
		this(new UniqueItemCollection<Task>(), new UniqueItemCollection<Alias>(), null);		
	}
	
	public TaskManager(UniqueItemCollection<Task> tasks, UniqueItemCollection<Alias> aliases, UserPrefs userPrefs) {
		this.tasks = tasks;
		this.aliases = aliases;
		this.modelHistory = new ModelHistory();
		filteredTasks = new FilteredList<>(this.tasks.getInternalList());
		filterUncompletedTasks();
	}
	
	@Override
	public synchronized void addTask(Task toAdd) throws DuplicateItemException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.add(toAdd);
	
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void updateTask(Task toUpdate, Task newTask) throws ItemNotFoundException {
		assert tasks.contains(toUpdate);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.replace(toUpdate, newTask);
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void deleteTask(Task toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();		
		
	    tasks.remove(toRemove);
	    
	    // Update stored values of tasks
	 	modelHistory.storeOldTasks(tempTasks);
	    indicateTaskManagerChanged();
	}
	
	@Override
	public void favoriteTask(Task toFavorite) {
		assert tasks.contains(toFavorite);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		toFavorite.setAsFavorite();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}

	@Override
	public void unfavoriteTask(Task toUnfavorite) {
		assert tasks.contains(toUnfavorite);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
						
		toUnfavorite.setAsNotFavorite();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public void completeTask(Task toComplete) {
		assert tasks.contains(toComplete);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();				
		
		toComplete.setAsComplete();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public void uncompleteTask(Task toUncomplete) {
		assert tasks.contains(toUncomplete);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();		
		
		toUncomplete.setAsUncomplete();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void addAlias(Alias toAdd) throws UniqueItemCollection.DuplicateItemException{
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Alias> tempAliases = aliases.copyCollection();
		 
		aliases.add(toAdd);
		
		// Update stored values of aliases
	    modelHistory.storeOldAliases(tempAliases);
	    indicateAliasChanged();
	}
	
	@Override
	public synchronized void deleteAlias(Alias toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Alias> tempAliases = aliases.copyCollection();
		
		aliases.remove(toRemove);
	    
	    // Update stored values of aliases
	    modelHistory.storeOldAliases(tempAliases);
	    indicateAliasChanged();
	}
	
	/**
	 * Undoes the previous command that has to do with tasks or aliases
	 * (Can only be called if the previous successful command was a successful task/alias command)
	 */
	//@@author A0139817U
	@Override
	public void undo() throws IllegalStateException {
		// Undoing task type command
		if (modelHistory.canUndoTasks()) {
			// Current values of tasks passed to undoManager to be stored to allow user to redo.
			// Tasks that will replace the current list is returned.
			UniqueItemCollection<Task> replacingTasks = modelHistory.undoTasks(tasks);
			
			// The current tasks have been reinstated to their older versions
			tasks = replacingTasks;
			
		// Undoing alias type command
		} else if (modelHistory.canUndoAliases()) {
			// Current values of aliases passed to undoManager to be stored to allow user to redo.
			// Aliases that will replace the current list is returned
			UniqueItemCollection<Alias> replacingAliases = modelHistory.undoAliases(aliases);
			
			// The current aliases have been reinstated to their older versions
			aliases = replacingAliases;
			
		} else { 
			throw new IllegalStateException("Unable to undo because there is no previous state to revert to"); 
		}
		// Clear old values of tasks & aliases
		modelHistory.clearOldCopies();
		
		// Save the old predicate before we reassign filteredTasks
		Predicate<? super Task> currentPredicate = filteredTasks.getPredicate();
		
		// Refresh the filtered tasks
		filteredTasks = new FilteredList<>(tasks.getInternalList());
		
		// Reapply the predicate
		filteredTasks.setPredicate(currentPredicate);
		
		// Raise the changes
		indicateNewTaskListEvent();
		indicateAliasChanged();
	}
	
	/**
	 * Redoes the command that has been undone
	 * (Can only be called if the previous successful command was a successful undo command)
	 */
	//@@author A0139817U
	@Override
	public void redo() throws IllegalStateException {
		// Redoing task type command
		if (modelHistory.canRedoTasks()) {
			// Current values of tasks passed to undoManager to be stored to allow user to undo.
			// Tasks that will replace the current list is returned.
			UniqueItemCollection<Task> replacingTasks = modelHistory.redoTasks(tasks);
			
			// The current tasks have been reinstated to the versions before 'undo' has been called
			tasks = replacingTasks;
			
		// Undoing alias type command
		} else if (modelHistory.canRedoAliases()) {
			// Current values of aliases passed to undoManager to be stored to allow user to undo.
			// Aliases that will replace the current list is returned
			UniqueItemCollection<Alias> replacingAliases = modelHistory.redoAliases(aliases);
			
			// The current aliases have been reinstated to the versions before 'undo' has been called
			aliases = replacingAliases;
			
		} else { 
			throw new IllegalStateException("Unable to redo because the previous successful command was not an undo command"); 
		}
		// Clear values of tasks & aliases that have been stored due to 'undo'
		modelHistory.clearUndoneCopies();
		
		// Save the old predicate before we reassign filteredTasks
		Predicate<? super Task> currentPredicate = filteredTasks.getPredicate();
		
		// Refresh the filtered tasks
		filteredTasks = new FilteredList<>(tasks.getInternalList());
		
		// Reapply the predicate
		filteredTasks.setPredicate(currentPredicate);
		
		// Raise the changes
		indicateNewTaskListEvent();
		indicateAliasChanged();
	}
	
    /** Keeps the internal ObservableList sorted.
     * Raises an event to indicate the model has changed.
     */
    private void indicateTaskManagerChanged() {
    	FXCollections.sort(tasks.getInternalList());
        raise(new TaskManagerChangedEvent(tasks));
    }
    
    private void indicateNewTaskListEvent() {
    	raise(new NewTaskListEvent(tasks, filteredTasks));
    }
    
    private void indicateAliasChanged() {
        raise(new AliasChangedEvent(aliases));
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
	public UnmodifiableObservableList<Alias> getAlias() {
		return new UnmodifiableObservableList<>(aliases.getInternalList());
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
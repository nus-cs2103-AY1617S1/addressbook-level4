package w15c2.tusk.model.task;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.collections.UniqueItemCollection.DuplicateItemException;
import w15c2.tusk.commons.collections.UniqueItemCollection.ItemNotFoundException;
import w15c2.tusk.commons.core.ComponentManager;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.commons.core.UnmodifiableObservableList;
import w15c2.tusk.commons.events.model.AliasChangedEvent;
import w15c2.tusk.commons.events.model.NewTaskListEvent;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.ui.FilterLabelChangeEvent;
import w15c2.tusk.commons.events.ui.FilterLabelChangeEvent.COMMANDTYPE;
import w15c2.tusk.commons.util.StringUtil;
import w15c2.tusk.logic.commands.taskcommands.AddAliasCommand;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.ClearTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.CompleteTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.DeleteAliasCommand;
import w15c2.tusk.logic.commands.taskcommands.FindTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.PinTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.RedoTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.SetStorageCommand;
import w15c2.tusk.logic.commands.taskcommands.UncompleteTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UndoTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UnpinTaskCommand;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.ModelHistory;
import w15c2.tusk.model.UserPrefs;

/**
 * Manages a list of tasks & aliases and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter.getLogger(TaskManager.class);
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
		indicateTaskManagerChanged();
	}
	
	@Override
	public UniqueItemCollection<Task> getTasks(){
		return tasks;
	}
	
	@Override
	public synchronized void addTask(Task toAdd) throws DuplicateItemException {
		// Create a temporary storage of tasks and update the global copy only when add task is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.add(toAdd);
	
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void updateTask(Task toUpdate, Task newTask) throws ItemNotFoundException {
		assert tasks.contains(toUpdate);
		
		// Create a temporary storage of tasks and update the global copy only when update task is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.replace(toUpdate, newTask);
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void deleteTask(Task toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when delete task is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();		
		
	    tasks.remove(toRemove);
	    
	    // Update stored values of tasks
	 	modelHistory.storeOldTasks(tempTasks);
	    indicateTaskManagerChanged();
	}
	
	@Override
	public void pinTask(Task toPin) {
		assert tasks.contains(toPin);
		
		// Create a temporary storage of tasks and update the global copy only when favorite is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		toPin.setAsPin();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	// Returns the number of tasks successfully deleted
	@Override
	public int clearTasks() {
		// Create a temporary storage of tasks and update the global copy only when clear tasks is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		int deleted = 0;
		for (int i = filteredTasks.size() - 1; i >= 0; i--) {
			try {
				tasks.remove(filteredTasks.get(i));
				deleted++;
			} catch (ItemNotFoundException infe) {
				logger.warning("Failed to remove task while clearing tasks: " + filteredTasks.get(i).getDescription().getContent());
			}
		}
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
	    indicateTaskManagerChanged();
	    clearTasksFilter();
	    return deleted;
	}

	@Override
	public void unpinTask(Task toUnpin) {
		assert tasks.contains(toUnpin);
		
		// Create a temporary storage of tasks and update the global copy only when unfavorite is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
						
		toUnpin.setAsNotPin();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public void completeTask(Task toComplete) {
		assert tasks.contains(toComplete);
		
		// Create a temporary storage of tasks and update the global copy only when complete task is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();				
		
		toComplete.setAsComplete();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public void uncompleteTask(Task toUncomplete) {
		assert tasks.contains(toUncomplete);
		
		// Create a temporary storage of tasks and update the global copy only when uncomplete task is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();		
		
		toUncomplete.setAsUncomplete();
		
		// Update stored values of tasks
		modelHistory.storeOldTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void addAlias(Alias toAdd) throws UniqueItemCollection.DuplicateItemException{
		// Create a temporary storage of tasks and update the global copy only when add alias is successful
		UniqueItemCollection<Alias> tempAliases = aliases.copyCollection();
		 
		aliases.add(toAdd);
		
		// Update stored values of aliases
	    modelHistory.storeOldAliases(tempAliases);
	    indicateAliasChanged();
	}
	
	@Override
	public synchronized void deleteAlias(Alias toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when delete alias is successful
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
	//@@author A0139708W
    @Override
    public ObservableList<String> getHelpList() {
        ObservableList<String> helpItems = FXCollections.observableArrayList ();
        helpItems.add(AddTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(AddAliasCommand.HELP_MESSAGE_USAGE);
        helpItems.add(DeleteAliasCommand.HELP_MESSAGE_USAGE);
        helpItems.add(ClearTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(FindTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(ListTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(CompleteTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(UncompleteTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(UndoTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(RedoTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(PinTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(UnpinTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(SetStorageCommand.HELP_MESSAGE_USAGE);
        return helpItems;
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
	    EventsCenter.getInstance().post(new FilterLabelChangeEvent(COMMANDTYPE.Find));
	    filterTasks(new PredicateExpression(new NameQualifier(keywords)));
	}
	
	
	public void filterTasks(Expression expression) {
	    filteredTasks.setPredicate(expression::satisfies);
	}
	
	@Override
	public void filterUncompletedTasks() {
	    EventsCenter.getInstance().post(new FilterLabelChangeEvent(COMMANDTYPE.List));
		filteredTasks.setPredicate(p -> !p.isCompleted());
	}
	
	@Override
	public void clearTasksFilter() {
	    EventsCenter.getInstance().post(new FilterLabelChangeEvent(COMMANDTYPE.List));
	    filteredTasks.setPredicate(p -> !p.isCompleted());
	}
	
	//@@author A0138978E
	@Override
	public void refreshTasksFilter() {
		Predicate<? super Task> currentPredicate = filteredTasks.getPredicate();
		filteredTasks.setPredicate(null);
		filteredTasks.setPredicate(currentPredicate);
	}
	
	@Override
	public void filterCompletedTasks(){
	    EventsCenter.getInstance().post(new FilterLabelChangeEvent(COMMANDTYPE.ListComplete));
		filteredTasks.setPredicate(p -> p.isCompleted());
	}

	//@@author A0139708W
	@Override
	public UnmodifiableObservableList<Task> getCurrentFilteredTasks() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}
    
    @Override
	public UnmodifiableObservableList<Alias> getAlias() {
		return new UnmodifiableObservableList<>(aliases.getInternalList());
	}
    
   
  //@@author A0139708W
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
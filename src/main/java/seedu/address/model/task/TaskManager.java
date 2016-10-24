package seedu.address.model.task;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AliasChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.DeleteAliasCommand;
import seedu.address.logic.commands.taskcommands.FavoriteTaskCommand;
import seedu.address.logic.commands.taskcommands.FindTaskCommand;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.logic.commands.taskcommands.UnfavoriteTaskCommand;
import seedu.address.model.Alias;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/*
 * Manages a list of tasks 1and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements InMemoryTaskList {
	private UniqueItemCollection<Task> tasks;
	private UniqueItemCollection<Alias> alias;
	private final FilteredList<Task> filteredTasks;


	public TaskManager() {
		// TODO: make use of loaded data
		this.tasks = new UniqueItemCollection<Task>();
		this.alias = new UniqueItemCollection<Alias>();
		filteredTasks = new FilteredList<>(tasks.getInternalList());
	}
	
	public TaskManager(UniqueItemCollection<Task> tasks, UniqueItemCollection<Alias> alias, UserPrefs userPrefs) {
		this.tasks = tasks;
		this.alias = alias;
		filteredTasks = new FilteredList<>(this.tasks.getInternalList());
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
    public ObservableList<String> getHelpList() {
        ObservableList<String> helpItems = FXCollections.observableArrayList ();
        helpItems.add(AddTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(AddAliasCommand.HELP_MESSAGE_USAGE);
        helpItems.add(DeleteAliasCommand.HELP_MESSAGE_USAGE);
        helpItems.add(FavoriteTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(FindTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(ListTaskCommand.HELP_MESSAGE_USAGE);
        helpItems.add(UnfavoriteTaskCommand.HELP_MESSAGE_USAGE);
        return helpItems;
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
	public void clearTasksFilter() {
	    filteredTasks.setPredicate(null);
		
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
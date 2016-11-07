package seedu.lifekeeper.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.lifekeeper.commons.core.ComponentManager;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.commons.core.UnmodifiableObservableList;
import seedu.lifekeeper.commons.events.model.AddressBookChangedEvent;
import seedu.lifekeeper.commons.events.model.SaveEvent;
import seedu.lifekeeper.commons.util.StringUtil;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.lifekeeper.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;

import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final LifeKeeper lifeKeeper;
    private final FilteredList<Activity> filteredActivities;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(LifeKeeper src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        lifeKeeper = new LifeKeeper(src);
        filteredActivities = new FilteredList<>(lifeKeeper.getAllEntries());
        filteredTags = new FilteredList<>(lifeKeeper.getTag());
        updateFilteredListToShowAll();
    }

    public ModelManager() {
        this(new LifeKeeper(), new UserPrefs());
    }

    public ModelManager(ReadOnlyLifeKeeper initialData, UserPrefs userPrefs) {
        lifeKeeper = new LifeKeeper(initialData);
        filteredActivities = new FilteredList<>(lifeKeeper.getAllEntries());
        filteredTags = new FilteredList<>(lifeKeeper.getTag());
        updateFilteredListToShowAll();
    }

    @Override
    public void resetData(ReadOnlyLifeKeeper newData) {
        lifeKeeper.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyLifeKeeper getLifekeeper() {
        return lifeKeeper;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(lifeKeeper));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyActivity target) throws TaskNotFoundException {
        lifeKeeper.removeActivity(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Activity activity) throws UniqueActivityList.DuplicateTaskException {
        lifeKeeper.addActivity(activity);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }       

	@Override
	public void undoDelete(int index, Activity taskToAdd) throws UniqueActivityList.DuplicateTaskException {
		lifeKeeper.addActivity(index, taskToAdd);
		updateFilteredListToShowAll();
		indicateAddressBookChanged();

	}

    //@@author A0125680H
    @Override
    public synchronized Activity editTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = lifeKeeper.editTask(oldTask, newParams, "edit");
        indicateAddressBookChanged();
        
        return editedTask;
    }
    
    @Override
    public synchronized Activity undoEditTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = lifeKeeper.editTask(oldTask, newParams, "undo");
        indicateAddressBookChanged();
        
        return editedTask;
    }

	@Override
	public synchronized void markTask(Activity taskToMark, boolean isComplete) throws TaskNotFoundException {
		lifeKeeper.markTask(taskToMark, isComplete);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
		
	}
	
	@Subscribe
	public void indicateSaveLocChanged(SaveEvent event) {
	    indicateAddressBookChanged();
	}
    
    //=========== Filtered Activity List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredActivities);
    }
    
    @Override
    public UnmodifiableObservableList<Activity> getFilteredTaskListForEditing() {
        return new UnmodifiableObservableList<>(filteredActivities);
    }
    
  //@@author A0125284H A0131813R
  	@Override
  	public UnmodifiableObservableList<ReadOnlyActivity> getFilteredOverdueTaskList() {
  		
  		FilteredList<Activity> filteredOverdueTaskList = new FilteredList<>(lifeKeeper.getAllEntries());

  		filteredOverdueTaskList.setPredicate(p->
  		p.getClass().getSimpleName().equals("Task") && p.getCompletionStatus() == false && p.hasPassedDueDate() == true);
  		
  		return new UnmodifiableObservableList<ReadOnlyActivity>(filteredOverdueTaskList);
  	}
  	
  //@@author A0131813R
    @Override
    public void updateFilteredListToShowAll() {
        filteredActivities.setPredicate(p->
        p.getCompletionStatus() == false && p.getisOver() == false);
  		FilteredList<Activity> filteredList = new FilteredList<>(lifeKeeper.getAllEntries());
  		
  		filteredList.setPredicate(p->
  		p.getClass().getSimpleName().equalsIgnoreCase("Task")
  		 && (p.getCompletionStatus() == false && p.hasPassedDueDate() == true));
  		
  		
  		return;
  	}
  	
    
    @Override
    public void updateFilteredByTagListToShowAll(String tag) {
        filteredActivities.setPredicate(p->
        p.getTags().contains1(tag));
    }
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredActivities.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Task"));
    }
    
    @Override
    public void updateFilteredDoneListToShowAll() {
        filteredActivities.setPredicate(p->
        p.getCompletionStatus() == true || p.getisOver() == true);
    }
    
    @Override
    public void updateFilteredActivityListToShowAll() {
        filteredActivities.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Activity"));
    }
    
    @Override
    public void updateAllListToShowAll() {
        filteredActivities.setPredicate(null);
    }
    
    @Override
    public void updateFilteredEventListToShowAll() {
        filteredActivities.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Event"));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredActivityList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredActivityList(Expression expression) {
        filteredActivities.setPredicate(expression::satisfies);
    }
    /**
     * Method to remove any Tasks and Events that are irrelevant for Upcoming Dashboard.
     * @param filteredList
     * @return filteredList
     */
    //@@ author A0125284H
    private FilteredList<Activity> createUpcomingList(FilteredList<Activity> filteredList) {
    	return filteredList;
    	/*
    	FilteredList<Activity> listOfEvents = new FilteredList<Activity> (filteredList);
    	
    	listOfEvents.setPredicate(p->
		p.getClass().getSimpleName().equalsIgnoreCase("Event"));
    	
    	(FilteredList<Events>) listOfEvents.setPredicate(p.);
    	
  		FilteredList<Task> filteredOverdueTaskList = (FilteredList<Task>) new FilteredList<>( ervableList<? extends ReadOnlyTask>) filteredList);

    	
		for (int i=0; i<filteredList.size(); i++) {
			
			switch (filteredList.get(i).getClass().getSimpleName()) {
			case "Event": {
				Event listItem = (Event) filteredList.get(i);
				if (!listItem.isUpcoming()) {
					filteredList.remove(i);
					i--;
				}
				break;
			}
			case "Task": {
				Task listItem = (Task) filteredList.get(i);
				if (listItem.isDueDateApproaching() && !(listItem.hasPassedDueDate())) {
					filteredList.remove(i);
					i--;
				}
				break;
			}

			default: break;
			}
		}
		return filteredList;
		*/
    }
        
  //@@author
    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyActivity activity);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity activity) {
            return qualifier.run(activity);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity activity);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyActivity activity) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(activity.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }






}

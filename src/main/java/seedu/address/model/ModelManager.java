package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Activity> filteredPersons;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredPersons = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyLifeKeeper initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredPersons = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
    }

    @Override
    public void resetData(ReadOnlyLifeKeeper newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyLifeKeeper getLifekeeper() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyActivity target) throws TaskNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Activity person) throws UniqueActivityList.DuplicateTaskException {
        addressBook.addPerson(person);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }       

	@Override
	public void undoDelete(int index, Activity taskToAdd) throws UniqueActivityList.DuplicateTaskException {
		addressBook.addPerson(index, taskToAdd);
		updateFilteredListToShowAll();
		indicateAddressBookChanged();

	}

    //@@author A0125680H
    @Override
    public synchronized Activity editTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = addressBook.editTask(oldTask, newParams, "edit");
        indicateAddressBookChanged();
        
        return editedTask;
    }
    
    @Override
    public synchronized Activity undoEditTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = addressBook.editTask(oldTask, newParams, "undo");
        indicateAddressBookChanged();
        
        return editedTask;
    }

	@Override
	public synchronized void markTask(Activity taskToMark, boolean isComplete) throws TaskNotFoundException {
		addressBook.markTask(taskToMark, isComplete);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
		
	}
    
    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }
    
    @Override
    public UnmodifiableObservableList<Activity> getFilteredTaskListForEditing() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }
    
  //@@author A0125284H A0131813R
  	@Override
  	public UnmodifiableObservableList<ReadOnlyActivity> getFilteredOverdueTaskList() {
  		
  		FilteredList<Activity> filteredList = new FilteredList<>(addressBook.getAllEntries());
  		
  		filteredList.setPredicate(p->
  		p.getClass().getSimpleName().equalsIgnoreCase("Task")
  		 && (p.getCompletionStatus() == false && p.hasPassedDueDate() == true));
  		
  		
  		return new UnmodifiableObservableList<ReadOnlyActivity>(filteredList);
  	}
  	
  	//@@Author A0125284H
	@Override
	public UnmodifiableObservableList<ReadOnlyActivity> getFilteredUpcomingList() {
  		
		/*FilteredList<Activity> filteredList = new FilteredList<>(addressBook.getAllEntries());
		
		//obtain a filtered list of all tasks and events.
		filteredList.setPredicate(p->
		p.getClass().getSimpleName().equalsIgnoreCase("Event") || p.getClass().getSimpleName().equalsIgnoreCase("Task"));
		
		//filter out tasks/events that are over, or not yet upcoming.
		return new UnmodifiableObservableList(createUpcomingList(filteredList));
		
		*/
		//obtain a filtered list of upcoming events.
		FilteredList<Activity> filteredList = new FilteredList<>(addressBook.getAllEntries());
  
		filteredList.setPredicate(p->
		(p.getClass().getSimpleName().equalsIgnoreCase("Event") && ((Event) p).isUpcoming())
		|| ( p.getClass().getSimpleName().equalsIgnoreCase("Task") && ((Task) p).isDueDateApproaching()));
		
		return new UnmodifiableObservableList<ReadOnlyActivity>(filteredList);
	}

  	
  //@@author A0131813R
    @Override
    public void updateFilteredListToShowAll() {
    	filteredPersons.setPredicate(p->
    	p.getCompletionStatus() == false && p.getisOver() == false);
    	
    }
    
    @Override
    public void updateFilteredByTagListToShowAll(String tag) {
        filteredPersons.setPredicate(p->
        p.getTags().contains1(tag));
    }
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Task"));
    }
    
    @Override
    public void updateFilteredDoneListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getCompletionStatus() == true || p.getisOver() == true);
    }
    
    @Override
    public void updateFilteredActivityListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Activity"));
    }
    
    @Override
    public void updateFilteredEventListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Event"));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
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
        boolean satisfies(ReadOnlyActivity person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyActivity person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }






}

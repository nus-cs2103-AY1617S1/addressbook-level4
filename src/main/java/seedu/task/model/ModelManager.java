package seedu.task.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueEventList.DuplicateEventException;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.taskcommons.core.ComponentManager;
import seedu.taskcommons.core.LogsCenter;
import seedu.taskcommons.core.Status;
import seedu.taskcommons.core.UnmodifiableObservableList;


/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Status INCOMPLETE_STATUS = Status.INCOMPLETED;
    private static final Status COMPLETE_STATUS = Status.COMPLETED;
    
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        
        updateFilteredEventListToShowWithStatus(INCOMPLETE_STATUS);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    //@@author A0121608N
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        taskBook.removeEvent(target);
        updateFilteredEventListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }    
    
    @Override
    public synchronized void clearTasks() {
        
        updateFilteredTaskListToShowWithStatus(COMPLETE_STATUS);
        while(!filteredTasks.isEmpty()){
            ReadOnlyTask task = filteredTasks.get(0);
            try {
                taskBook.removeTask(task);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        updateFilteredTaskListToShowAll();
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void clearEvents() {
        updateFilteredEventListToShowWithStatus(COMPLETE_STATUS);
        while(!filteredEvents.isEmpty()){
            ReadOnlyEvent event = filteredEvents.get(0);
            try {
                taskBook.removeEvent(event);
            } catch (EventNotFoundException tnfe) {
                assert false : "The target event cannot be missing";
            }
        }
        updateFilteredEventListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void markTask(ReadOnlyTask target){
        taskBook.markTask(target);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }
    
    //@@author A0127570H

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        taskBook.addEvent(event);
        updateFilteredEventListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();
    }
   
    @Override
    public synchronized void editTask(Task editTask, ReadOnlyTask targetTask) throws UniqueTaskList.DuplicateTaskException {
        taskBook.editTask(editTask, targetTask);
        updateFilteredTaskListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged();   
    }
    
    @Override
    public void editEvent(Event editEvent, ReadOnlyEvent targetEvent) throws UniqueEventList.DuplicateEventException {
        taskBook.editEvent(editEvent, targetEvent);
        updateFilteredEventListToShowWithStatus(INCOMPLETE_STATUS);
        indicateTaskBookChanged(); 
    }
    //@@author 
        
   
    //=========== Filtered Task List Accessors ===============================================================

    //@@author A0144702N
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
    	SortedList<Task> sortedTasks = new SortedList<>(filteredTasks);
    	sortedTasks.setComparator(Task.getAscComparator());
    	return new UnmodifiableObservableList<>(sortedTasks);
    }
   
    
    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        SortedList<Event> sortedEvents = new SortedList<>(filteredEvents);
    	sortedEvents.setComparator(Event.getAscComparator());
    	return new UnmodifiableObservableList<>(sortedEvents);
    }

    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void showFoundTaskList(Set<String> keywords, boolean isPowerSearch){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, isPowerSearch)));
    }
    
    @Override
    public void showFoundEventList(Set<String> keywords, boolean isPowerSearch){
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords, isPowerSearch)));
    }
    
    @Override
	public void updateFilteredTaskListToShowWithStatus(Status status) {
    	if(status == Status.BOTH) {
    		updateFilteredTaskListToShowAll();
    	} else {
    		updateFilteredTaskList(new PredicateExpression(new StatusQualifier(status)));
    	}
	}
    
    @Override
	public void updateFilteredEventListToShowWithStatus(Status status) {
    	if(status == Status.BOTH) {
    		updateFilteredEventListToShowAll();
    	} else {
    		updateFilteredEventList(new PredicateExpression(new StatusQualifier(status)));
    	}
	}
    
    @Override
	public void updateFilteredEventListToShowAll() {
    	filteredEvents.setPredicate(null);
	}
    
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(ReadOnlyEvent event);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
        	qualifier.prepare(task);
            return qualifier.run();
        }

        @Override
		public boolean satisfies(ReadOnlyEvent event) {
        	qualifier.prepare(event);
        	return qualifier.run();
		}
        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
		boolean run();
		void prepare(ReadOnlyTask task);
		void prepare(ReadOnlyEvent event);
        String toString();
    }

    private class NameQualifier implements Qualifier {
    	private boolean isPowerSearch;
    	private Set<String> keyWords;
    	
        private String targetName;
        private String targetDesc; 
        
        
        NameQualifier(Set<String> keyWords, boolean isPowerSearch) {
            this.keyWords = keyWords;
            this.isPowerSearch = isPowerSearch;
        }

        @Override
        public String toString() {
            return String.join(", ", keyWords);
        }

		@Override
		/**
		 * Filter out those having names and description not matched with the keywords.
		 */
		public boolean run() {
    		List<String> sourceSet = new ArrayList<>();
    		
        	if(isPowerSearch) {
        		//break the name and desc to allow power search
        		sourceSet = new ArrayList<>(Arrays.asList(targetName.split("\\s")));
        		sourceSet.addAll(Arrays.asList(targetDesc.split("\\s")));
        		
        		//break the keyword to allow power search
        		List<String> tempSet = new ArrayList<>(keyWords);
        		keyWords = new HashSet<>();
        		tempSet.stream().forEach(keyword -> keyWords.addAll(Arrays.asList(keyword.split("\\s"))));
        		
        	} else {
        		sourceSet.add(targetName);
        		sourceSet.add(targetDesc);
        	}
        	
        	for(String source: sourceSet) {
    			boolean found = keyWords.stream()
                .filter(keyword -> StringUtil.isSimilar(source.trim(), keyword.trim()))
                .findAny()
                .isPresent();
    			
    			if (found) {
    				return true;
    			}
    		}
    		return false;
		}

		@Override
		public void prepare(ReadOnlyTask task) {
			targetName = task.getTask().fullName;
    		targetDesc = task.getDescriptionValue();
		}

		@Override
		public void prepare(ReadOnlyEvent event) {
			targetName = event.getEvent().fullName;
    		targetDesc = event.getDescriptionValue();
		}
    }
    
    private class StatusQualifier implements Qualifier {
    	private Boolean status;
    	private Boolean targetStatus;
    	
    	StatusQualifier(Status status){
    		switch(status) {
    		case COMPLETED:
    			this.status = true;
    			break;
    		case INCOMPLETED:
    			this.status = false;
    			break;
    		default:
    			this.status = false;
    		}
    	}
    	
		@Override
		public boolean run() {
			return targetStatus.equals(status);
		}
		
		@Override 
		public String toString() {
			return (status ? "completed" : "not yet completed");  
		}

		@Override
		public void prepare(ReadOnlyTask task) {
			targetStatus = task.getTaskStatus();
		}

		@Override
		public void prepare(ReadOnlyEvent event) {
			targetStatus = event.isEventCompleted();
		}
    	
    }

}

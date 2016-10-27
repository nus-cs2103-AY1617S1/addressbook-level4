package seedu.malitio.model;

import javafx.collections.transformation.FilteredList;
import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.util.StringUtil;

import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineCompletedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventMarkedException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskCompletedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;
import seedu.malitio.model.history.InputAddHistory;
import seedu.malitio.model.history.InputClearHistory;
import seedu.malitio.model.history.InputDeleteHistory;
import seedu.malitio.model.history.InputEditHistory;
import seedu.malitio.model.history.InputHistory;
import seedu.malitio.model.history.InputMarkHistory;

import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the malitio data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Malitio malitio;
    private final FilteredList<FloatingTask> filteredFloatingTasks;
    private final FilteredList<Deadline> filteredDeadlines;
    private final FilteredList<Event> filteredEvents;
    private Stack<InputHistory> history;
    private Stack<InputHistory> future;

    /**
     * Initializes a ModelManager with the given Malitio
     * Malitio and its variables should not be null
     */
    public ModelManager(Malitio src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with malitio: " + src + " and user prefs " + userPrefs);

        malitio = new Malitio(src);
        filteredFloatingTasks = new FilteredList<>(malitio.getFloatingTasks());
        filteredDeadlines = new FilteredList<>(malitio.getDeadlines());
        filteredEvents = new FilteredList<>(malitio.getEvents());
        history = new Stack<InputHistory>();
        future = new Stack<InputHistory>();
    }

    public ModelManager() {
        this(new Malitio(), new UserPrefs());
    }

    public ModelManager(ReadOnlyMalitio initialData, UserPrefs userPrefs) {
        malitio = new Malitio(initialData);
        filteredFloatingTasks = new FilteredList<>(malitio.getFloatingTasks());
        filteredDeadlines = new FilteredList<>(malitio.getDeadlines());
        filteredEvents = new FilteredList<>(malitio.getEvents());
        history = new Stack<InputHistory>();
        future = new Stack<InputHistory>();
    }

    @Override
    public void resetData(ReadOnlyMalitio newData) {
        history.add(new InputClearHistory(malitio.getUniqueFloatingTaskList(), 
                malitio.getUniqueDeadlineList(), 
                malitio.getUniqueEventList(), 
                malitio.getUniqueTagList()));
        malitio.resetData(newData);
        indicateMalitioChanged();
    }
    

    @Override
    public ReadOnlyMalitio getMalitio() {
        return malitio;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateMalitioChanged() {
        raise(new MalitioChangedEvent(malitio));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyFloatingTask target) throws FloatingTaskNotFoundException {
        history.add(new InputDeleteHistory(target, malitio.getUniqueFloatingTaskList().getInternalList()));
        malitio.removeTask(target);
        indicateMalitioChanged();        
    }

    @Override
    public void deleteTask(ReadOnlyDeadline target) throws DeadlineNotFoundException {
        malitio.removeDeadline(target);
        history.add(new InputDeleteHistory(target));
        indicateMalitioChanged();        
    }

    @Override
    public void deleteTask(ReadOnlyEvent target) throws EventNotFoundException {
        malitio.removeEvent(target);
        history.add(new InputDeleteHistory(target));
        indicateMalitioChanged();        
    }


    //@@author A0129595N
    @Override
    public void addFloatingTask(FloatingTask task) throws DuplicateFloatingTaskException {
        malitio.addFloatingTask(task);
        history.add(new InputAddHistory(task));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }
    
    @Override
    public void addFloatingTaskAtSpecificPlace(FloatingTask task, int index) throws DuplicateFloatingTaskException {
        malitio.addFloatingTask(task, index);
        history.add(new InputAddHistory(task));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void addDeadline(Deadline deadline) throws DuplicateDeadlineException {
        malitio.addDeadline(deadline);
        history.add(new InputAddHistory(deadline));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }
    
    @Override
    public void addEvent(Event event) throws DuplicateEventException {
        malitio.addEvent(event);
        history.add(new InputAddHistory(event));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }
    
    @Override
    public void editFloatingTask(FloatingTask edited, ReadOnlyFloatingTask beforeEdit) throws DuplicateFloatingTaskException, FloatingTaskNotFoundException {
        malitio.editFloatingTask(edited, beforeEdit);
        history.add(new InputEditHistory(edited, beforeEdit));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }
    
    @Override
    public void editDeadline(Deadline edited, ReadOnlyDeadline beforeEdit) throws DuplicateDeadlineException, DeadlineNotFoundException {
        malitio.editDeadline(edited, beforeEdit);
        history.add(new InputEditHistory(edited, beforeEdit));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }
    
    @Override
    public void editEvent(Event edited, ReadOnlyEvent beforeEdit) throws DuplicateEventException, EventNotFoundException {
        malitio.editEvent(edited, beforeEdit);
        history.add(new InputEditHistory(edited, beforeEdit));
        updateFilteredEventListToShowAll();
        indicateMalitioChanged();
    }
    //@@author A0122460W
	@Override
	public void completeFloatingTask(ReadOnlyFloatingTask taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException {
		malitio.completeTask(taskToComplete);
		updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
	}
	
	@Override
	public void completeDeadline(ReadOnlyDeadline deadlineToEdit) throws DeadlineCompletedException, DeadlineNotFoundException {
		malitio.completeDeadline(deadlineToEdit);
		updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
	}

	//@@author A0153006W
	@Override
	public void markFloatingTask(ReadOnlyFloatingTask taskToMark, boolean marked)
	        throws FloatingTaskNotFoundException, FloatingTaskMarkedException, FloatingTaskUnmarkedException {
	    malitio.markTask(taskToMark, marked);
	    history.add(new InputMarkHistory(taskToMark, marked));
	    updateFilteredTaskListToShowAll();
	    indicateMalitioChanged();
	}
	
	@Override
    public void markDeadline(ReadOnlyDeadline deadlineToMark, boolean marked)
            throws DeadlineNotFoundException, DeadlineMarkedException, DeadlineUnmarkedException {
        malitio.markDeadline(deadlineToMark, marked);
        history.add(new InputMarkHistory(deadlineToMark, marked));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }
	
	@Override
	public void markEvent(ReadOnlyEvent eventToMark, boolean marked)
	        throws EventNotFoundException, EventMarkedException, EventUnmarkedException {
	    malitio.markEvent(eventToMark, marked);
	    history.add(new InputMarkHistory(eventToMark, marked));
	    updateFilteredEventListToShowAll();
	    indicateMalitioChanged();
	}
    //@@author
    
    @Override
    public Stack<InputHistory> getHistory() {
        return history;
    }
    
    @Override
    public Stack<InputHistory> getFuture() {
        return future;
    }
    
    //@@author a0126633j
    @Override
    public void dataFilePathChanged() {
        logger.info("Data storage file path changed, updating..");
        indicateMalitioChanged();
    }
    
    //@@author
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList() {
        return new UnmodifiableObservableList<>(filteredFloatingTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyDeadline> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredFloatingTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredDeadlineListToShowAll() {
        filteredDeadlines.setPredicate(null);
    }
    
    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredFloatingTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredDeadlineList(Set<String> keywords){
    	updateFilteredDeadlines(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredDeadlineList(DateTime keyword) {
        updateFilteredDeadlines(new PredicateExpression(new TimeQualifier(keyword)));
    }

    private void updateFilteredDeadlines(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredEventList(Set<String> keywords){
        updateFilteredEvents(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredEventList(DateTime keyword) {
        updateFilteredEvents(new PredicateExpression(new TimeQualifier(keyword)));
    }

    private void updateFilteredEvents(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyFloatingTask task);
        boolean satisfies(ReadOnlyDeadline deadline);
        boolean satisfies(ReadOnlyEvent event);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyFloatingTask task) {
            return qualifier.run(task);
        }
        
        @Override
        public boolean satisfies(ReadOnlyDeadline deadline) {
            return qualifier.run(deadline);
        }
        
        @Override
        public boolean satisfies(ReadOnlyEvent event) {
            return qualifier.run(event);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyFloatingTask task);
        boolean run(ReadOnlyDeadline schedule);
        boolean run(ReadOnlyEvent event);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public boolean run(ReadOnlyDeadline deadline) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(deadline.getName().fullName
                            + " " + deadline.getDue().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent();
        }
        
        @Override
        public boolean run(ReadOnlyEvent event) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(event.getName().fullName
                            + " " + event.getStart().toString()
                            + " " + event.getEnd().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent();
        }


        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class TimeQualifier implements Qualifier {
        private DateTime timeKeyWord;

        TimeQualifier(DateTime timeKeyWord) {
            this.timeKeyWord = timeKeyWord;
        }

        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return false;
        }
        
        @Override
        public boolean run(ReadOnlyDeadline deadline) {
            if (timeKeyWord.compareTo(deadline.getDue()) <= 0) {
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public boolean run(ReadOnlyEvent event) {
            if (timeKeyWord.compareTo(event.getStart()) <= 0) {
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public String toString() {
            return timeKeyWord.toString();
        }
    }
}

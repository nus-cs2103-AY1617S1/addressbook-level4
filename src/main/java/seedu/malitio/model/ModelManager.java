package seedu.malitio.model;

import javafx.collections.transformation.FilteredList;
import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.util.StringUtil;

import seedu.malitio.model.task.*;
import seedu.malitio.model.task.UniqueDeadlineList.*;
import seedu.malitio.model.task.UniqueEventList.*;
import seedu.malitio.model.task.UniqueFloatingTaskList.*;
import seedu.malitio.model.history.*;

import java.util.Date;
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
    public void deleteTask(Object target) throws FloatingTaskNotFoundException, DeadlineNotFoundException, EventNotFoundException {
        addCorrectInputDeleteHistory(target);
        malitio.removeTask(target);
        indicateMalitioChanged();        
    }

    private void addCorrectInputDeleteHistory(Object target) {
        if (target instanceof ReadOnlyFloatingTask) {
            history.add(new InputDeleteHistory(target, malitio.getUniqueFloatingTaskList().getInternalList()));
        } else {
            history.add(new InputDeleteHistory(target));
        }
    }

    //@@author A0129595N

    @Override
    public void addTask(Object task)
            throws DuplicateFloatingTaskException, DuplicateDeadlineException, DuplicateEventException {
        malitio.addTask(task);
        history.add(new InputAddHistory(task));
        updateAllListToShowAll();
        indicateMalitioChanged();

    }

    @Override
    public void addFloatingTaskAtSpecificPlace(Object task, int index) throws DuplicateFloatingTaskException {
        malitio.addTask(task, index);
        history.add(new InputAddHistory(task));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void editTask(Object edited, Object beforeEdit) throws FloatingTaskNotFoundException, DuplicateFloatingTaskException, DuplicateDeadlineException,
    DeadlineNotFoundException, DuplicateEventException, EventNotFoundException {
        malitio.editTask(edited, beforeEdit);
        history.add(new InputEditHistory(edited, beforeEdit));
        updateAllListToShowAll();
        indicateMalitioChanged();
    }

    public void completeTask(Object taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException, DeadlineCompletedException, DeadlineNotFoundException {
        malitio.completeTask(taskToComplete);
        history.add(new InputCompleteHistory(taskToComplete));
        updateAllListToShowAll();
        indicateMalitioChanged();
    }

    public void uncompleteTask(Object taskToUncomplete) throws FloatingTaskUncompletedException, FloatingTaskNotFoundException, DeadlineUncompletedException, DeadlineNotFoundException {
        malitio.uncompleteTask(taskToUncomplete);
        history.add(new InputUncompleteHistory(taskToUncomplete));
        updateAllListToShowAll();
        indicateMalitioChanged();
    }

    //@@author A0122460W
    @Override
    public void completeFloatingTask(ReadOnlyFloatingTask taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException {
        malitio.completeTask(taskToComplete);
        history.add(new InputCompleteHistory(taskToComplete));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void completeDeadline(ReadOnlyDeadline deadlineToComplete) throws DeadlineCompletedException, DeadlineNotFoundException {
        malitio.completeDeadline(deadlineToComplete);
        history.add(new InputCompleteHistory(deadlineToComplete));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void uncompleteFloatingTask(ReadOnlyFloatingTask taskToUncomplete) throws FloatingTaskUncompletedException, FloatingTaskNotFoundException {
        malitio.uncompleteTask(taskToUncomplete);
        history.add(new InputCompleteHistory(taskToUncomplete));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void uncompleteDeadline(ReadOnlyDeadline deadlineToUncomplete) throws DeadlineUncompletedException, DeadlineNotFoundException {
        malitio.uncompleteDeadline(deadlineToUncomplete);
        history.add(new InputCompleteHistory(deadlineToUncomplete));
        updateFilteredDeadlineListToShowAll();
        indicateMalitioChanged();
    }

    @Override
    public void markTask(Object taskToMark) throws FloatingTaskNotFoundException, FloatingTaskMarkedException,
    DeadlineNotFoundException, DeadlineMarkedException, EventNotFoundException, EventMarkedException {
        malitio.markTask(taskToMark);
        history.add(new InputMarkHistory(taskToMark));
        updateAllListToShowAll();
    }

    @Override
    public void unmarkTask(Object taskToUnmark) throws FloatingTaskNotFoundException, FloatingTaskUnmarkedException,
    DeadlineNotFoundException, DeadlineUnmarkedException, EventNotFoundException, EventUnmarkedException {
        malitio.unmarkTask(taskToUnmark);
        history.add(new InputUnmarkHistory(taskToUnmark));
        updateAllListToShowAll();
    }

    private void updateAllListToShowAll() {
        updateFilteredTaskListToShowAll();
        updateFilteredDeadlineListToShowAll();
        updateFilteredEventListToShowAll();

    }

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
	public void ShowAllTask() {
		filteredFloatingTasks.setPredicate(null);
		filteredDeadlines.setPredicate(null);
        filteredEvents.setPredicate(null);
	}
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredFloatingTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredDeadlineListToShowAll() {
        filteredDeadlines.setPredicate(p->!p.getCompleted() || p.getDue().compareTo(new Date())>0);
    }

    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEvents.setPredicate(p ->p.getStart().compareTo(new Date())>0);
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
        
        //@@author a0126633j
        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName 
                            + " " + StringUtil.reformatTagString(task.tagsString()), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public boolean run(ReadOnlyDeadline deadline) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(deadline.getName().fullName
                            + " " + StringUtil.reformatTagString(deadline.tagsString()) 
                            + " " + deadline.getDue().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent();
        }

        @Override
        public boolean run(ReadOnlyEvent event) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(event.getName().fullName
                            + " " + StringUtil.reformatTagString(event.tagsString()) 
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

    //@@author
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

package seedu.malitio.model;

import javafx.collections.transformation.FilteredList;
import seedu.malitio.commons.events.ui.JumpToListRequestEvent;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Arrays;

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
        addInputDeleteHistory(target);
        malitio.removeTask(target);
        indicateMalitioChanged();        
    }

    private void addInputDeleteHistory(Object target) {
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
        indicateTaskListChanged(task);
    }

    @Override
    public void addFloatingTaskAtSpecificPlace(Object task, int index) throws DuplicateFloatingTaskException {
        malitio.addTask(task, index);
        history.add(new InputAddHistory(task));
        updateFilteredTaskListToShowAll();
        indicateMalitioChanged();
        indicateTaskListChanged(task);
    }

    @Override
    public void editTask(Object edited, Object beforeEdit) throws FloatingTaskNotFoundException, DuplicateFloatingTaskException, DuplicateDeadlineException,
    DeadlineNotFoundException, DuplicateEventException, EventNotFoundException {
        malitio.editTask(edited, beforeEdit);
        history.add(new InputEditHistory(edited, beforeEdit));
        updateAllListToShowAll();
        indicateMalitioChanged();
        indicateTaskListChanged(edited);
    }
    
    //@@author A0122460W
    @Override
    public void completeTask(Object taskToComplete) throws FloatingTaskCompletedException, FloatingTaskNotFoundException, DeadlineCompletedException, DeadlineNotFoundException {
        malitio.completeTask(taskToComplete);
        history.add(new InputCompleteHistory(taskToComplete));
        updateAllListToShowAll();
        indicateMalitioChanged();
        indicateTaskListChanged(taskToComplete);
    }
    
    @Override
    public void uncompleteTask(Object taskToUncomplete) throws FloatingTaskUncompletedException, FloatingTaskNotFoundException, DeadlineUncompletedException, DeadlineNotFoundException {
        malitio.uncompleteTask(taskToUncomplete);
        history.add(new InputUncompleteHistory(taskToUncomplete));
        updateAllListToShowAll();
        indicateMalitioChanged();
        indicateTaskListChanged(taskToUncomplete);
    }

    //@@author
    @Override
    public void markTask(Object taskToMark) throws FloatingTaskNotFoundException, FloatingTaskMarkedException,
    DeadlineNotFoundException, DeadlineMarkedException, EventNotFoundException, EventMarkedException {
        malitio.markTask(taskToMark);
        history.add(new InputMarkHistory(taskToMark));
        updateAllListToShowAll();
        indicateTaskListChanged(taskToMark);
    }

    @Override
    public void unmarkTask(Object taskToUnmark) throws FloatingTaskNotFoundException, FloatingTaskUnmarkedException,
    DeadlineNotFoundException, DeadlineUnmarkedException, EventNotFoundException, EventUnmarkedException {
        malitio.unmarkTask(taskToUnmark);
        history.add(new InputUnmarkHistory(taskToUnmark));
        updateAllListToShowAll();
        indicateTaskListChanged(taskToUnmark);
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
    
    @Override
    public void clearExpiredTasks() {
        
        history.add(new InputClearHistory(malitio.getUniqueFloatingTaskList(), 
                malitio.getUniqueDeadlineList(), 
                malitio.getUniqueEventList(), 
                malitio.getUniqueTagList()));
        
        clearExpiredFloatingTasks(malitio.getFloatingTaskList());
        clearExpiredDeadlines(malitio.getDeadlineList());
        clearExpiredEvents(malitio.getEventList());

        indicateMalitioChanged();        
    }   
    
    private void clearExpiredFloatingTasks(List<ReadOnlyFloatingTask> list) {
        List<ReadOnlyFloatingTask> toBeRemoved = new ArrayList<ReadOnlyFloatingTask>();
    
        for (ReadOnlyFloatingTask task : list) {
            if (task.getCompleted()) {
             toBeRemoved.add(task);
            }
        }
        for (ReadOnlyFloatingTask task : toBeRemoved) {
            try {
                malitio.removeTask(task);
            } catch (FloatingTaskNotFoundException | DeadlineNotFoundException | EventNotFoundException e) {
                assert(false); //impossible
            }
        } 
    }
    private void clearExpiredDeadlines(List<ReadOnlyDeadline> list) {
        List<ReadOnlyDeadline> toBeRemoved = new ArrayList<ReadOnlyDeadline>();
        
        for (ReadOnlyDeadline task : list) {
            if (task.getCompleted()) {
             toBeRemoved.add(task);
            }
        }
        for (ReadOnlyDeadline task : toBeRemoved) {
            try {
                malitio.removeTask(task);
            } catch (FloatingTaskNotFoundException | DeadlineNotFoundException | EventNotFoundException e) {
                assert(false); //impossible
            }
        }  
    }

    private void clearExpiredEvents(List<ReadOnlyEvent> list) {
        Date current = new Date();
        List<ReadOnlyEvent> toBeRemoved = new ArrayList<ReadOnlyEvent>();

        for (ReadOnlyEvent task : list) {
            if (current.after(task.getEnd().getDate())) {
                toBeRemoved.add(task);
            }
        }  
        
        for (ReadOnlyEvent task : toBeRemoved) {
            try {
                malitio.removeTask(task);
            } catch (FloatingTaskNotFoundException | DeadlineNotFoundException | EventNotFoundException e) {
                assert(false); //impossible
            }
        }    
    }
    //@@author
    
    //@@author A0129595N
    /**
     * This method will post an event to indicate that a task has been added/modified.
     * @param task the task which an action has been performed on it i.e. add/edit/mark/unmark/complete/uncomplete
     */
    private void indicateTaskListChanged(Object task) {
        String taskType;
        int positionOfTask;
        if (isFloatingTask(task)) {
            taskType = "floating task";
            positionOfTask = filteredFloatingTasks.indexOf(task);
        } else if (isDeadline(task)) {
            taskType = "deadline";
            positionOfTask = filteredDeadlines.indexOf(task);
        } else {
            taskType = "event";
            positionOfTask =filteredEvents.indexOf(task);
        }
        raise(new JumpToListRequestEvent(positionOfTask, taskType));
    }

    private boolean isFloatingTask(Object task) {
        return task instanceof FloatingTask;
    }
    
    private boolean isDeadline(Object task) {
        return task instanceof Deadline;
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
        filteredEvents.setPredicate(p ->p.getEnd().compareTo(new Date())>0);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new FindCommandQualifier(keywords, filteredFloatingTasks)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredFloatingTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredDeadlineList(Set<String> keywords) {
        updateFilteredDeadlines(new PredicateExpression(new FindCommandQualifier(keywords, filteredDeadlines)));
    }

    @Override
    public void updateFilteredDeadlineList(DateTime keyword) {
        updateFilteredDeadlines(new PredicateExpression(new TimeQualifier(keyword)));
    }

    private void updateFilteredDeadlines(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEvents(new PredicateExpression(new FindCommandQualifier(keywords, filteredEvents)));
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
        boolean satisfies(Object task);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Object task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(Object task);
        boolean run(ReadOnlyFloatingTask task);
        boolean run(ReadOnlyDeadline task);
        boolean run(ReadOnlyEvent task);
        String toString();
    }
        
    //@@author a0126633j
    /**
     * A qualifier that checks if a task's name/tags/date/time is qualified. Only tasks in the current shown UI list is qualified.
     * 
     */
    private class FindCommandQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private List<Object> currentShownList;
        
        FindCommandQualifier(Set<String> nameKeyWords, FilteredList<?> listInput) {
            this.nameKeyWords = nameKeyWords;
            currentShownList = Arrays.asList(listInput.toArray());
        }

        @Override
        public boolean run(Object task) {
          if(isFloatingTask(task)) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(((ReadOnlyFloatingTask) task).getName().fullName 
                            + " " + StringUtil.reformatTagString(((ReadOnlyFloatingTask) task).tagsString()), keyword))
                    .findAny()
                    .isPresent()
                    && 
                    currentShownList.contains(task);
          } else if (isDeadline(task)) {
          
               return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(((ReadOnlyDeadline) task).getName().fullName
                            + " " + StringUtil.reformatTagString(((ReadOnlyDeadline) task).tagsString()) 
                            + " " + ((ReadOnlyDeadline) task).getDue().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent() 
                    && 
                    currentShownList.contains(task);
          } else {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(((ReadOnlyEvent) task).getName().fullName
                            + " " + StringUtil.reformatTagString(((ReadOnlyEvent) task).tagsString()) 
                            + " " + ((ReadOnlyEvent) task).getStart().toString()
                            + " " + ((ReadOnlyEvent) task).getEnd().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent()
                    && 
                    currentShownList.contains(task);
            }
               
           }
        //@@author

        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return false;
        }

        @Override
        public boolean run(ReadOnlyDeadline task) {
            return false;
        }

        @Override
        public boolean run(ReadOnlyEvent task) {
            return false;
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

        @Override
        public boolean run(Object task) {
            if (isFloatingTask(task)) {
               return run((ReadOnlyFloatingTask) task);
            }
            else if (isDeadline(task)) {
                return run((ReadOnlyDeadline) task);
            } else {
                return run((ReadOnlyEvent) task);
            }
            
        }
    }

}

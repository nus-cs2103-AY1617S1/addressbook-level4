package seedu.address.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.ui.OverdueChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredEvents;
    private final Stack<SaveState> undoStack;
    private final Stack<SaveState> redoStack;
    private final ArrayList<String> commandHistory;
    private Config config;
    private FilteredList<Task> filteredDeadlines;
    private FilteredList<Task> filteredTodos;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs, Config config) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredEvents = new FilteredList<>(taskBook.getEvents());
        filteredDeadlines = new FilteredList<>(taskBook.getDeadlines());
        filteredTodos = new FilteredList<>(taskBook.getTodo());
        undoStack = new Stack<SaveState>();
        redoStack = new Stack<SaveState>();
        commandHistory = new ArrayList<String>();
        this.config = config;
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs(), new Config());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs, Config config) {
        taskBook = new TaskBook(initialData);
        filteredEvents = new FilteredList<>(taskBook.getEvents());
        filteredDeadlines = new FilteredList<>(taskBook.getDeadlines());
        filteredTodos = new FilteredList<>(taskBook.getTodo());
        undoStack = new Stack<SaveState>();
        redoStack = new Stack<SaveState>();
        commandHistory = new ArrayList<String>();
        this.config = config;
    }
    
    //@@author A0147890U
    @Override
    public ArrayList<String> getCommandHistory() {
        return commandHistory;
    }
    
    //@@author A0147890U
    @Override
    public void addToUndoStack() {
        TaskBook taskBookToBeAdded = new TaskBook(taskBook);
        //Config configToBeAdded = new Config(config);
        SaveState saveToBeAdded = new SaveState(taskBookToBeAdded, config);
        
        undoStack.push(saveToBeAdded);
    }
    
    //@@author A0147890U
    @Override
    public Config getConfig() {
        return config;
    }
    
    //@@author A0147890U
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }
    
    //@@author A0147890U
    @Override 
    public Stack<SaveState> getUndoStack() {
        return this.undoStack;
    }
    
    //@@author A0147890U
    @Override
    public Stack<SaveState> getRedoStack() {
        return this.redoStack;
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }
    
    //@@author A0147890U
    /** Raises an event to indicate task overdue status might have changed */
    private void indicateTaskOverdueChanged() {
        raise(new OverdueChangedEvent());
    }
    
    //@@author A0139430L JingRui
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateAddressBookChanged();
    }
    
    @Override 
    public synchronized void editTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException {
        taskBook.changeTask(target, args, category);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }
    
    //@@author A0135722L Zhiyuan
    public synchronized void markDone(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.completeTask(target);
        updateFilteredListToShowAllUncompleted();
        indicateAddressBookChanged();
    }
    
    //@@author A0138993L
    @Override
    public synchronized void overdueTask() {
    	final Runnable overdue = new Runnable() {
    		public void run() {
    			taskBook.overdueTask();
    			indicateTaskOverdueChanged();
    	        indicateAddressBookChanged();
    		};
    	};
    	scheduler.scheduleAtFixedRate(overdue, 0, 1, TimeUnit.SECONDS); 
    }
    
    //@@author A0139430L JingRui
    @Override
    public synchronized void changeTaskCategory() {
        try {
            taskBook.changeTaskCategory();
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        indicateAddressBookChanged();
    }
    
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        System.out.println("yes");
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        System.out.println("yes1");
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList() {
        System.out.println("yes2");
        return new UnmodifiableObservableList<>(filteredTodos);
    }
    
    @Override
    public void updateFilteredListToShowAll() {
        filteredEvents.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredTodos.setPredicate(null);
    }
    
    //@@author A0147890U
    @Override
    public void updateFilteredListToShowAllCompleted() {
        filteredEvents.setPredicate(task -> {
            if (task.getIsCompleted()) {
                return true;
            }
            return false;
        });
        filteredDeadlines.setPredicate(task -> {
            if (task.getIsCompleted()) {
                return true;
            }
            return false;
        });
        filteredTodos.setPredicate(task -> {
            if (task.getIsCompleted()) {
                return true;
            }
            return false;
        });
    }
    
    //@@author A0147890U
    @Override 
    public void updateFilteredListToShowAllUncompleted() {
        filteredEvents.setPredicate(task -> {
            if (!task.getIsCompleted()) {
                return true;
            }
            return false;
        });
        filteredDeadlines.setPredicate(task -> {
            if (!task.getIsCompleted()) {
                return true;
            }
            return false;
        });
        filteredTodos.setPredicate(task -> {
            if (!task.getIsCompleted()) {
                return true;
            }
            return false;
        });
        
    }

    @Override
    public void updateFilteredEventList(Set<String> keywords){
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredDeadlineList(Set<String> keywords){
        updateFilteredDeadlineList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTodoList(Set<String> keywords){
        updateFilteredTodoList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    private void updateFilteredDeadlineList(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredTodoList(Expression expression) {
        filteredTodos.setPredicate(expression::satisfies);
    }
    
    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> anyKeyWords;

        NameQualifier(Set<String> anyKeyWords) {
            this.anyKeyWords = anyKeyWords;
        }
        //@@author A0139430L JingRui
        @Override
        public boolean run(ReadOnlyTask Task) {
            
            return anyKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(Task.getName().taskDetails.toLowerCase(), keyword)
                    || StringUtil.containsIgnoreCase(Task.getDate().value, keyword)
                    || StringUtil.containsIgnoreCase(Task.getStart().value, keyword)
                    || StringUtil.containsIgnoreCase(Task.getEnd().value, keyword)
                    || StringUtil.containsIgnoreCase(Task.getTags().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", anyKeyWords);
        }
    }
    

}

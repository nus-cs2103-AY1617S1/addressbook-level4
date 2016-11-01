package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.SortedObservableArrayList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.storage.Storage;
import seedu.address.commons.events.model.TaskAddedEvent;
import seedu.address.commons.events.model.TaskEditedEvent;
import seedu.address.commons.events.model.ToDoChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.core.ComponentManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;
import java.lang.reflect.Field;
/**
 * Represents the in-memory model of the SmartyDo data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDo toDo;
    private final FilteredList<Task> filteredTasks;
    private final Storage storage;

    /**
     * Initializes a ModelManager with the given ToDo
     * ToDo and its variables should not be null
     */
    public ModelManager(ToDo src, UserPrefs userPrefs, Storage storage) {
        super();
        assert src != null;
        assert userPrefs != null;
        assert storage != null;

        logger.fine("Initializing with SmartyDo: " + src + " and user prefs " + userPrefs);

        toDo = new ToDo(src);
        filteredTasks = new FilteredList<>(toDo.getTasks());
        this.storage = storage;
    }

    public ModelManager(Storage storage) {
        this(new ToDo(), new UserPrefs(), storage);
    }

    public ModelManager(ReadOnlyToDo initialData, UserPrefs userPrefs, Storage storage) {
        toDo = new ToDo(initialData);
        filteredTasks = new FilteredList<>(toDo.getTasks());
        this.storage = storage;
    }

    @Override
    public void resetData(ReadOnlyToDo newData) {
        toDo.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyToDo getToDo() {
        return toDo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ToDoChangedEvent(toDo));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDo.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDo.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
        int index = filteredTasks.indexOf(task);
        indicateTaskAdded(index, filteredTasks.get(index));
    }
    
    //@@author A0135812L
    @Override
    public synchronized ReadOnlyTask editTask(ReadOnlyTask task, HashMap<Field, Object> changes) throws TaskNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
        ReadOnlyTask editedTask = toDo.editTask(task, changes);
        indicateAddressBookChanged();
        return editedTask;
    }
    
    private void indicateTaskAdded(int i, ReadOnlyTask taskAdded) {
        raise(new AddTaskEvent(i, taskAdded));
    }
    
    private void indicateTaskEdited(int i, ReadOnlyTask taskEdited) {
        raise(new TaskEditedEvent(i, taskEdited));
    }
    //@@author

    @Override
    public synchronized void markTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDo.toggleTaskStatus(target);
        indicateAddressBookChanged();
        int index = filteredTasks.indexOf(target);
        indicateTaskEdited(index, filteredTasks.get(index));
    }
    
    //@@author A0126649W
    @Override
    public synchronized void saveToDo(String filePath) throws IOException {
        storage.saveToDo(toDo, filePath);
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void loadToDo(String filePath) throws IOException {
        Optional<ReadOnlyToDo> addressBookOptional;
        ReadOnlyToDo initialData;
        try {
            addressBookOptional = storage.readToDo(filePath);
            if(!addressBookOptional.isPresent()){
                logger.info("Data file not found.");
                throw new IOException();
            }else{
                toDo.resetData(addressBookOptional.get());
                storage.loadToDo(filePath);
                updateFilteredListToShowAll();
                indicateAddressBookChanged();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format.");
        }
    }
    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    //@@author A0135767U
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredListToShowCompleted(boolean done) {
    	updateFilteredTaskList(new PredicateExpression(new CompleteQualifier(done)));
    }

    @Override
    public void updateFilteredListToShowUpcoming() {
    	updateFilteredTaskList(new PredicateExpression(new TimeQualifier()));
    }

    @Override
    public void updateFilteredListToShowOverdue() {
    	updateFilteredTaskList(new PredicateExpression(new OverdueQualifier()));
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
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsSubstringIgnoreCase(task.getName().taskName, keyword)
                    		        || StringUtil.containsSubstringIgnoreCase(task.tagsString(), keyword) )
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class CompleteQualifier implements Qualifier {
        private boolean done;

        CompleteQualifier(boolean done) {
            this.done = done;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getCompleted() == done);
        }

        @Override
        public String toString() {
            return "complete=" + done;
        }
    }

    private class TimeQualifier implements Qualifier {
    	Time currentTime;

        TimeQualifier() {
        	try {
				currentTime = new Time(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
			} catch (IllegalValueException e) {
				e.printStackTrace();
			}
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	if (task.getTime().isPresent()) {
        		int result = task.getTime().get().compareTo(currentTime);

        		if (result >= 0) {
        			return true;
        		}
        	}

            return false;
        }

        @Override
        public String toString() {
            return "time=" + currentTime.toString();
        }
    }
    private class OverdueQualifier implements Qualifier {
    	TimeQualifier timeQualifier;
    	CompleteQualifier completeQualifier;

        OverdueQualifier() {
        	timeQualifier = new TimeQualifier();
        	completeQualifier = new CompleteQualifier(false);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	return ((!timeQualifier.run(task)) && completeQualifier.run(task));
        }

        @Override
        public String toString() {
            return "overdue= true";
        }
    }

}

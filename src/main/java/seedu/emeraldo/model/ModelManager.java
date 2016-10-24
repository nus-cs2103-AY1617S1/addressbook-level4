package seedu.emeraldo.model;

import javafx.collections.transformation.FilteredList;
import seedu.emeraldo.commons.core.ComponentManager;
import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.events.model.EmeraldoChangedEvent;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.commons.util.StringUtil;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.task.DateTime;
import seedu.emeraldo.model.task.Description;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniqueTaskList;
import seedu.emeraldo.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import java.util.Stack;

/**
 * Represents the in-memory model of the Emeraldo data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Emeraldo emeraldo;

    private final FilteredList<Task> filteredTasks;
    
    private final Stack<Emeraldo> savedStates;
    
    /**
     * Initializes a ModelManager with the given Emeraldo
     * Emeraldo and its variables should not be null
     */
    public ModelManager(Emeraldo src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with Emeraldo: " + src + " and user prefs " + userPrefs);

        emeraldo = new Emeraldo(src);

        filteredTasks = new FilteredList<>(emeraldo.getTasks());
        
        savedStates = new Stack<Emeraldo>();
        
        saveState();
    }
    
    //Saves the new state of emeraldo into the stack, after changes has been made
	private void saveState() {
		Emeraldo temp = new Emeraldo(emeraldo);
        savedStates.push(temp);
	}

    public ModelManager() {
        this(new Emeraldo(), new UserPrefs());
    }

    public ModelManager(ReadOnlyEmeraldo initialData, UserPrefs userPrefs) {
        emeraldo = new Emeraldo(initialData);

        filteredTasks = new FilteredList<>(emeraldo.getTasks());
        
        savedStates = new Stack<Emeraldo>();
        
        saveState();
    }
    
    public void undoChanges() throws EmptyStackException, UndoException{
    	
    	
    	if(savedStates.size() > 1){
    	    savedStates.pop();    	    
	        emeraldo.resetData(savedStates.peek());
    	    indicateEmeraldoChanged();
    	}
    	else if(savedStates.size() == 1){
    	    throw new UndoException();
    	}
    	else{
    	    System.out.println("savedState.size = " + savedStates.size());
    	}
    }
    
    public void clearEmeraldo(){
    	emeraldo.resetData(Emeraldo.getEmptyEmeraldo());
    	saveState();
    	indicateEmeraldoChanged();
    }
    
    @Override
    public void resetData(ReadOnlyEmeraldo newData) {
        emeraldo.resetData(newData);
        indicateEmeraldoChanged();
    }

    @Override
    public ReadOnlyEmeraldo getEmeraldo() {
        return emeraldo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateEmeraldoChanged() {
        raise(new EmeraldoChangedEvent(emeraldo));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        emeraldo.removeTask(target);
        saveState();
        indicateEmeraldoChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	emeraldo.addTask(task);
    	saveState();
        updateFilteredListToShowAll();
        indicateEmeraldoChanged();
    }

    @Override
    public synchronized void editTask(Task target, int index, Description description, DateTime dateTime) 
            throws TaskNotFoundException {
        try {
            emeraldo.editTask(target, index, description, dateTime);
            saveState();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        indicateEmeraldoChanged();
    }
    
    @Override 
    public synchronized void completedTask(Task target, int index)
    		throws TaskNotFoundException {
    	try {
    		emeraldo.completedTask(target, index);
    	} catch (IllegalValueException e) {
    		e.printStackTrace();
    	}
    	indicateEmeraldoChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

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
            updateFilteredTaskList(new PredicateExpression(new DescriptionQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredTaskList(String keyword){
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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

    /*
     * Compare tasks description with keywords
     */
    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionKeyWords;

        DescriptionQualifier(Set<String> descriptionKeyWords) {
            this.descriptionKeyWords = descriptionKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return descriptionKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().fullDescription, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "description=" + String.join(", ", descriptionKeyWords);
        }
    }
    
    /*
     *  Compare tasks tags with keywords
     */
    private class TagQualifier implements Qualifier {
        private String tagKeyWord;

        TagQualifier(String keyWord) {
            this.tagKeyWord = keyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean tagMatcher = false;
            Tag tag;
            Iterator<Tag> tagIterator = task.getTags().iterator();
            while(tagIterator.hasNext()){
                tag = tagIterator.next();
                tagMatcher = tagMatcher || run(tag);
            }
            return tagMatcher;
        }
        
        private boolean run(Tag tag){
            return tag.tagName.equalsIgnoreCase(tagKeyWord);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWord);
        }
    }
}

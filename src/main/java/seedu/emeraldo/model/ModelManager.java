package seedu.emeraldo.model;

import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.emeraldo.commons.core.ComponentManager;
import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.events.model.EmeraldoChangedEvent;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.commons.util.StringUtil;
import seedu.emeraldo.logic.commands.ListCommand.TimePeriod;
import seedu.emeraldo.logic.commands.ListCommand.Completed;
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
    
    //@@author A0139342H
    @Override
    public synchronized void editTask(Task target, Description description, DateTime dateTime) 
            throws TaskNotFoundException {
        try {
            emeraldo.editTask(target, description, dateTime);
            saveState();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        indicateEmeraldoChanged();
    }
    
    //@@author A0142290N
    @Override 
    public synchronized void completedTask(Task target)
    		throws TaskNotFoundException {
    	try {
    		emeraldo.completedTask(target);
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
    public void updateFilteredListToShowUncompleted() {
        updateFilteredTaskList(new PredicateExpression(new ListQualifier()));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new DescriptionQualifier(keywords)));
    }
    
    //@@author A0139749L
    @Override
    public void updateFilteredTaskList(String keyword){
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }
    
    public void updateFilteredTaskList(Completed keyword){
    	updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(keyword)));
    }
    
    @Override
    public void updateFilteredTaskList(TimePeriod keyword){
        updateFilteredTaskList(new PredicateExpression(new DateTimeQualifier(keyword)));
    }

    //@@author
    
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
    
    //@@author A0139749L
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
            return "tag= " + tagKeyWord;
        }
    }
    
    /*
     *  Compare tasks dateTime with the period specified by the keyword
     */
    private class DateTimeQualifier implements Qualifier {
        private TimePeriod DateTimeKeyWord;

        DateTimeQualifier(TimePeriod keyWord) {
            this.DateTimeKeyWord = keyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	DateTime dateTime = task.getDateTime();
        	if(dateTime.valueDate == null)	//For tasks without date specified
        		return false;
        	else if(DateTimeKeyWord == TimePeriod.today)
        		return dateTime.valueDate.equals(LocalDate.now());
        	else if(DateTimeKeyWord == TimePeriod.tomorrow)
        		return dateTime.valueDate.equals(LocalDate.now().plusDays(1));
        	else
        		return false;
        }

        @Override
        public String toString() {
            return "DateTime= " + DateTimeKeyWord;
        }
    }
    
    //@@author A0142290N
    /*
     *  Compare tasks tags with the keyword "completed"
     */
    private class ListQualifier implements Qualifier {
    	private String completedTag = "Completed";
    	
        ListQualifier() {}

        @Override
        public boolean run(ReadOnlyTask task) {
            boolean completedFinder = false;
            Tag tag;
            Iterator<Tag> tagIterator = task.getTags().iterator();
            while(tagIterator.hasNext()){
                tag = tagIterator.next();
                completedFinder = completedFinder || run(tag);
            }
            return !completedFinder;
        }
        
        private boolean run(Tag tag){
            return tag.tagName.equalsIgnoreCase(completedTag);
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", completedTag);
        }
    }

    /**
     * Qualifies the tasks that are completed
     *
     */
    private class CompletedQualifier implements Qualifier {
        private Completed CompletedKeyword;

        CompletedQualifier(Completed keyword) {
            this.CompletedKeyword = keyword;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	DateTime dateTime = task.getDateTime();
        	if(dateTime.valueFormatted.startsWith("Completed")) 
        		return true;
        	else
        		return false;
        }

        @Override
        public String toString() {
            return "Completed= " + CompletedKeyword;
        }
    }
}

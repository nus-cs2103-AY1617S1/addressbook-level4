package seedu.emeraldo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import seedu.emeraldo.commons.exceptions.QualifierLogicalOperatorMismatch;
import seedu.emeraldo.commons.exceptions.TagExistException;
import seedu.emeraldo.commons.exceptions.TagListEmptyException;
import seedu.emeraldo.commons.exceptions.TagNotFoundException;
import seedu.emeraldo.commons.exceptions.TaskAlreadyCompletedException;
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

/**
 * Represents the in-memory model of the Emeraldo data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	public static final String MESSAGE_QUALIFIER_ERROR = "Mismatch size! logicalOperatorList size: %d, qualifierList size: %d";

    private final Emeraldo emeraldo;

    private final FilteredList<Task> filteredTasks;
    
    private final Stack<Emeraldo> savedStates;
    
    private Object keywords = null;
    
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
    	else{
    	    throw new UndoException();
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
    
    //@@author A0139196U
    public synchronized void addTag(Task target, Tag tag) throws TagExistException {
        try {
            emeraldo.taskAddTag(target, tag);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        indicateEmeraldoChanged();
    }
    
    public synchronized void deleteTag(Task target, Tag tag) throws TagNotFoundException {
        try {
            emeraldo.taskDeleteTag(target, tag);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        indicateEmeraldoChanged();
    }
    
    public synchronized void clearTag(Task target) throws TagListEmptyException {
        try {
            emeraldo.taskClearTag(target);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        indicateEmeraldoChanged();
    }
    
    //@@author A0142290N
    @Override 
    public synchronized void completedTask(Task target)
    		throws TaskAlreadyCompletedException {
    	try {
    		emeraldo.completedTask(target);
    		saveState();
    	} catch (IllegalValueException e) {
    		e.printStackTrace();
    	}
    	updateFilteredTaskListWhenCompletedIsUsed();
    	indicateEmeraldoChanged();
    }
    
    private void updateFilteredTaskListWithCompleted(){
    	if(keywords instanceof Set<?>)
    		updateFilteredTaskListWithCompleted((Set<String>)keywords);
    	else if(keywords instanceof String)
    		updateFilteredTaskListWithCompleted((String)keywords);
    	else if(keywords instanceof TimePeriod)
    		updateFilteredTaskListWithCompleted((TimePeriod)keywords);
    }
    
    //@@author A0139749L
    private void updateFilteredTaskListWhenCompletedIsUsed(){
    	if(keywords == null || keywords.equals("show all")){
    		updateFilteredListToShowAll();
    	}else if(keywords.equals("show uncompleted")){
    		updateFilteredTaskListWithCompletedInPast10Seconds();
    	}else if(keywords instanceof Set<?>){
    		updateFilteredTaskListWithCompletedInPast10Seconds((Set<String>)keywords);
    	}else if(keywords instanceof String){
    		updateFilteredTaskListWithCompletedInPast10Seconds((String)keywords);
    	}else if(keywords instanceof TimePeriod){
    		updateFilteredTaskListWithCompletedInPast10Seconds((TimePeriod)keywords);
    	}
    }
    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public void updateFilteredListToShowAll() {
    	this.keywords = "show all";
    	filteredTasks.setPredicate(null);
    }
    
    //@@author A0142290N
    public void updateFilteredTaskList(Completed keyword){
    	updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(keyword)));
    }

    //@@author A0139749L
    //=========== Filtered Task List Accessors (Without completed tag) ========================================
    @Override
    public void updateFilteredListToShowUncompleted() {
    	this.keywords = "show uncompleted";
        updateFilteredTaskList(new PredicateExpression(new UncompletedQualifier()));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
    	this.keywords = keywords;
        PredicateExpression predicateExpression = new PredicateExpression(new DescriptionQualifier(keywords), new UncompletedQualifier());
        predicateExpression.setLogicalOperatorList("and");
    	updateFilteredTaskList(predicateExpression);
    }
    
    @Override
    public void updateFilteredTaskList(String keyword){
    	this.keywords = keyword;
        PredicateExpression predicateExpression = new PredicateExpression(new TagQualifier(keyword), new UncompletedQualifier());
        predicateExpression.setLogicalOperatorList("and");
    	updateFilteredTaskList(predicateExpression);
    }
    
    @Override
    public void updateFilteredTaskList(TimePeriod keyword){
    	this.keywords = keyword;
        PredicateExpression predicateExpression = new PredicateExpression(new DateTimeQualifier(keyword), new UncompletedQualifier());
        predicateExpression.setLogicalOperatorList("and");
    	updateFilteredTaskList(predicateExpression);
    }
    
    //=========== Filtered Task List Accessors (With completed tag) ========================================
    @Override
    public void updateFilteredTaskListWithCompleted(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new DescriptionQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredTaskListWithCompleted(String keyword){
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }
    
    @Override
    public void updateFilteredTaskListWithCompleted(TimePeriod keyword){
        updateFilteredTaskList(new PredicateExpression(new DateTimeQualifier(keyword)));
    }
    
    //=========== Filtered Task List Accessors (With completed tag) ========================================
    
    public void updateFilteredTaskListWithCompletedInPast10Seconds(Set<String> keywords){
        PredicateExpression predicateExpression
        	= new PredicateExpression(new DescriptionQualifier(keywords),new Past10SecondsQualifier(),new UncompletedQualifier());
        updateFilteredTaskListWithCompletedInPast10Seconds(predicateExpression);
    }
    
    public void updateFilteredTaskListWithCompletedInPast10Seconds(String keyword){
        PredicateExpression predicateExpression
    		= new PredicateExpression(new TagQualifier(keyword),new Past10SecondsQualifier(),new UncompletedQualifier());
        updateFilteredTaskListWithCompletedInPast10Seconds(predicateExpression);
    }
    
    public void updateFilteredTaskListWithCompletedInPast10Seconds(TimePeriod keyword){
        PredicateExpression predicateExpression
    		= new PredicateExpression(new DateTimeQualifier(keyword),new Past10SecondsQualifier(),new UncompletedQualifier());
        updateFilteredTaskListWithCompletedInPast10Seconds(predicateExpression);
    }
    
    public void updateFilteredTaskListWithCompletedInPast10Seconds(){
        PredicateExpression predicateExpression = new PredicateExpression(new Past10SecondsQualifier(), new UncompletedQualifier());
        predicateExpression.setLogicalOperatorList("or");
    	updateFilteredTaskList(predicateExpression);
    }
    
    private void updateFilteredTaskListWithCompletedInPast10Seconds(Expression expression) {
        filteredTasks.setPredicate(expression::satisfiesPast10Seconds);
    }
    //@@author
    
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfiesPast10Seconds(ReadOnlyTask task);
        String toString();
    }

    //@@author A0139749L
    private class PredicateExpression implements Expression {

        private ArrayList<Qualifier> qualifierList = new ArrayList<Qualifier>();
        private ArrayList<String> logicalOperatorList = new ArrayList<String>();
        
        PredicateExpression(Qualifier... qualifier) {
        	for(Qualifier q: qualifier)
        		qualifierList.add(q);
        }
        
        public void setLogicalOperatorList(String... logicalOperator){
        	for(String s: logicalOperator)
        		logicalOperatorList.add(s);
        }

        /*
         * Compares the boolean result from each qualifier using the logical operator in sequence
         */
        @Override
        public boolean satisfies(ReadOnlyTask task) {
        	try{
        		if(!isValidQualifierAndOperator())
        			throw new QualifierLogicalOperatorMismatch(String.format(MESSAGE_QUALIFIER_ERROR
        				, logicalOperatorList.size(), qualifierList.size()));
        	} catch(QualifierLogicalOperatorMismatch e) {
        		e.printStackTrace();
        	}
        	
        	int i = 0;
        	boolean result = qualifierList.get(0).run(task);
        	
        	for(i = 0; i < logicalOperatorList.size(); i++){
        		result = resultOfOperatorOnQualifiers(result, qualifierList.get(i+1), logicalOperatorList.get(i), task);
        	}
        	return result;
        }
        
        private boolean isValidQualifierAndOperator(){
        	return logicalOperatorList.size() == qualifierList.size()-1;
        }
        
        private boolean resultOfOperatorOnQualifiers(boolean prevResult, Qualifier qualifier, String operator, ReadOnlyTask task){
        	if(operator.equalsIgnoreCase("and")){
        		return prevResult && qualifier.run(task); 
        	}else if(operator.equalsIgnoreCase("or")){
        		return prevResult || qualifier.run(task); 
        	}else{
        		return false;
        	}
        }
        
        @Override
        public boolean satisfiesPast10Seconds(ReadOnlyTask task){
        	return (qualifierList.get(0).run(task) && qualifierList.get(1).run(task))
        			|| (qualifierList.get(0).run(task) && qualifierList.get(2).run(task));
        }
        
        @Override
        public String toString() {
        	StringBuilder qualifierString = new StringBuilder();
        	
        	for(Qualifier q: qualifierList)
        		qualifierString.append(q.toString());
            return qualifierString.toString();
        }
    }
    //@@author

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
        String tagKeyWord;

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
        TimePeriod dateTimeKeyWord;

        DateTimeQualifier(TimePeriod keyWord) {
            this.dateTimeKeyWord = keyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	DateTime dateTime = task.getDateTime();
        	
        	if(dateTime.valueDate == null){				//For tasks without date specified
        		return false;
        	}else if(dateTime.valueDateEnd == null){	//For tasks without end date specified
        		return isWithinTimePeriodSpecified(dateTimeKeyWord,dateTime,false);
        	}else{										//For tasks with end date specified
        		return isWithinTimePeriodSpecified(dateTimeKeyWord,dateTime,true);
        	}
        }
        
        private boolean isWithinTimePeriodSpecified(TimePeriod dateTimeKeyWord,DateTime dateTime, boolean hasEndDate){
        	boolean result;
        	LocalDate startDate = dateTime.valueDate;
        	LocalDate endDate;
        	LocalDate dateForConsideration;
        	
        	if(!hasEndDate){
        		switch(dateTimeKeyWord){
					case today:
						result = startDate.equals(LocalDate.now());
						break;
					case tomorrow:
						result = startDate.equals(LocalDate.now().plusDays(1));
						break;
					case thisWeek:
						result = startDate.isAfter(dateOfThisWeekSunday().minusWeeks(1))
							&& startDate.isBefore(dateOfThisWeekSunday().plusDays(1));
						break;
					case nextWeek:
						result = startDate.isAfter(dateOfThisWeekSunday())
							&& startDate.isBefore(dateOfThisWeekSunday().plusDays(8));
						break;
					case thisMonth:
						result = startDate.getMonthValue() == LocalDate.now().getMonthValue();
						break;
					case nextMonth:
						result = startDate.getMonthValue() == LocalDate.now().plusMonths(1).getMonthValue();
						break;
					default:
						result = false;
        		}
        		
        	} else {
        		endDate = dateTime.valueDateEnd;
        		switch(dateTimeKeyWord){
					case today:
						dateForConsideration = LocalDate.now();
						result = startDate.equals(dateForConsideration) || endDate.equals(dateForConsideration)
							|| (startDate.isBefore(dateForConsideration) && endDate.isAfter(dateForConsideration));
						break;
					case tomorrow:
						dateForConsideration = LocalDate.now().plusDays(1);
						result = startDate.equals(dateForConsideration) || endDate.equals(dateForConsideration)
							|| (startDate.isBefore(dateForConsideration) && endDate.isAfter(dateForConsideration));
						break;
					case thisWeek:
						result = startDate.isBefore(dateOfThisWeekSunday().plusDays(1))
							&& endDate.isAfter(dateOfThisWeekSunday().minusWeeks(1));
						break;
					case nextWeek:
						result = startDate.isBefore(dateOfThisWeekSunday().plusDays(8))
							&& endDate.isAfter(dateOfThisWeekSunday());
						break;
					case thisMonth:
						result = startDate.getMonthValue() <= LocalDate.now().getMonthValue()
							&& endDate.getMonthValue() >= LocalDate.now().getMonthValue();
						break;
					case nextMonth:
						result = startDate.getMonthValue() <= LocalDate.now().plusMonths(1).getMonthValue()
							&& endDate.getMonthValue() >= LocalDate.now().plusMonths(1).getMonthValue();
						break;
					default:
						result = false;
        		}
        	}
        	
        	return result;
        }
        
        //Returns a LocalDate object with the date of this week's Sunday
        private LocalDate dateOfThisWeekSunday(){
        	int noOfDaysFromTodayToSunday = 7 - LocalDate.now().getDayOfWeek().getValue();
        	return LocalDate.now().plusDays(noOfDaysFromTodayToSunday);
        }

        @Override
        public String toString() {
            return "DateTime= " + dateTimeKeyWord;
        }
    }
    
    /*
     *  Compare tasks dateTime with the period specified by the keyword
     */
    private class Past10SecondsQualifier implements Qualifier {
        LocalDate completedValueDate;
        LocalTime completedValueTime;

        Past10SecondsQualifier() {}

        @Override
        public boolean run(ReadOnlyTask task) {
        	this.completedValueDate = task.getDateTime().completedValueDate;
        	this.completedValueTime = task.getDateTime().completedValueTime;
        	if(this.completedValueDate != null && this.completedValueTime != null && isCompletedInLast10Seconds())
        		return true;
        	else
        		return false;
        }
        
        //Returns true if task is completed within the last 10 seconds
        private boolean isCompletedInLast10Seconds(){
    		return completedValueDate.equals(LocalDate.now())
        			&& completedValueTime.isAfter(LocalTime.now().minusSeconds(10));
        }

        @Override
        public String toString() {
            return "Completed Date and Time= " + completedValueDate.toString()
            	+ " " + completedValueTime.toString();
        }
    }
    
    //@@author A0142290N
    /*
     *  Compare tasks that are marked as completed, allows only uncompleted tasks to be shown.
     */
    private class UncompletedQualifier implements Qualifier {
        private Completed CompletedKeyword;

        UncompletedQualifier() {  }	

        @Override
        public boolean run(ReadOnlyTask task) {
        	DateTime dateTime = task.getDateTime();
        	if(dateTime.valueFormatted.startsWith("Completed")) 
        		return false;
        	else
        		return true;
        }

        @Override
        public String toString() {
            return "List= " + CompletedKeyword;
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

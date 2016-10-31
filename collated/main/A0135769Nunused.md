# A0135769N // these code was replaced by natty time parser
package seedu.tasklist.model.task;

import java.util.Calendar;

public class DateTime {
    protected EndDate endDate;
    protected StartDate startDate;
    protected StartTime startTime;
    protected EndTime endTime;
    protected Calendar calBegin;
    protected Calendar calEnd;
    private int timeHrs = 0;
    private int timeMins = 0;
    
    public DateTime(StartTime startTime, StartDate startDate, EndTime endTime, EndDate endDate){
    	this.startTime = startTime;
    	this.startDate = startDate;
    	this.endTime =  endTime;
    	this.endDate = endDate;
    	calBegin = startDate.cal;
    	calEnd = endDate.cal2;
    	
    } 
    
    public void check24hrFormat(Time time){
    	String checkTime = time.value;
    	String[] timeComponent = new String[2];
    	if(checkTime.length()==4){
    		timeComponent = checkTime.split(":");
    	    timeHrs = Integer.valueOf(timeComponent[0]);
    	    timeMins = Integer.valueOf(timeComponent[1]);
    	}
    	else{
    		if(checkTime.matches("(pm)"))
               timeHrs = Integer.valueOf(checkTime.charAt(0)) + 12;
    		else 
    			timeHrs = Integer.valueOf(checkTime.charAt(0));
    	}
    }
    
    public void setCalBegin(){
    	check24hrFormat(startTime);
    	calBegin = startDate.cal;
    	calBegin.set(Calendar.HOUR_OF_DAY, timeHrs);
    	calBegin.set(Calendar.MINUTE,timeMins);
    }
    
    public void setCalEnd(){
    	check24hrFormat(endTime);
    	calEnd = endDate.cal2;
    	calEnd.set(Calendar.HOUR_OF_DAY, timeHrs);
    	calEnd.set(Calendar.MINUTE,timeMins);
    }
    
    public Calendar getBeginCal(){
       return calBegin;
    }
    
    public Calendar getEndCal(){
    	return calEnd;
    }
    
    public String beginCalString(){
    	return calBegin.toString();
    }
    
    public String endCalString(){
    	return calEnd.toString();
    }
}
```

package seedu.tasklist.model.task;

import java.util.Calendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.tasklist.commons.exceptions.IllegalValueException;

public class EndDate {

	    public static final String MESSAGE_ENDDATE_CONSTRAINTS = "Please enter a valid date in this format: dd/mm/yyyy!";
	    public static final String ENDDATE_VALIDATION_REGEX = "(([0-3][0-9]{1}+)(/|-)([0-1][0-9]{1}+)(/|-)([1-2][0-9][0-9][0-9]{1}+))";

	    public final Calendar cal2;
	    public String value = "";
	    /**
	     * Validates given phone number.
	     *
	     * @throws IllegalValueException if given phone string is invalid.
	     */
	    public EndDate(String endDate) throws IllegalValueException {
	     //  assert phone != null;
	    	cal2 = Calendar.getInstance();
	    	if(!endDate.equals("")){
	        if (endDate!=null&&!isValidEndDate(endDate)) {
	            throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
	        }
	        

	    	String[] dateParameters = {"0", "0", "0"};
	    	
	    	if(endDate!=null&&!endDate.equals("")){
	    	dateParameters = endDate.split("(/|-)");
	    	cal2.set(Integer.valueOf(dateParameters[2]), (Integer.valueOf(dateParameters[1])-1),Integer.valueOf(dateParameters[0]));	    	
	        value = endDate;
	    	}
	    	}
	    	else{
	    		value = "";
	    	}
	    }

	    /**
	     * Returns true if a given string is a valid person phone number.
	     */
	    public static boolean isValidEndDate(String test) throws IllegalValueException{
	    	if(test==null){
	    		return true;
	    	}
	    	
	    	//checks whether the date itself is valid or not
	    	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CHINA);
	    	    dateFormat.setLenient(false);
	    	    try {
	    	      dateFormat.parse(test.trim());
	    	    } catch (ParseException pe) {
	    	      return false;
	    	    }
	    	    
	    	    return test.matches(ENDDATE_VALIDATION_REGEX); //checks for date format usign regex
	
	    }

	    @Override
	    public String toString() {
	        return value;
	    }

	    @Override
	    public boolean equals(Object other) {
	        return  (other instanceof EndDate // instanceof handles nulls
	                && this.cal2.equals(((EndDate) other).cal2)); // state check
	    }

	    @Override
	    public int hashCode() {
	        return cal2.hashCode();
	    }
}

package seedu.tasklist.model.task;

import java.util.Calendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.tasklist.commons.exceptions.IllegalValueException;

public class StartDate {

	    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Please enter a valid date in this format: dd/mm/yyyy!";
	    public static final String STARTDATE_VALIDATION_REGEX = "(([0-3][0-9]{1}+)(/|-)([0-1][0-9]{1}+)(/|-)([1-2][0-9][0-9][0-9]{1}+))";

	    public final Calendar cal;
	    public String value = "na";

	    /**
	     * Validates given phone number.
	     *
	     * @throws IllegalValueException if given phone string is invalid.
	     */
	    public StartDate(String startDate) throws IllegalValueException{
	     //  assert phone != null;
	    	cal = Calendar.getInstance();
	    	if(!startDate.equals("")){
	        if (startDate!=null&&!isValidStartDate(startDate)) {
	            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
	        }
	        
            String[] dateParameters = {"0", "0", "0"};
	    	
	    	if(startDate!=null&&!startDate.equals("")){
	    	dateParameters = startDate.split("(/|-)");
	    	cal.set(Integer.valueOf(dateParameters[2]), (Integer.valueOf(dateParameters[1])-1),Integer.valueOf(dateParameters[0]));
	    	value = startDate;    	
	    	}
	    	}
	    	else{
	    		value = "";
	    	}
	    }

	    /**
	     * Returns true if a given string is a valid person phone number.
	     */
	    public static boolean isValidStartDate(String test) throws IllegalValueException{
	    	if(test==null){
	    		return true;
	    	}
	    	
	    	//checks whether the date itself is valid or not
	    	 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CHINA);
	    	    dateFormat.setLenient(false);
	    	    try {
	    	      dateFormat.parse(test.trim());
	    	    } catch (ParseException pe) {
	    	      return false;
	    	    }
	    	    
	    	    return test.matches(STARTDATE_VALIDATION_REGEX); //checks for date format usign regex
	    }

	    @Override
	    public String toString() {
	        return value;
	    }

	    @Override
	    public boolean equals(Object other) {
	        return  (other instanceof StartDate // instanceof handles nulls
	                && this.cal.equals(((StartDate) other).cal)); // state check
	    }

	    @Override
	    public int hashCode() {
	        return cal.hashCode();
	    }
}

package seedu.tasklist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tasklist.commons.exceptions.DuplicateDataException;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {
	
	final HashSet<Calendar> calSet = new HashSet<Calendar>();
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicatePersonException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        try{
        checkClashingTime(toAdd);
        }  catch(IllegalValueException expObj){
        	System.out.println(expObj.getMessage());
        }
        internalList.add(toAdd);
    }
    
    /**
     * Adds a person to the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean setComplete(ReadOnlyTask toComplete) throws PersonNotFoundException {
        assert toComplete != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toComplete.getUniqueID()){
        		i.markAsComplete();
        		return true;
        	}
        }
        throw new PersonNotFoundException();
    }
    

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toRemove.getUniqueID()){
        		internalList.remove(i);
        		return true;
        	}
        }
        throw new PersonNotFoundException();
    }
    
    public boolean checkClashingTime(Task toAdd) throws IllegalValueException{
    	boolean added = false;
    	for(Task cal: internalList){
    		calSet.add(cal.getDateTime().getBeginCal());
    	}
    	if(toAdd!=null){
    		 added = calSet.add(toAdd.getDateTime().getBeginCal());
    	}
			if (!added) {
			    IllegalValueException ive = new IllegalValueException("Time and Dates are clashing! Please reschedule.");
			    throw ive;
			}
			return true;
		}

}
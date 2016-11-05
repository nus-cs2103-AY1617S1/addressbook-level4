package seedu.menion.model.activity;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.CollectionUtil;
import seedu.menion.commons.util.DateChecker;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
//@@author A0139164A
public class Activity implements ReadOnlyActivity {

    // Types of Activity
    public static final String FLOATING_TASK_TYPE = "floating";
    public static final String TASK_TYPE = "task";
    public static final String EVENT_TYPE = "event";

    public static final Integer FLOATING_TASK_LENGTH = 3; // ActivityType,
                                                          // ActivityName, Note
    public static final Integer TASK_LENGTH = 5; // ActivityType, Note,
                                                 // ActivityName StartDate,
                                                 // StartTime
    public static final Integer EVENT_LENGTH = 7; // ActivityType, Note,
                                                  // ActivityName, StartDate,
                                                  // StartTime, EndDate, EndTime

    // Indexes of the parameters to retrieve from the constructor.
    public static final Integer INDEX_ACTIVITY_TYPE = 0;
    public static final Integer INDEX_ACTIVITY_NAME = 1;
    public static final Integer INDEX_ACTIVITY_NOTE = 2;
    public static final Integer INDEX_ACTIVITY_STARTDATE = 3;
    public static final Integer INDEX_ACTIVITY_STARTTIME = 4;
    public static final Integer INDEX_ACTIVITY_ENDDATE = 5;
    public static final Integer INDEX_ACTIVITY_ENDTIME = 6;

    private ActivityName name;
    private ActivityDate startDate;
    private ActivityTime startTime;
    private ActivityDate endDate;
    private ActivityTime endTime;
    private Note note;
    private String activityType;
    private Completed status;
    private Boolean emailSent;
    private Boolean activityTimePassed;
    private Boolean eventOngoing;
    
    // Every Activity Object will have an array list of it's details for ease of
    // accessibility
    private ArrayList<String> activityDetails;

    /**
     * For floatingTask
     * Every field must be present and not null.
     */
    public Activity(String type, ActivityName name, Note note, Completed status) {
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.status = status;
        setActivityDetails();
    }
    
    /**
     * For Task
     * Every field must be present and not null.
     * @param activityTimePassed TODO
     * @param emailSent TODO
     */
    public Activity(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, Completed status, Boolean activityTimePassed, Boolean emailSent) {
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.status = status;
        if (activityTimePassed == null){
        	this.activityTimePassed = false;
        }
        else {
        	this.activityTimePassed = activityTimePassed;
        }
        if (emailSent == null){
        	this.emailSent = false;
        }
        else {
        	this.emailSent = emailSent;
        }
        
        setActivityDetails();
    }
    
    /**
     * For Event
     * Every field must be present and not null.
     * @param activityTimePassed TODO
     * @param eventOngoing TODO
     */
    public Activity(String type, ActivityName name, Note note, ActivityDate startDate, 
            ActivityTime startTime, ActivityDate endDate, ActivityTime endTime, Completed status, Boolean activityTimePassed, Boolean eventOngoing) {
        
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = status;
        if (activityTimePassed == null){
        	this.activityTimePassed = false;
        }
        else {
        	this.activityTimePassed = activityTimePassed;
        }
        if (eventOngoing == null){
        	this.eventOngoing = false;
        }
        else {
        	this.eventOngoing = eventOngoing;
        }
        
        setActivityDetails();
    }
    

    /**
     * Copy constructor.
     * 
     */
    public Activity(ReadOnlyActivity source) {
        
        if (source.getActivityType().equals(FLOATING_TASK_TYPE)) {
            activityType = source.getActivityType();
            name = source.getActivityName();
            note = source.getNote();
            status = source.getActivityStatus();
        } else if (source.getActivityType().equals(TASK_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            status = source.getActivityStatus();
            emailSent = source.isEmailSent();
            activityTimePassed = source.isTimePassed();
        } else if (source.getActivityType().equals(EVENT_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            endDate = source.getActivityEndDate();
            endTime = source.getActivityEndTime();
            status = source.getActivityStatus();
            activityTimePassed = source.isTimePassed();
            eventOngoing = source.isEventOngoing();

        }
        this.status = source.getActivityStatus();
        this.activityDetails = source.getActivityDetails();
    }
    
    // Creates a unique ArrayList of details for each activity.
    @Override
    public void setActivityDetails() {
        if (activityType.equals(FLOATING_TASK_TYPE)) {
            activityDetails = new ArrayList<String>(FLOATING_TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(status.toString());
        } else if (activityType.equals(TASK_TYPE)) {
            activityDetails = new ArrayList<String>(TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(status.toString());
        } else if (activityType.equals(EVENT_TYPE)) {
            activityDetails = new ArrayList<String>(EVENT_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(endDate.toString());
            activityDetails.add(endTime.toString());
            activityDetails.add(status.toString());
        }
    }

    /**
     * @throws IllegalValueException 
     * List of methods to set Activity's param : Type, Name, Note, startDate, startTime, completion status, emailSent status, timePassed status.
     */

    @Override
    public void setActivityType(String newType) throws IllegalValueException {
        if (!newType.equals(FLOATING_TASK_TYPE) && !newType.equals(TASK_TYPE) && !newType.equals(EVENT_TYPE)) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_TYPE);
        }
        else {
            this.activityType = newType;
        }
    }
    
    @Override
    public void setActivityName(String newName) throws IllegalValueException {
        assert (newName != null);
            this.name = new ActivityName(newName);
    }

    @Override
    public void setActivityNote(String newNote) throws IllegalValueException {
        assert (newNote != null);
        this.note = new Note(newNote);

    }
    
    // Only can be called by Task & Events
    @Override
    public void setActivityStartDateTime(String newDate, String newTime) throws IllegalValueException {

        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        if (isEvent) {
            DateChecker check = new DateChecker();
            check.validEventDate(newDateObject, newTimeObject, this.endDate, this.endTime);
        } else {
            this.startDate = newDateObject;
            this.startTime = newTimeObject;
        }
    }
    
    @Override
    public void setActivityEndDateTime(String newDate, String newTime) throws IllegalValueException {
        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        DateChecker check = new DateChecker();
        assert (isEvent);
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        check.validEventDate(this.startDate, this.startTime, newDateObject, newTimeObject);
        this.endDate = newDateObject;
        this.endTime = newTimeObject;
    }

    @Override
    public void setCompleted() {
        this.status = new Completed(true);
    }

    @Override
    public void setUncompleted() {
        this.status = new Completed(false);
    }
    
    public void setTimePassed(Boolean timePassed){
        this.activityTimePassed = timePassed;
    }
    
    public void setEventOngoing(Boolean eventOngoing){
        this.eventOngoing = eventOngoing;
    }
    
    public void setEmailSent(Boolean sentStatus){
        this.emailSent = sentStatus;
    }
    
    /**
     * Getter methods for Activity, returns all possible params of activity.
     */
    @Override
    public Completed getActivityStatus() {
        return this.status;
    }
    @Override
    public ActivityName getActivityName() {
        return this.name;
    }
    
    @Override
    public Note getNote() {
        return this.note;
    }

    @Override
    public ActivityDate getActivityStartDate() {
        return this.startDate;
    }

    @Override
    public ActivityTime getActivityStartTime() {
        return this.startTime;
    }

    @Override
    public ActivityDate getActivityEndDate() {
        return this.endDate;
    }

    @Override
    public ActivityTime getActivityEndTime() {
        return this.endTime;
    }

    @Override
    public String getActivityType() {
        return this.activityType;
    }

    /**
     * returns the arrayList consisting of an activity's details.
     */
    @Override
    public ArrayList<String> getActivityDetails() {
        return activityDetails;
    }

    public Boolean isEmailSent(){
    	return this.emailSent;
    }
    
    public Boolean isTimePassed(){
    	return this.activityTimePassed;
    }
    
    public Boolean isEventOngoing(){
    	return this.eventOngoing;
    }

    @Override
    public Activity get() {
        return this;
    }

    @Override
    public String toString() {
        switch(this.activityType){
        case FLOATING_TASK_TYPE:
            return getFloatingTaskAsText();
        case TASK_TYPE:
            return getTaskAsText();
        case EVENT_TYPE:
            return getEventAsText();
        }
        return null;
    }
    
   //@@author A0139277U
    @Override
    public boolean equals(Object o){
    	
    	if (this.getActivityType().equals(Activity.EVENT_TYPE) && o!= null &&
    			checkActivityType((ReadOnlyActivity) o).equals(this.getActivityType())){
    		
    		return o == this ||
    				(o instanceof ReadOnlyActivity &&
    						this.isEventSameStateAs((ReadOnlyActivity)o));
    		
    	}
    	
    	else if (this.getActivityType().equals(Activity.FLOATING_TASK_TYPE) && o!= null &&
    			checkActivityType((ReadOnlyActivity) o ).equals(this.getActivityType())){
    		
    		return o == this ||
    				(o instanceof ReadOnlyActivity && 
    						this.isFloatingTaskSameStateAs((ReadOnlyActivity)o));
    				
    	}
    	
    	else if (this.getActivityType().equals(Activity.TASK_TYPE) && o!= null &&
    			checkActivityType((ReadOnlyActivity) o ).equals(this.getActivityType())){
    		
    		return o == this ||
    				(o instanceof ReadOnlyActivity &&
    						this.isTaskSameStateAs((ReadOnlyActivity)o));
    	}
    	
    	else {
    		return false;
    	}
    	
    }
    
    private String checkActivityType(ReadOnlyActivity activityToCheck){
    	
    	return activityToCheck.getActivityType();
    	
    }
    
}
